package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.payload.LoginDto;
import jp.co.axa.apidemo.payload.RegisterDto;

public interface AuthService {
	
	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}
