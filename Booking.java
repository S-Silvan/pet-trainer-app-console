package com.pettrainerappointment.online;

import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Booking {
	private Appointment appointment;
	private Client client;
	ArrayList<Appointment> appointmentList=new ArrayList<>();
	private Connection conn;
	private PreparedStatement ps;
	private String query;
	Booking(){
		
	}
	public Booking(Client client,Appointment appointment) {
		this.client=client;
		this.appointment=appointment;
	}
	public int addAppointment() throws SQLException{
		query="INSERT INTO"
				+ " appointment (ap_date,ap_start_time,ap_end_time,cl_id,tr_id,pt_id)"
				+" VALUES(?,?,?,?,?,?)";
		
		ps=conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1,appointment.getDate().toString());
		ps.setString(2,appointment.getStartTime().toString());
		ps.setString(3,appointment.getEndTime().toString());
		ps.setInt(4,appointment.getClient().getClientId());
		ps.setInt(5,appointment.getTrainer().getTrainerId());
		ps.setInt(6,appointment.getPet().getPetId());
		int appointmentId=ps.executeUpdate();
		
		appointment.setAppointmentId(appointmentId);
		ResultSet generatedKeys = ps.getGeneratedKeys();
		generatedKeys.next();
		return generatedKeys.getInt(1);
	}
	public void addBooking() throws SQLException{
		query="INSERT INTO"
				+ " booking (bk_status,ap_id,cl_id,tr_id,pt_id)"
				+ " VALUES (?,?,?,?,?)";
		
		ps=conn.prepareStatement(query);
		ps.setString(1,BookingStatus.Booked.toString());
		ps.setInt(2,appointment.getAppointmentId());
		ps.setInt(3,appointment.getClient().getClientId());
		ps.setInt(4,appointment.getTrainer().getTrainerId());
		ps.setInt(5,appointment.getPet().getPetId());
		ps.execute();
	}
	public void confirm() {
			try {
				conn=JDBCSingleton.getInstance().getConnection();
				loadAppointmentList();
				appointment.setClient(client);
				appointment.setPet(client.getPet());
				boolean isTrainerAppointed=appointTrainer(conn);
				if(isTrainerAppointed) {
					int appointmentId=addAppointment();
					appointment.setAppointmentId(appointmentId);
					addBooking();
					System.out.println("--Appointment booking successfull--");
				}else {
					cancel();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(ps!=null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
	}
	public void cancel() {
		System.out.println("(X)Sorry! Unsuccessfull Booking");
	}
	public void loadAppointmentList() throws SQLException{
		query="SELECT * FROM appointment"
				+ " INNER JOIN trainer ON trainer.tr_id=appointment.tr_id"
				+ " INNER JOIN pet ON pet.pt_id=appointment.pt_id"
				+ " INNER JOIN client ON client.cl_id=appointment.cl_id"
				+ " WHERE tr_type='"+client.getPet().getType()+"' AND ap_date='"+appointment.getDate()+"'";
		
		ps=conn.prepareStatement(query);
		ResultSet rs=ps.executeQuery(query);
		
		while(rs.next()) {
			Trainer trainer=new Trainer(rs.getInt("tr_id"),rs.getString("tr_name"),rs.getString("tr_phone_number"),rs.getString("tr_email"),rs.getString("tr_address"),rs.getString("tr_type"));
			Pet pet=new Pet(rs.getInt("pt_id"),rs.getString("pt_name"),rs.getString("pt_dob"),rs.getString("pt_type"),rs.getString("pt_breed"));
			Client client=new Client(rs.getInt("cl_id"),rs.getString("cl_name"),rs.getString("cl_phone_number"),rs.getString("cl_email"),rs.getString("cl_address"),pet);
			int id=rs.getInt("ap_id");
			LocalDate date=LocalDate.parse(rs.getString("ap_date"),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalTime sTime=LocalTime.parse(rs.getString("ap_start_time"));
			LocalTime eTime=LocalTime.parse(rs.getString("ap_end_time"));
			
			Appointment appointment=new Appointment(id,date,sTime,eTime,client,pet,trainer);
			appointmentList.add(appointment);
		}
	}
	public boolean appointTrainer(Connection conn) throws SQLException{
		for(Appointment appointmentItem:appointmentList)
			if(appointment.getStartTime().compareTo(appointmentItem.getStartTime())>0 && appointment.getEndTime().compareTo(appointmentItem.getEndTime())<0)
				return false;
		query="SELECT * FROM trainer"
				+ " WHERE tr_type='"+client.getPet().getType()+"'";
		ps=conn.prepareStatement(query);
		ResultSet rs=ps.executeQuery(query);
		if(rs.next()) {
			appointment.setTrainer(
					new Trainer(
						rs.getInt("tr_id"),
						rs.getString("tr_name"),
						rs.getString("tr_phone_number"),
						rs.getString("tr_email"),
						rs.getString("tr_address"),
						rs.getString("tr_type")
					)
				);
		}else
			return false;
		return true;
	}
}
