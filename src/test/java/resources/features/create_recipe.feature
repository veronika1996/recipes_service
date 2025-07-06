Feature: Create a new recipe

  Background:
    Given the system is initialized

  @create
  Scenario: Successfully create a new recipe
    Given the recipe data in JSON file "/requests/create_recipe_request.json"
    When I send a POST request to "/recipes"
    Then I should receive a 201 response
    And the recipe response should be like the recipe data in JSON file "/responses/create_recipe_response.json"

  @error
  Scenario: Create recipe with missing name
    Given the recipe data in JSON file "/requests/create_recipe_request_missing_name.json"
    When I send a POST request to "/recipes"
    Then I should receive a 400 response
    And the response should contain error message "Name of recipe cannot be empty"

  @error
  Scenario: Create recipe with negative calorie number
    Given the recipe data in JSON file "/requests/create_recipe_request_negative_calories.json"
    When I send a POST request to "/recipes"
    Then I should receive a 400 response
    And the response should contain error message "Calories number needs to be a positive value"

  @error
  Scenario: Create recipe with missing calorie number
    Given the recipe data in JSON file "/requests/create_recipe_request_missing_calories.json"
    When I send a POST request to "/recipes"
    Then I should receive a 400 response
    And the response should contain error message "Calories number cannot be null!"
