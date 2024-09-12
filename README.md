# Insurance Management System
# NEEDS TO BE FINSIHED!!!!!
## Project Overview

This Insurance Management System is a comprehensive web application designed for an insurance company. It provides functionality for customers, agents, employees, and system administrators to manage various aspects of insurance policies, claims, and user management.

## Key Features

- Multi-user system with role-based access control
- Policy management for vehicle, house, and health insurance
- Risk assessment and premium calculation
- Claims processing and approval system
- Customer loyalty program
- Fraud detection system
- Automated invoicing and email notifications ???

## User Roles

1. **Customer**
  - Register and manage account
  - Create and purchase insurance policies
  - File and track claims
  - View policy details and payment history

2. **Agent**
  - Review and approve/deny policies under review
  - Manage customer accounts
  - Process claims

3. **Employee**
  - Manage insurance products
  - Adjust risk rates and coverage options
  - Review suspicious claims

4. **System Administrator**
  - User management (add/remove agents, employees, admins)
  - System-wide configuration and maintenance

## Technical Stack

- Backend: Spring Boot
- Frontend: Vaadin
- Database: PostgreSQL (Supabase.com)
- Security: Spring Security

## Key Components

1. **User Management**
  - Authentication and authorization
  - Role-based access control

2. **Policy Management**
  - Policy creation and quotation
  - Risk assessment and premium calculation
  - Policy renewal and expiration handling

3. **Claims Processing**
  - Claim submission and tracking
  - Approval workflow
  - Fraud detection system

4. **Payment Handling**
  - Invoice generation
  - Payment tracking

5. **Document Management**
  - Store and retrieve policy-related documents

6. **Reporting and Analytics**
  - Customer history view
  - Policy performance metrics

## Database Schema

The system uses a relational database with the following key tables:
- Users (Customer, Agent, Employee)
- Policies
- Risks(House,Vehicle,Health)
- Claims
- Payments
- Documents

## User Interfaces

1. **Customer Portal**
  - Policy creation wizard
  - Policy management dashboard
  - Claim submission form

2. **Agent Dashboard**
  - Policy review queue
  - Customer search and management
  - Claim processing interface

3. **Employee Tools**
  - Risk and rate management interface
  - Product configuration panel

4. **Admin Console**
  - User management interface
  - System configuration panel

## Security Features

- Encrypted password storage
- Secure communication (HTTPS)
- Role-based access control
- Session management?
- Password reset functionality?

## Future Enhancements

- Multi-language support
- Chatbot for customer support
- Mobile application
- Integration with external risk assessment APIs

## Getting Started

1. Clone the repository
2. Configure the database connection in `application.properties`
3. Run `mvn clean install` to build the project
4. Start the application 
