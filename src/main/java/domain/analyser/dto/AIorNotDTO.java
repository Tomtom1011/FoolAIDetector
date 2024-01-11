package domain.analyser.dto;

import com.google.gson.Gson;

public class AIorNotDTO {
    private Report report;

    public Report getReport() {
        return report;
    }

    public static class Report {
        private AnalysisResult ai;
        private AnalysisResult human;

        public AnalysisResult getAi() {
            return ai;
        }

        public AnalysisResult getHuman() {
            return human;
        }
    }

    public static class AnalysisResult {
        private double confidence;
        private boolean isDetected;

        public double getConfidence() {
            return confidence;
        }

        public boolean isDetected() {
            return isDetected;
        }
    }

    public static AIorNotDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AIorNotDTO.class);
    }
}
