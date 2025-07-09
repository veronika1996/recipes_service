package com.recepies_service.dto;

import com.recepies_service.enums.RecipeCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class RecipeDTO {

    Long id;

    @NotEmpty(message = "Name of recipe cannot be empty")
    private String name;

    private List<IngredientDTO> ingredients;

    @NotEmpty(message = "Preparation instructions cannot be empty")
    private String preparation;

    @NotNull(message = "Calories number cannot be null!")
    @Positive(message = "Calories number needs to be a positive value")
    private Integer caloriesNumber;

    @NotNull(message = "Category cannot be null!")
    private RecipeCategory category;

    @NotNull(message = "Number of portions cannot be null!")
    @Positive(message = "Number of portions must be a positive value")
    private Integer numberOfPortions;

    @NotNull(message = "CreatedBy UUID cannot be null!")
    private String createdBy;

    public RecipeDTO() {
    }

    public RecipeDTO(String name, List<IngredientDTO> ingredients, String preparation, Integer caloriesNumber, RecipeCategory category, Integer numberOfPortions, String createdBy) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.caloriesNumber = caloriesNumber;
        this.category = category;
        this.numberOfPortions = numberOfPortions;
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Integer getCaloriesNumber() {
        return caloriesNumber;
    }

    public void setCaloriesNumber(Integer caloriesNumber) {
        this.caloriesNumber = caloriesNumber;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public Integer getNumberOfPortions() {
        return numberOfPortions;
    }

    public void setNumberOfPortions(Integer numberOfPortions) {
        this.numberOfPortions = numberOfPortions;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
