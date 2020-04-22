package com.bsoft.template.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * 生成uuid工具类
 *
 * @author Artolia Pendragon
 * @version 1.0.0
 */
public class UUIDUtil {

    /**
     * 随机生成uuid
     * @return uuid
     */
    public static String generateRandomUUID() {
        UUID uuid = Generators.randomBasedGenerator().generate();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 生成基于时间戳的uuid
     * @return uuid
     */
    public static String generateTimeUUID() {
        UUID uuid = Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
        return uuid.toString().replaceAll("-", "");
    }
}
