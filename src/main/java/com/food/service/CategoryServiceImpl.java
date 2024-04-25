package com.food.service;

import com.food.model.Category;
import com.food.model.Restaurent;
import com.food.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private RestaurentService restaurentService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category createCategory(String name, Long userId) {
        try {
            Restaurent restaurent = restaurentService.findRestaurentByUserId(userId) ;
            Category category = new Category();
            category.setName(name);
            category.setRestaurent(restaurent);
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Category> findCategoriesByRestaurentId(Long id) throws Exception {
        Restaurent restaurent = restaurentService.findRestaurentByUserId(id) ;

        return categoryRepository.findByRestaurentId(restaurent.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        return optionalCategory.get();
    }
}
