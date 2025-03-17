package com.travel.utils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.travel.common.content.AuthContent.USER_HOLDER_KEY;

public class UserHolder {
    //创建一个ThreadLocal，其中存的是map类型的数据  
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL
            = new InheritableThreadLocal<>();  
  
    //设置值  
    public static void set(String key, Object val) {  
        Map<String, Object> map = getThreadLocalMap();  
        map.put(key, val);  
    }  
  
    //获取值  
    public static Object get(String key){  
        Map<String, Object> threadLocalMap = getThreadLocalMap();  
        return threadLocalMap.get(key);  
    }  

    //删除 ThreadLocal    
    public static void remove(){  
        THREAD_LOCAL.remove();  
    }  
    
    //单独设置的一个获取loginId的方法，其实和get方法一样，只是为了方便  
    public static String getLoginId(){
        return (String) getThreadLocalMap().get(USER_HOLDER_KEY);
    } 
    
    //获取ThreadLocal中的map，若map为空，则创建一个；若不为空，则使用之前的那个  
    public static Map<String,Object> getThreadLocalMap(){  
        Map<String, Object> map = THREAD_LOCAL.get();  
        if(Objects.isNull(map)){
            map=new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);  
        }  
        return map;  
    }  
}