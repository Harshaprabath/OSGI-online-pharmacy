package UserManagementPublisher;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CustomerManagementPublisher.Customer;
import dbcontext.IDbContext;
import dbcontext.DbContextImpl;

public class UserImpl implements IUserService {
	
	private Connection connection = null; 
	private IDbContext  dbContext;
	private Statement statement;
	private ResultSet  resultSet;
	private static PreparedStatement preparedStatement = null;
	
	Scanner sc = new Scanner(System.in);
	
	public UserImpl() {
		super();
		
		this.dbContext = new DbContextImpl();
		this.connection = dbContext.getDatabaseConnection();
		
	}

	
	@Override
	public void getAllUsersDetails() {
		try {
			
			String query = "SELECT id, firstName, lastName, email, address, mobileNumber, roleId FROM user WHERE isActive = 1";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("\n==========================================User Details=============================================================================================================================");
			System.out.println
			(
					String.format
					(
							"%20s %20s %20s %20s %20s %20s %20s\n", 
							"UserId", "First Name", "Last Name", "Email", "Address", "Mobile Number", "Role"
					)
			);
			
			System.out.println("=====================================================================================================================================================================================");
			
			String role;
						
			while(resultSet.next()) {
				
				if(resultSet.getInt("roleId") == 1){
					role = "Customer";
				}else {
					role = "Employee";
				}
				
				System.out.printf
				(
						"%20d %20s %20s %24s %20s %20s %20s\n", 
						resultSet.getInt("id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("email"),
						resultSet.getString("address"),
						resultSet.getString("mobileNumber"),
						role
						
						
				);
				
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
			
			
			
		}catch(Exception ex) {
			
			System.out.println("getAllUsersDetailsException:" + ex.getMessage());
			
		}
		
		
	}

	@Override
	public void deleteUser() {
		Integer userId;
		
		System.out.print("\nPlease enter User id : ");
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
	public void saveEmployee() {
		Employee employee = new Employee();
		
		System.out.println("Enter Employee First Name :");
		employee.setFirstName(sc.nextLine().trim());
		
		System.out.println("Enter Employee Last Name :");
		employee.setLastName(sc.nextLine().trim());
		
		System.out.println("Enter Employee Email :");
		employee.setEmail(sc.nextLine().trim());
		
		System.out.println("Enter Employee Address :");
		employee.setAddress(sc.nextLine().trim());
		
		System.out.println("Enter Employee Mobile Number :");
		employee.setMobileNumber(sc.nextLine().trim());
		
		
		try {
			
			String query = "INSERT INTO user VALUES(0, ?, ?, ?, ?, ?, 'None', '1', '2')";
			
			
			preparedStatement = connection.prepareStatement(query); 
			
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getEmail());
			preparedStatement.setString(4, employee.getAddress());
			preparedStatement.setString(5, employee.getMobileNumber());
			
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println("Employee Registration Has Been Successfully");
				
			}else {
				
				System.out.println("Error has been orccured please try again");
				
			}
			
			
		}catch(Exception ex) {
			
			System.out.println("employeeSaveError : " + ex.getMessage());
		}
		
		
	}

	@Override
	public void genarateUserDetailsReportByRoleId() {
		try {
			
			System.out.println("=================================================================");
			System.out.println("Customer Role Id => 1  :");
			System.out.println("Employee Role Id => 2  :");
			System.out.println("Please Enter User Role Id");
			
			int roleId = sc.nextInt();
						
			String  query = "SELECT id, firstName, lastName, email, address, mobileNumber FROM user WHERE isActive = 1 && roleId = ?";
			  
	
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, roleId);
			resultSet = preparedStatement.executeQuery();
			
			File directory = new File("D:\\OSGI\\Assignment-01");
			
			directory.mkdirs();
			
			File file ;
			FileWriter fileWriter ;
			
			if(roleId == 1) {
				file = new File(directory,"CustomerList.txt");
				fileWriter = new FileWriter(file);
				
				fileWriter.write(String.format("================================================= User Details Report ============================================================\n"));
			}else {
				file = new File(directory,"EmployeeList.txt");
				fileWriter = new FileWriter(file);
				
				fileWriter.write(String.format("================================================= Employee Details Report ============================================================\n"));
			}
			
			fileWriter.write(
					
					String.format
					(
							"%20s %20s %20s %20s %20s %20s\n", 
							"UserId", "First Name", "Last Name", "Email", "Address", "Mobile Number"
					)
			);
			
			fileWriter.write(String.format("=========================================================================================================================================\n"));
			
			while(resultSet.next()) {
				
				fileWriter.write(
						
						String.format(
								
								"%20d %20s %20s %24s %20s %20s\n", 
								resultSet.getInt("id"),
								resultSet.getString("firstName"),
								resultSet.getString("lastName"),
								resultSet.getString("email"),
								resultSet.getString("address"),
								resultSet.getString("mobileNumber")
								
						)
				);
				
				fileWriter.write(String.format("-----------------------------------------------------------------------------------------------------------------------------------\n"));
			}
			
			fileWriter.flush();
			fileWriter.close();
			
			
			System.out.println("Report genaration has been successfully");
				
			
		}catch (Exception ex) {
			
			System.out.println("genarateUserDetailsReportException:" + ex.getMessage());
			
			
		}
		
		
	}

	@Override
	public void getDeletedUserDetails() {
	try {
			
			String query = "SELECT id, firstName, lastName, email, address, mobileNumber, roleId FROM user WHERE isActive = 0";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("\n==========================================Deleted User Details==================================================================================================");
			System.out.println
			(
					String.format
					(
							"%20s %20s %20s %20s %20s %20s %20s\n", 
							"UserId", "First Name", "Last Name", "Email", "Address", "Mobile Number", "Role"
					)
			);
			
			System.out.println("==================================================================================================================================================================");
			
			String role;
						
			while(resultSet.next()) {
				
				if(resultSet.getInt("roleId") == 1){
					role = "Customer";
				}else {
					role = "Employee";
				}
				
				System.out.printf
				(
						"%20d %20s %20s %24s %20s %20s %20s\n", 
						resultSet.getInt("id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("email"),
						resultSet.getString("address"),
						resultSet.getString("mobileNumber"),
						role
						
						
				);
				
				System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
			
			
			
		}catch(Exception ex) {
			
			System.out.println("getAllDeletedUsersDetailsException:" + ex.getMessage());
			
		}
		
		
		
	}


	@Override
	public void saveCustomer() {
			
		    Customer customer = new Customer();
			
			System.out.println("Enter Customer First Name :");
			customer.setFirstName(sc.nextLine().trim());
			
			System.out.println("Enter Customer Last Name :");
			customer.setLastName(sc.nextLine().trim());
			
			System.out.println("Enter Customer Email :");
			customer.setEmail(sc.nextLine().trim());
			
			System.out.println("Enter Customer Address :");
			customer.setAddress(sc.nextLine().trim());
			
			System.out.println("Enter Customer Mobile Number :");
			customer.setMobileNumber(sc.nextLine().trim());
			
			System.out.println("Enter Customer Password :");
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

}
