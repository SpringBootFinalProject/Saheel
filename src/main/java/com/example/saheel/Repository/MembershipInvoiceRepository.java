package com.example.saheel.Repository;

import com.example.saheel.Model.MembershipInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipInvoiceRepository extends JpaRepository<MembershipInvoice,Integer> {
    MembershipInvoice findInvoiceByIdAndHorseOwnerId(Integer invoiceId, Integer horseOwnerId);

}
