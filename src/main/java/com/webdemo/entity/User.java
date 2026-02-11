package com.webdemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 用于存储用户登录信息
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * 用户ID
     * 主键，自增长，唯一标识一个用户
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     * 登录时使用的用户名，不能为空，必须唯一
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 密码
     * 用户登录密码，不能为空
     */
    @Column(nullable = false, length = 255)
    private String password;

    /**
     * 用户角色
     * 标识用户的权限级别，如 ADMIN（管理员）或 USER（普通用户）
     */
    @Column(length = 20)
    private String role;

    /**
     * 创建时间
     * 记录用户账号创建的时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * 记录用户信息最后更新的时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 上次兑换卡密时间
     * 记录用户最后一次成功兑换卡密的时间
     * 用于控制用户兑换卡密的频率（30天内只能兑换一次）
     */
    @Column(name = "last_redeem_time")
    private LocalDateTime lastRedeemTime;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastRedeemTime() {
        return lastRedeemTime;
    }

    public void setLastRedeemTime(LocalDateTime lastRedeemTime) {
        this.lastRedeemTime = lastRedeemTime;
    }
}