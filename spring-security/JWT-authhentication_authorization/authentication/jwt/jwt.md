## JWT
JWT is a token based authentication mechanism

# Jwt structure 
1. Header
2. Payload
3. Signature

example: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
first part is header second part is payload and third part is signature
header tells about the type of token and the algorithm used to sign the token
payload contains the claims
signature is used to verify the token

# Jwt implementation steps
1. Generate token
2. Send token to client
3. Client stores the token
4. Client sends the token in the header of the request
5. Server verifies the token
6. Server processes the request

jwt flow diagram below 
![jwt flow](jwt_flow_1.png)
![jwt flow](jwt_flow_2.png)

When we use login/authenticate api then we will generate the token and send it to the client
but this api shouldn't be autnenticated so we will skip the filter by using `PermitAll` but 
the problem is we shouldn't generate the token for invalid username and password
so flow will be like this for login/authenticate api 

1. User will send the username and password to the server the login/authenticate api 
2. request will skip the filter and go to the controller
3. but we can't generate the token for invalid username/password so after the controller request will
go to the AuthenticationManager to validate the username and password like we do for basic authentication
4. if the username and password is valid then we will use JwtUtil to generate the token and send it to the client
5. if the username and password is invalid then we will throw the exception

Diagram below for the complete login flow 
![login flow](login_api_flow.png)


But for the other apis we need to validate the token so we will use the filter
so we will remove the BasicAuthenticationFilter from the filter chain so the default filter that is 
UsernamePasswordAuthenticationFilter will be applied but we don't want that and want to use our custom 
JwtAuthenticationFilter before UserNamePasswordAuthenticationFilter so UsernamePasswordAuthenticationFilter 
will not start processing the request , instead JwtAuthenticationFilter will process the request and after that
it'll not go to AuthenticationManager since we have already done the validation in authenticate/login api 
it just needs to validate the token and if the token is valid then it'll process the request

so our JwtAuthenticationFilter will directly talk to JwtUtil to validate the token, once the token is valid then it will
add the authentication object to the security context Holder so next will UsernamePasswordAuthenticationFilter
will come it will see the authentication object already in the security context so it'll validate annd request will 
directly go the respective controller

complete autnenticated apis flow diagram below 
![authenticated api flow](authenticated_api_flow.png)


