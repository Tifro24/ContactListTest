@registration @api
Feature: User Registration & Login

  @TC001 @smoke
  Scenario: Successfully register a new user
    Given I have valid and unique registration details
    When I register a new user
    Then the response status code should be 201
    Then I store the user registration details
    And the returned user details should match the submitted details
    And a valid authentication token "is" returned

  @TC002 @smoke
    Scenario: Login using incorrect user credentials
      Given I have an existing user
      Then I store the user registration details
      When I login using "invalid" user details
      Then the response status code should be 401
      And a valid authentication token "is not" returned

  @TC003 @smoke
    Scenario: Login using correct user credentials
      Given I have an existing user
      Then I store the user registration details
      When I login using "valid" user details
      Then the response status code should be 200
      And a valid authentication token "is" returned