package com.recepies_service.dto;

import com.recepies_service.enums.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class IngredientDTO {

    Long id;

    @NotEmpty(message = "Name of ingredient cannot be empty")
    private String name;

    @NotNull(message = "Calorie number cannot be null!")
    @Positive(message = "Calorie number needs to be positive value")
    private int calorieNumber;

    private String addedBy;

    private Category category;

    public IngredientDTO() {
    }

    public IngredientDTO(String name, int calorieNumber, String addedBy, Category category) {
        this.name = name;
        this.calorieNumber = calorieNumber;
        this.addedBy = addedBy;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalorieNumber() {
        return calorieNumber;
    }

    public void setCalorieNumber(int calorieNumber) {
        this.calorieNumber = calorieNumber;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
