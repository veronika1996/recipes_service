package com.recepies_service.service;

import com.recepies_service.dto.IngredientDTO;
import com.recepies_service.dto.IngredientQuantityDto;
import com.recepies_service.dto.RecipeDTO;
import com.recepies_service.entity.IngredientClient;
import com.recepies_service.entity.RecipeEntity;
import com.recepies_service.entity.RecipeIngredientEntity;
import com.recepies_service.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class RecipeService {

    private static final String RECIPE_NOT_FOUND_ERROR = "Recipe not found for recipe name: ";
    private static final String RECIPE_NAME_ALREADY_EXIST = "Recipe name already exists: ";
    private static final String INGREDIENT_NOT_VALID_ERROR = "Ingredient is not valid.";


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

    @Transactional
    public RecipeDTO updateRecipe(String name, @Valid RecipeDTO recipeDTO) {
        RecipeEntity foundEntity = findEntityByName(name);

        RecipeEntity recipeEntity = mapToEntity(recipeDTO);
        recipeEntity.setId(foundEntity.getId());
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
        List<Long> ingredientIds = entity.getIngredientsWithQuantity()
            .stream()
            .map(RecipeIngredientEntity::getIngredientId)
            .toList();

        List<IngredientDTO> ingredientDTOs = ingredientClient.getIngredientsByIds(ingredientIds);

        List<IngredientQuantityDto> ingredientsWithQuantities = entity.getIngredientsWithQuantity().stream()
            .map(ri -> {
                IngredientDTO dto = ingredientDTOs.stream()
                    .filter(i -> i.getId().equals(ri.getIngredientId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(INGREDIENT_NOT_VALID_ERROR));

                return new IngredientQuantityDto(dto.getName(), ri.getQuantity());
            })
            .toList();

        return new RecipeDTO(
            entity.getName(),
            ingredientsWithQuantities,
            entity.getPreparation(),
            entity.getCaloriesNumber(),
            entity.getCategory(),
            entity.getNumberOfPortions(),
            entity.getCreatedBy()
        );
    }

    public RecipeEntity mapToEntity(RecipeDTO dto) {
        RecipeEntity entity = new RecipeEntity();

        entity.setName(dto.getName());
        entity.setPreparation(dto.getPreparation());
        entity.setCaloriesNumber(calculateCaloriesPerPortion(dto.getIngredients(), dto.getNumberOfPortions()));
        entity.setCategory(dto.getCategory());
        entity.setNumberOfPortions(dto.getNumberOfPortions());
        entity.setCreatedBy(dto.getCreatedBy());
        List<RecipeIngredientEntity> ingredientsWithQuantity = dto.getIngredients().stream()
            .map(iq -> {
                RecipeIngredientEntity rie = new RecipeIngredientEntity();
                IngredientDTO i = ingredientClient.getIngredientByName(iq.getName());
                rie.setIngredientId(i.getId());
                rie.setQuantity(iq.getQuantity());
                rie.setRecipe(entity);
                return rie;
            })
            .toList();

        entity.setIngredientsWithQuantity(ingredientsWithQuantity);

        return entity;
    }

    private Integer calculateCaloriesPerPortion(List<IngredientQuantityDto> ingredients,
        Integer numberOfPortions) {
        double result = 0.0;
        for(IngredientQuantityDto quantityDto : ingredients) {
            IngredientDTO ingredientDTO = ingredientClient.getIngredientByName(quantityDto.getName());
            result += ingredientDTO.getCalorieNumber() * quantityDto.getQuantity() / 100;
        }
        return (int) (result/numberOfPortions);
    }

}
