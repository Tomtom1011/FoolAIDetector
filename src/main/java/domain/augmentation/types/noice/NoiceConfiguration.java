package domain.augmentation.types.noice;

import domain.augmentation.infrastructure.AugmentationConfiguration;

public class NoiceConfiguration extends AugmentationConfiguration<NoiceConfiguration> {

    // TODO set fix limits for random generator and configuration of noice augmentation

    @Override
    public NoiceConfiguration createRandomConfiguration() {
        return null;
    }

    @Override
    public NoiceConfiguration createBestConfiguration() {
        return null;
    }
}