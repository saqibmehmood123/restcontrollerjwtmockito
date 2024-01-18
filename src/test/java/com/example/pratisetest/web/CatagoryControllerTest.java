package com.example.pratisetest.web;

import com.example.pratisetest.model.Book;
import com.example.pratisetest.model.Category;
import com.example.pratisetest.model.Customer;
import com.example.pratisetest.services.CategoryService;
import com.example.pratisetest.services.CustomerServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@WebMvcTest(CategoryController.class)
@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureMockMvc
public class CatagoryControllerTest
{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    public static final List<Category> customerList = new ArrayList<>();
    public static final MediaType APPLICATION_JSON_UTF8 = new
            MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8") );

    public static final String  BASE_URL="http://localhost:8080/api/categories";
   private  String token;
    @BeforeAll
    static void beforeAllTests() {
        Category category = new Category();
        category.setName("saqib");
        category.setDescription ("descritpion for catagory");

        customerList.add(category);
        //
    }

        @Test
        void getAllCustomers() throws Exception {
            when(categoryService.getAllCategories() ).thenReturn(customerList);

            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)

                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk()) ;
                    //.andExpect(jsonPath("$[0].name", equalTo("saqib") )

        }

        @Test
        void  createCategory() throws Exception
        {
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


          //  outputCatagory.addComment(book);


            System.out.println(new ObjectMapper().writeValueAsString(outputCatagory));


            // Mock the service method
            when(categoryService.createCategory(any(Category.class))).thenReturn(outputCatagory);
            // Act and Assert
            token = generateToken("bbc413" );


            mockMvc.perform(MockMvcRequestBuilders.post("/api/categories/category")
                             .  header("AUTHORIZATION","Bearer "+token).contentType(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(outputCatagory)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
//                    .andExpect(jsonPath("$.id").value(1L))
//                    .andExpect(jsonPath("$.name").value("John Doe"));
        }

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
    ///    when(customerServices.updateCustomer(customerId, updatedCustomer)).thenReturn(updatedCustomer);
        token = generateToken("bbc414" );

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", customerId)
                        .  header("AUTHORIZATION","Bearer "+token).contentType(MediaType.APPLICATION_JSON)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk());
          //      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerId))
          //      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Name"));

    }


    @Test
    public void testDeleteCustomer() throws Exception {
        // Arrange
        Long customerId = 1L;
        // Mock the service method to do nothing (void method)
        doNothing().when(categoryService).deleteCategory(customerId);
        // Act and Assert
        token = generateToken("bbc415" );

//BASE_URL+
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", customerId)
                        .  header("AUTHORIZATION","Bearer "+token).contentType(MediaType.APPLICATION_JSON)

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Verify that the service method was called
   //     verify(categoryService, times(1)).deleteCategory(customerId);
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
