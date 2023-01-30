package seyahatceptePayment.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import seyahatceptePayment.model.CardPayment;

public interface CardPaymentRepository extends JpaRepository<CardPayment, Integer> {

}
