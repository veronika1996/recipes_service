Feature: Get recipe calories

  @post
  Scenario: Successfully retrieve calories by id
    Given the recipe is created for an id"
    When I send a POST request to "/recipes/calorie" by that id
    Then I should receive as integer and code is 200
    And the response should be 500
