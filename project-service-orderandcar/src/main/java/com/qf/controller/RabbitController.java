package com.qf.controller;

import com.google.gson.Gson;
import com.qf.constant.Constant;
import com.qf.dto.OrderDTO;
import com.qf.dto.Pt;
import com.qf.entity.TOrder;
import com.qf.entity.TOrderdetail;
import com.qf.entity.TProduct;
import com.qf.mapper.TProductMapper;
import com.qf.service.OrdertService;
import com.qf.service.ProductService;
import com.qf.service.RedisService;
import com.qf.util.JsonUtil;
import com.qf.util.StringAppend;
import com.qf.vo.CartItem;
import com.rabbitmq.client.Channel;
import org.bouncycastle.jcajce.provider.symmetric.IDEA;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-13 14:49
 **/
@Component
public class RabbitController {


    @Autowired
    private ProductService productService;

    @Autowired
    private OrdertService orderService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    private TProductMapper productMapper;
/*
* 监听队列,
* 执行创建订单操作
* */
        @RabbitListener(queues = "creatorder")
        public void creatorder(OrderDTO orderDTO,Message message,Channel channel) throws IOException {


            try{



            //创建一天订单信息并存到数据库  数据库有
            List<Pt> pts = orderDTO.getPts();
            //通过pid去获取product的数据,如果没有就到数据库中去找  然后将数据相乘 ,返回和
            Long sum=Long.valueOf(0);
            for (Pt pt : pts) {
                long count =(long) pt.getCount();

                //设置一下,先去redis找,找不到再去数据去找  还没有完成
                TProduct tProduct = productService.getone(pt.getPid());
                Long price = tProduct.getPrice();
                //计算总金额
                sum =sum+ price*count;
            }
            TOrder torder=new TOrder();
            torder.setOid(orderDTO.getOrderno());
            torder.setName(orderDTO.getName());
            torder.setPhone(orderDTO.getPhone());
            torder.setAddress(orderDTO.getAddress());
            torder.setCreatdata(new Date());
            torder.setUid(orderDTO.getId());
            torder.setTotalprice(sum);
            torder.setStatue(0);

            int getone = orderService.getone(torder);
              //如果int=1,即表示添加数据库完成

                if(getone!=1){

                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
                }
                 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }catch (Exception e){
                System.out.println(e);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            }

        }


    /*
     * 监听队列,
     * 执行创建订单详情操作
     * */

    @RabbitListener(queues = "creatorderlist")
    public void creatorderlist(OrderDTO orderDTO,Message message,Channel channel) throws IOException {


        try{
            List<TOrderdetail> tOrderdetails = new ArrayList<TOrderdetail>();
            List<Pt> pts = orderDTO.getPts();
            //用户id
            Long id = orderDTO.getId();

            //订单编号

            for (Pt pt : pts) {
                //通过pid 去获取整个p对象,并将商品对象存到redis中.
                String orderno = orderDTO.getOrderno();
                TOrderdetail tOrderdetail = new TOrderdetail();
                tOrderdetail.setUid(id);
                tOrderdetail.setOid(orderno);
                String productKey = StringAppend.getnewString(Constant.PROFUCTKEY, pt.getPid().toString());
                TProduct pro = (TProduct) redisTemplate.opsForValue().get(productKey);
                if(pro==null){
                    //去数据库拿。再存redis
                    pro = productMapper.selectByPrimaryKey(pt.getPid());
                    //存redis
                    redisTemplate.opsForValue().set(productKey,pro);
                }
                //pro肯定是有的
                tOrderdetail.setPrice(pro.getPrice());
                tOrderdetail.setNumber(pt.getCount());
                tOrderdetail.setImgurl(pro.getPimage());
                tOrderdetail.setPname(pro.getPname());
                tOrderdetail.setPid(Integer.valueOf(String.valueOf(pt.getPid())));
                tOrderdetails.add(tOrderdetail);
            }
            int insert = orderService.insert(tOrderdetails);

            if(insert<1){
                //添加为未成功,需要查看情况,返回到队列重新试
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            }

            //添加成功不返回到队列,消息已经被消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
           // channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //添加成功不返回到队列,消息已经被消费
           channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }

    }



    /*
     * 监听队列,
     * 执行修改购物车操作
     * */

    @RabbitListener(queues = "changecart")
    public void changecart(OrderDTO orderDTO,Message message,Channel channel) throws IOException {

        try{
            //用户id
            Long id = orderDTO.getId();

            String cartid = StringAppend.getnewString(Constant.USERCART, id);
            List<Pt> pts = orderDTO.getPts();

            Object object = redisTemplate.opsForValue().get(cartid);
            List<CartItem> carts = (List<CartItem>) object;
            List<CartItem> newcart=new ArrayList<CartItem>();
            for (CartItem cart : carts) {
                Long productId = cart.getProductId();
                int count = cart.getCount();
                boolean a=true;
                for (Pt pt : pts) {
                    CartItem newcartItem = new CartItem();
                    if (productId.equals(pt.getPid())) {
                        a=false;
                        count= count - pt.getCount();
                        if (count > 0) {
                            newcartItem.setCount(count);
                            newcartItem.setProductId(productId);
                            newcartItem.setUpdateTime(cart.getUpdateTime());
                            newcart.add(newcartItem);
                            break;
                        }
                    }

                }
                if(a) {
                    CartItem newcartItem = new CartItem();
                    newcartItem.setCount(count);
                    newcartItem.setProductId(productId);
                    newcartItem.setUpdateTime(cart.getUpdateTime());
                    newcart.add(newcartItem);
                }
            }
            if (newcart.size()==0){
                newcart=null;
            }
            redisTemplate.opsForValue().set(cartid,newcart);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
           channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);

        }

    }







    @RabbitListener(queues = "delectorder")
    public void get(String message){
        System.out.println("接收到的delectorder消息："+message);
    }


    }

