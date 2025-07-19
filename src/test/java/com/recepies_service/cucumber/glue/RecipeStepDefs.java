package com.recepies_service.cucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recepies_service.dto.ErrorDto;
import com.recepies_service.dto.IngredientDTO;
import com.recepies_service.dto.RecipeDTO;
import com.recepies_service.entity.IngredientClient;
import com.recepies_service.entity.RecipeEntity;
import com.recepies_service.entity.RecipeIngredientEntity;
import com.recepies_service.enums.Category;
import com.recepies_service.enums.RecipeCategory;
import com.recepies_service.repository.RecipeRepository;
import com.recepies_service.service.RecipeService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
public class RecipeStepDefs {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired private RecipeRepository recipeRepository;

  @Autowired private RecipeService recipeService;

  @Autowired private IngredientClient ingredientClient;

  private final RestTemplate restTemplate = new RestTemplate();
  private ResponseEntity<String> response;
  private RecipeDTO recipeDTO;
  private Long recipeId;

  @When("I send a POST request to {string}")
  public void iSendAPostRequestTo(String path) {
    IngredientDTO ingredientDTO =  new IngredientDTO("Tomato", 25, "Admin", Category.FRUIT);
    ingredientDTO.setId(20L);
    when(ingredientClient.getIngredientByNameAndUsername("Tomato", "Admin")).thenReturn(ingredientDTO);
    when(ingredientClient.getIngredientsByIds(List.of(20L))).thenReturn(List.of(ingredientDTO));
    String url = "http://localhost:" + 8084 + "/meal_plan" + path;
    try {
      response = restTemplate.postForEntity(url, recipeDTO, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Given("the system is initialized")
  public void theSystemIsInitialized() {
    recipeRepository.deleteAll();
    RecipeEntity entity = new RecipeEntity("Recipe1", null, "Way of preparation", 500, RecipeCategory.DINNER, 2, "Admin");
    RecipeIngredientEntity rie = new RecipeIngredientEntity();
    rie.setIngredientId(20L);
    rie.setQuantity(50.0);
    rie.setRecipe(entity);
    entity.setIngredientsWithQuantity(List.of(rie));
    RecipeEntity savedEntity = recipeRepository.save(entity);

    recipeId = savedEntity.getId();
    RecipeEntity savedRecipe = recipeRepository.findByName("Recipe1").orElse(null);
    assertNotNull("Recipe should be saved and found", savedRecipe);
  }

  @Given("the recipe data in JSON file {string}")
  public void theRecipeDataInJsonFile(String fileName) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    // Load the JSON file from the resources folder
    recipeDTO =
        objectMapper.readValue(
            new File("src/test/java/resources/" + fileName), RecipeDTO.class);
  }

  @When("I send a DELETE request to {string}")
  public void iSendADELETERequestTo(String path) {
    String url = "http://localhost:" + 8084 + "/meal_plan" + path;

    try {
      response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Then("I should receive a {int} response")
  public void iShouldReceiveAResponse(int statusCode) {
    assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
  }

  @And("the recipe response should be like the recipe data in JSON file {string}")
  public void theRecipeResponseShouldBeLikeTheRecipeDataInJsonFile(String fileName)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    RecipeDTO expectedRecipeDTO =
        objectMapper.readValue(
            new File("src/test/java/resources/" + fileName), RecipeDTO.class);
    RecipeDTO actualRecipeDTO =
        objectMapper.readValue(response.getBody(), RecipeDTO.class);

    assertEquals(expectedRecipeDTO.getName(), actualRecipeDTO.getName());
    assertEquals(expectedRecipeDTO.getPreparation(), actualRecipeDTO.getPreparation());
    assertEquals(expectedRecipeDTO.getCaloriesNumber(), actualRecipeDTO.getCaloriesNumber());
    assertEquals(expectedRecipeDTO.getNumberOfPortions(), actualRecipeDTO.getNumberOfPortions());
    assertEquals(expectedRecipeDTO.getCategory(), actualRecipeDTO.getCategory());
  }


  @Then("the response should contain error message {string}")
  public void theResponseShouldContainErrorMessage(String errorMessage) throws IOException {
    ErrorDto errorDto = objectMapper.readValue(response.getBody(), ErrorDto.class);
    assertEquals(errorMessage, errorDto.getMessage());
  }

  @And("the recipe {string} should be deleted from the system")
  public void theRecipeShouldBeDeletedFromTheSystem(String recipeName) {
    try {
      String getUrl = "http://localhost:" + 8084 + "/meal_plan/recipes/";
      // Try to get the recipe after deletion
      ResponseEntity<String> getResponse =
          restTemplate.exchange(getUrl + recipeName, HttpMethod.GET, null, String.class);
      // Expect 404 response when recipe is not found
      assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    } catch (Exception e) {
      // Recipe is deleted, so no entity is found, and exception will be thrown
    }
  }

  @When("I send a GET request to {string}")
  public void iSendAGETRequestTo(String path) {
    String url = "http://localhost:" + 8084 + "/meal_plan" + path;
    IngredientDTO ingredientDTO =  new IngredientDTO("Tomato", 25, "Admin", Category.FRUIT);
    ingredientDTO.setId(20L);
    when(ingredientClient.getIngredientsByIds(List.of(20L))).thenReturn(List.of(ingredientDTO));
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Given("the recipe list is empty")
  public void theRecipeListIsEmpty() {
    recipeRepository.deleteAll();
  }

  @When("I send a PUT request to {string}")
  public void iSendAPUTRequestTo(String path) {
    IngredientDTO ingredientDTO =  new IngredientDTO("Tomato", 25, "Admin", Category.FRUIT);
    ingredientDTO.setId(20L);
    when(ingredientClient.getIngredientByNameAndUsername("Tomato", "Admin")).thenReturn(ingredientDTO);
    when(ingredientClient.getIngredientsByIds(List.of(20L))).thenReturn(List.of(ingredientDTO));
    String url = "http://localhost:" + 8084 + "/meal_plan/" + path;
    try {
      response =
          restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(recipeDTO), String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @And("the response should contain an empty list")
  public void theResponseShouldContainAnEmptyList() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<RecipeDTO> recipes =
        objectMapper.readValue(response.getBody(), new TypeReference<List<RecipeDTO>>() {});

    assertTrue(recipes == null || recipes.isEmpty());
  }

  @Given("the recipe list contains {int} recipe")
  public void theRecipeListContainsRecipe(int size) {
    recipeRepository.deleteAll();
    for (int i = 0; i < size; i++) {
      RecipeEntity entity = new RecipeEntity("Name" + i, null, "Way of preparation", 500, RecipeCategory.DINNER, 2, "Admin1");
      RecipeIngredientEntity rie = new RecipeIngredientEntity();
      rie.setIngredientId(20L);
      rie.setQuantity(50.0);
      rie.setRecipe(entity);
      entity.setIngredientsWithQuantity(List.of(rie));
      recipeRepository.save(entity);
    }
    List<RecipeEntity> savedRecipe1 = recipeRepository.findAll();
    System.out.println("Dosla si dovde savedRecipe size je : " + savedRecipe1.size());
    assertEquals(savedRecipe1.size(), size);
  }

  @And("the response should contain {int} recipe")
  public void theResponseShouldContainNumberRecipe(int size) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<RecipeDTO> recipes =
        objectMapper.readValue(response.getBody(), new TypeReference<List<RecipeDTO>>() {});

    assertEquals(recipes.size(), size);
  }

  @And("the response should be list of data as in JSON file {string}")
  public void theResponseShouldBeListOfDataAsInJSONFile(String filePath) {
    try {
      String expectedJson =
          new String(Files.readAllBytes(Paths.get("src/test/java/resources/" + filePath)));

      String actualJson = response.getBody();
      System.out.println("Actual " + actualJson);

      JSONAssert.assertEquals(
          expectedJson, actualJson, false);

      System.out.println("Expected " + expectedJson);

    } catch (Exception e) {
      throw new RuntimeException("Error during JSON comparison: " + e.getMessage(), e);
    }
  }

  @When("I send a GET by username request to {string}")
  public void iSendAGETByUsernameRequestTo(String path) {
    String url = "http://localhost:" + 8084 + "/meal_plan" + path;
    IngredientDTO ingredientDTO =  new IngredientDTO("Tomato", 25, "Admin", Category.FRUIT);
    ingredientDTO.setId(20L);
    when(ingredientClient.getIngredientsByIds(List.of(20L))).thenReturn(List.of(ingredientDTO));
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @When("I send a DELETE by id request")
  public void iSendADELETEByIdRequest() {
    String url = "http://localhost:" + 8084 + "/meal_plan/recipes?id=" + recipeId;

    try {
      response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @When("I send a PUT by id request")
  public void iSendAPUTByIdRequest() {
    IngredientDTO ingredientDTO =  new IngredientDTO("Tomato", 25, "Admin", Category.FRUIT);
    ingredientDTO.setId(20L);
    when(ingredientClient.getIngredientByNameAndUsername("Tomato", "Admin")).thenReturn(ingredientDTO);
    when(ingredientClient.getIngredientsByIds(List.of(20L))).thenReturn(List.of(ingredientDTO));
    String url = "http://localhost:" + 8084 + "/meal_plan/recipes?id=" + recipeId;;
    try {
      response =
          restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(recipeDTO), String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  public RecipeStepDefs() {
    MockitoAnnotations.openMocks(this);
  }
}
