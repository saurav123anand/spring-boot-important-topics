## 🔐 JWT (JSON Web Token)

JWT is a **token-based authentication mechanism** used to securely authenticate and authorize users in an application.

Instead of sending username and password on every request, the client sends a **JWT token**, and the server validates it.

---

## 🧱 JWT Structure

A JWT has **three parts**, separated by dots (`.`):

1. **Header**
2. **Payload**
3. **Signature**

### Example JWT

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

- First part → **Header**
- Second part → **Payload**
- Third part → **Signature**

### What each part does

- **Header**
  - Token type (`JWT`)
  - Signing algorithm (`HS256`, `RS256`, etc.)

- **Payload**
  - Contains **claims** (username, roles, expiry time, etc.)

- **Signature**
  - Verifies token integrity
  - Ensures the token is not tampered with

---

## 🔄 JWT Implementation Steps

1. Server generates JWT
2. Token is sent to the client
3. Client stores the token
4. Client sends token in request header
5. Server validates the token
6. Request is processed

---

## 📊 JWT Flow Diagram

![jwt flow](jwt_flow_1.png)
![jwt flow](jwt_flow_2.png)

---

## 🔑 Login / Authenticate API Flow

The **login/authenticate API** is responsible for generating the JWT.

### Important points

- Login API is **public** (`permitAll`)
- JWT filter is skipped for this API
- Token is generated **only after credentials validation**

### Login Flow

1. User sends **username + password**
2. Request skips filter and goes to controller
3. Controller calls `AuthenticationManager` to validate the credentials
4. If valid → controller will call the custom JwtUtil class and JWT is generated and returned
5. If invalid → Exception is thrown

### Login Flow Diagram

![login flow](login_api_flow.png)

---

## 🔒 Protected APIs (Authenticated Flow)

All other APIs require JWT validation.

### What we do

- Remove `BasicAuthenticationFilter` so by default `UsernamePasswordAuthenticationFilter` will be applied
- Do not rely on `UsernamePasswordAuthenticationFilter`
- Add custom `JwtAuthenticationFilter`
- Place it **before** `UsernamePasswordAuthenticationFilter` so that  UsernamePasswordAuthenticationFilter
  will not start processing the request first, instead  custom JwtAuthenticationFilter will process the request and after that
  it'll not go to AuthenticationManager since we have already done the validation in authenticate/login api
  it just needs to validate the token and if the token is valid then it'll process the request

  so our JwtAuthenticationFilter will directly talk to JwtUtil to validate the token, once the token is valid then it will
  add the authentication object to the security context Holder so next when UsernamePasswordAuthenticationFilter
  will come it will see the authentication object already in the security context so it'll not validate and request will
  directly go to the respective controller

### Protected API Flow

1. Client sends request with JWT in header
2. `JwtAuthenticationFilter` intercepts request
3. Token is validated using `JwtUtil`
4. Authentication object is created
5. Stored in `SecurityContextHolder`
6. Request reaches controller

---

## 📊 Authenticated API Flow Diagram

![authenticated api flow](authenticated_api_flow.png)

---

## ✅ Summary

- Login API → Validate credentials → Generate JWT
- Other APIs → Validate JWT → Allow access
- JWT avoids sending credentials repeatedly
- SecurityContext stores authenticated user
- This is the standard JWT flow used in Spring Boot

# 🔐 Authorization

**Authorization** is the process of deciding **what actions an authenticated user is allowed to perform** on application resources.

In simple terms:
- **Authentication** → Who are you?
- **Authorization** → What are you allowed to do?

---

## 👤 Roles and Permissions

### 🔹 Roles
Roles represent a **high-level authority** assigned to a user.

Examples:
```
ROLE_ADMIN
ROLE_USER
```

- `ROLE_` is the **default prefix** used by Spring Security
- If you want to change this prefix, you must define a **custom `GrantedAuthorityDefaults` bean**

---

### 🔹 Permissions
Permissions are **fine-grained actions** defined under a role.

Examples:
```
READ
WRITE
DELETE
UPDATE
```

Usually:
- **Roles** → Collection of permissions
- **Permissions** → Actual operations allowed on resources


---

## 🔁 Authorization in Spring Security Filter Chain

- Authorization happens **after authentication**
- Once authentication is successful, the **AuthorizationFilter** is triggered
- By default, `AuthorizationFilter` comes **after authentication filters** in the Spring Security filter chain

---

## 🧠 AuthorizationManager

After authentication:

1. `AuthorizationFilter` intercepts the request
2. It delegates the authorization decision to **AuthorizationManager**
3. This is similar to how `AuthenticationFilter` delegates to `AuthenticationManager`

### 🔹 AuthorizationManager
- It is a **functional interface**
- Main method:
```java
check(Authentication authentication, Object object);
```

- It checks whether the request is **authorized or not** based on roles/authorities

---

## 🧩 AuthorizationManager Implementations

Spring Security provides multiple implementations, such as:

- `RequestMatcherDelegatingAuthorizationManager`
- `AuthorityAuthorizationManager`
- `PreAuthorizeAuthorizationManager`
- `PostAuthorizeAuthorizationManager`

---

### 🔹 RequestMatcherDelegatingAuthorizationManager

- Uses **RequestMatcher** to match incoming requests
- Matches based on URL, HTTP method, etc.
- Decides whether a request is authorized according to configured rules

Example:
- `/admin/**` → ROLE_ADMIN
- `/user/**` → ROLE_USER

---

## ✅ Authorization Decision Flow

- If the request is **authorized** → it proceeds to the controller
- If the request is **not authorized** → Spring Security throws an `AccessDeniedException`

---

## 📊 Complete Authorization Flow Diagram

![authorization flow](authorization_flow.png)

---

## ✅ Summary

- Authorization decides **what a user can access**
- It runs **after authentication**
- `AuthorizationFilter` delegates decisions to `AuthorizationManager`
- Decisions are based on **roles and permissions**
- Authorized → request reaches controller
- Not authorized → access denied error
