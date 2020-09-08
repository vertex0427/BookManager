package org.neptune.BookManager.utils;

import java.util.UUID;

//用JDK自带的UUID生成器生成一串随机的字符串，作为TicketInfo
public class UuidUtils {

    public static String next(){
        return UUID.randomUUID().toString().replace("-","a");
    }

}
