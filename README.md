Order Management System (OMS)

Overview
--------
Order Management System (OMS) is a microservices-based application developed using Spring Boot. The system manages users, products, inventory, carts, and orders through independent services.

Modules
-------
1. API Gateway
   - Acts as a single entry point for client requests.

2. Eureka Server
   - Provides service registration and discovery.

3. User Service
   - Handles user-related operations.

4. Product Service
   - Manages product information.

5. Inventory Service
   - Maintains stock availability and updates.

6. Cart Service
   - Handles customer shopping cart operations.

7. Order Service
   - Processes customer orders.

8. Common Events
   - Contains shared event classes and DTOs used by multiple services.

Technologies Used
----------------
- Spring Boot
- Spring Cloud
- Eureka Server
- Spring Cloud Gateway
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- OpenFeign
- Maven

Features
--------
- Microservices Architecture
- Service Discovery
- API Gateway Routing
- Event-Driven Communication using Kafka
- Inventory Management
- Product Management
- Order Processing
- Cart Management
- Inter-Service Communication using Feign Client

Project Structure
-----------------
OMS
|
|-- apiGateway
|-- cartService
|-- common-events
|-- eureka-server
|-- inventoryService
|-- orderService
|-- productService
|-- user-service

How to Run
----------
1. Start PostgreSQL.
2. Start Kafka and Zookeeper.
3. Run Eureka Server.
4. Run API Gateway.
5. Run User Service.
6. Run Product Service.
7. Run Inventory Service.
8. Run Cart Service.
9. Run Order Service.

Future Enhancements
-------------------
- JWT Authentication
- Docker Support
- Kubernetes Deployment
- CI/CD Integration
- Monitoring and Logging

Author
------
Greeshma Thogiti
Software Engineer