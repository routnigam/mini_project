package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name="COUNTRY_MASTER")
@Data
public class CountryMaster {
	@Id
	private Integer countryId;
	private String countryName;

}
