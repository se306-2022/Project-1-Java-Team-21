package com.group21.sneakerhub.repository.abstractions;

import com.group21.sneakerhub.model.Category;

import java.util.List;

/**
 * Interface for CategoryRepository
 */

public interface ICategoryRepository {

    public List<Category> getCategories();

}
