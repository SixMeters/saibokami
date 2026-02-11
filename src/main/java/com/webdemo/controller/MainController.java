package com.webdemo.controller;

import com.webdemo.entity.CardKey;
import com.webdemo.entity.User;
import com.webdemo.service.CardKeyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主控制器
 * 处理功能选择、卡密兑换等请求
 */
@Controller
public class MainController {

    private final CardKeyService cardKeyService;

    public MainController(CardKeyService cardKeyService) {
        this.cardKeyService = cardKeyService;
    }

    /**
     * 显示功能选择页面
     * 
     * @param session HTTP 会话对象
     * @param model 模型对象
     * @return 功能选择页面视图名称，未登录返回登录页面
     */
    @GetMapping("/dashboard")
    public String dashboard(jakarta.servlet.http.HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        System.out.println("访问Dashboard - 用户: " + user.getUsername() + ", 角色: " + user.getRole());
        
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/import";
        }
        
        model.addAttribute("user", user);
        return "dashboard";
    }

    /**
     * 显示卡密兑换页面
     * 
     * @param session HTTP 会话对象
     * @param model 模型对象
     * @return 卡密兑换页面视图名称，未登录返回登录页面
     */
    @GetMapping("/redeem")
    public String redeemPage(jakarta.servlet.http.HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "redeem";
    }

    /**
     * 处理卡密兑换请求
     * 
     * @param session HTTP 会话对象
     * @return 兑换成功返回功能选择页面，失败返回兑换页面
     */
    @PostMapping("/redeem")
    public String redeem(jakarta.servlet.http.HttpSession session,
                    org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        System.out.println("========== 开始兑换卡密 ==========");
        System.out.println("Session中的user: " + user);
        
        if (user == null) {
            System.out.println("用户未登录，重定向到登录页面");
            return "redirect:/login";
        }
        
        System.out.println("用户ID: " + user.getId());
        System.out.println("用户名: " + user.getUsername());
        System.out.println("用户角色: " + user.getRole());
        System.out.println("上次兑换时间: " + user.getLastRedeemTime());
        
        CardKey cardKey = cardKeyService.redeemCardKey(user.getUsername());
        if (cardKey != null) {
            session.setAttribute("cardKey", cardKey);
            session.removeAttribute("redeemError");
            System.out.println("卡密兑换成功: " + cardKey.getCardKey());
            System.out.println("卡密过期时间: " + cardKey.getExpireTime());
            return "redirect:/dashboard";
        } else {
            session.removeAttribute("cardKey");
            redirectAttributes.addFlashAttribute("redeemError", "没有可用的卡密，请稍后再试");
            System.out.println("卡密兑换失败：没有可用的卡密");
            return "redirect:/redeem";
        }
    }
}