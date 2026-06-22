package com.torloksz.arethium.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AssessmentGenerator {
    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    public String generateAssessment(String description,String title) throws HttpException, IOException {
        Client client = new Client.Builder().apiKey(apiKey).build();

        String prompt = "Create a 10-question multiple-choice assessment about " + title +
        ". The module description is: " + description + ". " +
                "Return the output strictly in this JSON format: " +
                "{\"questions\": [{\"question\": \"...\", \"optionA\": \"...\", \"optionB\": \"...\", " +
                "\"optionC\": \"...\", \"optionD\": \"...\", \"correctAnswer\": \"...\"}]}"+
                "Do not include any introductory or concluding text. Return only the JSON.";

        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash",prompt,null);
        return response.text();
    }
}
