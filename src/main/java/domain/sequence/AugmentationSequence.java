package domain.sequence;

import domain.augmentation.infrastructure.AbstractAugmentation;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.persistence.SequenceConfigurationFilePersistence;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class AugmentationSequence<T extends AbstractAugmentation> {

    private final Queue<T> augmentations = new LinkedList<>();
    private int bestResult;

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
        augmentations.forEach(a -> a.transform(data));

        int resultPercentage = checkAIPercentageForData(data);

        if (hasBetterResult(resultPercentage)) {
            persistSequenceConfiguration(resultPercentage);
        }
    }

    private int checkAIPercentageForData(AugmentationData data) {
        // TODO check ai percentage for data and return percentage as int
        return 50;
    }

    private void persistSequenceConfiguration(int result) {
        String dataToPersist = "Result:" + result + "\n";
        dataToPersist += augmentations.stream()
                .map(a -> a.getClass().getSimpleName() + ": \n" + a.getConfiguration().getConfigurationToPersist())
                .collect(Collectors.joining("\n"));

        SequenceConfigurationFilePersistence.persistDataToFile(dataToPersist);
    }

    private boolean hasBetterResult(int percentage) {
        if (percentage < bestResult) {
            bestResult = percentage;
            System.out.println("Has better result = " + percentage);
            return true;
        } else {
            System.out.println("Has no better result = " + percentage);
            return false;
        }
    }

    private void readBestResult() {
        this.bestResult = SequenceConfigurationFilePersistence.readBestResult();
    }
}