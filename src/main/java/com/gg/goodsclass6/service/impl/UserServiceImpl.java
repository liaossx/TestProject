package com.gg.goodsclass6.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.goodsclass6.entity.User;

import com.gg.goodsclass6.povos.ResultPovo;
import com.gg.goodsclass6.service.UserService;
import com.gg.goodsclass6.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper  userMapper;
    /*校验用户名业务*/
    @Override
    public Boolean verifyLoginname(String loginname) {

        User  u  =  userMapper.selectUserByLoginname(loginname);
        if(u==null){
            return  true ;
        }
        return  false ;
    }
    /*校验邮箱业务*/
    @Override
    public Boolean verifyEmail(String email) {
        User  u  = userMapper.selectUserByEmail(email);
        if(u==null){
            return  true ;
        }
        return  false ;
    }
    @Transactional
    /*用户注册业务*/
    @Override
    public boolean regist(User user) {
        //未激活
        user.setStatus(0);
        //激活码  64  UUID
        String uuid = UUID.randomUUID().toString().replace("-","").toUpperCase();
        String uuid2 = UUID.randomUUID().toString().replace("-","").toUpperCase();
        user.setActivationcode(uuid+uuid2);

        return  userMapper.insertUser(user);

    }
    /*开启 事务管理  */
    /*如果你的业务方法  带有 写写操作增删改 ，就开启事务，自动出错回滚事务*/
    @Transactional


    @Override
    public User login(User user) {
        return  userMapper.selectUserByLoginnameAndLoginpass(user);
    }


    /*开启 事务管理  */
    /*如果你的业务方法  带有 写写操作增删改 ，就开启事务，自动出错回滚事务*/
    @Transactional
    @Override
    public ResultPovo activation(String activationCode) {
        //select  *  from  `user` where  activationCode =?
        ResultPovo povo = new ResultPovo();


        User u = userMapper.selectUserByactivationCode(activationCode);
        if (u != null) {
            if (u.getStatus() == 0) {
                /*1 第一次激活*/
                /*变更为已激活用户*/
                u.setStatus(1);
                povo.setMsg("恭喜你，激活成功");
                povo.setRes(true);
                /*操作数据库*/
                userMapper.updateById(u);
            } else {
                povo.setMsg("你不要重复激活");
                povo.setRes(false);
            }


        } else {
            /*激活码错误 */
            povo.setMsg("请使用正确的激活码");
            povo.setRes(false);
        }
        return   povo ;
    }

    @Override
    public User updateLoginpass(User user) {
        return userMapper.updateLoginpass(user);
    }
    @Override
    public Boolean validateLoginpass(String loginpass) {
        User u= userMapper.validateLoginpass(loginpass);
        if (u==null){
            return false;
        }
        return true;


    }


}
