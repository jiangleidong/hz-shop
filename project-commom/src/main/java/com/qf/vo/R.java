package com.qf.vo;

import java.io.Serializable;

public class R implements Serializable {
    private Integer code;
    private String message;
    private boolean success;
    private Object data;

    public static R ok(){
        R r = new R();
        r.setCode(200);
        r.setSuccess(true);
        return r;
    }
    public static R ok(String message){
        R r = new R();
        r.setCode(200);
        r.setSuccess(true);
        r.setMessage(message);
        return r;
    }

    public static R ok(Integer code,String message,Object data){
        R r = new R();
        r.setCode(code);
        r.setSuccess(true);
        r.setData(data);
        return r;
    }
    public static R ok(Object obj){
        R r = new R();
        r.setCode(200);
        r.setSuccess(true);
        r.setData(obj);
        return r;
    }

    public static R error(){
        R r = new R();
        r.setCode(0);
        r.setSuccess(false);
        return r;
    }


    public static R error(int code , String message){
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }

    public static R error( String message){
        R r = new R();
        r.setCode(0);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }


}
