package InventoryManagementPublisher;

public class Medicine {

	private int id;
	private String name;
	private String code;
	private String brandName;
	private int price;
	private boolean isActive;
	
	
	
	public Medicine() {
		super();
	}



	public Medicine(int id, String name, String code, String brandName, int price, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.brandName = brandName;
		this.price = price;
		this.isActive = isActive;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getBrandName() {
		return brandName;
	}



	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public boolean isActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
