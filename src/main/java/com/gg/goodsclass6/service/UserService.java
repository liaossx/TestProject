package com.gg.goodsclass6.service;

import com.gg.goodsclass6.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.goodsclass6.povos.ResultPovo;


/**
 *
 */
public interface UserService extends IService<User> {

    Boolean verifyLoginname(String loginname);

    Boolean verifyEmail(String email);

    boolean regist(User user);

    ResultPovo activation(String activationCode);

    User login(User user);

    Boolean validateLoginpass(String loginpass, String loginname);
    boolean changePassword(String username, String newpass);

}
