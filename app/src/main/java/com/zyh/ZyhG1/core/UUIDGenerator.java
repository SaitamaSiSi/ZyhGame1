package com.zyh.ZyhG1.core;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDGenerator {

    private static final AtomicLong sequence = new AtomicLong(0); // 使用AtomicLong来确保线程安全

    public static synchronized String generateUUID() {
        // 获取当前时间戳（毫秒）
        long currentTimeMillis = System.currentTimeMillis();

        // 获取自增序列号
        long seq = sequence.incrementAndGet();

        // 组合时间戳和序列号来生成UUID
        return String.format("%d-%d", currentTimeMillis, seq);
    }
}