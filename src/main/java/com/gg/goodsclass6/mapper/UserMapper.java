package com.gg.goodsclass6.mapper;

import com.gg.goodsclass6.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @Entity com.gg.goodsclass6.entity.User
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select  *  from  `user` where  loginname = #{loginname}  ")
    User selectUserByLoginname(String loginname);
    @Select("select *  from  `user` where  email = #{email} ")
    User selectUserByEmail(String email);
    @Insert("insert into  `user` values (#{uid},#{loginname},#{loginpass},#{email},#{status},#{activationcode})")
    boolean insertUser(User user);
    @Select("select  *  from `user` where  activationCode = #{activationCode} ")
    User selectUserByactivationCode(String activationCode);
    @Select("select *  from  `user` where loginname = #{loginname}  and loginpass=#{loginpass} ")
    User selectUserByLoginnameAndLoginpass(User user);


    //获取登录密码
    @Select("select * from user where  loginpass=#{loginpass}")
    User validateLoginpass(String loginpass);

    //修改登录密码
    @Select("update user set loginpass=#{loginpass} where loginname=#{loginname}")
    User updateLoginpass(User user);
}