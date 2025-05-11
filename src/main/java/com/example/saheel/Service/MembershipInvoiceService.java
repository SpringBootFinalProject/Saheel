package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.MembershipInvoice;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.MembershipInvoiceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class MembershipInvoiceService {

    private final MembershipInvoiceRepository membershipInvoiceRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final PaymentService paymentService;


    public byte[] getMembershipInvoicePdf(Integer ownerId, Integer invoiceId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) throw new ApiException("HorseOwner not found");

        MembershipInvoice invoice = membershipInvoiceRepository.findInvoiceByIdAndHorseOwnerId(invoiceId, ownerId);
        if (invoice == null) throw new ApiException("Invoice not found");

        if (invoice.getPaymentId().equalsIgnoreCase("No Payment From The Owner")) {
            return createPdf(invoice);
        } else try {
            String paymentJson = paymentService.getPaymentStatus(invoice.getPaymentId());
            JsonNode jsonNode = new ObjectMapper().readTree(paymentJson);
            String status = jsonNode.get("status").asText();
            invoice.setStatus(status);
            return createPdf(invoice);
        } catch (Exception e) {
            throw new ApiException("Failed to generate invoice: " + e.getMessage());
        }
    }

    private byte[] createPdf(MembershipInvoice invoice) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Paragraph title = new Paragraph("MEMBERSHIP INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Invoice Info
            document.add(new Paragraph("Invoice ID:      #" + invoice.getId()));
            document.add(new Paragraph("Date:            " + invoice.getDateTime().toLocalDate()));
            document.add(new Paragraph("Time:            " + invoice.getDateTime().toLocalTime()));
//            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("----------------------------------------------"));

            // Horse Owner Info
            document.add(new Paragraph("Horse Owner:     " + invoice.getHorseOwner().getUser().getFullName()));
            document.add(new Paragraph("Stable:          " + invoice.getMembership().getStable().getName()));
            document.add(new Paragraph("Membership Type: " + invoice.getMembership().getMembershipType()));
            document.add(new Paragraph("Period:          " +
                    invoice.getMembership().getStartDate() + " to " + invoice.getMembership().getEndDate()));
            document.add(Chunk.NEWLINE);

            // Line separator
            document.add(new Paragraph("----------------------------------------------"));

            // Membership Info
            document.add(new Paragraph("Description:     " + invoice.getMembership().getMembershipType() + " Membership"));
            document.add(new Paragraph("Quantity:        1"));
            document.add(new Paragraph("Price:           " + String.format("%.2f SAR", invoice.getTotalPrice())));

            document.add(new Paragraph("----------------------------------------------"));


            // Footer
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("Thank you for being part of Saheel ",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate simplified PDF", e);
        }
    }

}
