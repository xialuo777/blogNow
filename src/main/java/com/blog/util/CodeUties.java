package com.blog.util;

import org.springframework.stereotype.Component;

import java.util.Random;

public class CodeUties {
    /**
     * @Title CodeUties
     * @Description 获取6位验证码
     * @return String
     * @Data 2024/8/22
     */
    public static String getCode() {
        int num = 6;
        String code = "";
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            int type = r.nextInt(3);
            switch (type){
                case 0:
                    code += r.nextInt(10);
                    break;
                case 1:
                    code += (char)('a' + r.nextInt(26));
                    break;
                default:
                    code += (char)('A' + r.nextInt(26));
            }
        }
        return code;
    }
}
