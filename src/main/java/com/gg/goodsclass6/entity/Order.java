package com.gg.goodsclass6.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order implements Serializable {
    /**
     * 
     */
    @TableId
    private String oid;

    /**
     * 
     */
    private Date ordertime;

    /**
     * 
     */
    private BigDecimal total;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String uid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}