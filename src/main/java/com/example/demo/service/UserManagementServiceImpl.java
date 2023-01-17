 package com.example.demo.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.binding.LoginForm;
import com.example.demo.binding.UnlockAccount;
import com.example.demo.binding.UserForm;
import com.example.demo.entity.CityMaster;
import com.example.demo.entity.CountryMaster;
import com.example.demo.entity.StateMaster;
import com.example.demo.entity.User;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.StateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.EmailUtils;
@Component
@Service 
public class UserManagementServiceImpl implements UserMgmtService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String checkEmail(String email) {
		User user=userRepo.findByEmail(email);
		
		if(user == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> getCountries() {
		
	List<CountryMaster> countries =	countryRepo.findAll();
	
	Map<Integer, String> countryMap= new HashMap<>();
	
	countries.forEach(country ->{
		countryMap.put(country.getCountryId(), country.getCountryName());
	});
	
		return countryMap ;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
	
		List<StateMaster> states=stateRepo.findByCountryId(countryId) ;
		
		Map<Integer, String> stateMap=new HashMap<>();
		
		states.forEach(state ->{
			stateMap.put(state.getStateId(), state.getStateName());
		});
		
		return stateMap ;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityMaster> cities = cityRepo.findByStateId(stateId);
		Map<Integer,String> cityMap=new HashMap<>();
		cities.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});
					
		return cityMap;
	}

	@Override
	public String registerUser(UserForm userform) {
		//copy data from binding obj to entity obj
		User entity=new User();
		BeanUtils.copyProperties(userform, entity);
		
		//Generate & Set random password
		entity.setUserPwd(generateRandomPwd());
		
		//set account status as locked
		entity.setAccStatus("LOCKED");
		
		userRepo.save(entity);
		
		//todo:send email to unlock account
		String to = userform.getEmail();
		String subject = "Registration email";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity) ;
		
		emailUtils.sendEmail(to, subject, body);
		
		return "user account created";
	}

	@Override
	public String unlockAccount(UnlockAccount unlockaccForm) {
	
		String email = unlockaccForm.getEmail();
		User user = userRepo.findByEmail(email);
		
		if(user!=null && user.getUserPwd().equals(unlockaccForm.getTempPwd())) {
			user.setUserPwd(unlockaccForm.getNewPwd());
			user.setAccStatus("UNLOCK");
			userRepo.save(user);
			return "Account Unlocked";
		}
		return "invalid temp pwd";
	}

	@Override
	public String login(LoginForm loginForm) {
		
		User user = userRepo.FindByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());
		
		if(user == null) {
			return  "invalid credential";
		}
		if(user.getAccStatus().equals("LOCKED")) {
			return "account lockes";
		}
		return "success";
	}

	@Override
	public String forgotPwd(String email) {
		
		User user = userRepo.findByEmail(email);
		if(user == null) {
			return "no acc found";
		}
		
		
		String subject = "Recover password";
		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user) ;
		
		emailUtils.sendEmail(email, subject, body);
		
		return "password send to register email";
	}
	
	private String generateRandomPwd() {
		String text="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		StringBuilder sb = new StringBuilder();
		
		Random random = new Random();
		
		int pwdLenght = 6;
		
		for(int i=1; i <= pwdLenght ; i++) {
			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));
		}
		return sb.toString();
	}
	
	public String readEmailBody(String filename, User user){
		
		StringBuffer sb = new StringBuffer();
		
		try (Stream<String >lines = Files.lines(Paths.get(filename))){
			lines.forEach(line ->{
				
				line = line.replace("${fNAME}", user.getFname());
				line = line.replace("${LNAME}", user.getLname());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("$${EMAIL}", user.getEmail());
				line = line.replace("${LNAME}", user.getLname());
				
				sb.append(line);
				
			});
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
		
	}

}












