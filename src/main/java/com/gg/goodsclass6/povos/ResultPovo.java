package com.gg.goodsclass6.povos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //直接提供给你get set   toString
@NoArgsConstructor  //无参构造器
@AllArgsConstructor  //有参构造器
public class ResultPovo {

    private Boolean  res ;
    private   String  msg ;
}
