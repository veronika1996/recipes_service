package com.recepies_service.entity;

import com.recepies_service.enums.RecipeCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "app_recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredientEntity> ingredientsWithQuantity;

    @Column(length = 2000)
    private String preparation;

    private Integer caloriesNumber;

    @Enumerated(EnumType.STRING)
    private RecipeCategory category;

    private Integer numberOfPortions;

    private String createdBy;

    public List<RecipeIngredientEntity> getIngredientsWithQuantity() {
        return ingredientsWithQuantity;
    }

    public void setIngredientsWithQuantity(List<RecipeIngredientEntity> ingredientsWithQuantity) {
        this.ingredientsWithQuantity = ingredientsWithQuantity;
    }

    public RecipeEntity() {
    }

    public RecipeEntity(String name, List<RecipeIngredientEntity> ingredientsWithQuantity,
        String preparation,
        Integer caloriesNumber, RecipeCategory category,
        Integer numberOfPortions, String createdBy) {
        this.name = name;
        this.ingredientsWithQuantity = ingredientsWithQuantity;
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
