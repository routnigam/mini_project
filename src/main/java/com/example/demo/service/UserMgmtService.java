package com.example.demo.service;

import java.util.Map;

import com.example.demo.binding.LoginForm;
import com.example.demo.binding.UnlockAccount;
import com.example.demo.binding.UserForm;


public interface UserMgmtService {
	//this method is check email id is Unic and duplicate
	public String checkEmail (String email);
	//this method will get all the country and return  data in key format
	public Map<Integer, String> getCountries ( ) ;
    //this method it will take countryid and baase on countryid it will return states
	public Map<Integer, String> getStates (Integer countryId);

	public Map<Integer, String> getCities (Integer stateId);

	public String registerUser (UserForm user);

	public String unlockAccount (UnlockAccount accForm);

	public String login (LoginForm loginForm);

	public String forgotPwd (String email);

}
