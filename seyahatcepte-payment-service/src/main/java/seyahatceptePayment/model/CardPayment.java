package seyahatceptePayment.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "card_payment")
public class CardPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "card_no")
	private String cardNo;
	@Column(name = "customer_genders")
	private String customerGenders;
	
	public CardPayment() {
		super();
	}


	public CardPayment(String userName, String cardNo, String customerGenders) {
		super();
		this.userName = userName;
		this.cardNo = cardNo;
		this.customerGenders = customerGenders;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getCustomerGenders() {
		return customerGenders;
	}


	public void setCustomerGenders(String customerGenders) {
		this.customerGenders = customerGenders;
	}

	
	
}
