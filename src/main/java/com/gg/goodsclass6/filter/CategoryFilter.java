package com.gg.goodsclass6.filter;

import com.gg.goodsclass6.povos.CategoryPovo;
import com.gg.goodsclass6.service.CategoryService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/*
* 用户访问服务器任意路径，换句话说，当前过滤器拦截全站。
*
* urlPatterns = "/*"  当前过滤器拦截任意路径
* */
@WebFilter(filterName = "CategoryFilter" ,urlPatterns = "/*")
public class CategoryFilter implements Filter {
    @Autowired
    private CategoryService   categoryService;
    /*
    * doFilter ：过滤器业务的具体实现
    * */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        /*1.转换类型*/
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse  res = (HttpServletResponse) response;
        /*2.业务逻辑*/
        /*从Session中获取一个叫做categorypovos*/
        HttpSession  session = req.getSession();
        /*
        * 规定好，用户加载类别 Session中叫  categorypovos
        * */
        List<CategoryPovo> povos = (List<CategoryPovo>) session.getAttribute("categorypovos");
        if(povos!=null){
            /*说明用户曾经加载过类别，无须再加载，直接放行*/
            chain.doFilter(request, response);
        }else{
            /*说明用户没加载过类别，让它去调用加载类别即可*/
            List<CategoryPovo> categoryPovos =categoryService.loadCategory();
            categoryPovos.forEach(categoryPovo -> System.out.println(categoryPovo));
            //把加载后的类别放到Session里
            session.setAttribute("categorypovos", categoryPovos);
            //让它回主页
            req.getRequestDispatcher("/index.jsp").forward(req,res);
        }
        //放行：chain.doFilter(request, response);
    }


    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

}
