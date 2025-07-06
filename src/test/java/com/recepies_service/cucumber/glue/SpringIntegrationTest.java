package com.recepies_service.cucumber.glue;

import static org.mockito.ArgumentMatchers.anyList;

import com.recepies_service.RecipesServiceApplication;
import com.recepies_service.config.GlobalExceptionHandler;
import com.recepies_service.config.SecurityConfig;
import com.recepies_service.dto.IngredientDTO;
import com.recepies_service.entity.IngredientClient;
import com.recepies_service.enums.Category;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@SpringBootTest(classes = {RecipesServiceApplication.class, SecurityConfig.class,
    GlobalExceptionHandler.class,
    IngredientClient.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SpringIntegrationTest {

    @MockitoBean
    private IngredientClient ingredientClient;

    @Test
    public void test() {
        System.out.println("Running Cucumber tests...");
    }
}
