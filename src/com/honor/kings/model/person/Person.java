package com.honor.kings.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;

// 继承：Person 是抽象基类，Player 和 Admin 继承自它
// 多态：getRole() 为抽象方法，子类分别返回 "Player" 和 "Admin"
// 封装：所有字段为 private，通过 getter/setter 访问
// 文件I/O：实现 Serializable 以便序列化保存
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    private LocalDateTime createTime;

    public Person() {
    }

    public Person(String id, String name, String email, LocalDateTime createTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createTime = createTime;
    }

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
