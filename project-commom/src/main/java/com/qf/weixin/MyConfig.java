package com.qf.weixin;

import java.io.InputStream;

/**
 * @Author:jiang
 * @Date:2019/12/20 23:52
 * @introduction:介绍
 */
public class MyConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return "wx632c8f211f8122c6";
    }

    @Override
    String getMchID() {
        return "1497984412";
    }

    @Override
    String getKey() {
        return "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return  new WXPayDominimpl();
    }
}
