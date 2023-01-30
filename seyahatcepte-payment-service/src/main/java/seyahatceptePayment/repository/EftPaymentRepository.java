package seyahatceptePayment.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import seyahatceptePayment.model.EftPayment;


public interface EftPaymentRepository extends JpaRepository<EftPayment, Integer>{

}
