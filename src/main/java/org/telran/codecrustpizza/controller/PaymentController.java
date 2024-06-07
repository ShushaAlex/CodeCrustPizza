package org.telran.codecrustpizza.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telran.codecrustpizza.dto.payment.PaymentCreateDto;
import org.telran.codecrustpizza.entity.enums.OrderStatus;
import org.telran.codecrustpizza.service.OrderService;
import org.telran.codecrustpizza.service.paypal.PaypalService;

@Slf4j
@RestController
@RequestMapping(value = "api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaypalService paypalService;
    private final OrderService orderService;

    @PostMapping("/pay")
    public String payment(@RequestParam Long orderId, HttpSession session) {

        PaymentCreateDto paymentCreateDto = orderService.getPaymentDtoByOrderId(orderId);

        try {
            Payment payment = paypalService.createPayment(paymentCreateDto.priceTotal(), paymentCreateDto.toString());
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    session.setAttribute("orderId", orderId);

                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {

            log.error("Error occurred: ", e);
        }
        return "redirect:/";
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam String paymentId,
                                 @RequestParam(value = "PayerID") String payerId,
                                 HttpSession session) {

        Long orderId = (Long) session.getAttribute("orderId");

        if (orderId == null) {
            log.error("Order ID not found in session.");
            return "paymentError";
        }

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                orderService.updateOrderStatus(orderId, OrderStatus.PAYED);
                return "Success!";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return "Payment is processed";
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/error")
    public String paymentError() {
        return "paymentError";
    }
}