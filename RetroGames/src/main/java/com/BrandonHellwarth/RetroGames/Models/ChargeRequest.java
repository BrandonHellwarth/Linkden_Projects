package com.BrandonHellwarth.RetroGames.Models;

import lombok.Data;

@Data
public class ChargeRequest {

    public enum Currency {
        EUR, USD;
    }
    private String description;
    private String amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
    
    public ChargeRequest() {
    	
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getStripeEmail() {
		return stripeEmail;
	}

	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}
}