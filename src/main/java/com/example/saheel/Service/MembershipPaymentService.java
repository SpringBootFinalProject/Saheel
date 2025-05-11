package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.*;
import com.example.saheel.Model.PaymentRequest;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.InvoiceRepository;
import com.example.saheel.Repository.MembershipInvoiceRepository;
import com.example.saheel.Repository.MembershipRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
        import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MembershipPaymentService {

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments";
    private final MembershipRepository membershipRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final MembershipInvoiceRepository invoiceRepository;

    public ResponseEntity<ApiResponse> processMembershipPayment(PaymentRequest paymentRequest, Integer ownerId, Integer membershipId) throws Exception {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) throw new ApiException("HorseOwner not found.");

        Membership membership = membershipRepository.findMembershipById(membershipId);
        if (membership == null) throw new ApiException("Membership not found.");

        if (!membership.getHorseOwner().getId().equals(ownerId))
            throw new ApiException("Membership does not belong to this owner.");

        MembershipInvoice  invoice = membership.getInvoice();
        if (invoice == null) throw new ApiException("Invoice not found.");

        String url = "https://api.moyasar.com/v1/payments";
        String requestBody = String.format(
                "source[type]=creditcard&" +
                        "source[name]=%s&" +
                        "source[number]=%s&" +
                        "source[month]=%d&" +
                        "source[year]=%d&" +
                        "source[cvc]=%d&" +
                        "amount=%d&" +
                        "currency=%s&" +
                        "description=%s&" +
                        "callback_url=%s",
                paymentRequest.getName(),
                paymentRequest.getNumber(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                paymentRequest.getCvc(),
                (int) (invoice.getTotalPrice() * 100),
                paymentRequest.getCurrency(),
                paymentRequest.getDescription(),
                paymentRequest.getCallbackUrl()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL , HttpMethod.POST, entity, String.class);

        String body = response.getBody();
        JsonNode json = new ObjectMapper().readTree(body);
        String paymentId = json.get("id").asText();



        invoice.setPaymentId(paymentId);
        invoice.setStatus("initiated");
        invoiceRepository.save(invoice);


        return ResponseEntity.status(response.getStatusCode()).body(new ApiResponse(body));
    }
}
