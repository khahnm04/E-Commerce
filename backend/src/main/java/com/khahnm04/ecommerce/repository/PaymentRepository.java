package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionCode(String transactionCode);

}
