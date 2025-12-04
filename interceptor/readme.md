🔍 What is an Interceptor in Spring Boot?

A Spring MVC Interceptor is a component that allows us to intercept HTTP requests before they reach the controller, and optionally intercept responses after the controller finishes.

Interceptors are used to handle cross-cutting concerns at the web layer without modifying controller or service logic.

🎯 Why do we use an Interceptor?

Because sometimes we need to apply logic to many endpoints, and doing it inside every controller becomes messy.

Common use cases:

Use Case	Why Interceptor fits
Logging user requests	Runs before every controller
Authentication / token validation	Block request before business logic
API rate limiting	Detect abuse before controller
Multi-tenant applications	Identify tenant from headers/subdomain
Request transformation	Modify path variables / attributes
ID mapping (Long → UUID)	Controller stays clean
⚙ How Interceptors fit in Spring MVC request lifecycle
Client Request
↓
DispatcherServlet
↓
HandlerInterceptor.preHandle()      ← Our custom logic (most important)
↓
Controller executes
↓
Service → Repository → DB
↓
HandlerInterceptor.postHandle()     (optional for JSP/ModelAndView)
↓
HandlerInterceptor.afterCompletion() (final cleanup/logging)
↓
Response returned to client


For REST APIs, preHandle() is the most important method.

🧠 Interceptor Lifecycle Methods
Method	Order	Purpose
preHandle()	1️⃣ Before controller	Approve/deny/modify request
postHandle()	2️⃣ After controller	Modify ModelAndView (not JSON)
afterCompletion()	3️⃣ After full response	Cleanup, auditing
❗ Why we don't use postHandle() for REST APIs

postHandle() works only when a view is rendered (JSP, Thymeleaf)

REST APIs return JSON using @ResponseBody

So postHandle() is often skipped by Spring

That’s why JSON modification should be done using:

ResponseBodyAdvice

🧩 Interceptor vs Filter vs AOP — Important Interview Comparison
Feature	Interceptor	Filter	AOP
Layer	Spring MVC	Servlet API	Application/business layer
Access to controller method	✔	❌	✔
Suitable for request modification	✔	❌	❌
Best for logging/auth/ID mapping	✔	❌	❌
JSON response modification	❌	❌	❌ → use ResponseBodyAdvice

Your UUID mapping use case is a perfect fit for Interceptors because it requires access to:

Path variables

Controller information

Request modification before controller executes

🧩 When to choose an Interceptor
Requirement	Best choice
Modify incoming request before controller	Interceptor
Validate auth token / tenant	Interceptor
Global CORS / encoding / static resources	Filter
Modify returned JSON	ResponseBodyAdvice
Add logging around service methods	AOP
🔥 One-line definition for README / Interview

A Spring MVC Interceptor allows us to run logic before the controller is executed, making it perfect for cross-cutting concerns such as authentication, logging, and request transformation — without modifying controllers or services.

📌 Why this matters in real applications

Large companies use this pattern:

Company	Example usage
Amazon	Convert seller ID → internal SKU UUID
Zomato	Customer order ID → internal UUID
Banks	Mask transaction numbers before returning to UI
Netflix	Map region-based content routing

Our Long ID → UUID mapping interceptor follows the same industry pattern.

🏁 Final takeaway
Interceptor = request transformation / validation BEFORE controller
ResponseBodyAdvice = response enrichment BEFORE sending JSON


Combining both gives a clean, scalable, production-level design.


# UUID Mapping Interceptor in Spring Boot – Full Theory and Implementation

## 📌 Problem Background
In many real-world applications, older systems or mobile apps use **numeric IDs (Long)** to identify entities.  
However, modern microservices and secure applications prefer **UUIDs** as primary keys since UUIDs are:

- Globally unique across tables/machines
- Hard to guess (higher security)
- Better suited for distributed systems

During migration, **clients can't instantly switch from Long → UUID**, so the backend should support Long **without modifying controllers or services**.

---

## 🎯 Goal
Allow client to call:


Even though the `products` table uses:



Controller should stay **100% UUID based** without knowing that the client sent a Long ID.

---

## 🚀 Final Result

| Client sends | Controller receives | DB lookup |
|-------------|---------------------|-----------|
| `/products/101` | UUID | Product table |

Response returned to client:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "MacBook Pro",
  "price": 199999.0,
  "originalExternalId": 101,
  "metaMessage": "Product fetched using external id mapping"
}

Client → /products/101
            |
            ↓
ProductIdMappingInterceptor
    - Reads {id = 101}
    - Looks up UUID from product_key_mapping
    - Rewrites request path variable to UUID
            |
            ↓
Controller receives UUID → Service → DB → DTO
            |
            ↓
ResponseBodyAdvice
    - Adds originalExternalId & metaMessage
            |
            ↓
Client receives enriched JSON


🔥 Key Components
Component	Responsibility
HandlerInterceptor	Convert Long → UUID before controller
ResponseBodyAdvice	Add extra fields into JSON response
Controller	Works with UUID only
Service	Loads product by UUID
Mapping table	Links externalId ↔ productUUID
🧱 Database Schema
Table: products
CREATE TABLE products (
    id BINARY(16) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price DOUBLE NOT NULL
);

Table: product_key_mapping
CREATE TABLE product_key_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    external_id BIGINT NOT NULL UNIQUE,
    product_id BINARY(16) NOT NULL UNIQUE
);