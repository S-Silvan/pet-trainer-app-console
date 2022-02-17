package com.pettrainerappointment.online;

public class Client {
	protected int clientId;
	protected String name;
	protected String phoneNumber;
	protected String address;
	protected String email;
	protected Pet pet;
	
	public Client() {
		
	}
	Client(int clientId,String name,String phoneNumber,String email,String address,Pet pet){
		this.clientId=clientId;
		this.name=name;
		this.phoneNumber=phoneNumber;
		this.email=email;
		this.address=address;
		this.pet=pet;
	}
	public int getClientId() {
		return clientId;
	}

	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public void setProfileId(int clientId) {
		this.clientId = clientId;
	}
	public Client getClient() {
		return this;
	}
}
