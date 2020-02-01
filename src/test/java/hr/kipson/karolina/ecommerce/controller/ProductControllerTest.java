package hr.kipson.karolina.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void invalidProductSubmit() throws Exception {
        this.mockMvc
                .perform(
                        post("/emusicstore/add")
                                .param("name", "Test")
                                .param("description", "Teste test test")
                                .param("price", "34")
                                .param("unitInStock", "3")
                                .param("manufacturer", "Samsung")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("admin").password("admin").roles("USER", "ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("newProduct"));

    }

    @Test
    public void validProductSubmit() throws Exception {
        this.mockMvc
                .perform(
                        post("/emusicstore/add")
                                .param("name", "Test")
                                .param("description", "Teste test test")
                                .param("price", "34")
                                .param("unitInStock", "3")
                                .param("manufacturer", "Samsung")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("admin").password("admin").roles("USER", "ADMIN"))
                )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/emusicstore/productList"));

    }


}
