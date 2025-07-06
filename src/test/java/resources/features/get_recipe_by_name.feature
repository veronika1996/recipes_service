Feature: Get an recipe by name

  Background:
    Given the system is initialized

  @getByName
  Scenario: Successfully retrieve an recipe by name
    When I send a GET request to "/recipes/Recipe1"
    Then I should receive a 200 response
    And the recipe response should be like the recipe data in JSON file "/responses/get_recipe_by_name_response.json"

  @error
  Scenario: Get a non-existing recipe
    When I send a GET request to "/recipes/NonExistingRecipe"
    Then I should receive a 404 response
    And the response should contain error message "Recipe not found for recipe name: NonExistingRecipe"
