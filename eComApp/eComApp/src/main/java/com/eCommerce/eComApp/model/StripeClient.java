package com.eCommerce.eComApp.model;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.InvoiceItemCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import com.stripe.net.ApiRequestParams;
@Component  
public class StripeClient {
    @Autowired
    StripeClient() {
        Stripe.apiKey = "sk_test_51IzNiTHatCln2rQspqxXHv0bgGkXKH9ht58bEOA2Hr8LBdQmVkxCoxoKQrdI2qbtLtsduh7tOgXU99goqY58uUjF00PlX4SgtM";
    }
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }
    public Charge chargeNewCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        createInvoice();

        return charge;
    }
    public void createInvoice() throws StripeException {
        Stripe.apiKey = "sk_test_51IzNiTHatCln2rQspqxXHv0bgGkXKH9ht58bEOA2Hr8LBdQmVkxCoxoKQrdI2qbtLtsduh7tOgXU99goqY58uUjF00PlX4SgtM";
        InvoiceItemCreateParams params =
                InvoiceItemCreateParams.builder()
                        .setPrice("price_CBb6IXqvTLXp3f")
                        .setCustomer("cus_4fdAW5ftNQow1a")
                        .build();

        InvoiceItem invoiceItem = InvoiceItem.create(params);
        InvoiceCreateParams paramss =
                InvoiceCreateParams.builder()
                        .setCustomer("cus_4fdAW5ftNQow1a")
                        .setCollectionMethod(InvoiceCreateParams.CollectionMethod.SEND_INVOICE)
                        .setDaysUntilDue(30L)
                        .build();

        Invoice invoice = Invoice.create(paramss);

        invoice.sendInvoice();

    }
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}