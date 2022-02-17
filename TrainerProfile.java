package com.pettrainerappointment.online;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TrainerProfile extends Trainer{
	private PreparedStatement ps;
	private ResultSet rs;
	private String query;
	private Connection conn=JDBCSingleton.getInstance().getConnection();
	private Scanner sc=new Scanner(System.in);
	TrainerProfile(int id,String name,String phoneNumber,String email,String address,String type){
		super(id,name,phoneNumber,email,address,type);
	}
	public void viewAppointments() throws SQLException{
		query="SELECT * FROM appointment "
				+ "INNER JOIN client ON appointment.cl_id=client.cl_id "
				+ "INNER JOIN pet ON appointment.pt_id=client.pt_id "
				+ "WHERE tr_id="+trainerId;
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
			System.out.println("Client Details");
			System.out.println("Name :"+rs.getString("cl_name"));
			System.out.println("Phone Number :"+rs.getString("cl_phone_number"));
			System.out.println("Email :"+rs.getString("cl_email"));
			System.out.println("Pet Details");
			System.out.println("Name :"+rs.getString("pt_name"));
			System.out.println("DOB :"+rs.getString("pt_dob"));
			System.out.println("Type :"+rs.getString("pt_type"));
			System.out.println("Breed :"+rs.getString("pt_breed"));
		}
		
		menu();
	}
	public void logout() {
		System.out.println("--Logout Successful--");
		System.exit(0);
	}
	public void completeAppointment() throws SQLException {
		System.out.println(">>Enter appointment ID");
		int appointmentId=sc.nextInt();
		
		query="UPDATE appointment "
				+ "SET ap_status='Completed' "
				+ "WHERE ap_id="+appointmentId;
		ps=conn.prepareStatement(query);
		int result=ps.executeUpdate();
		if(result==1)
			System.out.println("--Appointment Completion Successful--");
		else
			System.out.println("(X)Appointment Completion Unsuccessful");
		menu();
	}
	public void menu() {
		System.out.println("1.View Appointments Details");
		System.out.println("2.Appointment Status Completed");
		System.out.println("3.Log out");
		System.out.println(">>What you want to do?");
		int choice=sc.nextInt();
		try {
			switch(choice) {
				case 1:
					viewAppointments();
					break;
				case 2:
					completeAppointment();
					break;
				case 3:
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
	

