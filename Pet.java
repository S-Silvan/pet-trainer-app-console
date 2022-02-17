package com.pettrainerappointment.online;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pet {
	private int petId;
	private String name;
	private LocalDate dob;
	private String type;
	private String breed;
	
	Pet(int petId,String name,String dob,String type,String breed){
		this.petId=petId;
		this.name=name;
		this.dob=LocalDate.parse(dob,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.type=type;
		this.breed=breed;
	}
	public int getPetId() {
		return petId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

/*
CREATE TABLE pet(
    pt_id INT AUTO_INCREMENT PRIMARY KEY,
    pt_name VARCHAR(40) NOT NULL,
    pt_type VARCHAR(20) NOT NULL,
    pt_breed VARCHAR(20),
    pt_dob VARCHAR(20)
);
 */
