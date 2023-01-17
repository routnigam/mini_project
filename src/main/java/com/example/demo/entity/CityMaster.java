package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name = "CITY_MASTER")
@Data
public class CityMaster {
		@Id
		private Integer cityId;
		private String cityName;
		private Integer stateId;

}
