package com.torloksz.arethium.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.torloksz.arethium.dto.ScoreDTO;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class InterviewGeneratorService {
    @Value("${GEMINI_API_KEY}")
    private String apiKey;
    public String generateQuestions(String role) throws HttpException, IOException {
        Client client = new Client.Builder().apiKey(apiKey).build();

        String prompt =
                """
                Generate exactly 5 interview questions.

                Return ONLY JSON.

                {
                  "questions":[
                    "...",
                    "...",
                    "...",
                    "...",
                    "..."
                  ]
                }

                Target Role:
                """ + role;

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.5-flash", prompt, null);

        return response.text();
    }

    public Integer evaluateAnswers(List<String> answers
    ) {
        try {
            Client client = new Client.Builder().apiKey(apiKey).build();
            String prompt =
                    """
                    You are a senior Google interviewer.
    
                    Evaluate these answers.
    
                    Return ONLY JSON.
    
                    {
                      "score": 0
                    }
    
                    Answers:
                    """ + answers;

            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);

            String clean = Objects.requireNonNull(response.text()).replaceAll("```json|```", "").trim();

            ObjectMapper mapper = new ObjectMapper();

            ScoreDTO dto = mapper.readValue(clean, ScoreDTO.class);

            return dto.score();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
