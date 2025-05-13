package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.CourseEnrollmentRepository;
import com.example.saheel.Repository.CustomerRepository;
import com.example.saheel.Repository.InvoiceRepository;
import com.example.saheel.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${moyasar.api.key}")
    private String apiKey;
    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";
    private final InvoiceRepository invoiceRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CustomerRepository customerRepository;


    public ResponseEntity<ApiResponse> processPayment(PaymentRequest paymentRequest, Integer customerId, Integer courseEnrollmentId) throws JsonProcessingException {
        // Get the customer and check if it's in the database.
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found.");

        // Get the Course Enrollment and check if it's in the database.
        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findCourseEnrollmentById(courseEnrollmentId);
        if (courseEnrollment == null) throw new ApiException("Enrollment not found.");

        // Check if the enrollment belongs to the customer.
        if (!customer.getCourseEnrollments().contains(courseEnrollment))
            throw new ApiException("The enrollment does not belongs to the customer.");

        if (courseEnrollment.getEnrollmentCanceled())
            throw new ApiException("This enrollments have been canceled.");

        String url = "https://api.moyasar.com/v1/payments";
        String callbackUrl = "https://your-server.com/api/payments/callback";
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
                (int) (courseEnrollment.getCourse().getPrice() * 100),
                paymentRequest.getCurrency(),
                paymentRequest.getDescription(),
                paymentRequest.getCallbackUrl()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Get the response.
        String responseBody = response.getBody();
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        String paymentId = jsonNode.get("id").asText();

        // Get the invoice and set the paymentId and change the status of the invoice.
        EnrollmentInvoice invoice = courseEnrollment.getInvoice();
        if (invoice == null) throw new ApiException("Invoice not found.");
        invoice.setPaymentId(paymentId);

        // Save
        invoiceRepository.save(invoice);

        return ResponseEntity.status(response.getStatusCode()).body(new ApiResponse(response.getBody()));
    }


    public String getPaymentStatus(String paymentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, String.class);

        return response.getBody();


    }
}
