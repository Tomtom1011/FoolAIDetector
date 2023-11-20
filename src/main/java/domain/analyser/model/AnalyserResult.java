package domain.analyser.model;

import domain.analyser.dto.AIorNotDTO;

public class AnalyserResult {
    private double aiPercent;
    private double humanPercent;

    public AnalyserResult(double aiPercent, double humanPercent) {
        this.aiPercent = aiPercent;
        this.humanPercent = humanPercent;
    }

    public double getAiPercent() {
        return aiPercent;
    }

    public double getHumanPercent() {
        return humanPercent;
    }

    // Factory method to create AnalysisResult from DTO
    public static AnalyserResult fromDTO(AIorNotDTO dto) {
        double aiConfidenceAsPercent = dto.getReport().getAi().getConfidence() * 100;
        double humanConfidenceAsPercent = dto.getReport().getHuman().getConfidence() * 100;
        return new AnalyserResult(aiConfidenceAsPercent, humanConfidenceAsPercent);
    }

}
