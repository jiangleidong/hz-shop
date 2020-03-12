package com.qf.dto;

/**
 * @program: hz-shop
 * @description: 传输到redis的传送格式
 * @author: Mr.jiang
 * @create: 2020-03-11 19:37
 **/
public class RedisDTO {
    private Object key;
    private Object value;
    private Long time;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public RedisDTO() {
    }

    public RedisDTO(Object key, Object value, Long time) {
        this.key = key;
        this.value = value;
        this.time = time;
    }
    public RedisDTO(Object key,  Long time) {
        this.key = key;
        this.time = time;
    }
    public RedisDTO(Object key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "RedisDTO{" +
                "key=" + key +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
