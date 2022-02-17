package com.pettrainerappointment.online;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
		System.out.println("1.Login");
		System.out.println("2.register");
		System.out.println(">>Enter your choice");
		
		Scanner in=new Scanner(System.in);
		int choice=in.nextInt();
		switch(choice) {
			case 1:
				new Authenticator().login();
				break;
			case 2:
				new Authenticator().register();
				break;
			default:
				System.out.println("(X)Invalid Choice");
		}
	}

}
