package org.tomek.podcaster.tokfm;

import org.tomek.podcaster.tokfm.dal.Categories;
import org.tomek.podcaster.tokfm.model.Category;

import javax.cache.Cache;
import java.util.HashMap;
import java.util.Map;

public class CategoryProvider {

    private final Categories categories;

    private final Cache<Integer, Category> cache;


    public CategoryProvider(Categories categories, Cache<Integer, Category> cache) {
        this.categories = categories;
        this.cache = cache;
    }


    public Map<Integer, Category> getCategories() {
        HashMap<Integer, Category> cachedCategories = new HashMap<>();
        for (Cache.Entry<Integer, Category> entry : cache) {
            cachedCategories.put(entry.getKey(), entry.getValue());
        }
        if (cachedCategories.isEmpty()) {
            cachedCategories.putAll(categories.fetchCategories());
            cache.putAll(cachedCategories);
        }
        return cachedCategories;
    }


    public Category getCategory(int id) {
        return getCategories().get(id);
    }
}
