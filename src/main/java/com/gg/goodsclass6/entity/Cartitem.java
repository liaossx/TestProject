package com.gg.goodsclass6.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName cartitem
 */
@TableName(value ="cartitem")
@Data
public class Cartitem implements Serializable {
    /**
     * 
     */
    @TableId(type =IdType.ASSIGN_UUID)
    private String cartitemid;

    /**
     * 
     */
    private Integer quantity;

    /**
     * 
     */
    private String bid;

    /**
     *
     */
    private String uid;

    /**
     * 
     */
    private Integer shunxu;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}