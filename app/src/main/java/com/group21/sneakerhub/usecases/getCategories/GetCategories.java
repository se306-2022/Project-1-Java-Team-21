package com.group21.sneakerhub.usecases.getCategories;


import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.repository.CategoryRepository;
import com.group21.sneakerhub.repository.ICategoryRepository;

import java.util.List;

public class GetCategories implements IGetCategories{
    private  static  final ICategoryRepository categoryRepository = CategoryRepository.getInstance();
    @Override
    public List<Category> getCategories() {
        return categoryRepository.getCategories();
    }
}