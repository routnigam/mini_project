package com.example.demo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.binding.LoginForm;
import com.example.demo.binding.UnlockAccount;
import com.example.demo.binding.UserForm;
import com.example.demo.service.UserMgmtService;
@Component
@RestController
public class UserRestController {
	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
	String status = userMgmtService.login(loginForm);
	return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	
	@GetMapping("/countries")
	public Map<Integer,String> loadCountry(){
	return	userMgmtService.getCountries();
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer, String> loadStates(@PathVariable Integer countryId){
		return userMgmtService.getStates(countryId);
			
	}
	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> loadCities(@PathVariable Integer stateId){
		return userMgmtService.getCities(stateId);
	}
	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable String findByEmail) {
	return 	userMgmtService.checkEmail(findByEmail);
	}
	@PostMapping("/user")
	public ResponseEntity<String> userRegistration(@RequestBody UserForm userForm){
	String status = userMgmtService.registerUser(userForm);
	return new ResponseEntity<>(status,HttpStatus.CREATED);
	}
	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccount unlockAccount){
	String	status = userMgmtService.unlockAccount(unlockAccount);
	return new ResponseEntity<>(status, HttpStatus.OK);
	}
	@GetMapping("/forgotpwt/{email}")
	public ResponseEntity<String> forgotpwt(@PathVariable String email){
		String status = userMgmtService.forgotPwd(email);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	
	
}










