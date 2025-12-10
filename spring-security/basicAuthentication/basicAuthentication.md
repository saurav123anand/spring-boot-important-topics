Reference doc: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html

In default Form based authentication we had UserNamePasswordAuthenticationFilter similarly for basic
authentication we have BasicAuthenticationFilter and this field will generate UsernamePasswordAuthenticationToken
that will be passed to AuthenticationManager for the next step 

Now we will tweak the default configuration to use basic authentication

