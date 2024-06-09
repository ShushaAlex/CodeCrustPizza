package org.telran.codecrustpizza.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment Controller", description = "Operations related to payment processing")
public class PaymentController {

    private final PaypalService paypalService;
    private final OrderService orderService;

    @Operation(summary = "Create payment", description = "Initiate payment process for an order and redirect to PayPal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Received redirection link"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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


    @Operation(summary = "Handle payment success", description = "Handle successful payment and update order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment approved and order status updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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