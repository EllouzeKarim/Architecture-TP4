package com.example.riskcalculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RiskCalculatorService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;

    @Autowired
    public RiskCalculatorService(KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "eligibility-response", groupId = "risk-calculator-group", containerFactory = "kafkaListenerContainerFactory")
    public void processEligibilityResponse(String eligibilityResponse) {
        // Check if eligibility response is "Yes" (eligible for risk assessment)
        if (eligibilityResponse.trim().equalsIgnoreCase("Yes")) {
            // Simulate risk assessment (replace with actual API call and risk calculation)
            double riskScore = callRiskApi();

            // Publish risk response to Kafka
            publishRiskResponse(riskScore);
        }
    }

    private double callRiskApi() {
        // TODO: Call the actual external risk assessment API and retrieve the risk score
        return Math.random() * 100;
    }

    private void publishRiskResponse(double riskScore) {
        // Publish risk response to Kafka broker (risk-response topic)
        String responseMessage = "Risk Score: " + riskScore;
        kafkaTemplate.send("risk-response", responseMessage);
    }
}
