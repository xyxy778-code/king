package com.king.dao;

/**
 * 持久化接口：支持将数据保存到文件和从文件读取
 */
public interface Persistable {
    String toFileString();
    void fromFileString(String line);
}
