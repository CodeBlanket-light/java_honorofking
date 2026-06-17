package com.honor.kings.service;

import java.util.List;

/**
 * Searchable 接口：定义按名称搜索的通用能力
 * 演示：泛型（类型参数 T）、接口多态（多个实现类各自实现搜索逻辑）
 */
public interface Searchable<T> {
    List<T> searchByName(String keyword);
}
