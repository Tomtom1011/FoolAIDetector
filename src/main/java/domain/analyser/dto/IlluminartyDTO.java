package domain.analyser.dto;
import com.google.gson.Gson;


public class IlluminartyDTO {
    private Data data;

    // Getter for Data
    public Data getData() {
        return data;
    }

    public static class Data {
        private double probability;

        // Getter for Probability
        public double getProbability() {
            return probability;
        }
    }

    // Method to parse JSON string to IlluminartyDTO object
    public static IlluminartyDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, IlluminartyDTO.class);
    }
}

