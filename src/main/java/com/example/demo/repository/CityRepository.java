package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CityMaster;

public interface CityRepository extends JpaRepository<CityMaster, Serializable>{
	//select * from CITY_MASTER where stateId  ?
	public List<CityMaster> findByStateId(Integer StateId);
	

}
