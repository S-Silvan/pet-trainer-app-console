package com.pettrainerappointment.online;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Appointment {
	private int appointmentId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private AppointmentStatus status=AppointmentStatus.Pending;
	private Client client;
	private Pet pet;
	private Trainer trainer;
	Appointment(){
		
	}
	Appointment(String date,String startTime,String endTime){
		this.date=LocalDate.parse(date,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.startTime=LocalTime.parse(startTime);
		this.endTime=LocalTime.parse(endTime);
	}
	Appointment(int appointmentId,LocalDate date,LocalTime startTime,LocalTime endTime,Client client,Pet pet,Trainer trainer){
		this.appointmentId=appointmentId;
		this.date=date;
		this.startTime=startTime;
		this.endTime=endTime;
		this.client=client;
		this.pet=pet;
		this.trainer=trainer;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public AppointmentStatus getStatus() {
		return status;
	}
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
}
