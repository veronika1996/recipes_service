package com.recepies_service.service;

import com.recepies_service.dto.IngredientDTO;
import com.recepies_service.dto.RecipeDTO;
import com.recepies_service.entity.IngredientClient;
import com.recepies_service.entity.RecipeEntity;
import com.recepies_service.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
//
@Service
@Validated
public class RecipeService {

    private static final String RECIPE_NOT_FOUND_ERROR = "Recipe not found for recipe name: ";
    private static final String RECIPE_NAME_ALREADY_EXIST = "Recipe name already exists: ";


    private final RecipeRepository recipeRepository;
    private final IngredientClient ingredientClient;

    public Page<RecipeDTO> searchRecipesByName(String name, Pageable pageable) {
        return recipeRepository.findByNameContainingIgnoreCase(name, pageable)
            .map(this::mapToDto);
    }

    public RecipeService(RecipeRepository recipeRepository, IngredientClient ingredientClient) {
        this.recipeRepository = recipeRepository;
        this.ingredientClient = ingredientClient;
    }

    public RecipeDTO createRecipe(@Valid RecipeDTO recipeDTO) {
        if( recipeRepository.findByName(recipeDTO.getName()).isPresent()) {
            throw new IllegalArgumentException(RECIPE_NAME_ALREADY_EXIST + recipeDTO.getName());
        }

        RecipeEntity recipeEntity = this.mapToEntity(recipeDTO);
        RecipeEntity savedEntity = recipeRepository.save(recipeEntity);
        return mapToDto(savedEntity);
    }

    public RecipeEntity mapToEntity(RecipeDTO dto) {
        List<Long> ingredientIds = dto.getIngredients().stream()
            .map(IngredientDTO::getId)
            .toList();

        RecipeEntity entity = new RecipeEntity();
        entity.setName(dto.getName());
        entity.setIngredientIds(ingredientIds);
        entity.setPreparation(dto.getPreparation());
        entity.setCaloriesNumber(dto.getCaloriesNumber());
        entity.setCategory(dto.getCategory());
        entity.setNumberOfPortions(dto.getNumberOfPortions());
        entity.setCreatedBy(dto.getCreatedBy());

        return entity;
    }

    @Transactional
    public RecipeDTO updateRecipe(String name, @Valid RecipeDTO recipeDTO) {
        //checking if the entity exists in the database
        RecipeEntity recipeEntity = findEntityByName(name);

        recipeEntity = mapToEntity(recipeDTO);
        RecipeEntity updatedEntity = recipeRepository.save(recipeEntity);
        return mapToDto(updatedEntity);
    }

    public void deleteRecipeByName(String name) {
        RecipeEntity recipeEntity = findEntityByName(name);

        recipeRepository.deleteByName(name);
    }
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public RecipeDTO getRecipeByName(String name) {
        RecipeEntity recipeEntity = findEntityByName(name);
        return mapToDto(recipeEntity);
    }

    private RecipeEntity findEntityByName(String name) {
        return recipeRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(RECIPE_NOT_FOUND_ERROR + name));
    }


    public RecipeDTO mapToDto(RecipeEntity entity) {
        List<IngredientDTO> ingredients = ingredientClient.getIngredientsByIds(entity.getIngredientIds());
        return new RecipeDTO(
            entity.getName(),
            ingredients,
            entity.getPreparation(),
            entity.getCaloriesNumber(),
            entity.getCategory(),
            entity.getNumberOfPortions(),
            entity.getCreatedBy()
        );
    }

}
