package com.dreamwing.utils;

import java.util.concurrent.ThreadLocalRandom;

public class VerifyCodeUtil {
    /**
     * 生成验证码
     * @return
     */
    public static String makeVerifyCode(){
        int randomNumber = ThreadLocalRandom.current().nextInt(1000000); // 生成0到999999之间的随机数
        String verifyNum = String.format("%06d", randomNumber); // 将随机数格式化为六位数
        return verifyNum;
    }

}
