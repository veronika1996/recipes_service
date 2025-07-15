package com.recepies_service.dto;

public class IngredientQuantityDto {

  private String  name;
  private Double quantity;

  public IngredientQuantityDto() {
  }

  public IngredientQuantityDto(String name, Double quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }
}
