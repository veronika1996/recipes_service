package com.recepies_service.api;

import com.recepies_service.dto.RecipeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RecipeApi {

  @Operation(
      summary = "Create a new recipe",
      description = "This endpoint allows you to create a new recipe.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Recipe successfully created",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class))
          ),
          @ApiResponse(responseCode = "400", description = "Invalid input")
      }
  )
  ResponseEntity<RecipeDTO> createRecipe(
      @Parameter(description = "Recipe to be created") @RequestBody RecipeDTO recipeDTO);

  @Operation(
      summary = "Update an existing recipe",
      description = "This endpoint allows you to update an existing recipe by its name.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Recipe successfully updated",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Recipe not found")
      }
  )
  ResponseEntity<RecipeDTO> updateRecipe(
      @Parameter(description = "Name of the recipe to be updated") @PathVariable String name,
      @Parameter(description = "Updated recipe details") @RequestBody RecipeDTO recipeDTO);

  @Operation(
      summary = "Update an existing recipe by id",
      description = "This endpoint allows you to update an existing recipe by its id.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Recipe successfully updated",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Recipe not found")
      }
  )
  ResponseEntity<RecipeDTO> updateRecipeById(
      @Parameter(description = "Name of the recipe to be updated") @RequestParam Long id,
      @Parameter(description = "Updated recipe details") @RequestBody RecipeDTO recipeDTO);


  @Operation(
      summary = "Delete a recipe",
      description = "This endpoint allows you to delete a recipe by its name.",
      responses = {
          @ApiResponse(responseCode = "204", description = "Recipe successfully deleted"),
          @ApiResponse(responseCode = "404", description = "Recipe not found")
      }
  )
  ResponseEntity<Void> deleteRecipe(
      @Parameter(description = "Name of the recipe to be deleted") @PathVariable String name);

  @Operation(
      summary = "Delete a recipe by id",
      description = "This endpoint allows you to delete a recipe by its id.",
      responses = {
          @ApiResponse(responseCode = "204", description = "Recipe successfully deleted"),
          @ApiResponse(responseCode = "404", description = "Recipe not found")
      }
  )
  ResponseEntity<Void> deleteRecipeById(
      @Parameter(description = "Name of the recipe to be deleted") @RequestParam Long id);


  @Operation(
      summary = "Get all recipes",
      description = "This endpoint returns a list of all recipes.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "List of all recipes retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class))
          )
      }
  )
  List<RecipeDTO> getAllRecipes();

  @Operation(
      summary = "Get a recipe by name",
      description = "This endpoint allows you to get a recipe by its name.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Recipe retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Recipe not found")
      }
  )
  ResponseEntity<RecipeDTO> getRecipeByName(
      @Parameter(description = "Name of the recipe to be fetched") @PathVariable String name);

  @Operation(
      summary = "Get all recipes by username",
      description = "This endpoint allows you to get recipes by their username.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Recipes retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class))
          ),
      }
  )
  ResponseEntity<List<RecipeDTO>> getRecipesByUsername(@Parameter(description = "Username for which recipes should be fetched") @RequestParam String name);


}
