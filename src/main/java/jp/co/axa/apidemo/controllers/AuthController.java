package jp.co.axa.apidemo.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.axa.apidemo.payload.JWTAuthResponse;
import jp.co.axa.apidemo.payload.LoginDto;
import jp.co.axa.apidemo.payload.RegisterDto;
import jp.co.axa.apidemo.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@ApiResponse(responseCode = "200", description = "HTTP Status Successfull")
@Tag(
		name = "REST APIs for Authorization control",
		description = "Can register new users or signin with the existing user to get JWT authorization token"
)
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	// Build Login/Signin Rest API
	@Operation(summary = "Login/Signin REST API", description = "User logs in and gets the bearer JWT token that can be further used to authenticate and authorise Employee CRM rest operation.")
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
	}

	// Build Register Rest API
	@Operation(summary = "Signup/Register REST API", description = "This registers new user with the role of \"USER\". Only Admin can register new user.")
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
}
