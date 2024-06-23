package jp.co.axa.apidemo.services.impl;


import jp.co.axa.apidemo.entities.Role;
import jp.co.axa.apidemo.entities.User;
import jp.co.axa.apidemo.exception.UserApiException;
import jp.co.axa.apidemo.payload.LoginDto;
import jp.co.axa.apidemo.payload.RegisterDto;
import jp.co.axa.apidemo.repositories.RoleRepository;
import jp.co.axa.apidemo.repositories.UserRepository;
import jp.co.axa.apidemo.security.JwtTokenProvider;
import jp.co.axa.apidemo.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
//	when the class has only one parametrized constructor spring can autoinject the dependency
//	so no need to write @Autowired annotation
	
	public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public String login(LoginDto loginDto) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(),loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}
		
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "User Registered Successfully";
	}

}
