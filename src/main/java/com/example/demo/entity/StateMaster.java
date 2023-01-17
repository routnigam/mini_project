package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name = "STATE_MASTER")
@Data
public class StateMaster {
	@Id
	private Integer stateId;
	private String stateName;
	private Integer countryId;

}
