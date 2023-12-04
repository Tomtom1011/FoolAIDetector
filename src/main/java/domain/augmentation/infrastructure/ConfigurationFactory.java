package domain.augmentation.infrastructure;

import domain.augmentation.types.image.noice.ExampleNoiceConfiguration;

public class ConfigurationFactory {

    public static ExampleNoiceConfiguration createRandomNoiceConfig() {
        return new ExampleNoiceConfiguration().createRandomConfiguration();
    }


}
