package CustomerManagementSubscriber;

import CustomerManagementPublisher.ICustomerService;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {
	
	ServiceReference serviceReference;
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		
		System.out.println("Start Customer Subscriber Service");
		
		serviceReference = bundleContext.getServiceReference(ICustomerService.class.getName());
		
		
		ICustomerService customerService = (ICustomerService)bundleContext.getService(serviceReference);
		renderCustomerDashboard(customerService);
		
	}
	
	public void stop(BundleContext bundleContext) throws Exception {
		
		System.out.println("Good Bye");
		bundleContext.ungetService(serviceReference);
		
	}
	
	public void  renderCustomerDashboard(ICustomerService customerService) {
		
		Scanner sc = new Scanner(System.in);
		
		int customerDashboardChoice;
		String choice = "y";
		
		System.out.println("\n\n");
		System.out.println("==============User Dashboard===============");
		System.out.println("1 => Customer Registration");
		System.out.println("2 => Request Medicine");
		System.out.println("3 => Get All Medicine Details");
		System.out.println("4 => Order Medicine");
		System.out.println("5 => Delete Customer");
		System.out.println("6 => Get Request Medicine Details");
		System.out.println("7 => Generate Request Medicine Details");
		System.out.println("8 => Generate Order Medicine Details");
		System.out.println("Please Select Your Option");
		
		customerDashboardChoice = Integer.parseInt(sc.nextLine().trim());
		
		switch (customerDashboardChoice) {
		case 1:
			
			customerService.saveCustomer();
			renderCustomerDashboard(customerService);
			
			break;
		case 2:
			
			customerService.requestMedicine();
			
			while(choice.equals("y")) {
				
				System.out.printf("\nDo you want to reqest another Medicine(y/n) ");
				choice = sc.nextLine().trim().toLowerCase();
				
				if(choice.equals("y")) {
					
					customerService.requestMedicine();
				}
				
			}
			
			renderCustomerDashboard(customerService);
			break;
		case 3:
			
			customerService.getAllMedicineDetails();
			
			renderCustomerDashboard(customerService);
			
			
			break;
		case 4:
				customerService.orderMedicine();
				
				while(choice.equals("y")) {
				
				System.out.printf("\nDo you want to reqest another Medicine(y/n) ");
				choice = sc.nextLine().trim().toLowerCase();
				
				if(choice.equals("y")) {
					
					customerService.orderMedicine();
				}
				
			}
			
			renderCustomerDashboard(customerService);
			break;
		case 5:
			customerService.deleteCustomer();
			
			while(choice.equals("y")) {
				
				System.out.printf("\nDo you want to delete another customer(y/n) ");
				choice = sc.nextLine().trim().toLowerCase();
				
				if(choice.equals("y")) {
					
					customerService.saveCustomer();
				}
			}
			
			renderCustomerDashboard(customerService);
			break;
		case 6:
			customerService.getAllReqestMedicine();
			
			renderCustomerDashboard(customerService);
			
			
			break;
		case 7:
			customerService.getRequestMedicinereport();
			
			renderCustomerDashboard(customerService);
			
			
			break;
		case 8:
			customerService.getOrderMedicinereport();
			
			renderCustomerDashboard(customerService);
			
			
			break;
			
		default:
			System.out.println("User Option has been incorrect please try again ");
			renderCustomerDashboard(customerService);
		}
		
	}

}
