package seyahatcepteNotification.response;

import seyahatcepteNotification.response.enums.VehicleType;

public class TicketResponse {

	private Integer id;
	private String beginning;
	private String destination;
	private String month;
	private String day;
	private String hour;
	private VehicleType type;
	private Integer serviceId;

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

	@Override
	public String toString() {
		return "TicketResponse [id=" + id + ", beginning=" + beginning + ", destination=" + destination + ", month="
				+ month + ", day=" + day + ", hour=" + hour + ", type=" + type + ", serviceId=" + serviceId + "]";
	}

	

	
	

}
