Feature: Delete an recipe

  Background:
    Given the system is initialized

  @delete
  Scenario: Successfully delete an recipe
    When I send a DELETE request to "/recipes/Recipe1"
    Then I should receive a 204 response
    And the recipe "Recipe1" should be deleted from the system

  @delete
  Scenario: Successfully delete an recipe by its id
    When I send a DELETE by id request
    Then I should receive a 204 response
    And the recipe "Tomato" should be deleted from the system

  @error
  Scenario: Delete a non-existing recipe
    When I send a DELETE request to "/recipes/NonExistingRecipe"
    Then I should receive a 404 response
    And the response should contain error message "Recipe not found for recipe name: NonExistingRecipe"

  @error
  Scenario: Delete a recipe by non existing id
    When I send a DELETE request to "/recipes?id=1"
    Then I should receive a 404 response
    And the response should contain error message "Recipe not found for recipe id: 1"
