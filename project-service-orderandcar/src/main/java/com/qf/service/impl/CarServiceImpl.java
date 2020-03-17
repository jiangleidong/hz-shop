package com.qf.service.impl;

import com.qf.constant.Constant;
import com.qf.dto.TProductCartDTO;
import com.qf.entity.TProduct;
import com.qf.mapper.TProductMapper;
import com.qf.service.CartService;
import com.qf.service.RedisService;
import com.qf.util.StringAppend;
import com.qf.vo.CartItem;
import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: hz-shop
 * @description:
 * @author: Mr.jiang
 * @create: 2020-03-15 20:08
 **/
@Service
public class CarServiceImpl implements CartService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TProductMapper productMapper;

    @Override
    public R addProduct(String id, Long productId, int count) {
        /*
         *     * 	1）当前用户没有购物车
         *      * 		新建购物车，把商品添加到购物车中，再把购物车存到redis中
         *      * 	2）当前用户有购物车，但是购物车中没有该商品
         *      * 		先从redis中获取该购物车，再把商品添加都购物车中，再存入到redis中。
         *      * 	3）当前用户有购物车，且购物车中有该商品
         *      * 		先从redis中获取该购物车，再获取该商品的数量，再让新的数量和老的数量相加，更新回购物车中，再更新回redis中。
         */


        String cartid = StringAppend.getnewString(Constant.USERCART, id);
        Object data = redisTemplate.opsForValue().get(cartid);
        if(data==null){
            //当前用户没有购物车
            //封装购物车商品对象
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setCount(count);
            cartItem.setUpdateTime(new Date());

            //存入到购物车中
            List<CartItem> carts = new ArrayList<>();
            carts.add(cartItem);
            //存入到redis中,这个是永久保存的


            redisTemplate.opsForValue().set(cartid,carts);
            return R.ok(200,"添加购物车成功",carts);
        }

        //第2 或第3中情况
        List<CartItem> carts = (List<CartItem>) data;
        for (CartItem cartItem : carts) {

            if(cartItem.getProductId().equals(productId)&&productId!=null){
                //当前用户有购物车，且购物车中有该商品
                cartItem.setCount(cartItem.getCount()+count);
                //更新商品的时间
                cartItem.setUpdateTime(new Date());
                //购物车中的商品已更新，得把购物车存回到redis中,这个也是永久保存的

                redisTemplate.opsForValue().set(cartid,carts);
                return R.ok(200,"添加购物车成功",carts);
            }
        }

        //当前用户有购物车，但购物车中没有该商品
        //封装购物车商品对象
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setCount(count);
        cartItem.setUpdateTime(new Date());
        carts.add(cartItem);
        //存到redis里面
        redisTemplate.opsForValue().set(cartid,carts);
        return R.ok(200,"添加购物车成功",carts);


    }
    //清空购物车
    @Override
    public R clean(String id) {

        String cartid = StringAppend.getnewString(Constant.USERCART, id);
        redisTemplate.delete(cartid);


        return R.ok("清空购物车成功");
    }

    //更新购物车
    @Override
    public R update(String id, Long productId, int count) {

        if(id!=null) {
            //组织redis中的键
            String cartid = StringAppend.getnewString(Constant.USERCART, id);
            Object o = redisTemplate.opsForValue().get(cartid);
            if (o != null) {
                List<CartItem> carts = (List<CartItem>) o;
                for (CartItem cartItem : carts) {
                    if (cartItem.getProductId().longValue() == productId.longValue()) {
                        cartItem.setCount(count);
                        cartItem.setUpdateTime(new Date());
                        //把集合直接存回到redis中
                        redisTemplate.opsForValue().set(cartid, carts);
                        return R.ok(200, "更新购物车成功",carts);
                    }

                }
            }
        }

        return R.error("当前用户没有购物车");
    }

    //查看购物车
    @Override
    public R showCart(String id) {// user:cart:userId     userId   排序

        if(id!=null){
            String cartid = StringAppend.getnewString(Constant.USERCART, id);
            Object o = redisTemplate.opsForValue().get(cartid);
            if(o!=null){
                List<CartItem> carts = (List<CartItem>) o;
//                List<TProduct> products = new ArrayList<>();
                List<TProductCartDTO> products = new ArrayList<>();
                for (CartItem cartItem : carts) {
                    //去reids中取
                    // product:10
                    String productKey = StringAppend.getnewString(Constant.PROFUCTKEY, cartItem.getProductId().toString());
                    TProduct pro = (TProduct) redisTemplate.opsForValue().get(productKey);
                    if(pro==null){
                        //去数据库拿。再存redis
                        pro = productMapper.selectByPrimaryKey(cartItem.getProductId());
                        //存redis
                        redisTemplate.opsForValue().set(productKey,pro);
                    }
                    //pro肯定是有的
                    TProductCartDTO cartDTO = new TProductCartDTO();

                    //封装
                    cartDTO.setProduct(pro);
                    cartDTO.setCount(cartItem.getCount());
                    cartDTO.setUpdateTime(cartItem.getUpdateTime());




                    //存到product集合中
                    products.add(cartDTO);
                }

                //对集合中的元素进行排序，Comparator 用来指明排序依据。
                Collections.sort(products, new Comparator<TProductCartDTO>() {
                    @Override
                    public int compare(TProductCartDTO o2, TProductCartDTO o1) {
                        return (int)(o1.getUpdateTime().getTime()-o2.getUpdateTime().getTime());
                    }
                });

                return R.ok(products);
            }
        }

        return R.error("当前用户没有购物车");
    }

    //合并购物车
    @Override
    public R merge(Long id,String keyid) {
        /*
        合并
1.未登录状态下没有购物车==》合并成功
2.未登录状态下有购物车，但已登录状态下没有购物车==》把未登录的变成已登录的
3.未登录状态下有购物车，但已登录状态下也有购物车，而且购物车中的商品有重复==》难点！
         */
        //获得两种状态下的购物车
        String noLoginRedisKey = StringAppend.getnewString(Constant.USERCART,keyid);
        String loginRedisKey = StringAppend.getnewString(Constant.USERCART, String.valueOf(id));
        Object noLoginO = redisTemplate.opsForValue().get(noLoginRedisKey);//未登录下的购物车
        Object loginO = redisTemplate.opsForValue().get(loginRedisKey);//已登录下的购物车
        if(noLoginO==null){
            //1.未登录状态下没有购物车==》合并成功
            return R.ok("未登录状态下没有购物车，不需要合并");
        }

        if(loginO==null){
            //2.未登录状态下有购物车，但已登录状态下没有购物车==》把未登录的变成已登录的
            redisTemplate.opsForValue().set(loginRedisKey,noLoginO);
            //删除未登录状态下的购物车
            redisTemplate.delete(noLoginRedisKey);
            return R.ok(200,"合并成功",noLoginO);
        }

        //3.未登录状态下有购物车，但已登录状态下也有购物车，而且购物车中的商品有重复==》难点！
        List<CartItem> noLoginCarts = (List<CartItem>) noLoginO;
        List<CartItem> loginCarts = (List<CartItem>) loginO;
        //先创建一个Map
        Map<Long,CartItem> map = new HashMap<>();
        for (CartItem noLoginCartItem : noLoginCarts) {
            map.put(noLoginCartItem.getProductId(),noLoginCartItem);
        }
        //此时map中就有所有的未登录状态下的购物车的商品
        //存入已登录状态下购物车的商品
        for (CartItem loginCartItem : loginCarts) {
            //尝试去检查下map中该商品是否已存在
            CartItem currentCartItem = map.get(loginCartItem.getProductId());
            if(currentCartItem!=null){
                //已存在
                currentCartItem.setCount(currentCartItem.getCount()+loginCartItem.getCount());
                //时间 必然是未登录状态下的更近
            }else{
                //不存在，直接放
                map.put(loginCartItem.getProductId(),loginCartItem);
            }
        }
        //此时Map中存放的数据就是合并之后的购物车
        //删除未登录状态下的购物车
        redisTemplate.delete(noLoginRedisKey);
        //把新的购物车存入到redis中
        Collection<CartItem> values = map.values();
        List<CartItem> newCarts = new ArrayList<>(values);
        redisTemplate.opsForValue().set(loginRedisKey,newCarts);
        return R.ok(200,"合并成功",newCarts);
    }

}
