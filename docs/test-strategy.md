
# Contact List App — Test Strategy

## 1. Objective
The purpose of testing this application is to have confidence in that a user can create an account safely, they are able to create, update and delete contacts and sucessfully log out and return to their account by logging in.
We cannot ensure there are absolutely no flaws, but we aim to test the above features to provide coverage for the majority of user interactions with the application such as the examples aforementioned.

## 2. Scope
The following features will be tested as part of our API tests:
- Creating a user
- Deleting a user
- Logging in
- Logging out
- Creating a contact
- Deleting a contact
- Updating a contact
- Retrieving a contact
- Retrieving all contacts
- Appropriate authentication bearer token
- Application can handle missing details for contact creation
- Application can handle missing details for login
- Application can handle incorrect details for login
  
The following features will be tested as part of our UI tests:
- Correct navigation from login page to contacts page after logging in.
- Correct navigation from contacts page to contact details after clicking on a contact
- Correct navigation from contact details to contacts page after clicking 'back'
- Correct navigation from login page to create user page after clicking 'sign up'
- Success messaging after the creation of an account
- Correct navigation to login page after logging out
- Correct updating of the records table for a created contact with correct details for that row
- Correct updating of the records table for a deleted contact
- Correct messaging for missing details error when creating a contact
- Correct messaging for incorrect user login details



## 3. Test Approach
Our automation strategy will use a combination of API and UI testing to verify the application's functionality at different levels.

### API Testing
We will prioritise API automation using Java, REST Assured and JUnit.
API testing will validate core functionality, including user registration, authentication, contact creation, retrieval, updating and deletion, along with negative testing and access control.
We have prioritised API testing because it allows us to validate backend behaviour directly, without depending on the browser. This generally provides faster execution and more focused feedback when investigating failures.

### UI Testing
We will use Playwright with TypeScript to automate important user journeys, including registration, login, contact management and logout.
UI testing will focus on verifying that users can navigate the application, interact with its features and see accurate information and appropriate validation messages.
Rather than duplicating every API test through the UI, we will select important end-to-end journeys to demonstrate that the frontend and backend work correctly together.


## 4. Key Risks
What are the three biggest risks to users?
Authentication and authorisation risk - an unauthorised user can tamper with or steal contact detail information for a given user's account which puts their data in a vulnerable position. This is a high priority risk.
Data integrity - if data is not stored correctly, contact details can be incorrectly stored or displayed meaning a user will not have the correct details they expect. This is a high priority risk.
Functionality and availability risk - users may not be able to register an account, create, update or delete contact details or log in or log out of their account successfully, causing the application to not fulfil its requirements and rendering it unusable. This is a high priority risk.

## 5. Automation Priorities
1. Registration - ensures a user can successfully create an account 
2. Authentication - ensures that API requests are protected
3. Correct details when creating or retrieving a contact - ensures a user can successfully create contacts which is part of the core functionality
4. Unauthorised access - protects the private details of the contacts
5. Update and delete contacts - ensures data is relevant and up to date where necessary
6. Invalid and boundary inputs - ensures correct information is being stored and is relevant by handling errors

## 6. Test Data Management
Automated tests will use dynamically generated test data to minimise dependencies between test cases.
Where supported, contacts and test accounts will be cleaned up after execution, including when a test fails. Reusable setup and cleanup methods will be introduced to reduce duplicated code and improve maintainability.
This approach aims to produce a reliable, maintainable and scalable automation framework.

## 7. Limitations
We do not have a controlled testing environments.
We also do not have access to the internal code.
We don't have server logs so we cannot verify how the code is running step by step.
There is also a data purge that occurs and we do not know the details of such.

