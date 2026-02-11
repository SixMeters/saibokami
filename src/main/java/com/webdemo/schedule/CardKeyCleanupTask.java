package com.webdemo.schedule;

import com.webdemo.service.CardKeyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 * 用于定期清理过期的已兑换卡密
 * 
 * 该类使用 Spring 的 @Scheduled 注解实现定时任务功能
 * 每天自动清理两个月前已兑换的卡密，以减少数据库存储空间
 */
@Component
public class CardKeyCleanupTask {

    /**
     * 卡密服务
     * 用于执行卡密相关的业务逻辑操作
     */
    private final CardKeyService cardKeyService;

    /**
     * 构造函数
     * 通过依赖注入获取 CardKeyService 实例
     * 
     * @param cardKeyService 卡密服务
     */
    public CardKeyCleanupTask(CardKeyService cardKeyService) {
        this.cardKeyService = cardKeyService;
    }

    /**
     * 定时清理两个月前的已兑换卡密
     * 
     * @Scheduled 注解配置定时任务：
     * - fixedRate = 86400000：每隔 86400000 毫秒（24小时）执行一次
     * - initialDelay = 10000：应用启动后延迟 10000 毫秒（10秒）开始首次执行
     * 
     * 该方法会调用 CardKeyService 的 deleteOldRedeemedCardKeys 方法
     * 删除所有兑换时间在两个月之前的卡密记录
     */
    @Scheduled(fixedRate = 86400000, initialDelay = 10000)
    public void cleanupOldRedeemedCardKeys() {
        System.out.println("========== 开始清理两个月前的已兑换卡密 ==========");
        cardKeyService.deleteOldRedeemedCardKeys();
        System.out.println("========== 清理完成 ==========");
    }
}
