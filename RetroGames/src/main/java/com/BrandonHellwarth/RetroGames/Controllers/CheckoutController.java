package com.BrandonHellwarth.RetroGames.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BrandonHellwarth.RetroGames.Models.ChargeRequest;
import com.BrandonHellwarth.RetroGames.Models.ChargeRequest.Currency;
import com.BrandonHellwarth.RetroGames.Services.StripeService;
import com.BrandonHellwarth.RetroGames.Services.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Controller
public class CheckoutController {
	@Autowired
    private StripeService paymentsService;
	@Autowired
	private UserService userServ;
    
    //ACTION ROUTES
    
    @PostMapping("/charge/{id}")
    public String charge(@PathVariable("id") Long id, ChargeRequest chargeRequest, Model model)
      throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.USD);
        userServ.clearitems(id);
        Charge charge = paymentsService.charge(chargeRequest);//unsure why stops running here
        System.out.println("working");
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        model.addAttribute("user", userServ.findById(id));
        return "result.jsp";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result.jsp";
    }
}
