package com.example.to_do_list.controller;

import com.example.to_do_list.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void createAndGetCategory() {
        Category request = new Category();
        request.setName("Учёба");
        request.setColor("#123456");

        ResponseEntity<Category> createResponse =
                restTemplate.postForEntity(url("/categories"), request, Category.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Category created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();

        ResponseEntity<Category> getResponse =
                restTemplate.getForEntity(url("/categories/" + created.getId()), Category.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo("Учёба");
    }
}
