package org.telran.codecrustpizza.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.telran.codecrustpizza.CodeCrustPizzaApplication;
import org.telran.codecrustpizza.service.paypal.PaypalService;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ContextConfiguration(classes = {CodeCrustPizzaApplication.class})
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaypalService paypalService;

    @BeforeEach
    void setUp() throws Exception {

        Payment payment = new Payment();
        Links approvalLink = new Links();
        approvalLink.setRel("approval_url");
        approvalLink.setHref("http://test-approval-url");
        payment.setLinks(Collections.singletonList(approvalLink));
        when(paypalService.createPayment(any(BigDecimal.class), any(String.class))).thenReturn(payment);

        Payment executedPayment = new Payment();
        executedPayment.setState("approved");
        when(paypalService.executePayment(any(String.class), any(String.class))).thenReturn(executedPayment);
    }

    @Test
    void paymentTest() throws Exception {

        mockMvc.perform(post("http://localhost:8080/api/payment/pay").param("orderId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void paymentSuccessTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/payment/success")
                        .sessionAttr("orderId", 1L)
                        .param("paymentId", "test-payment-id")
                        .param("PayerID", "test-payer-id"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success!"));
    }


    @Test
    void paymentCancelTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/payment/cancel"))
                .andExpect(status().isOk())
                .andExpect(content().string("paymentCancel"));
    }

    @Test
    public void paymentErrorTest() throws Exception {

        mockMvc.perform(get("http://localhost:8080/api/payment/error"))
                .andExpect(status().isOk())
                .andExpect(content().string("paymentError"));
    }
}