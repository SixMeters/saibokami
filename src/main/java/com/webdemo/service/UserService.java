package com.webdemo.service;

import com.webdemo.entity.User;
import com.webdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * 用户服务层
 * 处理用户相关的业务逻辑
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 用户登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回 null
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            System.out.println("用户不存在: " + username);
            return null;
        }
        
        if (user.getPassword().equals(password)) {
            System.out.println("用户登录成功: " + username);
            return user;
        }
        
        System.out.println("密码错误: " + username);
        return null;
    }

    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 存在返回 true，否则返回 false
     */
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    /**
     * 获取所有用户
     * 
     * @return 用户列表
     */
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 根据ID获取用户
     * 
     * @param id 用户ID
     * @return 用户对象，如果不存在返回 null
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 保存用户
     * 
     * @param user 用户对象
     * @return 保存后的用户对象
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 删除用户
     * 
     * @param id 用户ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}