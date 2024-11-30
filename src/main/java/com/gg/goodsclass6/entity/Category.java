package com.gg.goodsclass6.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName category
 */
@TableName(value ="category")
@Data
public class Category implements Serializable {
    /**
     * 
     */
    @TableId
    private String cid;

    /**
     * 
     */
    private String cname;

    /**
     * 
     */
    private String pid;

    /**
     * 
     */
    private String miaoshu;

    /**
     * 
     */
    private Integer shunxu;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}