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
