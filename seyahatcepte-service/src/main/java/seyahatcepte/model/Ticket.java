package seyahatcepte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import seyahatcepte.model.enums.VehicleType;

@Entity
@Table(name = "ticket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
public class Ticket {

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
	@Column(name= "hour")
	private String hour;
	@Column(name = "vehicle_type")
	@Enumerated(EnumType.STRING)
	private VehicleType type;
	@Column(name = "service_id")
	private Integer serviceId;
	/*@Column(name = "customer_name")
	private String userName;*/
	@ManyToOne
	@JoinColumn(name = "customer_name", referencedColumnName = "user_name", nullable = false)
	private User user;
	@Column(name = "cost")
	private Integer cost;
	
	public Ticket() {
		super();
	}


	public Ticket(String beginning, String destination, String month, String day, String hour, VehicleType type
			, Integer serviceId, User user,/*String userName*/ Integer cost) {
		super();
		this.beginning = beginning;
		this.destination = destination;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.type = type;
		this.serviceId = serviceId;
		//this.userName = userName;
		this.user = user;
		this.cost = cost;
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


	public VehicleType getType() {
		return type;
	}


	public void setType(VehicleType type) {
		this.type = type;
	}


	public Integer getServiceId() {
		return serviceId;
	}


	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}


	/*public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}*/


	public Integer getCost() {
		return cost;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setCost(Integer cost) {
		this.cost = cost;
	}

	
	
}
