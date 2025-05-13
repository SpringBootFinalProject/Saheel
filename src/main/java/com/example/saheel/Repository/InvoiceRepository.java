package com.example.saheel.Repository;

import com.example.saheel.Model.EnrollmentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<EnrollmentInvoice, Integer> {
    EnrollmentInvoice findInvoiceById(Integer invoiceId);
    List<EnrollmentInvoice> findInvoicesByStatus(String status);
}
