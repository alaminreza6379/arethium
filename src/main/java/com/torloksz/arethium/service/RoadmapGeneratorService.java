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
                "You are an expert career mentor. Generate a roadmap for a %s role at %s. and listen i want at least 12 modules to be built and of quality what skills is needed" +
                        "Return ONLY valid JSON that maps to this structure: " +
                        "{\"modules\": [{\"order\": 1, \"title\": \"...\", \"description\": \"...\"}]}. " +
                        "Do not include any introductory text or markdown formatting.",
                targetRole,targetCompany
        );

        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash",prompt,null);
        return response.text();
    }
}
