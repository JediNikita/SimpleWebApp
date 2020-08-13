package com.simplewebapp.beans;

public class Product {
	private String name;
	private String code;
	private float price;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Product(String name, String code, float price) {
		super();
		this.name = name;
		this.code = code;
		this.price = price;
	}
	
	
}
