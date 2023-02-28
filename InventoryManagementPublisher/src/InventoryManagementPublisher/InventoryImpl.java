package InventoryManagementPublisher;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import dbcontext.IDbContext;
import dbcontext.DbContextImpl;

public class InventoryImpl implements IInventoryService {

	private Connection connection = null; 
	private IDbContext  dbContext;
	private Statement statement;
	private ResultSet  resultSet;
	private static PreparedStatement preparedStatement = null;
	
	public InventoryImpl() {
		super();
		
		this.dbContext = new DbContextImpl();
		this.connection = dbContext.getDatabaseConnection();
		
	}

	Scanner sc = new Scanner(System.in);
	
	@Override
	public void saveMedicine() {
		
		Medicine medicine = new Medicine();
		
		System.out.println("Enter Medicine Name");
		medicine.setName(sc.nextLine().trim());
		
		System.out.println("Enter Brand Name :");
		medicine.setBrandName(sc.nextLine().trim());
		
		System.out.println("Enter Medicine Code:");
		medicine.setCode(sc.nextLine().trim());
		
		System.out.println("Enter Medicine Price:");
		medicine.setPrice(sc.nextInt());
		
		
		
		
		try {
			
			String query = "INSERT INTO medicine VALUES(0, ?, ?,?, ?,'1')";
			
			preparedStatement = connection.prepareStatement(query); 
			
			preparedStatement.setString(1, medicine.getName());
			preparedStatement.setString(2, medicine.getCode());
			preparedStatement.setString(3, medicine.getBrandName());
			preparedStatement.setInt(4, medicine.getPrice());
			
			
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println("Medicine has been Successfully...");
				
			}else {
				
				System.out.println("Error has been occred please try again...");
				
			}	
			
		}catch(Exception ex) {
			
			System.out.println("MedicineServiceException:" + ex.getMessage());
		}
		
		
		
		
	}

	@Override
	public void deleteMedicine() {
		Integer medicineId;
		
		System.out.print("\nPlease enter Medicine id : ");
		medicineId = (sc.nextInt());
		
		try {
			
			String query = "UPDATE medicine SET isActive = 0 WHERE id = ?";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, medicineId);
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println("Medicine has been delete successfully..");
				
			}else {
				
				System.out.println("Cannot find Medicine..");
				
			}
			
		}catch (Exception ex) {
			
			System.out.println("MedicineDeleteException : " + ex.getMessage());
			
		}
		
		
	}

	@Override
	public void getAllMedicinesDetails() {
		
		try {
			
			String query = "SELECT id, name, code, brandName, price FROM medicine WHERE isActive = 1";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("\n==========================================Medicine Details=================================================");
			System.out.println
			(
					String.format
					(
							"%20s %20s %20s %20s %20s\n", 
							"Medicine Id", "Name", "Code", "Brand", "Price"
					)
			);
			
			System.out.println("--------------------------------------------------------------------------------------------------------");
			
			
			while(resultSet.next()) {
				
				System.out.printf
				(
						"%20d %20s %20s %20s %20s\n", 
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("code"),
						resultSet.getString("brandName"),
						resultSet.getString("price")
						
						
				);
				
				System.out.println("--------------------------------------------------------------------------------------------------------");
			}
			
			
			
		}catch(Exception ex) {
			
			System.out.println("getAllMedicineDetailsException:" + ex.getMessage());
			
		}
		
		
	}

	@Override
	public void getMedicineById() {
		Integer medicineId;
		
		System.out.println("Enter Medicine Id : ");
		medicineId = (sc.nextInt());
		
		String query = "SELECT * FROM medicine WHERE id = '"+ medicineId +"' && isActive = 1";
		
		try {
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("=============Medicine Details=============");
			
			while (resultSet.next()) {  
				
		    	  System.out.println("MedicineId      :" + resultSet.getInt("id"));
		    	  System.out.println("Medicine Name   :" + resultSet.getString("name"));
		    	  System.out.println("Medicine Code :" + resultSet.getString("code"));
		    	  System.out.println("Price       :" + resultSet.getInt("price"));
		    	    
		      }	
			
			System.out.println("=====================================");

		} catch (Exception ex) {
			
			System.out.println("Error has been orccured please try again");
			System.out.println(ex.getMessage());
			
		}
		
		
	}

	@Override
	public void genarateMedicineDetailsReport() {
		
		try {
			
			String  query = "SELECT id, name, code, brandName, price FROM medicine WHERE isActive = 1";
			  
	
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			File directory = new File("D:\\OSGI\\Assignment-01");
			
			directory.mkdirs();
			
			File file = new File(directory,"MedicineList.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			

			fileWriter.write(String.format("================================================= Medicine Details Report ============================================================\n"));
			
			
			fileWriter.write(
					
					String.format
					(
							"%20s %20s %20s %20s %20s\n", 
							"Medicine Id", "Name", "Code", "Brand", "Price"
					)
			);
			
			fileWriter.write(String.format("=========================================================================================================================================\n"));
			
			while(resultSet.next()) {
				
				fileWriter.write(
						
						String.format(
								
								"%20d %20s %20s %20s %20s\n", 
								resultSet.getInt("id"),
								resultSet.getString("name"),
								resultSet.getString("code"),
								resultSet.getString("brandName"),
								resultSet.getString("price")
						)
				);
				
				fileWriter.write(String.format("-----------------------------------------------------------------------------------------------------------------------------------\n"));
			}
			
			fileWriter.flush();
			fileWriter.close();
			
			
			System.out.println("Report genaration has been successfully");
				
			
		}catch (Exception ex) {
			
			System.out.println("genarateInventoryDetailsReportException:" + ex.getMessage());
			
			
		}
		
	}

}
