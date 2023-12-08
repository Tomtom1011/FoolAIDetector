package domain.sequence;

import domain.augmentation.infrastructure.AbstractAugmentation;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.persistence.SequenceConfigurationFilePersistence;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class AugmentationSequence<T extends AbstractAugmentation> {

    private final Queue<T> augmentations = new LinkedList<>();
    private double bestResult;

    public AugmentationSequence() {
        readBestResult();
    }

    public void addAugmentation(T augment) {
        augmentations.add(augment);
    }

    public boolean removeAugmentation(T augment) {
        return augmentations.remove(augment);
    }

    public void run(AugmentationData data) {

//        int iterationNumber = 100;

//        for (int i = 0; i < iterationNumber; i++) {

            AugmentationData freshData = data.copy();

            augmentations.forEach(a -> a.transform(freshData));

            try {
                double resultPercentage = checkAIPercentageForData(freshData);

                System.out.println(gatherConfigurationData(resultPercentage));

                if (hasBetterResult(resultPercentage)) {
                    persistSequenceConfiguration(resultPercentage);
                }

//                augmentations.stream()
//                        .map(AbstractAugmentation::getConfiguration)
//                        .forEach(c -> mutate(resultPercentage));

            } catch (IOException ioe) {
                System.out.println("Could not get result for image");
                ioe.printStackTrace();
            }
//        }
    }

    private double checkAIPercentageForData(AugmentationData data) throws IOException {
        // TODO check ai percentage for data and return percentage as int
        return TemporaryResultChecker.checkResult(data.getImage());
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

    public Queue<T> getAugmentations() {
        return augmentations;
    }

    public void mutate() {
        augmentations.forEach(AbstractAugmentation::mutate);
    }
}