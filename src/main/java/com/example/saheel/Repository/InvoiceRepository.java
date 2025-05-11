package com.example.saheel.Repository;

import com.example.saheel.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findInvoiceById(Integer invoiceId);
    List<Invoice> findInvoicesByStatus(String status);
}
