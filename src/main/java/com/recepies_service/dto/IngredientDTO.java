package com.recepies_service.dto;

import com.recepies_service.enums.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class IngredientDTO {

    Long id;

    @NotEmpty(message = "Ime sastojka ne moze biti prazno")
    private String name;

    @NotNull(message = "Broj kalorija ne moze biti null!")
    @Positive(message = "Broj kalorija mora biti pozitivan")
    private int calorieNumber;

    private String addedBy;

    private Category category;

    public IngredientDTO(String name, int calorieNumber, String addedBy, Category category) {
        this.name = name;
        this.calorieNumber = calorieNumber;
        this.addedBy = addedBy;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getCalorieNumber() {
        return calorieNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
