package com.gg.goodsclass6.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.goodsclass6.entity.Category;
import com.gg.goodsclass6.mapper.CategoryMapper;
import com.gg.goodsclass6.povos.CategoryPovo;
import com.gg.goodsclass6.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServicelmpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<CategoryPovo> loadCategory() {
        return categoryMapper.selectCategoryByPidIsNull();
    }

}
