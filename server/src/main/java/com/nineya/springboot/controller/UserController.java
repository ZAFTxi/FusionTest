package com.nineya.springboot.controller;


import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.User;
import com.nineya.springboot.service.ModelService;
import com.nineya.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // 这里只是一个示例，实际的登录逻辑会更复杂
        return "Please login with a username and password.";
    }

    @GetMapping("/authenticate")
    public String authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return "Authenticated user: " + authentication.getName();
        }
        return "Not authenticated";
    }
}
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private static String UPLOAD_PATH;

    @GetMapping("/getAll")
    public R insertUser() {
        User user=new User();
        user.setName("na");
        return userService.insertUser(user);
    }

}
