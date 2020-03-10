package com.qf.weixin;

/**
 * @Author:jiang
 * @Date:2019/12/20 23:58
 * @introduction:介绍
 */
public class WXPayDominimpl implements IWXPayDomain {
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {

    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
           //"api.mch.weixin.qq.com" 这个域名 后续可以改成 公司的域名
        return new DomainInfo("api.mch.weixin.qq.com", true);
    }
}
