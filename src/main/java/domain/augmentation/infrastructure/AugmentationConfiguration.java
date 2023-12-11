package domain.augmentation.infrastructure;

import java.util.Random;

public abstract class AugmentationConfiguration<T> implements Configurable<T> {

    public abstract String getConfigurationToPersist();

    protected int createRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(min, max);
    }
}
