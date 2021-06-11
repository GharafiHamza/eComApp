package com.eCommerce.eComApp.controller;

import com.eCommerce.eComApp.model.PaypalOrder;
import com.eCommerce.eComApp.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {

    @Autowired
    PaypalService paypalService;
    public static final String successUrl = "";
    public static final String cancelUrl = "";

    @PostMapping("/pay")
    public String payment(@RequestBody PaypalOrder order) {
        try {
            Payment payment = paypalService.createPayment(order.getTotal(), order.getCurrency(), order.getMethod(),order.getIntent(),order.getDescription(),successUrl,cancelUrl);
            for(Links link:payment.getLinks()){
                if(link.getRel().equals("approval_url")){
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
//    @GetMapping(value = cancelUrl)
//    public String cancelPay() {
//        return "cancel";
//    }

    @GetMapping(value = successUrl)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }


}
