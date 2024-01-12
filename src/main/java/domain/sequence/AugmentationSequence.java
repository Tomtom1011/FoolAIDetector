package domain.sequence;

import domain.analyser.Analyser;
import domain.analyser.ExternalAnalyserService;
import domain.analyser.model.AnalyserResult;
import domain.augmentation.infrastructure.AbstractAugmentation;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.persistence.SequenceConfigurationFilePersistence;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class AugmentationSequence<T extends AbstractAugmentation> {

    private final Queue<T> augmentations = new LinkedList<>();
    private double bestResult;

    public AugmentationSequence() {
//        readBestResult();
    }

    public void addAugmentation(T augment) {
        augmentations.add(augment);
    }

    public boolean removeAugmentation(T augment) {
        return augmentations.remove(augment);
    }

    public double run(AugmentationData data) {
        augmentations.forEach(a -> a.transform(data));
        return 0;
//        int tryCount = 0;
//        do {
//            try {
//                return checkForResult(data);
//            } catch (IOException e) {
//                if (tryCount > 2) {
//                    System.out.println("Could not check for result");
//                    e.printStackTrace();
//                    return 100;
//                }
//                tryCount++;
//            }
//        } while (true);
    }

    private double checkForResult(AugmentationData freshData) throws IOException {
        double resultPercentage = checkAIPercentageForData(freshData);

        System.out.println(gatherConfigurationData(resultPercentage));

        if (hasBetterResult(resultPercentage)) {
            persistSequenceConfiguration(resultPercentage);
        }
        return resultPercentage;
    }

    private double checkAIPercentageForData(AugmentationData data) throws IOException {
        // TODO check ai percentage for data and return percentage as double
        Analyser analyser = new Analyser(new ExternalAnalyserService());
        String base64Image = encodeToBase64(data.getImage());
        String bodyData = "{ \"data\": [\"data:image/jpeg;base64," + base64Image + "\"] }";

        Optional<AnalyserResult> result = analyser.analyseWithAiOrNot(bodyData);
//        return result.map(AnalyserResult::getAiPercent).orElse(100.0);

        return TemporaryResultChecker.checkResult(data.getImage());
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

    private void persistSequenceConfiguration(double result) {
        String data = gatherConfigurationData(result);
        SequenceConfigurationFilePersistence.persistDataToFile(data);
    }

    private String gatherConfigurationData(double result) {
        String dataToPersist = "Result:" + result + "\n";
        dataToPersist += augmentations.stream()
                .map(a -> a.getClass().getSimpleName() + ": \n" + a.getConfiguration().getConfigurationToPersist())
                .collect(Collectors.joining("\n"));
        return dataToPersist;
    }

    private boolean hasBetterResult(double percentage) {
        if (percentage < bestResult) {
            bestResult = percentage;
            System.out.println("New best percentage " + percentage + " is better than old percentage" + bestResult);
            return true;
        } else {
            System.out.println("New percentage " + percentage + " is not better than best percentage " + bestResult);
            return false;
        }
    }

    private void readBestResult() {
        this.bestResult = SequenceConfigurationFilePersistence.readBestResult();
    }

}