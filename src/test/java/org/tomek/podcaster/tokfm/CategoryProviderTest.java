package org.tomek.podcaster.tokfm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tomek.podcaster.tokfm.dal.Categories;
import org.tomek.podcaster.tokfm.model.Category;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryProviderTest {
    private CategoryProvider categoryProvider;

    @Mock
    private Categories categories;

    @Mock
    private Cache<Integer, Category> cache;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryProvider = new CategoryProvider(categories, cache);
    }


    @Test
    void canGetCategoriesWithColdCache() {
        HashMap<Integer, Category> categoriesFetched = new HashMap<>();
        categoriesFetched.put(1, new Category(1, "abc123", null, "example.com", "pic.png"));
        when(categories.fetchCategories()).thenReturn(categoriesFetched);
        when(cache.iterator()).thenReturn(Collections.emptyListIterator());
        assertEquals(categoriesFetched, categoryProvider.getCategories());
        verify(categories).fetchCategories();
        verify(cache).putAll(any());
    }


    @Test
    void canGetCategoriesWithWarmCache() {
        ArrayList<Cache.Entry<Integer, Category>> cachedCategories = new ArrayList<>();
        Category category0 = mock(Category.class);
        cachedCategories.add(new CacheEntry(0, category0));
        when(cache.iterator()).thenReturn(cachedCategories.iterator());
        Map<Integer, Category> categoriesGotten = categoryProvider.getCategories();
        assertEquals(1, categoriesGotten.size());
        assertEquals(category0, categoriesGotten.get(0));
        verify(categories, never()).fetchCategories();
        verify(cache, never()).putAll(any());
    }


    private class CacheEntry implements Cache.Entry<Integer, Category> {
        private final Integer key;
        private final Category value;

        private CacheEntry(Integer key, Category value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Integer getKey() {
            return key;
        }

        @Override
        public Category getValue() {
            return value;
        }

        @Override
        public <T> T unwrap(Class<T> clazz) {
            return null;
        }
    }
}