Feature: Get all recipes by username

  @getAll
  Scenario: Successfully retrieve empty list when username doesn't have recipes
    Given the recipe list contains 1 recipe
    When I send a GET by username request to "/recipes?username=Admin0"
    Then I should receive a 200 response
    And the response should contain an empty list

  @getAll
  Scenario Outline: Successfully retrieve all recipes when list has one recipe
    Given the recipe list contains <number> recipe
    When I send a GET by username request to "/recipes?username=Admin1"
    Then I should receive a 200 response
    And the response should contain <number> recipe
    And the response should be list of data as in JSON file "/responses/<fileName>"
    Examples:
      | number | fileName                                    |
      | 1      | get_all_recipe_by_id_size_one_response.json |
      | 2      | get_all_recipe_by_id_size_two_response.json |

