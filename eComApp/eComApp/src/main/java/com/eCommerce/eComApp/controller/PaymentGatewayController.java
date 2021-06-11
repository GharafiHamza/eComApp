package com.eCommerce.eComApp.controller;
import com.eCommerce.eComApp.model.StripeClient;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin("*")
@RequestMapping("/api/payment")
public class PaymentGatewayController {

    private StripeClient stripeClient;
    @Autowired
    PaymentGatewayController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping("/charge/{amount}")
    public Charge chargeCard(@RequestHeader(value="token") String token, @PathVariable(value="amount") Double amount) throws Exception {
        System.out.println("ana hna");
        System.out.println(token);
        System.out.println(amount);
        return this.stripeClient.chargeNewCard(token, amount);
    }
}