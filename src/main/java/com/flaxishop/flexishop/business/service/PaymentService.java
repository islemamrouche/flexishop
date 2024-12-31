package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.entity.Payment;
import com.flaxishop.flexishop.business.repository.PaymentRepository;
import com.flaxishop.flexishop.presentation.dto.PaymentDTO;
import com.flaxishop.flexishop.presentation.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create a new payment
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = PaymentMapper.toEntity(paymentDTO);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.toDTO(savedPayment);
    }

    // Get all payments
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(PaymentMapper::toDTO)
                .toList();
    }

    // Get a specific payment by ID
    public PaymentDTO getPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        return paymentOptional.map(PaymentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }

    // Update a payment
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));

        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setStatus(paymentDTO.getStatus());

        // If you need to update the related order, implement that logic here.
        if (paymentDTO.getOrderId() != null) {
            Order order = new Order();
            order.setId(paymentDTO.getOrderId());
            payment.setOrder(order);
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return PaymentMapper.toDTO(updatedPayment);
    }

    // Delete a payment
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
