package com.honor.kings.service;

import java.util.List;

// 接口：Searchable<T> 定义按名称搜索的通用接口
// 泛型：使用类型参数 T，支持搜索 Hero、Team 等不同类型
// 多态：HeroServiceImpl 和 TeamServiceImpl 以不同方式实现此接口
public interface Searchable<T> {
    List<T> searchByName(String keyword);
}
