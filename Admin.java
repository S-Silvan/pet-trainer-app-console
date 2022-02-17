package com.pettrainerappointment.online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
       private PreparedStatement ps;
       private ResultSet rs;
       private String query;
       private Connection conn=JDBCSingleton.getInstance().getConnection();
       private Scanner sc=new Scanner(System.in);

       public void viewTrainers() throws SQLException{
               query="SELECT * FROM trainer";

               ps=conn.prepareStatement(query);
               rs=ps.executeQuery();
               while(rs.next()) {
                       System.out.println("Trainer Details");
                       System.out.println("Id :"+rs.getString("tr_id"));
                       System.out.println("Name :"+rs.getString("tr_name"));
                       System.out.println("Phone Number :"+rs.getString("tr_phone_number"));
                       System.out.println("Email :"+rs.getString("tr_email"));
                       System.out.println("Address :"+rs.getString("tr_address"));
                       System.out.println("Type :"+rs.getString("tr_type"));
               }

               menu();
       }
       public void viewAppointments() throws SQLException{
               query="SELECT * FROM appointment "
                               + "INNER JOIN client ON appointment.cl_id=client.cl_id "
                               + "INNER JOIN pet ON appointment.pt_id=client.pt_id "
                               + "INNER JOIN trainer ON appointment.tr_id=trainer.tr_id";

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
                       System.out.println("Trainer Details");
                       System.out.println("Name :"+rs.getString("tr_name"));
                       System.out.println("Phone Number :"+rs.getString("tr_phone_number"));
                       System.out.println("Email :"+rs.getString("tr_email"));
               }

               menu();
       }
       public void addTrainer() throws SQLException {
               System.out.println(">>Enter trainer Name:");
               String name=sc.next();
               System.out.println(">>Enter trainer Phone No:");
               String phoneNumber=sc.next();
               System.out.println(">>Enter trainer Email ID:");
               String emailId=sc.next();
               System.out.println(">>Enter trainer Address:");
               String address=sc.next();
               System.out.println(">>Enter trainer Type:");
               String type =sc.next();
               System.out.println(">>Enter trainer Password:");
               String password =sc.next();

               query="INSERT INTO"
                               +" trainer"
                               +" (tr_name,tr_phone_number,tr_email,tr_address,tr_type,tr_password)"
                               +" VALUES(?,?,?,?,?,?)";

               ps=conn.prepareStatement(query);
               ps.setString(1,name);
               ps.setString(2,phoneNumber);
               ps.setString(3, emailId);
               ps.setString(4, address);
               ps.setString(5, type);
               ps.setString(6, password);

               int result=ps.executeUpdate();
               if(result==1)
                       System.out.println("--Trainer Registration Successful--");
               else
                       System.out.println("(X)Trainer Registration Unsuccessful");
               menu();
       }
       /*public void deleteTrainer() throws SQLException {
               System.out.println(">>Enter Trainer ID");
               int trainerId=sc.nextInt();
               query="DELETE FROM trainer WHERE "+trainerId;

               ps=conn.prepareStatement(query);
               int result=ps.executeUpdate();
               if(result==1)
                       System.out.println("--Trainer Deletion Successful--");
               else
                       System.out.println("--Trainer Deletion Unsuccessful--");
               menu();
       }*/
       public void logout() {
               System.out.println("--Logout Successful--");
               System.exit(0);
       }
       public void menu() {
               System.out.println("1.View Appointments");
               System.out.println("2.View Trainers");
               System.out.println("3.Add Trainer");
               System.out.println("4.Log out");
               System.out.println(">>What you want to do?");
               int choice=sc.nextInt();
               try {
                       switch(choice) {
                               case 1:
                                       viewAppointments();
                                       break;
                               case 2:
                                       viewTrainers();
                                       break;
                               case 3:
                                      addTrainer();
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
