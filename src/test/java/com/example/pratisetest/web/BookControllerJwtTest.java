package com.example.pratisetest.web;

import com.example.pratisetest.model.Book;
import com.example.pratisetest.security.impl.UserServiceImpl;
import com.example.pratisetest.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerJwtTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookController messageController;
    @MockBean
    BookService bookService;
    private  String token;///="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXFpMkBnbWFpbC5jb20iLCJpYXQiOjE3MDU1OTU2NzksImV4cCI6MTcwNTU5NzExOX0.3zJj_S3c6Mnnjw70ao28FIUZzBMt6Ai8jmBaPct42WU";
    public static final List<Book> bookList = new ArrayList<>();
    @BeforeEach
    public void setup() throws IOException, InterruptedException {
        Book book = new Book();
        book.setTitle("myTitle");
        book.setAuthor("my author");
        book.setPublisher("my publisher");
        book.setPublishingYear(1984);
        book.setAuthor("my author");
        bookList.add(book);

System.out.println(" -------------  "  +  token);
    }


    @Test
    public void adminMessageTestSuccess() throws Exception {

        when(bookService.getAllBooks() ).thenReturn(bookList);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/books")

                .accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", equalTo("myTitle") )

    }
    @Test
    void  createBook() throws Exception
    {
        token = generateToken("bb213" );
        Book book = new Book();
        book.setAuthor("auth");
        book.setPublisher("publisher");
        book.setTitle("title");
        book.setPublishingYear(1982);
        // Mock the service method
        when(bookService.createBook(any(Book.class))).thenReturn(book);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/book")
                        .  header("AUTHORIZATION","Bearer "+token).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk());
                //  .andExpect(jsonPath("$.id").value(1L))
              ///  .andExpect(jsonPath("$.title").value("title"));
    }
    @Test
    void  UpdateCustomerTest() throws Exception
    {

       token = generateToken("bb313" );

        Long customerId = 1L;

        Book book = new Book();
        book.setAuthor("auth");
        book.setPublisher("publisher");
        book.setTitle("title");
        book.setPublishingYear(1982);

        // Mock the service method
        when(bookService.updateBook ( any(Long.class) ,any(Book.class))).thenReturn(book);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", customerId)
                        .  header("AUTHORIZATION","Bearer "+token).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk());
           //     .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerId))
            //    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Name"));

    }
    @Test
    public void testDeleteCustomer() throws Exception {
        token = generateToken("bb413" );

        // Arrange
        Long customerId = 1L;
        // Mock the service method to do nothing (void method)
        doNothing().when(bookService).deleteBook(customerId);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", customerId)
                        .  header("AUTHORIZATION","Bearer "+token).contentType(MediaType.APPLICATION_JSON)

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Verify that the service method was called
       /// verify(bookService, times(1)).deleteBook(customerId);
    }


    private String generateToken(String emailAddress ) throws IOException, InterruptedException {

        String requestBody1 = "{\n" +
                "    \"firstName\":\"saqib1\",\n" +
                "    \"lastName\": \"saqib2\",\n" +
                "     \"email\": \"" +  emailAddress + "\" ,\n" +
                "    \"password\": \"123456\"\n" +
                "    \n" +
                "} ";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody1))
                .uri(URI.create("http://localhost:8080/api/v1/auth/signup"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());


        String[] parts = response.body().split(":");
        String part1 = parts[0]; // 004
        token = parts[1]; // 034556
      return   token = token.substring(1,token.length()-2);
    }

}
