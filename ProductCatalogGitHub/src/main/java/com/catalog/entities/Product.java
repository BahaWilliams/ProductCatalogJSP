package com.catalog.entities;


public class Product {
	private int no;
    private String name;
    private String type;
    private double price;
    
    public Product() {}
    
    public Product(int no, String name, String type, double price) {
		this.no = no;
		this.name = name;
		this.type = type;
		this.price = price;
	}
    
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    
    

}