package com.gg.goodsclass6.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @TableName orderitem
 */
@TableName(value = "orderitem")
@Data
public class Orderitem implements Serializable {
    /**
     *
     */
    @TableId
    private String orderitemid;

    /**
     *
     */
    private Integer quantity;

    /**
     *
     */
    private BigDecimal subtotal;

    /**
     *
     */
    private String bid;

    /**
     *
     */
    private String bname;

    /**
     *
     */
    private BigDecimal currprice;

    /**
     *
     */
    private String imageB;

    /**
     *
     */
    private String oid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}