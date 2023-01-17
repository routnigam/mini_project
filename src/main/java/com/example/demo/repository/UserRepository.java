package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;



public interface UserRepository extends JpaRepository<User, Serializable>{
	
	//select * from User_Master where email ?
	public User findByEmail(String email);
	
	//select * from user_master where email=? user_pwd=?
	public  User FindByEmailAndUserPwd(String email, String userpwd);

}
