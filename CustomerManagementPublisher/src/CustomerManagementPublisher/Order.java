package CustomerManagementPublisher;

public class Order {
	
	private int id;
	private String medicineName;
	private String address;
	private boolean isActive;
		
	public Order() {
		super();
	}

	public Order(int id, String medicineName, String address, boolean isActive) {
		super();
		this.id = id;
		this.medicineName = medicineName;
		this.address = address;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
