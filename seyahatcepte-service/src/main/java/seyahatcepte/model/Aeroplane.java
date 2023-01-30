package seyahatcepte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aeroplane")
public class Aeroplane {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "beginning")
	private String beginning;
	@Column(name = "destination")
	private String destination;
	@Column(name = "month")
	private String month;
	@Column(name = "day")
	private String day;
	@Column(name = "hour")
	private String hour;
	@Column(name = "capacity")
	private final Integer capacity = 189;
	@Column(name = "price")
	private Integer price;
	
	
	public Aeroplane() {
		super();
	}


	public Aeroplane(String beginning, String destination, String month, String day, String hour, Integer price) {
		super();
		this.beginning = beginning;
		this.destination = destination;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.price = price;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getBeginning() {
		return beginning;
	}


	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public String getHour() {
		return hour;
	}


	public void setHour(String hour) {
		this.hour = hour;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public Integer getCapacity() {
		return capacity;
	}
	
	

}
