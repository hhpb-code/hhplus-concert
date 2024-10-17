package com.example.hhplus.concert.infra.db.payment;

import com.example.hhplus.concert.domain.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

}
