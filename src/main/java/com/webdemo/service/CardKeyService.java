package com.webdemo.service;

import com.webdemo.entity.CardKey;
import com.webdemo.entity.User;
import com.webdemo.repository.CardKeyRepository;
import com.webdemo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * 卡密服务层
 * 处理卡密相关的业务逻辑
 */
@Service
public class CardKeyService {

    private final CardKeyRepository cardKeyRepository;
    private final UserRepository userRepository;

    public CardKeyService(CardKeyRepository cardKeyRepository, UserRepository userRepository) {
        this.cardKeyRepository = cardKeyRepository;
        this.userRepository = userRepository;
    }

    /**
     * 生成随机卡密
     * 
     * @return 生成的卡密
     */
    public String generateCardKey() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            key.append(chars.charAt(random.nextInt(chars.length())));
        }
        return key.toString();
    }

    /**
     * 用户兑换卡密
     * 
     * @param username 用户名
     * @return 兑换的卡密及过期时间，如果已兑换过则返回 null
     */
    @Transactional
    public CardKey redeemCardKey(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("用户不存在: " + username);
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        
        if (user.getLastRedeemTime() != null) {
            LocalDateTime nextAllowedTime = user.getLastRedeemTime().plusDays(30);
            if (now.isBefore(nextAllowedTime)) {
                long daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(now, nextAllowedTime);
                System.out.println("用户 " + username + " 距离上次兑换不足30天，还需等待 " + daysRemaining + " 天");
                return null;
            }
        }

        List<CardKey> availableKeys = cardKeyRepository.findByExpireTimeAfterAndIsRedeemedFalse(now);
        
        if (availableKeys.isEmpty()) {
            System.out.println("没有可用的卡密");
            return null;
        }

        CardKey cardKey = availableKeys.get(0);
        cardKey.setDescription(username);
        cardKey.setUpdatedAt(now);
        cardKey.setIsRedeemed(true);
        cardKey.setRedeemedAt(now);
        cardKeyRepository.save(cardKey);
        
        user.setLastRedeemTime(now);
        userRepository.save(user);
        
        System.out.println("用户 " + username + " 兑换卡密成功: " + cardKey.getCardKey());
        return cardKey;
    }

    /**
     * 删除两个月前的已兑换卡密
     */
    @Transactional
    public void deleteOldRedeemedCardKeys() {
        LocalDateTime twoMonthsAgo = LocalDateTime.now().minusMonths(2);
        List<CardKey> oldRedeemedKeys = cardKeyRepository.findByIsRedeemedTrueAndRedeemedAtBefore(twoMonthsAgo);
        
        if (!oldRedeemedKeys.isEmpty()) {
            System.out.println("删除两个月前的已兑换卡密，共 " + oldRedeemedKeys.size() + " 条");
            cardKeyRepository.deleteAll(oldRedeemedKeys);
        }
    }

    /**
     * 保存卡密（管理员使用）
     * 
     * @param cardKey 卡密对象
     */
    public void saveCardKey(CardKey cardKey) {
        cardKeyRepository.save(cardKey);
    }

    /**
     * 获取所有卡密
     * 
     * @return 所有卡密列表
     */
    public List<CardKey> getAllCardKeys() {
        return cardKeyRepository.findAll();
    }

    /**
     * 删除卡密
     * 
     * @param id 卡密 ID
     */
    public void deleteCardKey(Long id) {
        cardKeyRepository.deleteById(id);
    }
}