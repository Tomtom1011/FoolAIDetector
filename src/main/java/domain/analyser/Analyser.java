package domain.analyser;

import domain.analyser.dto.AIorNotDTO;
import domain.analyser.dto.IlluminartyDTO;
import domain.analyser.model.AnalyserResult;

import java.util.Optional;

public class Analyser {
    ExternalAnalyserService externalAnalyserService;

    public Analyser(ExternalAnalyserService externalAnalyserService) {
        this.externalAnalyserService = externalAnalyserService;
    }

    public Optional<AnalyserResult> analyse(String imageUrl) {
        this.externalAnalyserService = new ExternalAnalyserService();
        Optional<AnalyserResult> result = analyseWithIlluminarty(imageUrl);
        System.out.println("Making request to external analyser");
        String resultLog = result.map(AnalyserResult::toString).orElse("No result");
        System.out.println(resultLog);
        return result;
    }

    public Optional<AnalyserResult> analyseWithAiOrNot(String imageUrl) {
        try {
            String result = externalAnalyserService.analyzeImageWithAiOrNot(imageUrl);
            AIorNotDTO dto = AIorNotDTO.fromJson(result);
            return Optional.of(AnalyserResult.fromAIorNotDTO(dto));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public  Optional<AnalyserResult> analyseWithIlluminarty(String imagePath) {
        try {
            String result = externalAnalyserService.analyzeImageWithIlluminarty(imagePath);
            IlluminartyDTO dto = IlluminartyDTO.fromJson(result);
            return Optional.of(AnalyserResult.fromIlluminartyDTO(dto));
        } catch (Exception e) {
            return Optional.empty();
        }
    }



}
