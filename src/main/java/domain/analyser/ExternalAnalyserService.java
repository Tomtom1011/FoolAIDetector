package domain.analyser;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;

public class ExternalAnalyserService {

    private final OkHttpClient client = new OkHttpClient();

    public String analyzeImageWithAiOrNot(String imageUrl) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("AI_OR_NOT_API_KEY");
        String jsonPayload = "{\"object\": \"" + imageUrl + "\"}";

        Request request = new Request.Builder()
                .url("https://prod.ai-or-not.com/aion/ai-generated/reports")
                .post(RequestBody.create(jsonPayload, MediaType.get("application/json")))
                .addHeader("x-api-key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

//          check if respons is not null
            if (response.body() == null) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }


}

