package com.honor.kings.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抽象基类 Person
 * 演示：继承（作为父类）、多态（抽象方法 getRole() 由子类各自实现）、
 *       封装（private 字段 + getter/setter）、文件I/O（实现 Serializable）
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    private LocalDateTime createTime;

    public Person() {
    }

    /** 构造器：初始化人员基本信息 */
    public Person(String id, String name, String email, LocalDateTime createTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createTime = createTime;
    }

    /** 抽象方法：返回角色名称，由 Player 和 Admin 分别实现 */
    public abstract String getRole();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
