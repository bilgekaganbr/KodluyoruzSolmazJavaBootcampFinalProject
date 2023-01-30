package seyahatcepte.client;

public class EftPayment {

	private String userName;
	private String senderIban;
	private String receiverIban;
	private String customerGenders;
	
	
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
