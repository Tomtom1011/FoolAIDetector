package domain.augmentation.infrastructure;

import domain.augmentation.types.image.noice.ExampleNoiceAugmentation;
import domain.augmentation.types.image.noice.ExampleNoiceConfiguration;

public class AugmentationFactory {

    public static ExampleNoiceAugmentation createRandomNoiceAugmentation() {
        ExampleNoiceConfiguration config = ConfigurationFactory.createRandomNoiceConfig();
        return new ExampleNoiceAugmentation(config);
    }

    public static ExampleNoiceAugmentation createNoiceAugmentation(ExampleNoiceConfiguration config) {
        return new ExampleNoiceAugmentation(config);
    }
}