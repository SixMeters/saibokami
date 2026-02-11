package com.webdemo.controller;

import com.webdemo.entity.User;
import com.webdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 处理登录、登出等认证相关请求
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 显示登录页面
     * 
     * @return 登录页面视图名称
     */
    @GetMapping("/login")
    public String loginPage(org.springframework.ui.Model model) {
        model.addAttribute("currentPage", "login");
        return "login";
    }

    /**
     * 处理登录请求
     * 
     * @param username 用户名
     * @param password 密码
     * @param session HTTP 会话对象
     * @return 登录成功跳转到功能选择页面，失败返回登录页面
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                    @RequestParam String password,
                    jakarta.servlet.http.HttpSession session) {
        System.out.println("========== 登录请求 ==========");
        System.out.println("用户名: [" + username + "]");
        System.out.println("密码: [" + password + "]");
        
        User user;
        
        if ("admin".equals(username)) {
            if ("admin123".equals(password)) {
                user = new User(username, password, "ADMIN");
            } else {
                System.out.println("管理员登录失败：密码错误");
                return "redirect:/login?error";
            }
        } else {
            user = userService.login(username, password);
            if (user == null) {
                System.out.println("普通用户登录失败：用户不存在或密码错误");
                return "redirect:/login?error";
            }
        }
        
        session.setAttribute("user", user);
        return "redirect:/dashboard";
    }

    /**
     * 处理登出请求
     * 
     * @param session HTTP 会话对象
     * @return 重定向到登录页面
     */
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}