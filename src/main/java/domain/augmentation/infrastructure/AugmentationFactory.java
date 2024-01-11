package domain.augmentation.infrastructure;

import domain.augmentation.types.image.noise.NoiseAugmentation;
import domain.augmentation.types.image.noise.NoiseConfiguration;

public class AugmentationFactory {

    public static NoiseAugmentation createRandomNoiceAugmentation() {
        NoiseConfiguration config = ConfigurationFactory.createRandomNoiceConfig();
        return new NoiseAugmentation(config);
    }

    public static NoiseAugmentation createNoiceAugmentation(NoiseConfiguration config) {
        return new NoiseAugmentation(config);

    }
}