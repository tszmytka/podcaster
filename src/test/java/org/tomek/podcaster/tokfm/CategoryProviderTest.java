package org.tomek.podcaster.tokfm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.tomek.podcaster.tokfm.dal.Categories;
import org.tomek.podcaster.tokfm.model.Category;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryProviderTest {
    private CategoryProvider categoryProvider;

    @Mock
    private Categories categories;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryProvider = new CategoryProvider(categories);
    }


    @Test
    void canGetCategories() {
        HashMap<Integer, Category> categoriesFetched = new HashMap<>();
        when(categories.fetchCategories()).thenReturn(categoriesFetched);
        assertEquals(categoriesFetched, categoryProvider.getCategories());
        verify(categories).fetchCategories();
    }
}