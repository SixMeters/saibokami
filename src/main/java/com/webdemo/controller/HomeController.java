package com.webdemo.controller;

// 导入 Spring MVC 控制器注解：标识该类为 Web 控制器
import org.springframework.stereotype.Controller;
// 导入 GetMapping 注解：映射 HTTP GET 请求
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

/**
 * 首页控制器
 * 
 * @Controller 注解标识该类为 Spring MVC 控制器
 * 控制器负责处理 HTTP 请求并返回视图名称
 */
@Controller
public class HomeController {

    /**
     * 处理首页请求
     * 
     * @GetMapping("/") 注解将该方法映射到根路径 "/"
     * 当用户访问 http://localhost:10000/ 时，会调用此方法
     * 
     * @param model 模型对象，用于向视图传递数据
     * @return 主页视图名称 "index"，显示欢迎页面
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("currentPage", "index");
        return "index";
    }

    /**
     * 处理关于页面请求
     * 
     * @GetMapping("/about") 注解将该方法映射到 "/about" 路径
     * 当用户访问 http://localhost:8080/about 时，会调用此方法
     * 
     * @param model 模型对象，用于向视图传递数据
     * @return 返回视图名称 "about"，Spring 会查找 templates/about.html 文件
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("currentPage", "about");
        return "about";
    }
}