package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.Invoice;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Repository.CustomerRepository;
import com.example.saheel.Repository.InvoiceRepository;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
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
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
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


    private byte[] createPlainTextPdf(Invoice invoice) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Title (Plain, Centered)
            Paragraph title = new Paragraph("Invoice");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE); // Blank line
            // Format the renters
            StringBuilder renters = new StringBuilder("");

            // Format the pdf
            document.add(new Paragraph("---------------------------------------------------------------------"));
            document.add(new Paragraph("Invoice ID: " + invoice.getId()));
            document.add(new Paragraph("payment status: " + invoice.getStatus()));
            document.add(new Paragraph("Customer Name: " + invoice.getCustomer().getUser().getFullName()));
//             document.add(new Paragraph("Renters:\n" + renters));
            document.add(new Paragraph("---------------------------------------------------------------------"));


            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public List<Invoice> getPendingEnrollmentInvoices(Integer stableOwnerId, Integer stableId) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwnerId);
        if (stableOwner == null) throw new ApiException("Stable owner not found.");

        // Get the stable and check if it's in the system.
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) throw new ApiException("Stable not found.");

        // Check if the stable belongs to the owner.
        if (!stableOwner.getStables().contains(stable))
            throw new ApiException("The stable does not belongs to the owner.");

        List<Invoice> pendingInvoices = invoiceRepository.findInvoicesByStatus("pending");
        List<Invoice> stablePendingInvoices = new ArrayList<>();
        for (Invoice invoice : pendingInvoices)
            if (invoice.getCourseEnrollment().getCourse().getStable().equals(stable))
                stablePendingInvoices.add(invoice);

        return stablePendingInvoices;
    }
}
