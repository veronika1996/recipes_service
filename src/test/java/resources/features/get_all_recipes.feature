Feature: Get all recipes

  @getAll
  Scenario: Successfully retrieve all recipes when list is empty
    Given the recipe list is empty
    When I send a GET request to "/recipes"
    Then I should receive a 200 response
    And the response should contain an empty list

  @getAll
  Scenario Outline: Successfully retrieve all recipes when list has one recipe
    Given the recipe list contains <number> recipe
    When I send a GET request to "/recipes"
    Then I should receive a 200 response
    And the response should contain <number> recipe
    And the response should be list of data as in JSON file "/responses/<fileName>"
    Examples:
      | number | fileName |
      | 1      | get_all_recipe_size_one_response.json |
      | 2      | get_all_recipe_size_two_response.json |

