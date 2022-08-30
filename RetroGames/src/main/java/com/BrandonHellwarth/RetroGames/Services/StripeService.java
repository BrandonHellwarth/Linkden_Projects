package com.BrandonHellwarth.RetroGames.Services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import com.BrandonHellwarth.RetroGames.Models.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

@Service
public class StripeService {

    @Value("sk_test_51Lbve0BCPnUk3hBus8XwxIXgkwqNoAI6KHcxHoJjQjEOQInLpRqeJNYSa4H5qrlx1zOUNHrF3gbXRBPxOjcvj7o7004UtfzNZG")
    private String secretKey;
    
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    public Charge charge(ChargeRequest chargeRequest) 
      throws AuthenticationException, InvalidRequestException,
        APIConnectionException, CardException, APIException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
}