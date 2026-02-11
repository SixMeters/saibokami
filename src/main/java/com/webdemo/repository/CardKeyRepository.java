package com.webdemo.repository;

import com.webdemo.entity.CardKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 卡密数据访问层
 * 提供卡密相关的数据库操作
 */
@Repository
public interface CardKeyRepository extends JpaRepository<CardKey, Long> {

    /**
     * 查找所有未过期的卡密
     * 
     * @param now 当前时间
     * @return 未过期的卡密列表
     */
    List<CardKey> findByExpireTimeAfter(LocalDateTime now);

    /**
     * 查找未过期且未兑换的卡密
     * 
     * @param now 当前时间
     * @return 未过期且未兑换的卡密列表
     */
    List<CardKey> findByExpireTimeAfterAndIsRedeemedFalse(LocalDateTime now);

    /**
     * 查找已兑换且兑换时间在指定时间之前的卡密
     * 
     * @param dateTime 指定时间
     * @return 已兑换且兑换时间在指定时间之前的卡密列表
     */
    List<CardKey> findByIsRedeemedTrueAndRedeemedAtBefore(LocalDateTime dateTime);
}