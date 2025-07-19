package com.recepies_service.controller;

import com.recepies_service.api.RecipeApi;
import com.recepies_service.dto.RecipeDTO;
import com.recepies_service.service.RecipeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController implements RecipeApi {

  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @PostMapping
  public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
    RecipeDTO response = recipeService.createRecipe(recipeDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @PutMapping("/{name}")
  public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable String name, @RequestBody RecipeDTO recipeDTO) {
    RecipeDTO response = recipeService.updateRecipe(name, recipeDTO);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @DeleteMapping("/{name}")
  public ResponseEntity<Void> deleteRecipe(@PathVariable String name) {
    recipeService.deleteRecipeByName(name);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<RecipeDTO> updateRecipeById(@RequestParam Long id, @RequestBody RecipeDTO recipeDTO) {
    RecipeDTO response = recipeService.updateRecipeById(id, recipeDTO);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteRecipeById(@RequestParam Long id) {
    recipeService.deleteRecipeById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public List<RecipeDTO> getAllRecipes() {
    return recipeService.getAllRecipes();
  }

  @GetMapping("/{name}")
  public ResponseEntity<RecipeDTO> getRecipeByName(@PathVariable String name) {
    RecipeDTO response = recipeService.getRecipeByName(name);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @GetMapping(params = "username")
  public ResponseEntity<List<RecipeDTO>>  getRecipesByUsername(@RequestParam String username) {
    List<RecipeDTO> response = recipeService.getRecipesByUsername(username);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}
