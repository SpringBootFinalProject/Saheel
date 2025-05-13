package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.EnrollmentInvoice;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Repository.CustomerRepository;
import com.example.saheel.Repository.InvoiceRepository;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;
    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;

    public byte[] getEnrollmentInvoiceAsPdf(Integer customerId, Integer invoiceId) {
        // Get the customer and check if it's in the database.
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found.");

        // Get the invoice and check if it's in the database.
        EnrollmentInvoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        if (invoice == null) throw new ApiException("Contract not found.");

        // Check if the invoice belongs to the customer.
        if (!invoice.getCustomer().equals(customer)) {
            throw new ApiException("The invoice does not belong to the customer.");
        }

        // Check if the payment initiated.
        if (invoice.getPaymentId().equalsIgnoreCase("No Payment From The Customer")) {
            return createPlainTextPdf(invoice);
        } else try {
            // Get the payment and change the invoice status.
            String payment = paymentService.getPaymentStatus(invoice.getPaymentId());
            JsonNode jsonNode = new ObjectMapper().readTree(payment);
            String paymentStatus = jsonNode.get("status").asText();
            invoice.setStatus(paymentStatus);

            // Add your PDF generation logic here
            return createPlainTextPdf(invoice);

        } catch (JsonProcessingException e) {
            throw new ApiException("Failed to parse payment response");
        } catch (Exception e) {
            throw new ApiException("Failed to generate PDF: " + e.getMessage());
        }
    }


    private byte[] createPlainTextPdf(EnrollmentInvoice invoice) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Title
            Paragraph title = new Paragraph("ENROLLMENT INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Invoice Info
            document.add(new Paragraph("Invoice ID:      #" + invoice.getId()));
            document.add(new Paragraph("Date:            " + invoice.getDateTime().toLocalDate()));
            document.add(new Paragraph("Time:            " + invoice.getDateTime().toLocalTime()));
            document.add(new Paragraph("Status:          " + invoice.getStatus()));
            document.add(new Paragraph("----------------------------------------------"));

            // Customer Info
            document.add(new Paragraph("Customer:        " + invoice.getCustomer().getUser().getFullName()));
            document.add(new Paragraph("Course:          " + invoice.getCourseEnrollment().getCourse().getName()));
            document.add(new Paragraph("Stable:          " + invoice.getCourseEnrollment().getCourse().getStable().getName()));
            document.add(new Paragraph("Coach:           " + invoice.getCourseEnrollment().getCourse().getTrainer().getFullName()));
            document.add(Chunk.NEWLINE);

            // Line separator
            document.add(new Paragraph("----------------------------------------------"));

            // Course Info
            document.add(new Paragraph("Description:     Course Enrollment"));
            document.add(new Paragraph("Quantity:        1"));
            document.add(new Paragraph("Price:           " + String.format("%.2f SAR", invoice.getTotalPrice())));
            document.add(new Paragraph("----------------------------------------------"));

            // Footer
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("Thank you for choosing Saheel Academy!",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate enhanced PDF", e);
        }
    }


    public List<EnrollmentInvoice> getPendingEnrollmentInvoices(Integer stableOwnerId, Integer stableId) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwnerId);
        if (stableOwner == null) throw new ApiException("Stable owner not found.");

        // Get the stable and check if it's in the system.
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) throw new ApiException("Stable not found.");

        // Check if the stable belongs to the owner.
        if (!stableOwner.getStables().contains(stable))
            throw new ApiException("The stable does not belongs to the owner.");

        List<EnrollmentInvoice> pendingInvoices = invoiceRepository.findInvoicesByStatus("pending");
        List<EnrollmentInvoice> stablePendingInvoices = new ArrayList<>();
        for (EnrollmentInvoice invoice : pendingInvoices)
            if (invoice.getCourseEnrollment().getCourse().getStable().equals(stable))
                stablePendingInvoices.add(invoice);

        return stablePendingInvoices;
    }
}