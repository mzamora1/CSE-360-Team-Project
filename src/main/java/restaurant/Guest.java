package restaurant;

public class Guest {

	private String phoneNum;
	private String email;
	private String cardNum;
	private String cardExp;
	private String cardCCV;

	// setters

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setCardExp(String cardExp) {
		this.cardExp = cardExp;
	}

	public void setCardCCV(String cardCCV) {
		this.cardCCV = cardCCV;
	}

	// getters

	public String getPhoneNum() {
		return phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public String getCardNum() {
		return cardNum;
	}

	public String getCardExp() {
		return cardExp;
	}

	public String getCardCCV() {
		return cardCCV;
	}

}
