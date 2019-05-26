package dev.tomek.podcaster.tokfm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void canBuildCategory() {
        final int id = 321;
        final String name = "unit-test name";
        final String[] authors = {"Author1", "Author2"};
        final String url = "www.example.com/category/1";
        final String picHref = "www.example.com/pics/category_1";
        Category category = new Category(id, name, authors, url, picHref);
        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(authors, category.getAuthors());
        assertEquals(url, category.getUrl());
        assertEquals(picHref, category.getPicHref());
    }
}