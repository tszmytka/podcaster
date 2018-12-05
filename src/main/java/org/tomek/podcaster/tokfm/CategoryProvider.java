package org.tomek.podcaster.tokfm;

import org.tomek.podcaster.tokfm.dal.Categories;
import org.tomek.podcaster.tokfm.model.Category;

import java.util.Map;

public class CategoryProvider {

    private final Categories categories;


    public CategoryProvider(Categories categories) {
        this.categories = categories;
    }


    public Map<Integer, Category> getCategories() {
        return categories.fetchCategories();
    }


    public Category getCategory(int id) {
        return getCategories().get(id);
    }
}
