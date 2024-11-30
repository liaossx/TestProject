package com.gg.goodsclass6.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.goodsclass6.entity.Category;
import com.gg.goodsclass6.povos.CategoryPovo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<CategoryPovo> loadCategory();

}
