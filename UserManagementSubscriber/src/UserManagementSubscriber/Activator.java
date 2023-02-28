package UserManagementSubscriber;

import java.util.Scanner;

import UserManagementPublisher.IUserService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	ServiceReference serviceReference;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Start User Subscriber Service");
		
		serviceReference = bundleContext.getServiceReference(IUserService.class.getName());
		
		IUserService userService = (IUserService)bundleContext.getService(serviceReference);
		
		renderUserDashboard(userService);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Good Bye");
		bundleContext.ungetService(serviceReference);
	}
	
public void renderUserDashboard(IUserService userService) {
		
		Scanner sc = new Scanner(System.in);
		
		int userDashboardChoice;
		String choice = "y";
		
		System.out.println("\n\n");
		System.out.println("==============User Management Dashboard===============");
		System.out.println("1 => Add Customer");
		System.out.println("2 => Get All User Details");
		System.out.println("3 => Delete User");
		System.out.println("4 => Employee Registration");
		System.out.println("5 => Genarate User Detail Report");
		System.out.println("6 => Get Deleted User Details");
		System.out.println("Please Select Your Option");
		
		userDashboardChoice = Integer.parseInt(sc.nextLine().trim());
		
		switch(userDashboardChoice) {
			
			case 1:
				
				userService.saveCustomer();
				
				while(choice.equals("y")) {
					
					System.out.printf("\nDo you want to add another customer(y/n) ");
					choice = sc.nextLine().trim().toLowerCase();
					
					if(choice.equals("y")) {
						
						userService.saveCustomer();
					}
					
				}
				
				renderUserDashboard(userService);
				break;
			
			case 2:
				
				userService.getAllUsersDetails();
				renderUserDashboard(userService);
				break;
				
			case 3:
				
				userService.deleteUser();
				
				while(choice.equals("y")) {
					
					System.out.printf("\nDo you want to delete another customer(y/n) ");
					choice = sc.nextLine().trim().toLowerCase();
					
					if(choice.equals("y")) {
						
						userService.deleteUser();
					}
				}
				
				renderUserDashboard(userService);
				break;
			
			case 4:
				
				userService.saveEmployee();
				
				while(choice.equals("y")) {
					
					System.out.printf("\nDo you want to add another Employee(y/n) ");
					choice = sc.nextLine().trim().toLowerCase();
					
					if(choice.equals("y")) {
						
						userService.saveEmployee();
					}
					
				}
				
				renderUserDashboard(userService);
				break;
				
			case 5:
				
				userService.genarateUserDetailsReportByRoleId();
				renderUserDashboard(userService);
				break;
			case 6:
				
				userService.getDeletedUserDetails();
				renderUserDashboard(userService);
				break;
				
			default:
				
				System.out.println("User Option has been incorrect please try again ");
				renderUserDashboard(userService);
				
		
		
		
		}
	}


}
