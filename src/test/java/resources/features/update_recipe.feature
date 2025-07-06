Feature: Update an existing recipe

  Background:
    Given the system is initialized

  @update
  Scenario: Successfully update an recipe
    Given the recipe data in JSON file "/requests/update_recipe_request.json"
    When I send a PUT request to "/recipes/Recipe1"
    Then I should receive a 200 response
    And the recipe response should be like the recipe data in JSON file "/responses/update_recipe_response.json"

  @error
  Scenario: Update non-existing recipe
    Given the recipe data in JSON file "/requests/update_recipe_request.json"
    When I send a PUT request to "/recipes/NonExistingRecipe"
    Then I should receive a 404 response
    And the response should contain error message "Recipe not found for recipe name: NonExistingRecipe"

  @error
  Scenario: Update recipe with missing calorie number
    Given the recipe data in JSON file "/requests/update_recipe_request_missing_calories.json"
    When I send a PUT request to "/recipes/Recipe1"
    Then I should receive a 400 response
    And the response should contain error message "Calories number cannot be null!"
