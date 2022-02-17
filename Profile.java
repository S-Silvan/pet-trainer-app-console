package com.pettrainerappointment.online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Profile extends Client{
	private PreparedStatement ps;
	private ResultSet rs;
	private String query;
	private Connection conn=JDBCSingleton.getInstance().getConnection();
	private Scanner sc=new Scanner(System.in);
	Profile(int clientId,String name,String phoneNumber,String email,String address,Pet pet){
		super(clientId,name,phoneNumber,email,address,pet);
	}
	public void bookAppointment() throws SQLException{
		System.out.println(">>Enter appointment Date");
		String date=sc.next();
		System.out.println(">>Enter Start time");
		String startTime=sc.next();
		System.out.println(">>Enter End time");
		String endTime=sc.next();
		Appointment appointment=new Appointment(date,startTime,endTime);
		new Booking(getClient(),appointment).confirm();
		menu();
	}
	public void viewAppointments() throws SQLException{
		query="SELECT * FROM appointment "
				+ "INNER JOIN trainer ON appointment.tr_id=trainer.tr_id "
				+ "WHERE cl_id="+clientId;
		ps=conn.prepareStatement(query);
		rs=ps.executeQuery();
		while(rs.next()) {
			System.out.println("--Appointment--");
			System.out.println("Appointment Details");
			System.out.println("Id :"+rs.getInt("ap_id"));
			System.out.println("Date :"+rs.getString("ap_date"));
			System.out.println("Start Time :"+rs.getString("ap_start_time"));
			System.out.println("End Time :"+rs.getString("ap_end_time"));
			System.out.println("Status :"+rs.getString("ap_status"));
			System.out.println("Trainer Details");
			System.out.println("Name :"+rs.getString("tr_name"));
			System.out.println("Phone Number :"+rs.getString("tr_phone_number"));
			System.out.println("Email :"+rs.getString("tr_email"));
		}
		
		menu();
	}
	public void cancelAppointment() throws SQLException{
		System.out.println(">>Enter Appointment ID");
		int appointmentId=sc.nextInt();
		query="UPDATE appointment "
				+ "SET ap_status='Cancelled' "
				+ "WHERE ap_id="+appointmentId;
		ps=conn.prepareStatement(query);
		int result=ps.executeUpdate();
		if(result==1)
			System.out.println("--Appointment Cancellation Successful--");
		else
			System.out.println("(X)Appointment Cancellation Unsuccessful");
		menu();
	}
	public void logout() {
		System.out.println("--Logout Successful--");
		System.exit(0);
	}
	public void menu() {
		System.out.println("1.Book Appointment");
		System.out.println("2.View Appointments Details");
		System.out.println("3.Cancel Appointment");
		System.out.println("4.Log out");
		System.out.println(">>What you want to do?");
		int choice=sc.nextInt();
		try {
			switch(choice) {
				case 1:
					bookAppointment();
					break;
				case 2:
					viewAppointments();
					break;
				case 3:
					cancelAppointment();
					break;
				case 4:
					logout();
					break;
				default:
					System.out.println("(X)Invalid choice");
					menu();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
