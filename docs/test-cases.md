Test Case ID: TC-001

Title: Verify successful user registration

Preconditions: User is not logged in and the email address has not previously been registered. Does not require an authentication token.

Test Steps:
1. Create JSON request body with a first name, last name, email and password.
2. Send a POST request to /users with the Content-Type: application/json header.
3. Inspect the HTTP response
Expected Result: 
- Should return a 201 response with the correct user's details (as entered in the request)
- Should return a non-empty token.

Priority: High

Test Case ID: TC-002

Title: 	Login using incorrect credentials

Preconditions: A registered user exists and not currently logged in. Does not require an authentication token.

Test Steps:
1. Create JSON request body with a valid email and incorrect password.
2. Send a POST request to /users/login with the Content-Type: application/json header.
3. Inspect the HTTP response
Expected Result: 
- Should return a 401 unauthorised response.

Priority: High

Test Case ID: TC-003

Title: 	Create a contact with valid details

Preconditions: A registered user that is already logged in. Authentication token is required.

Test Steps:
1. Create JSON request body with a firstName and lastName.
2. Add the correct authentication token as part of the request
3. Send a POST request to /contacts with the Content-Type: application/json header.
4. Inspect the HTTP response
5. Store the newly created id for that contact - contactId
6. Send a GET request to /contacts/{contactId} with the Content-Type: application/json header.
7. Add the correct authentication token as part of the request
Expected Result: 
- A new contact is created and returned with its details in the response (matching firstName and lastName with the details in the request). 
- The API returns a 201 Created response.
- The API returns the 200 success response for the retrieval of the created user
- It returns the details of the retrieved user in the response.

Priority: High

Test Case ID: TC-004

Title: 	Validate contact creation fails when lastName is empty. 

Preconditions: A registered user is already logged in. Authentication token is required.

Test Steps:
1. Create JSON request body with a valid firstName and empty lastName.
2. Add the correct authentication token as part of the request
3. Send a POST request to /contacts with the Content-Type: application/json header.
4. Inspect the HTTP response
5. Send a GET request to /contacts
6. Add the correct authentication token as part of the request.
7. Inspect the HTTP response
Expected Result: 
- It should return a 400 bad request. 
- An error message stating that the last name is missing.
- Response to the get request should be empty, validating the contact was not created.

Priority: High

Test Case ID: TC-005

Title: 	Delete an existing contact

Preconditions: A registered user is already logged in with valid authentication token. A contact has been created with it's contactId stored.

Test Steps:
1. Send a DELETE request to /contacts/{contactId}.
2. Add the correct authentication token as part of the request
3. Inspect the HTTP response.
4. Send a GET request to /contacts/{contactId}.
5. Add the correct authentication token as part of the request
6. Inspect the HTTP response - should receive 404 not found. 
Expected Result: 
- Return a 200 response with "contact deleted" success message. 
- The contact is not found in the get contact response (i.e the response to the get request is empty).
- The deleted contact is no longer retrievable

Priority: Medium
