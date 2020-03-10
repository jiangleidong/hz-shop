package com.qf.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    Object getAndExpireBySecond(Object key, Long timeout, TimeUnit seconds);
    void setAndExpireBySecond(Object key, Object value, Long timeout, TimeUnit seconds);
    Boolean delect(Object key);
}
