package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CountryMaster;

public interface CountryRepository extends JpaRepository<CountryMaster, Serializable>{
	
	

}

