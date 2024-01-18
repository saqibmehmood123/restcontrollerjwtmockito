package com.example.pratisetest.web;

import com.example.pratisetest.model.Book;
import com.example.pratisetest.model.Category;
import com.example.pratisetest.services.BookService;
import com.example.pratisetest.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)

//@AutoConfigureMockMvc
public class BookControllerTest
{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    public static final List<Book> bookList = new ArrayList<>();
    public static final MediaType APPLICATION_JSON_UTF8 = new
            MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8") );

    public static final String  BASE_URL="http://localhost:8080/api/books";

    @BeforeAll
    static void beforeAllTests() {
       Book book = new Book();
       book.setTitle("myTitle");
        book.setAuthor("my author");
        book.setPublisher("my publisher");
        book.setPublishingYear(1984);
        book.setAuthor("my author");
        bookList.add(book);
        //
    }

        @Test
        @WithMockUser(value = "spring")
        void getAllBooks() throws Exception {
            when(bookService.getAllBooks() ).thenReturn(bookList);

            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                            .header("Authorization", format("Bearer %s", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXFpMkBnbWFpbC5jb20iLCJpYXQiOjE3MDU1OTU2NzksImV4cCI6MTcwNTU5NzExOX0.3zJj_S3c6Mnnjw70ao28FIUZzBMt6Ai8jmBaPct42WU" ))
           .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].title", equalTo("myTitle") ) );
        }

      /*  @Test
        void  createBook() throws Exception
        {


            Book book = new Book();
            book.setAuthor("auth");
            book.setPublisher("publisher");
            book.setTitle("title");
            book.setPublishingYear(1982);
            // Mock the service method
            when(bookService.createBook(any(Book.class))).thenReturn(book);
            // Act and Assert
            mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(book)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                  //  .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.title").value("title"));
        }
/*
    @Test
    void  UpdateCustomerTest() throws Exception
    {

        Long customerId = 1L;

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);
        updatedCustomer.setName("Updated Name");

        Customer outputCustomer = new Customer();
        outputCustomer.setId(customerId);
        outputCustomer.setName("Updated Name");

        // Mock the service method
        when(customerServices.updateCustomer(customerId, updatedCustomer)).thenReturn(updatedCustomer);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Name"));

    }
*/
/*
    @Test
    public void testDeleteCustomer() throws Exception {
        // Arrange
        Long customerId = 1L;
        // Mock the service method to do nothing (void method)
        doNothing().when(bookService).deleteBook(customerId);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Verify that the service method was called
        verify(bookService, times(1)).deleteBook(customerId);
    }
*/
}
