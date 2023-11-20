package domain.augmentation.types.image.noice;

import domain.augmentation.infrastructure.AugmentationConfiguration;

public class NoiseConfiguration extends AugmentationConfiguration<NoiseConfiguration> {

    // TODO set fix limits for random generator and configuration of noice augmentation

    @Override
    public NoiseConfiguration createRandomConfiguration() {
        return null;
    }

    @Override
    public NoiseConfiguration createBestConfiguration() {
        return null;
    }
}