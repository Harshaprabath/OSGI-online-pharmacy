package CustomerManagementPublisher;

public class RequestMedicine {
	
	private int id;
	private String medicineName;
	private String brandName;
	private String message;
	
	
	public RequestMedicine() {
		super();
	}
	
	public RequestMedicine(int id, String medicineName, String brandName, String message) {
		super();
		this.id = id;
		this.medicineName = medicineName;
		this.brandName = brandName;
		this.message = message;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
