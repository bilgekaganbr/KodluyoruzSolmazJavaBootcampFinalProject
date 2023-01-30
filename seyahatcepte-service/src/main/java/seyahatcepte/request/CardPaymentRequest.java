package seyahatcepte.request;


public class CardPaymentRequest {

	private String userName;
	private String cardNo;
	private String customerGenders;
	
	public CardPaymentRequest(String userName, String cardNo, String customerGenders) {
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
