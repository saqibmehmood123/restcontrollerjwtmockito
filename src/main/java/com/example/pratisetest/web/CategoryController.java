package com.example.pratisetest.web;

import com.example.pratisetest.dao.CategoryRepository;
import com.example.pratisetest.model.Book;
import com.example.pratisetest.model.Category;
import com.example.pratisetest.services.BookService;
import com.example.pratisetest.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {

        Category outputCatagory = new Category();
        outputCatagory.setId(1L);
        outputCatagory.setName("John Doe");
        outputCatagory.setDescription("this is description");

        Book book = new Book();
        book.setCategory(outputCatagory);
        book.setAuthor("auth");
        book.setPublisher("publisher");
        book.setTitle("title");
        book.setPublishingYear(1982);


        outputCatagory.addBook(book);

      ///  bookService.createBook(book);
        categoryService.createCategory(outputCatagory);
        return categoryService.getAllCategories();
    }

    @PostMapping("/category")
    public Category createCategory(@RequestBody Category category)
    {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {


        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}