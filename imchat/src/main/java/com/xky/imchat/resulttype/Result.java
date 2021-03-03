package com.xky.imchat.resulttype;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private String message;

    private String code;

    private Map<String,Object> data=new HashMap<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode(ResultCode.success_code);
        result.setMessage("成功");
        return result;
    }
    public static Result error(){
        Result result = new Result();
        result.setCode(ResultCode.error_code);
        result.setMessage("失败");
        return  result;
    }
    public Result message(String msg){
        this.setMessage(msg);
        return this;
    }
    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    public Result data(Map<String,Object>map){
        this.setData(map);
        return this;
    }

}
