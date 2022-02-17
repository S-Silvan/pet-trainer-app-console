package com.pettrainerappointment.online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Authenticator {
	private String userId;
	private String password;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;
	private Connection con;
	Scanner sc=new Scanner(System.in);
	
	public void addProfile(int petId) throws SQLException {
		System.out.println(">>Enter your Name:");
		String name=sc.nextLine();
		System.out.println(">>Enter your Phone No:");
		String phoneNumber=sc.nextLine();
		System.out.println(">>Enter your Email ID:");
		String emailId=sc.nextLine();
		System.out.println(">>Enter your Address:");
		String address=sc.nextLine();
		System.out.println(">>Enter your Password:");
		String password =sc.nextLine();
		
		query="INSERT INTO"
				+" client"
				+" (cl_name,cl_phone_number,cl_email,cl_address,cl_password,pt_id)"
				+" VALUES(?,?,?,?,?,?)";
		
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,name);
		pstmt.setString(2,phoneNumber);
		pstmt.setString(3, emailId);
		pstmt.setString(4, address);
		pstmt.setString(5, password);
		pstmt.setInt(6, petId);
		
		pstmt.executeUpdate();
	}
	public int addPet() throws SQLException {
		System.out.println(">>Enter pet Name:");
		String name=sc.nextLine();
		System.out.println(">>Select pet type:\n1. "+PetType.Cat+"\n2. "+PetType.Dog);
		int typeChoice=sc.nextInt();
		String type="Cat";
		switch(typeChoice) {
			case 1:
				type="Cat";
				break;
			case 2:
				type="Dog";
				break;
			default:
				System.out.println("Invalid Choise: Default Dog is taken");
				break;
		}
		type=sc.nextLine();
		System.out.println(">>Enter pet breed:");
		String breed=sc.nextLine();
		System.out.println(">>Enter pet Date of Birth:");
		String dob=sc.nextLine();
		
		query="INSERT INTO"
				+" pet"
				+" (pt_name,pt_type,pt_breed,pt_dob)"
				+" VALUES(?,?,?,?)";
		
		pstmt=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1,name);
		pstmt.setString(2,type);
		pstmt.setString(3,breed);
		pstmt.setString(4,dob);
		
		pstmt.executeUpdate();
		ResultSet generatedKeys = pstmt.getGeneratedKeys();
		generatedKeys.next();
		return generatedKeys.getInt(1);
	}
	public  void register(){
		con = JDBCSingleton.getInstance().getConnection();
		try {
			int petId=addPet();
			addProfile(petId);
			System.out.println("--Successful Registration--");
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException e){
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}					
				}
				e.printStackTrace();
			}	
		}
	}

	public void login() {
		System.out.println("--Login--");
		System.out.println("1.Client Login");
		System.out.println("2.Trainer Login");
		System.out.println("3.Admin Login");
		System.out.println(">>Select Type");
		int choice=sc.nextInt();
		
		System.out.println(">>Enter User ID:");
		userId=sc.next();
		System.out.println(">>Enter your Password:");
		password=sc.next();
		
		switch(choice) {
			case 1:
				userLogin();
				break;
			case 2:
				trainerLogin();
				break;
			case 3:
				adminLogin();
				break;
			default:
				System.out.println("(X)Invalid Choice");
		}
	}
	
	public  void userLogin() {
		query="SELECT * FROM client "
				+ "INNER JOIN pet ON client.pt_id=pet.pt_id "
				+ "WHERE cl_email=? AND cl_password=?";
		con = JDBCSingleton.getInstance().getConnection();
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,userId);
			pstmt.setString(2,password);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String name=rs.getString("cl_name");
				System.out.println("--Successfull Client login: "+name+"--");
				new Profile(
						rs.getInt("cl_id"),
						name,
						rs.getString("cl_phone_number"),
						rs.getString("cl_email"),
						rs.getString("cl_address"),
						new Pet(
								rs.getInt("pt_id"),
								rs.getString("pt_name"),
								rs.getString("pt_dob"),
								rs.getString("pt_type"),
								rs.getString("pt_breed")
						)
					).menu();
			}
			else {
				System.out.println("(X)Invalid User or Password");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException e){
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}					
				}
				e.printStackTrace();
			}
		}
	}

	public  void trainerLogin(){
		query="SELECT * FROM trainer WHERE tr_email=? AND tr_password=?";
		con = JDBCSingleton.getInstance().getConnection();
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,userId);
			pstmt.setString(2,password);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String name=rs.getString("tr_name");
				System.out.println("--Successfull Trainer login: "+name+"--");
				new TrainerProfile(
						rs.getInt("tr_id"),
						rs.getString("tr_name"),
						rs.getString("tr_phone_number"),
						rs.getString("tr_email"),
						rs.getString("tr_address"),
						rs.getString("tr_type")
					).menu();
			}
			else {
				System.out.println("(X)Invalid User or Password");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException e){
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}					
				}
				e.printStackTrace();
			}
		}
	}
	
	public  void adminLogin(){
		query="SELECT * FROM admin WHERE ad_email=? AND ad_password=?";
		con = JDBCSingleton.getInstance().getConnection();
		
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,userId);
			pstmt.setString(2,password);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String name=rs.getString("ad_name");
				System.out.println("--Successfull Admin login: "+name+"--");
				new Admin().menu();
			}
			else {
				System.out.println("(X)Invalid User or Password");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException e){
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}					
				}
				e.printStackTrace();
			}
		}
	}

}


