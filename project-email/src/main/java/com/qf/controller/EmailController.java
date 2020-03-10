package com.qf.controller;

import com.qf.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

/**
 * @program: hz-shop
 * @description: 邮件服务提供
 * @author: Mr.jiang
 * @create: 2020-03-10 21:18
 **/
@Controller
@RequestMapping("email")
public class EmailController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${activeServerUrl}")
    private String activeServerUrl;

/*点击激活邮箱时候,执行的代码*/
    @RequestMapping("active/{uuid}")
    public R activeAccount(@PathVariable String uuid ,@CookieValue(value = "active-key",required = false)String activeKey){
        //进入这个接口说明已经点击了链接,下面只有两种情况,1,超时,2未超时   ---超时未超时用cookie判定  (或者是redis判定 )


        if(!activeKey.equals(uuid)){
            return R.error("链接已经超时超时");
        }
        return R.ok("激活成功");
    }



/*通过传过来的邮箱,发送一封邮件, 并把邮件地址给返回 */
    @RequestMapping("send/{addr}/{uuid}")
    @ResponseBody
    public R sendEmail(@PathVariable String addr,
                                @PathVariable String uuid){

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper mailMessage = null;
        try {
            mailMessage = new MimeMessageHelper(message,true);
            mailMessage.setSubject("请激活您在本中心的账号");

            //读取模板内容
            Context context = new Context();
            context.setVariable("username",addr.toString());
            context.setVariable("url",activeServerUrl+uuid);

            String info = templateEngine.process("emailTemplate", context);

            mailMessage.setText(info,true);

            mailMessage.setFrom("1213668681@qq.com");//系统账号
            mailMessage.setTo(addr);

            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok("email send success");

    }


}
