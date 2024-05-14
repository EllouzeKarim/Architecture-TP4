package com.example.eligibilityscore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class EligibilityScoreService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Random random = new Random();

    @Autowired
    public EligibilityScoreService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processLoanApplication(String applicationInfo) {
        // Simulate database query and eligibility score calculation (replace with actual logic)
        int eligibilityScore = generateScore();

        // Publish eligibility response to Kafka
        publishEligibilityResponse(eligibilityScore);
    }

    private int generateScore() {
        // TODO: Replace with actual calculation logic
        return random.nextInt(101); // Generates a random score between 0 and 100
    }

    private void publishEligibilityResponse(int eligibilityScore) {
        String responseMessage = (eligibilityScore >= 50) ? "Eligible" : "Not Eligible";
        kafkaTemplate.send("eligibility-response", responseMessage);
        System.out.println("Eligibility response published: " + responseMessage);
    }
}
