package com.webdemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 卡密实体类
 * 用于存储卡密信息
 */
@Entity
@Table(name = "card_keys")
public class CardKey {

    /**
     * 卡密ID
     * 主键，自增长，唯一标识一个卡密
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 卡密
     * 卡密的实际值，不能为空，必须唯一
     * 格式为16位的大写字母和数字组合
     */
    @Column(nullable = false, unique = true, length = 100)
    private String cardKey;

    /**
     * 卡密类型
     * 标识卡密的类型，如月卡、季卡、年卡等
     */
    @Column(length = 50)
    private String cardType;

    /**
     * 卡密状态
     * 标识卡密的状态，如正常、禁用等
     */
    @Column(length = 50)
    private String cardStatus;

    /**
     * 过期时间
     * 卡密的过期时间，超过此时间后卡密将失效
     */
    @Column(nullable = false)
    private LocalDateTime expireTime;

    /**
     * 描述信息
     * 卡密的描述信息，可以记录兑换该卡密的用户名
     */
    @Column(length = 255)
    private String description;

    /**
     * 创建时间
     * 记录卡密创建的时间
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * 记录卡密信息最后更新的时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 是否已兑换
     * 标识卡密是否已被用户兑换
     * true 表示已兑换，false 表示未兑换
     */
    @Column(name = "is_redeemed")
    private Boolean isRedeemed;

    /**
     * 兑换时间
     * 记录卡密被兑换的时间
     */
    @Column(name = "redeemed_at")
    private LocalDateTime redeemedAt;

    public CardKey() {
    }

    public CardKey(String cardKey, LocalDateTime expireTime) {
        this.cardKey = cardKey;
        this.expireTime = expireTime;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isRedeemed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getIsRedeemed() {
        return isRedeemed;
    }

    public void setIsRedeemed(Boolean isRedeemed) {
        this.isRedeemed = isRedeemed;
    }

    public LocalDateTime getRedeemedAt() {
        return redeemedAt;
    }

    public void setRedeemedAt(LocalDateTime redeemedAt) {
        this.redeemedAt = redeemedAt;
    }
}