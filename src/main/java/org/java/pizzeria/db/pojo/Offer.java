package org.java.pizzeria.db.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 40, nullable = false)
	@Length(min = 3, max = 40, message = "Offer title must have between 3 and 40 characters")
	@NotBlank(message = "Offer title cannot be empty")
	private String title;

	@Column(nullable = false)
	@NotNull
	@Positive(message = "Offer discount rate must be greater than zero")
	@Max(value = 80, message = "Discount rate cannot be greater than 80")
	private int discountRate;

	@Column(nullable = false)
	@NotNull(message = "Starting date must be selected")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(nullable = false)
	@NotNull(message = "Ending date must be selected")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@Valid
	@NotNull(message = "A pizza must be selected")
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pizza pizza;

	public Offer() {
	}

	public Offer(String title, int discountRate, LocalDate startDate, LocalDate endDate, Pizza pizza) {
		setTitle(title);
		setDiscountRate(discountRate);
		setStartDate(startDate);
		setEndDate(endDate);
		setPizza(pizza);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getHtmlStartDate() {

		final DateTimeFormatter FORMATTERS = DateTimeFormatter.ofPattern("d/MM/uuuu");

		return getStartDate() == null ? null : getStartDate().format(FORMATTERS);
	}

	public void setHtmlStartDate(String startDate) {

		setStartDate(LocalDate.parse(startDate));
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String getHtmlEndDate() {

		final DateTimeFormatter FORMATTERS = DateTimeFormatter.ofPattern("d/MM/uuuu");

		return getEndDate() == null ? null : getEndDate().format(FORMATTERS);
	}

	public void setHtmlEndDate(String endDate) {

		setEndDate(LocalDate.parse(endDate));
	}

	
	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	@Override
	public String toString() {
		return "[" + getId() + "] " + "Offer: " + getTitle() + "\n\t" + getPizza() + "\n\t" + "Discount:" + getDiscountRate() + "\n\t"
				+ "Available from: " + getStartDate() + "\n\t" + "to: " + getEndDate();
	}

}
