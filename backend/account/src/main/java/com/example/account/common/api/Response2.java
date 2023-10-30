package com.example.account.common.api;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;


// 쓸지 안쓸지 고민중
@Getter
public class Response2 {
    private int code;
    private String message;
    private ConcurrentHashMap<String,Object> data;

    public Response2(){
        this.data=new ConcurrentHashMap<>();
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String key, Object data){
        this.data.put(key,data);
    }


    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


}
