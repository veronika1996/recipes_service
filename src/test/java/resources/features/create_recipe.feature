Feature: Create a new recipe

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

  Scenario: Create recipe with negative calorie number
    Given the recipe data in JSON file "/requests/create_recipe_request_negative_calories.json"
    When I send a POST request to "/recipes"
    Then I should receive a 201 response
    And the recipe response should be like the recipe data in JSON file "/responses/create_recipe_response.json"

  Scenario: Create recipe with missing calorie number
    Given the recipe data in JSON file "/requests/create_recipe_request_missing_calories.json"
    When I send a POST request to "/recipes"
    Then I should receive a 201 response
    And the recipe response should be like the recipe data in JSON file "/responses/create_recipe_response.json"

  @error
  Scenario: Create recipe name already exists
    Given the recipe data in JSON file "/requests/create_recipe_request.json"
    When I add an recipe and send a POST request to "/recipes"
    Then I should receive a 400 response
    And the response should contain error message "Vec postoji recept sa imenom: Recipe2"