package com.honor.kings.service;

import java.util.List;

/**
 * Persistable 接口：定义通用 CRUD（增删改查）操作的接口
 * 演示：泛型（类型参数 T）、接口多态（多个 Service 实现不同的 CRUD 逻辑）
 */
public interface Persistable<T> {
    boolean save(T entity);
    T findById(String id);
    boolean delete(String id);
    List<T> findAll();
}
