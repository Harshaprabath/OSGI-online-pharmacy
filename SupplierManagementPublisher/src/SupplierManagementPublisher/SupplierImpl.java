package SupplierManagementPublisher;

import dbcontext.DbContextImpl;
import dbcontext.IDbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;



public class SupplierImpl implements ISupplierService {

	
	private Connection connection = null; 
	private IDbContext  dbContext;
	private Statement statement;
	private ResultSet  resultSet;
	private static PreparedStatement preparedStatement = null;
	
	Scanner sc = new Scanner(System.in);
	
	public SupplierImpl() {
		super();
		
		this.dbContext = new DbContextImpl();
		this.connection = dbContext.getDatabaseConnection();
	}
	
	//Save Supplier
	@Override
	public void saveSupplier() {
		
		Supplier supplier = new Supplier();
		
		System.out.println("Enter Your First Name :");
		supplier.setFirstName(sc.nextLine().trim());
		
		System.out.println("Enter Your Last Name :");
		supplier.setLastName(sc.nextLine().trim());
		
		System.out.println("Enter Your Email :");
		supplier.setEmail(sc.nextLine().trim());
		
		System.out.println("Enter Your NIC :");
		supplier.setNic(sc.nextLine().trim());
		
		System.out.println("Enter Your Address :");
		supplier.setAddress(sc.nextLine().trim());
		
		System.out.println("Enter Your Mobile Number :");
		supplier.setMobileNumber(sc.nextLine().trim());
		
		System.out.println("Enter Your Company Name :");
		supplier.setCompanyName(sc.nextLine().trim());
		
		System.out.println("Enter Your Password :");
		supplier.setPassword(sc.nextLine().trim());
		
		try {
			
			String query = "INSERT INTO supplier VALUES( 0,?, ?, ?, ?, ?, ?, ?, ?, '1')";
			
			preparedStatement = connection.prepareStatement(query); 
			
			preparedStatement.setString(1, supplier.getFirstName());
			preparedStatement.setString(2, supplier.getLastName());
			preparedStatement.setString(3, supplier.getEmail());
			preparedStatement.setString(4, supplier.getNic());
			preparedStatement.setString(5, supplier.getAddress());
			preparedStatement.setString(6, supplier.getMobileNumber());
			preparedStatement.setString(7, supplier.getCompanyName());
			preparedStatement.setString(8, supplier.getPassword());
			
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				System.out.println("Supplier Registration Has Been Successfully ");
			}else {
				System.out.println("Error has been orccured please try again..");
			}
			
		}catch(Exception ex) {
			System.out.println("supplierSaveError : " + ex.getMessage());
		}
	}

	@Override
	public void getAllSupplierDetails() {
		try {
			
			String query = "SELECT id, firstName, lastName, email, nic, address, mobileNumber , companyName FROM supplier WHERE isActive = 1 ";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("\n======================================================================== Supplier  Details ==================================================================================\n");
			System.out.println
			(
					String.format
					(
							"%20s %20s %20s %25s %20s %20s %20s %20s\n", 
							"SupplierId", "First Name", "Last Name", "Email", "NIC" , "Address", "Mobile Number", "CompanyName"
					)
			);
			
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			
			
			while(resultSet.next()) {
				
				System.out.printf
				(
						"%20d %20s %20s %30s %20s %20s %20s %20s\n", 
						resultSet.getInt("id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("email"),
						resultSet.getString("nic"),
						resultSet.getString("address"),
						resultSet.getString("mobileNumber"),
						resultSet.getString("companyName")
						
						
				);
				
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
			
			
			
		}catch(Exception ex) {
			
			System.out.println("getAllSupplierDetailsException:" + ex.getMessage());
			
		}
		
	}

	@Override
	public void deleteSupplier() {
		
		Integer supplierId;
		
		System.out.print("\nPlease enter Supplier id : ");
		supplierId = (sc.nextInt());
		
		try {
			
			String query = "UPDATE supplier SET isActive = 0 WHERE id = ?";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, supplierId);
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println("Delete supplier has been successfully..");
				
			}else {
				
				System.out.println("Cannot find supplier..");
				
			}
			
		}catch (Exception ex) {
			
			System.out.println("supplierDeleteException : " + ex.getMessage());
			
		}
		
	}

	@Override
	public void getSupplierById() {
		Integer supplierId;
		
		System.out.println("Enter Supplier Id : ");
		supplierId = (sc.nextInt());
		
		String query = "SELECT * FROM supplier WHERE id = '"+ supplierId +"' && isActive = 1";
		
		try {
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {  
				
				System.out.printf
				(
						"\n Supplier Id: %d\n Supplier First Name: %s\n Supplier Last Name: %s\n Supplier Email: %s\n Supplier NIC: %s\n Supplier Address: %s\n Supplier No: %s\n Supplier Company Name: %s\n", 
						resultSet.getInt("id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("email"),
						resultSet.getString("nic"),
						resultSet.getString("address"),
						resultSet.getString("mobileNumber"),
						resultSet.getString("companyName")
						
						
				);
			}

		} catch (Exception ex) {
			
			System.out.println("Error has been occured please try again");
			System.out.println(ex.getMessage());
			
		}
	}



	@Override
	public void genarateSupplierDetailsReport() {
		try {
			
			String query = "SELECT id, firstName, lastName, email, nic, address, mobileNumber , companyName FROM supplier WHERE isActive = 1 ";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			File directory = new File("D:\\OSGI\\Assignment-01");
			
			directory.mkdirs();
			
			File file = new File(directory,"SupplierList.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write(String.format("=========================================================== Supplier Details Report =================\n"));
			fileWriter.write(
					
					String.format
					(
							"%20s %20s %20s %25s %20s %20s %20s %20s\n", 
							"SupplierId", "First Name", "Last Name", "Email", "NIC" , "Address", "Mobile Number", "CompanyName"
					)
			);
			
			fileWriter.write(String.format("======================================================================================================\n"));
			
			while(resultSet.next()) {
				
				fileWriter.write(
						
						String.format(
								
								"%20d %20s %20s %30s %20s %20s %20s %20s\n", 
								resultSet.getInt("id"),
								resultSet.getString("firstName"),
								resultSet.getString("lastName"),
								resultSet.getString("email"),
								resultSet.getString("nic"),
								resultSet.getString("address"),
								resultSet.getString("mobileNumber"),
								resultSet.getString("companyName")
								
						)
				);
				
				fileWriter.write(String.format("-------------------------------------------------------------------------------------------------\n"));
			}
			
			fileWriter.flush();
			fileWriter.close();
			
			
			System.out.println("Report genaration has been successfully");
				
			
		}catch (Exception ex) {
			
			System.out.println("genarateSupplierDetailsReportException:" + ex.getMessage());
			
			
		}
	}

	
	
}
