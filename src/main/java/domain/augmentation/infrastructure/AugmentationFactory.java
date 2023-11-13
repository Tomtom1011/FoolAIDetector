package domain.augmentation.infrastructure;

import domain.augmentation.types.image.noice.NoiceAugmentation;
import domain.augmentation.types.image.noice.NoiceConfiguration;

public class AugmentationFactory {

    public static NoiceAugmentation createRandomNoiceAugmentation() {
        NoiceConfiguration config = ConfigurationFactory.createRandomNoiceConfig();
        return new NoiceAugmentation(config);
    }

    public static NoiceAugmentation createNoiceAugmentation(NoiceConfiguration config) {
        return new NoiceAugmentation(config);
    }
}