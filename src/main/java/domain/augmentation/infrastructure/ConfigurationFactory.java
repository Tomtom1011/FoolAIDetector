package domain.augmentation.infrastructure;

import domain.augmentation.types.noice.NoiceConfiguration;

public class ConfigurationFactory {

    public static NoiceConfiguration createRandomNoiceConfig() {
        return new NoiceConfiguration().createRandomConfiguration();
    }


}
