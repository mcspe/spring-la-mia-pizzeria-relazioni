package org.java.pizzeria.db.pojo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Pizza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 40, nullable = false)
	@Length(min = 3, max = 40, message = "Pizza name must have between 3 and 40 characters")
	@NotBlank(message = "Pizza name cannot be empty")
	private String name;
	
	@Column(length = 1000)
	private String description;
	
	@Column(length = 1000)
	@URL(message = "Pizza image URL is not a valid URL")
	private String img;
	
	@Column(nullable = false)
	@NotNull
	@Positive(message = "Pizza price must be greater than zero")
	private int price;
	
	public Pizza() {}
	
	public Pizza(String name, String description, String img, int price) {
		setName(name);
		setDescription(description);
		setImg(img);
		setPrice(price);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "[" + getId() + "] " + getName() + "\n" + 
				"Description: " + getDescription() + "\n" + 
				"Price: " + getPrice();
	}
	
}
