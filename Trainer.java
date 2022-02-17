package com.pettrainerappointment.online;

import java.util.ArrayList;

public class Trainer {
	protected String name;
	protected String phoneNumber;
	protected String email;
	protected String address;
	protected String type;
	protected int trainerId;
	Trainer(){
		
	}
	Trainer(int trainerId,String name,String phoneNumber,String email,String address,String type){
		this.name=name;
		this.phoneNumber=phoneNumber;
		this.email=email;
		this.address=address;
		this.type=type;
		this.trainerId=trainerId;
	}
	public int getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}
	private ArrayList<Appointment> appointmentList=new ArrayList<>();
	public void displayAppointmentList() {
		//Fetching appointments from database and setting it to the appointments list
		for(Appointment appointment:appointmentList) {
			System.out.println("Appointment Details with Client Details and Pet Details");
		}
	}
	public void updateStatus(AppointmentStatus status) {
		//Database operation to update status whether completed or not
	}
}
