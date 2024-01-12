package domain.augmentation.types.image.brighter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrighterConfiguration extends AugmentationConfiguration<BrighterConfiguration> {

    private int maxNoice = 5;
    private int actNoice;
    private int minNoice = 1;

    @Override
    public BrighterConfiguration createRandomConfiguration() {
        BrighterConfiguration config = new BrighterConfiguration();
        config.setActNoice(createRandomInteger(minNoice, maxNoice));
        return config;
    }

    @Override
    public BrighterConfiguration createBestConfiguration() {
        return null;
    }

    @Override
    public String toString() {
        return "{" +
                "maxNoice=" + maxNoice +
                ", actNoice=" + actNoice +
                ", minNoice=" + minNoice +
                '}';
    }

    @Override
    public String getConfigurationToPersist() {
        return toString();
    }
}