package InventoryManagementSubscriber;

import java.util.Scanner;
import InventoryManagementPublisher.IInventoryService;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {
	
	ServiceReference serviceReference;
	
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Start Inventory Subscriber Service");
		
		serviceReference = bundleContext.getServiceReference(IInventoryService.class.getName());
		
		IInventoryService inventoryService = (IInventoryService)bundleContext.getService(serviceReference);
		
		renderInventoryDashBoard(inventoryService);
	}
	
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Good Bye");
		
		bundleContext.ungetService(serviceReference);
	}
	
	public void renderInventoryDashBoard(IInventoryService inventoryService) {
		
		Scanner sc = new Scanner(System.in);
		
		int userChoice;
		
		System.out.println("\n\n");
		System.out.println("==============Inventory Management Dashboard===============");
		System.out.println("1 => Save Medicine");
		System.out.println("2 => Get All Medicine Details");
		System.out.println("3 => Delete Medicine");
		System.out.println("4 => Get Medicine By Id");
		System.out.println("5 => Genarate Medicine Details Report");
		
		System.out.println("Please Select Your Option");
		
		userChoice = Integer.parseInt(sc.nextLine().trim());
		
		switch(userChoice) {
		
		case 1:
			inventoryService.saveMedicine();;
			renderInventoryDashBoard(inventoryService);
			break;
			
		case 2:
			inventoryService.getAllMedicinesDetails();;
			renderInventoryDashBoard(inventoryService);
			break;
			
		case 3:
			inventoryService.deleteMedicine();
			renderInventoryDashBoard(inventoryService);
			break;
		
		case 4:
			inventoryService.getMedicineById();
			renderInventoryDashBoard(inventoryService);
			break;
		
		case 5:
			inventoryService.genarateMedicineDetailsReport();
			renderInventoryDashBoard(inventoryService);
			break;
			
		default:
			
			System.out.println("User Option has been incorrect please try again ");
			renderInventoryDashBoard(inventoryService);

	  }

	}

}
