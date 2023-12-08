package domain.sequence;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class TemporaryResultChecker {

    public static double checkResult(BufferedImage image) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String base64Image = encodeToBase64(image);
        String bodyData = "{ \"data\": [\"data:image/jpeg;base64," + base64Image + "\"] }";

        Request request = new Request.Builder()
                .url("https://sooks-img.hf.space/run/predict")
                .post(RequestBody.create(bodyData, MediaType.get("application/json")))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Response not successful: " + response);
        }

        if (response.body() == null) {
            throw new IOException("Response body empty: " + response);
        }
        return parseResult(response.body().string());
    }

    private static double parseResult(String result) {

        JSONObject jsonObject = new JSONObject(result);

        JSONArray dataArray = jsonObject.getJSONArray("data");
        JSONObject firstDataObject = dataArray.getJSONObject(0);
        JSONArray confidencesArray = firstDataObject.getJSONArray("confidences");

        for (int i = 0; i < confidencesArray.length(); i++) {
            JSONObject confidenceObject = confidencesArray.getJSONObject(i);
            String label = confidenceObject.getString("label");
            double confidenceValue = confidenceObject.getDouble("confidence");
            if (label.equals("ai")) {
                return confidenceValue;
            }

        }
        System.err.println("Could not find ai confidence in result");
        return 99;
    }

    private static String encodeToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
