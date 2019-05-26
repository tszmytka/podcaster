package dev.tomek.podcaster.tokfm;

import dev.tomek.podcaster.tokfm.dal.Categories;
import dev.tomek.podcaster.tokfm.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.HashMap;
import java.util.Map;

public class CategoryProvider {
    Logger LOGGER = LoggerFactory.getLogger(CategoryProvider.class);

    private final Categories categories;

    private final Cache<Integer, Category> cache;


    public CategoryProvider(Categories categories, Cache<Integer, Category> cache) {
        this.categories = categories;
        this.cache = cache;
    }

    public Map<Integer, Category> getCategories() {
        // multi paste ctrl+shift+v
        // templates ctrl+alt+shift+j
        // refactor this ctrl+alt+shift+t

        HashMap<Integer, Category> cachedCategories = new HashMap<>();
        for (Cache.Entry<Integer, Category> entry : cache) {
            if (entry != null) {
                cachedCategories.put(entry.getKey(), entry.getValue());
            }
        }

        if (cachedCategories.isEmpty()) {
            LOGGER.warn("No cached category entries. Attempting to fetch.");
            cachedCategories.putAll(categories.fetchCategories());
            cache.putAll(cachedCategories);
        }
        return cachedCategories;
    }
}
