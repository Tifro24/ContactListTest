@registration @api
Feature: User Registration

  @TC001 @smoke
  Scenario: Successfully register a new user
    Given I have valid and unique registration details
    When I register a new user
    Then the response status code should be 201
    And the returned user details should match the submitted details
    And a valid authentication token should be returned