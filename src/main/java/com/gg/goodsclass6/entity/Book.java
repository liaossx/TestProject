package com.gg.goodsclass6.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName book
 */
@TableName(value ="book")
/*
* 1.无参构造器
* 2.getter  and  setter 方法
* */
@Data  //直接提供给你get set   toString
@NoArgsConstructor  //无参构造器
@AllArgsConstructor  //有参构造器
public class Book implements Serializable {



    /**
     * 
     */
    @TableId
    private String bid;

    /**
     * 
     */
    private String bname;

    /**
     * 
     */
    private String author;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 
     */
    private BigDecimal currprice;

    /**
     * 
     */
    private BigDecimal discount;

    /**
     * 
     */
    private String press;

    /**
     * 
     */
    private String publishtime;

    /**
     * 
     */
    private Integer edition;

    /**
     * 
     */
    private Integer pagenum;

    /**
     * 
     */
    private Integer wordnum;

    /**
     * 
     */
    private String printtime;

    /**
     * 
     */
    private Integer booksize;

    /**
     * 
     */
    private String paper;

    /**
     * 
     */
    private String cid;

    /**
     * 
     */
    private String imageW;

    /**
     * 
     */
    private String imageB;

    /**
     * 
     */
    private Integer shunxu;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



}