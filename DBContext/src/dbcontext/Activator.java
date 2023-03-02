package dbcontext;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	
	IDbContext context = new DbContextImpl();

	public void start(BundleContext bundleContext) throws Exception {
		//Activator.context = bundleContext;
		
		IDbContext dbContext = new DbContextImpl();
		serviceRegistration = bundleContext.registerService(IDbContext.class.getName(), dbContext, null);
		System.out.println("Pharmacy Store Data Publisher Service Started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		
		serviceRegistration.unregister();
		System.out.println("BookStore Data Publisher Service Stop");
	}

}
