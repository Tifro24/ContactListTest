@contacts @api
Feature: Contact Interactions

  @TC004 @smoke
  Scenario: Successfully create a new contact
    Given a registered user exists and is logged in
    Then I store the session token
    When I create a new contact
    Then the response status code is 201
    Then I store the contact id
    And the returned contact details should match the submitted details
    When I retrieve contact details
    Then the response status code is 200
    Then the returned contact details should match the submitted details
