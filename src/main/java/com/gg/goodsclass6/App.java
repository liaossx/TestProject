package com.gg.goodsclass6;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
/*
* 扫描当前包下所有的接口，全自动生成对应的代理对象。
* 把这些个对象，自动放到IOC 容器中。
* */
/*
    IOC 容器 的功能，  => 解耦！！！
     class  A{
        @AutoWird
        private  B  b ;
     }

    如果你在A类中new了B类的对象，我就称 A和B发生了耦合。

    class  UserController{
        private  UserService  service  =  new UserService();
    }
*   class  A {
*       mian(){
*
*               B  b =  new B();
*       }
*   }
*
* */
/*
* SpringBoot集成JavaWeb原生组件（Servlet、Filter、Listener）
* */
@ServletComponentScan(basePackages = "com.gg.goodsclass6.filter")
@MapperScan("com.gg.goodsclass6.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
