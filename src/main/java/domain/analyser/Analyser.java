package domain.analyser;

import domain.analyser.dto.AIorNotDTO;
import domain.analyser.model.AnalyserResult;

import java.util.Optional;

public class Analyser {
    ExternalAnalyserService externalAnalyserService;

    public Analyser(ExternalAnalyserService externalAnalyserService) {
        this.externalAnalyserService = externalAnalyserService;
    }

    public Optional<AnalyserResult> analyse(String imageUrl) {
        try {
            String result = externalAnalyserService.analyzeImageWithAiOrNot(imageUrl);
            AIorNotDTO dto = AIorNotDTO.fromJson(result);
            return Optional.of(AnalyserResult.fromDTO(dto));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
