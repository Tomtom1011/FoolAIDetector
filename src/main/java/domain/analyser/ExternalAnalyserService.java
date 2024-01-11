package domain.analyser;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import java.io.File;
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

    public String analyzeImageWithIlluminarty(String imagePath) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("ILLUMINARTY_API_KEY");

        // Create multipart request body with the image file
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imagePath,
                        RequestBody.create(new File(imagePath), MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url("https://api.illuminarty.ai/v1/image/classify")
                .post(formBody)
                .addHeader("X-API-Key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            if (response.body() == null) {
                throw new IOException("No response body");
            }
            return response.body().string();
        }
    }

}

