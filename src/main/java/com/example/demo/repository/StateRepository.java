package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.StateMaster;

public interface StateRepository extends JpaRepository<StateMaster, Serializable>{
	//select * from STATE_MASTER where countryId=?
	public List<StateMaster> findByCountryId(Integer CountryId);

}
