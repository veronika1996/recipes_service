package com.recepies_service.entity;

import com.recepies_service.enums.RecipeCategory;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "recipe_ingredient_ids", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient_id")
    private List<Long> ingredientIds;

    @Column(length = 2000)
    private String preparation;

    private Integer caloriesNumber;

    @Enumerated(EnumType.STRING)
    private RecipeCategory category;

    private Integer numberOfPortions;

    private String createdBy;

    public List<Long> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(List<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }

    public RecipeEntity() {
    }

    public RecipeEntity(String name, List<Long> ingredientIds, String preparation,
        Integer caloriesNumber, RecipeCategory category,
        Integer numberOfPortions, String createdBy) {
        this.name = name;
        this.ingredientIds = ingredientIds;
        this.preparation = preparation;
        this.caloriesNumber = caloriesNumber;
        this.category = category;
        this.numberOfPortions = numberOfPortions;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
