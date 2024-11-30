package com.gg.goodsclass6.povos;

import com.gg.goodsclass6.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CategoryPovo {
   private Category dad;
   private List<Category>children;
}
