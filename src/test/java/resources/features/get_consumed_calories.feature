Feature: Get consumed calories for a plan based on recipes ids

  @get
  Scenario Outline: Get consumed calories for a plan
    Given the recipe list contains <number> recipe
    When I send a POST request by ids
    Then I should receive as integer and code is 200
    And the response should be <result>

    Examples:
      | number | result |
      | 1      | 500    |
      | 2      | 1000   |