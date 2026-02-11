package com.webdemo.controller;

import com.webdemo.entity.CardKey;
import com.webdemo.entity.User;
import com.webdemo.service.CardKeyService;
import com.webdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理员控制器
 * 处理卡密导入、用户管理等管理功能
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CardKeyService cardKeyService;
    private final UserService userService;

    public AdminController(CardKeyService cardKeyService, UserService userService) {
        this.cardKeyService = cardKeyService;
        this.userService = userService;
    }

    /**
     * 显示卡密导入页面
     * 
     * @param session HTTP 会话对象
     * @param model 模型对象
     * @return 卡密导入页面视图名称，未登录返回登录页面
     */
    @GetMapping("/import")
    public String importPage(jakarta.servlet.http.HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("访问管理员导入页面 - session中的user: " + user);
        
        if (user == null || !"ADMIN".equals(user.getRole())) {
            System.out.println("用户未登录或不是管理员，重定向到登录页面");
            return "redirect:/login";
        }
        
        model.addAttribute("cardKeys", cardKeyService.getAllCardKeys());
        return "admin-import";
    }

    /**
     * 处理卡密导入请求
     * 
     * @param cardKeys 卡密列表（多行输入）
     * @param session HTTP 会话对象
     * @return 导入成功返回导入页面
     */
    @PostMapping("/import")
    public String importCardKeys(@RequestParam String cardKeys,
                                jakarta.servlet.http.HttpSession session,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        System.out.println("========== 开始导入卡密 ==========");
        
        String[] lines = cardKeys.split("\n");
        int successCount = 0;
        int failCount = 0;
        int duplicateCount = 0;
        
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            
            try {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    String key = parts[0].trim();
                    String cardType = parts[1].trim();
                    String cardStatus = parts[2].trim();
                    String expireTime = parts[3].trim();
                    
                    System.out.println("处理卡密: " + key + ", 类型: " + cardType + ", 状态: " + cardStatus + ", 过期时间: " + expireTime);
                    
                    java.time.LocalDateTime dateTime = java.time.LocalDateTime.parse(expireTime, formatter);
                    CardKey cardKey = new CardKey(key, dateTime);
                    cardKey.setCardType(cardType);
                    cardKey.setCardStatus(cardStatus);
                    
                    try {
                        cardKeyService.saveCardKey(cardKey);
                        successCount++;
                        System.out.println("卡密导入成功: " + key);
                    } catch (Exception e) {
                        System.out.println("卡密已存在，跳过: " + key);
                        duplicateCount++;
                    }
                } else {
                    System.out.println("格式错误，跳过: " + line);
                    failCount++;
                }
            } catch (Exception e) {
                System.out.println("导入失败: " + line);
                System.out.println("错误信息: " + e.getMessage());
                e.printStackTrace();
                failCount++;
            }
        }
        
        System.out.println("========== 导入完成 ==========");
        System.out.println("成功: " + successCount + ", 失败: " + failCount + ", 重复: " + duplicateCount);
        
        if (successCount > 0) {
            redirectAttributes.addFlashAttribute("importSuccess", "成功导入 " + successCount + " 条卡密");
        }
        if (duplicateCount > 0) {
            redirectAttributes.addFlashAttribute("importDuplicate", duplicateCount + " 条卡密已存在，未导入");
        }
        if (failCount > 0) {
            redirectAttributes.addFlashAttribute("importError", failCount + " 条卡密导入失败");
        }
        
        return "redirect:/admin/import";
    }

    /**
     * 删除选中的卡密
     * 
     * @param ids 要删除的卡密 ID 列表
     * @param session HTTP 会话对象
     * @return 删除成功返回导入页面
     */
    @PostMapping("/delete")
    public String deleteCardKeys(@RequestParam(required = false) List<Long> ids,
                                jakarta.servlet.http.HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        System.out.println("========== 开始删除卡密 ==========");
        
        if (ids != null && !ids.isEmpty()) {
            for (Long id : ids) {
                System.out.println("删除卡密 ID: " + id);
                cardKeyService.deleteCardKey(id);
            }
            System.out.println("删除完成，共删除: " + ids.size() + " 条");
        } else {
            System.out.println("没有选择要删除的卡密");
        }
        
        return "redirect:/admin/import";
    }

    /**
     * 显示用户管理页面
     * 
     * @param session HTTP 会话对象
     * @param model 模型对象
     * @return 用户管理页面视图名称，未登录返回登录页面
     */
    @GetMapping("/users")
    public String usersPage(jakarta.servlet.http.HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("访问用户管理页面 - session中的user: " + user);
        
        if (user == null || !"ADMIN".equals(user.getRole())) {
            System.out.println("用户未登录或不是管理员，重定向到登录页面");
            return "redirect:/login";
        }
        
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }

    /**
     * 添加用户
     * 
     * @param username 用户名
     * @param password 密码
     * @param role 角色
     * @param session HTTP 会话对象
     * @param redirectAttributes 重定向属性
     * @return 添加成功返回用户管理页面
     */
    @PostMapping("/users/add")
    public String addUser(@RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String role,
                       jakarta.servlet.http.HttpSession session,
                       org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/login";
        }
        
        System.out.println("========== 添加用户 ==========");
        System.out.println("用户名: " + username);
        System.out.println("角色: " + role);
        
        if (username == null || username.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("userError", "用户名不能为空");
            return "redirect:/admin/users";
        }
        
        if (password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("userError", "密码不能为空");
            return "redirect:/admin/users";
        }
        
        if (userService.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("userError", "用户名已存在");
            return "redirect:/admin/users";
        }
        
        User newUser = new User(username, password, role);
        userService.saveUser(newUser);
        System.out.println("用户添加成功: " + username);
        
        redirectAttributes.addFlashAttribute("userSuccess", "用户添加成功");
        return "redirect:/admin/users";
    }

    /**
     * 编辑用户
     * 
     * @param id 用户ID
     * @param username 用户名
     * @param password 密码
     * @param role 角色
     * @param session HTTP 会话对象
     * @param redirectAttributes 重定向属性
     * @return 编辑成功返回用户管理页面
     */
    @PostMapping("/users/edit")
    public String editUser(@RequestParam Long id,
                         @RequestParam String username,
                         @RequestParam(required = false) String password,
                         @RequestParam String role,
                         jakarta.servlet.http.HttpSession session,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/login";
        }
        
        System.out.println("========== 编辑用户 ==========");
        System.out.println("用户ID: " + id);
        System.out.println("用户名: " + username);
        System.out.println("角色: " + role);
        
        User user = userService.getUserById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("userError", "用户不存在");
            return "redirect:/admin/users";
        }
        
        if (username == null || username.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("userError", "用户名不能为空");
            return "redirect:/admin/users";
        }
        
        if (!username.equals(user.getUsername()) && userService.existsByUsername(username)) {
            redirectAttributes.addFlashAttribute("userError", "用户名已存在");
            return "redirect:/admin/users";
        }
        
        user.setUsername(username);
        user.setRole(role);
        
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }
        
        userService.saveUser(user);
        System.out.println("用户编辑成功: " + username);
        
        redirectAttributes.addFlashAttribute("userSuccess", "用户编辑成功");
        return "redirect:/admin/users";
    }

    /**
     * 删除用户
     * 
     * @param ids 要删除的用户 ID 列表
     * @param session HTTP 会话对象
     * @param redirectAttributes 重定向属性
     * @return 删除成功返回用户管理页面
     */
    @PostMapping("/users/delete")
    public String deleteUsers(@RequestParam(required = false) List<Long> ids,
                            jakarta.servlet.http.HttpSession session,
                            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/login";
        }
        
        System.out.println("========== 删除用户 ==========");
        
        if (ids != null && !ids.isEmpty()) {
            for (Long id : ids) {
                System.out.println("删除用户 ID: " + id);
                userService.deleteUser(id);
            }
            System.out.println("删除完成，共删除: " + ids.size() + " 条");
            redirectAttributes.addFlashAttribute("userSuccess", "成功删除 " + ids.size() + " 个用户");
        } else {
            redirectAttributes.addFlashAttribute("userError", "没有选择要删除的用户");
            return "redirect:/admin/users";
        }
        
        return "redirect:/admin/users";
    }
}