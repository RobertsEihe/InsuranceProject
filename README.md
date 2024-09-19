# Insurance Management System
## Project Overview

The Insurance Management System is a comprehensive and scalable web application designed to automate and streamline the workflow of an insurance company. It offers a user-friendly interface for customers, agents, employees, and administrators to manage various aspects of the insurance lifecycle.

## Key Features

- Multi-role system: Tailored access for Customers, Agents, Employees, and Admins.
- Policy management: Create and manage insurance policies for vehicles, houses, and health.
- Risk assessment: Premium calculations based on predefined parameters and risk logic.
- Claims system: Submission, tracking, and processing of claims.
- Fraud detection: Automatic flagging of claims using services like FraudCheckService.
- Customer loyalty program: Rewards based on user loyalty and behavior.
- Automated invoicing: PDF generation and email notifications (managed via PDFService).

## User Roles

1. **Customer**
  - Register for an account.
  - View, create, and purchase insurance policies.
  - Submit and track claims (ClaimService, ClaimView).
  - Manage payment information.

2. **Agent**
  - Review and approve/deny policies.
  - Manage customer accounts.
  - Process claims (PolicyRepository, ClaimService).

3. **Employee**
  - Manage insurance products.
  - Investigate flagged claims (PolicyLogic, FraudCheckService).
  - Configure product pricing and risk assessment.

4. **System Administrator**
  - Manage users (add, remove, modify)

## Technical Information
**Technologies Used**
  - Backend: Spring Boot
  - Frontend: Vaadin (Views include AgentView, ClaimView, etc.)
  - Database: PostgreSQL (Supabase) with Spring Data JPA 
  - Security: Spring Security with role-based access (SecurityConfig, CustomUserDetailsService)
  - Testing: JUnit for unit and integration testing
  - Deployment: Docker-ready

## Key Components Overview

1. **User Management**
  - Registration, authentication, and role assignment (UserService).

2. **Policy Management**
  - Policy creation, renewal, premium calculation (PolicyLogic).

3. **Claims Processing**
  - Submitting, tracking, and approval of claims (ClaimService).

4. **Fraud Detection**
  - Automatic checks for suspicious claims (FraudCheckService).

5. **PDF Handling**
  - Policy document generation and storage (PDFService).

## Project Organization
The project doesn't follow classic MVC (Model-View-Controller) architecture, instead Vaadin framework has view classes that act both as a view and controller:

**Backend Organization**
  - Model: `src/main/java/com/project/InsuranceProject/data/entity/`
  - Controller + View: `src/main/java/com/project/InsuranceProject/views/`
  - Repository: `src/main/java/com/project/InsuranceProject/data/repositories/`
  - Services: `src/main/java/com/project/InsuranceProject/data/logic/` and  `src/main/java/com/project/InsuranceProject/data/services/`

**Testing**
  - Unit Tests: `src/test/java/com/project/InsuranceProject`

## Business Requirements

**Policy Management**

  - Intuitive interface for customers
  - Agent approval process
  - Automatic premium calculations

**Claims Processing**
  - Multi-step submission and approval workflow
  - Fraud detection checks
  - Automated status notifications

**User Management**
  - Admin controls for user management
  - Secure login mechanisms

**Security and Data Integrity**
  - Encryption of sensitive data
  - ACID-compliant transactions
  - Regular backups

## Test Cases 

**Automated tests**
  - Agent Retrieval Test (AgentRetrieveTest.java) - Tests retrieving a list of agent usernames based on their roles.
  - Policy Management Tests (AllPoliciesTest.java): Retrieves policies by customer or agent. Tests policy approval, denial, and confirmation processes. Also includes tests for saving policy risks.
  - Claim Management Test (ClaimTest.java) - Approving and denying claims. Retrieving claims by username and saving them.
  - Employee View Test (EmployeeViewTest.java) - Tests retrieving all policies and risks. Managing risks (add, update, delete).
  - Risk Management Test (RiskTest.java) - Placeholder for future tests related to risk management.
  - User Management Test (UserTest.java) - Placeholder for future tests related to user management.
  - Vehicle Management Test (VehicleTest.java) - Placeholder for future tests related to user management.
  - Fraud Detection and Premium Calculation (PolicyLogicTest.java) - Tests confirming policy purchase while checking for fraud. Tests calculating the premium for policies.

**Manual tests*
  - Policy Approval and Denial - This test ensures that agents can approve or deny policies from the dashboard. It verifies the system's ability to update the policy status to "APPROVED" or "DENIED" and confirms that agents can only manage policies assigned to them.
  - View Agent Policies - Ensures that the policies displayed in the grid are the ones assigned to the logged-in agent.
  - Logout Functionality - Tests the functionality of the "Log out" button, ensuring it logs the agent out and redirects them to the login page.
  - View Customer Dashboard - Verifies that the customer dashboard displays correct information, including personal details, active policies, and claims after login.
  - Create a New Policy - Ensures that a customer can create a new insurance policy by completing the required details and submitting the form.
  - Submit a Claim - Verifies that a customer can submit a claim for an active policy.
  - Add New User - Ensures that the admin can add a new user with roles such as Customer, Agent, Employee, or Admin. 
  - View User List - Verifies that the admin can view a list of users in the system, including their roles, names, and email addresses.
  - Duplicate Username Error - Ensures that the system prevents creating a new user with a username that already exists.
  - Sign In and Log out Functionality - Verifies that users can sign in and out correctly, with role-based redirection to the appropriate dashboards.
  - Verify Navigation and Menu Functionality - Ensures that all menu items are clickable and lead to the correct pages based on the user’s role.
  - Validate Notification Messages - Verifies that notification messages are displayed correctly for various actions, such as policy approval or claim submission.

## Database Schema

The system uses a relational database with the following key tables:
  - User 
  - Policy
  - Vehicle
  - Claim
  - Payment
  - Document
  - Policy risk
  - House
  - Health

## Future Enhancements

  - Multi-language Support
  - Chatbot Integration
  - Mobile Application
  - Integration with Risk Assessment APIs
  - All roles can view activity history, transactions etc.


## Getting Started

1. Clone the repository
git clone https://github.com/RobertsEihe/InsuranceProject.git
2. Navigate to project directory and configure the database
3. Build the project
mvn clean install
4. Run the application
mvn spring-boot: run
5. Access the application
Open http://localhost:8080/ in your browser

