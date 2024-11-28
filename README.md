# Role-Based-Access-VRV-Security

 # Spring Boot Role-Based Access with JWT Authentication

This project demonstrates role-based access control (RBAC) using Spring Security in a Spring Boot application. It includes user authentication and authorization through JWT (JSON Web Token). The system defines two roles: **User** and **Admin**, each with different access levels.

## Features
- **Role-Based Access Control (RBAC)**: Implemented roles for User and Admin.
- **JWT Authentication**: Secure login and session management using JSON Web Tokens.
- **Spring Security**: Used for authentication and authorization to protect APIs.
- **Custom Access Rules**: Defined access restrictions for different user roles.
- **Journal Entries**: Users can create, read, update, and delete their journal entries.
- **Admin Features**: Admin can view all users, create new admins.

## Technologies Used
- **Spring Boot**: The backend framework used to build the application.
- **Spring Security**: Provides authentication and authorization mechanisms.
- **JWT**: Used for token-based authentication to secure endpoints.
- **Maven**: Project build management.
  
## Setup and Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/khushal-13/Role_Based_Access-VRV_Security.git
    cd Role_Based_Access-VRV_Security
    ```

2. Install dependencies:
    ```bash
    mvn install
    ```

3. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Example JSON for Testing

You can find the example JSON files for testing the application in the repository under the `json/` directory. Here are the steps to access and use them:

1. **Navigate to the `json/` directory**:
    The example JSON files are located in the `json/` folder in the root of the project. This folder contains sample payloads for various API requests, such as creating journal entries, updating user roles, and creating new admin users.

    ```bash
    cd json
    ```

2. **Use the JSON files**:
    You can open the relevant `.json` file to copy and use the payloads in your Postman requests or any other API testing tool.

    For example, to test the **Create Journal Entry** API, use the `create-journal-entry.json` file, which contains:
    ```json
    {
      "title": "My First Journal Entry",
      "content": "This is the content of my first journal entry."
    }
    ```

4. **Example JSON Files**:
    - `create-journal-entry.json`: Payload for creating a new journal entry.
    - `update-journal-entry.json`: Payload for updating an existing journal entry.
    - `create-admin.json`: Payload for creating a new admin user.
    - `update-user-role.json`: Payload for updating a user’s role.
    - `login.json`: Payload for logging in and receiving a JWT token.

By using these files, you can easily test the application's functionality.




 
