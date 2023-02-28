package CustomerManagementPublisher;

import dbcontext.IDbContext;
import dbcontext.DbContextImpl;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerImpl implements ICustomerService{
	
	private Connection connection = null; 
	private IDbContext  dbContext;
	private Statement statement;
	private ResultSet  resultSet;
	private static PreparedStatement preparedStatement = null;
	
	Scanner sc = new Scanner(System.in);
	
	public CustomerImpl() {
		super();
		
		this.dbContext = new DbContextImpl();
		this.connection = dbContext.getDatabaseConnection();
		
	}
	

	@Override
	public void saveCustomer() {
			
		    Customer customer = new Customer();
			
			System.out.println("Enter Your First Name :");
			customer.setFirstName(sc.nextLine().trim());
			
			System.out.println("Enter Your Last Name :");
			customer.setLastName(sc.nextLine().trim());
			
			System.out.println("Enter Your Email :");
			customer.setEmail(sc.nextLine().trim());
			
			System.out.println("Enter Your Address :");
			customer.setAddress(sc.nextLine().trim());
			
			System.out.println("Enter Your Mobile Number :");
			customer.setMobileNumber(sc.nextLine().trim());
			
			System.out.println("Enter Your Password :");
			customer.setPassword(sc.nextLine().trim());
			
			try {
				
				String query = "INSERT INTO user VALUES(0, ?, ?, ?, ?, ?, ?, '1', '1')";
				
				preparedStatement = connection.prepareStatement(query); 
				
				preparedStatement.setString(1, customer.getFirstName());
				preparedStatement.setString(2, customer.getLastName());
				preparedStatement.setString(3, customer.getEmail());
				preparedStatement.setString(4, customer.getAddress());
				preparedStatement.setString(5, customer.getMobileNumber());
				preparedStatement.setString(6, customer.getPassword());
				
				int isSuccess = preparedStatement.executeUpdate();
				
				if(isSuccess > 0) {
					
					System.out.println("Registration Has Been Successfully");
					
				}else {
					
					System.out.println("Error has been orccured please try again");
					
				}
				
				
			}catch(Exception ex) {
				
				System.out.println("customerSaveError : " + ex.getMessage());
			}
			
		
	}

	@Override
	public void requestMedicine() {
			
			RequestMedicine requestMedicine = new RequestMedicine();
			
			System.out.println("Please enter medicine name");
			requestMedicine.setMedicineName(sc.nextLine().trim());
			
			System.out.println("Please enter brand name");
			requestMedicine.setBrandName(sc.nextLine().trim());
			
			System.out.println("Please enter messeage");
			requestMedicine.setMessage(sc.nextLine().trim());
			
			try {
				
				String query = "INSERT INTO requestmedicine VALUES(0, ?, ?, ?)";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setString(1, requestMedicine.getMedicineName());
				preparedStatement.setString(2, requestMedicine.getBrandName());
				preparedStatement.setString(3, requestMedicine.getMessage());
				
				int isSuccess = preparedStatement.executeUpdate();
				
				if(isSuccess > 0) {
					
					System.out.println("Request medicine has been successfully");
					
				}else {
					
					System.out.println("Error has been orccured please try again");
					
				}
				
				
			}catch (Exception ex) {
				
				System.out.println("requestmedicineError : " + ex.getMessage());
			}
			
		}

	@Override
	public void orderMedicine() {
		
		Order order = new Order();
		
		System.out.println("Please enter medicine name");
		order.setMedicineName(sc.nextLine().trim());
		
		System.out.println("Please enter your address");
		order.setAddress(sc.nextLine().trim());
		
		
		try {
			
			String query = "INSERT INTO orders VALUES(0, ?, ?, '1')";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, order.getMedicineName());
			preparedStatement.setString(2, order.getAddress());
			
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println(" Order has been successfully");
				
			}else {
				
				System.out.println("Error has been orccured please try again");
				
			}
			
		}catch (Exception ex) {
			
			System.out.println("order : " + ex.getMessage());
			
		}
		
	}

	@Override
	public void getAllMedicineDetails() {
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
							"BookId", "Name", "Code", "Brand", "Price"
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
	public void deleteCustomer() {
		
		Integer userId;
		
		System.out.print("\nPlease enter Customer id : ");
		userId = (sc.nextInt());
		
		try {
			
			String query = "UPDATE user SET isActive = 0 WHERE id = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1,userId);
			
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println("Delete user has been successfully..");
				
			}else {
				
				System.out.println("Cannot find user..");
				
			}
			
		}catch (Exception ex) {
			
			System.out.println("userDeleteException : " + ex.getMessage());
			
		}
		
	}

	@Override
	public void getAllReqestMedicine() {
	try {
			
			String query = "SELECT id, medicineName, brandName, messeage FROM requestmedicine";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("\n==========================================Request Medicine Details=================================================");
			System.out.println
			(
					String.format
					(
							"%20s %20s %20s %20s\n", 
							"Id", "Medicine Name", "Brand", "Message"
					)
			);
			
			System.out.println("--------------------------------------------------------------------------------------------------------");
			
			
			while(resultSet.next()) {
				
				System.out.printf
				(
						"%20d %20s %20s %20s\n", 
						resultSet.getInt("id"),
						resultSet.getString("medicineName"),
						resultSet.getString("brandName"),
						resultSet.getString("messeage")
						
						
				);
				
				System.out.println("--------------------------------------------------------------------------------------------------------");
			}
			
			
			
		}catch(Exception ex) {
			
			System.out.println("getAllMedicineDetailsException:" + ex.getMessage());
			
		}
		
	}

	@Override
	public void getRequestMedicinereport() {
		
		try {
			
			String query = "SELECT id, medicineName, brandName, messeage FROM requestmedicine";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			File directory = new File("D:\\OSGI\\Assignment-01");
			
			directory.mkdirs();
			
			File file = new File(directory,"RequestMedicine.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write(String.format("=================================================== Request Medicine Details Report ============================================================================================\n\n"));
			fileWriter.write(
					
					String.format
					(
							"%25s %25s %25s %25s\n", 
							"Medicine Id", "Medicine Name", "Brand Name", "Message"
					)
			);
			
			fileWriter.write(String.format("===================================================================================================================================================================================\n\n"));
			
			while(resultSet.next()) {
				
				fileWriter.write(
						
						String.format(
								
								"%20d %25s %25s %25s\n", 
								resultSet.getInt("id"),
								resultSet.getString("medicineName"),
								resultSet.getString("brandName"),
								resultSet.getString("messeage")
								
						)
				);
				
				fileWriter.write(String.format("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}
			
			fileWriter.flush();
			fileWriter.close();
			
			
			System.out.println("Request Medicine Report genaration has been successfully");
				
			
		}catch (Exception ex) {
			
			System.out.println("requestMedicineDetailsReportException:" + ex.getMessage());
			
			
		}
		
	}

	@Override
	public void getOrderMedicinereport() {
		
		try {
			
			String query = "SELECT id, medicinename , address FROM orders";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			File directory = new File("D:\\OSGI\\Assignment-01");
			
			directory.mkdirs();
			
			File file = new File(directory,"OrderMedicine.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write(String.format("=================================================== Order Book Details Report ============================================================================================\n\n"));
			fileWriter.write(
					
					String.format
					(
							"%25s %25s %25s\n", 
							"Oder Id", "Medicine Name", "Address"
					)
			);
			
			fileWriter.write(String.format("===================================================================================================================================================================================\n\n"));
			
			while(resultSet.next()) {
				
				fileWriter.write(
						
						String.format(
								
								"%20d %25s %25s\n", 
								resultSet.getInt("id"),
								resultSet.getString("medicineName"),
								resultSet.getString("address")
								
						)
				);
				
				fileWriter.write(String.format("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}
			
			fileWriter.flush();
			fileWriter.close();
			
			
			System.out.println("Order Medicine Report genaration has been successfully");
				
			
		}catch (Exception ex) {
			
			System.out.println("orderMedicineDetailsReportException:" + ex.getMessage());
			
			
		}
	}
		
	
	
}
