package domain.augmentation.infrastructure;

import domain.augmentation.types.noice.NoiceAugmentation;
import domain.augmentation.types.noice.NoiceConfiguration;

public class AugmentationFactory {

    public static NoiceAugmentation createRandomNoiceAugmentation() {
        NoiceConfiguration config = ConfigurationFactory.createRandomNoiceConfig();
        return new NoiceAugmentation(config);
    }

    public static NoiceAugmentation createNoiceAugmentation(NoiceConfiguration config) {
        return new NoiceAugmentation(config);
    }
}