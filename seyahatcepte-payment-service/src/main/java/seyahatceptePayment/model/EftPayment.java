package seyahatceptePayment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eft_payment")
public class EftPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "sender_iban")
	private String senderIban;
	@Column(name = "receiver_iban")
	private String receiverIban;
	@Column(name = "customer_genders")
	private String customerGenders;
	
	
	public EftPayment() {
		super();
	}


	public EftPayment(String userName, String senderIban, String receiverIban, String customerGenders) {
		super();
		this.userName = userName;
		this.senderIban = senderIban;
		this.receiverIban = receiverIban;
		this.customerGenders = customerGenders;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getSenderIban() {
		return senderIban;
	}


	public void setSenderIban(String senderIban) {
		this.senderIban = senderIban;
	}


	public String getReceiverIban() {
		return receiverIban;
	}


	public void setReceiverIban(String receiverIban) {
		this.receiverIban = receiverIban;
	}


	public String getCustomerGenders() {
		return customerGenders;
	}


	public void setCustomerGenders(String customerGenders) {
		this.customerGenders = customerGenders;
	}


	
	
	
}
