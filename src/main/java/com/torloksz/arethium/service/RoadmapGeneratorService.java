package com.torloksz.arethium.service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RoadmapGeneratorService {

    private final String apiKey = System.getenv("GEMINI_API_KEY") != null
            ? System.getenv("GEMINI_API_KEY")
            : System.getProperty("gemini.api.key");

    public String generateRoadmap(String targetRole , String targetCompany) throws HttpException, IOException {
        Client client = new Client.Builder().apiKey(apiKey).build();

        String prompt = String.format(
                "You are an expert career mentor. Generate a detailed learning roadmap for a %s role at %s. " +
                        "Requirements: " +
                        "1. Create exactly 10 high-quality modules. " +
                        "2. For each module, provide: order (int), title (string), description (string), and estimatedWeeks (int). " +
                        "3. Return ONLY valid JSON that maps to this structure: " +
                        "{\"modules\": [{\"order\": 1, \"title\": \"...\", \"description\": \"...\", \"estimatedWeeks\": 0}]}. " +
                        "Do not include any introductory text, explanations, or markdown code blocks."+
                        "Include some youtube vido links which are the best ones in title and provide full link or name of the youtube"+
                        "Channel which teaches in best way in english...",
                targetRole, targetCompany
        );

        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash",prompt,null);
        return response.text();
    }
}
