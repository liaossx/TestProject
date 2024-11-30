package com.gg.goodsclass6.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gg.goodsclass6.entity.Category;
import com.gg.goodsclass6.povos.CategoryPovo;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {
//       查询一级类别们
       List<CategoryPovo>  selectCategoryByPidIsNull();
//       查询二级类别们
       List<Category> selectCategoryByPid(String pid);

}
