package com.wangpx.test;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        Map<String,String> map =  new HashMap<String,String>();

        /**
         * 当可以相同时，会覆盖之前的value
         */

        map.put("o","message");
        map.put("o","messages1");
        map.put("a","messages1");
        for (String key : map.keySet()) {
            System.out.println(map.get(key));
        }

    }
}
