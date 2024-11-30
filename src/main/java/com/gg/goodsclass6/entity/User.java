package com.gg.goodsclass6.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {

    @TableId(type =IdType.ASSIGN_UUID)
    private String uid;

    /**
     * 
     */
    private String loginname;

    /**
     * 
     */
    private String loginpass;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private String activationcode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}