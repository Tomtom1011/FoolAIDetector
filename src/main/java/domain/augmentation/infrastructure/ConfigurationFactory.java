package domain.augmentation.infrastructure;

import domain.augmentation.types.image.noice.NoiseConfiguration;

public class ConfigurationFactory {

    public static NoiseConfiguration createRandomNoiceConfig() {
        return new NoiseConfiguration().createRandomConfiguration();
    }


}
