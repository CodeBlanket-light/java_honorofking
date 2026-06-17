package com.honor.kings.service;

import java.util.List;

// 接口：Persistable<T> 定义通用 CRUD 操作的接口
// 泛型：使用类型参数 T，支持对 Hero、Team、MatchRecord 等不同类型的持久化操作
// 多态：HeroServiceImpl、TeamServiceImpl、MatchServiceImpl 分别实现此接口
public interface Persistable<T> {
    boolean save(T entity);
    T findById(String id);
    boolean delete(String id);
    List<T> findAll();
}
