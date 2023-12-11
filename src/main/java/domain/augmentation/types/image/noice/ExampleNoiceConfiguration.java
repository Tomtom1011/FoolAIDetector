package domain.augmentation.types.image.noice;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleNoiceConfiguration extends AugmentationConfiguration<ExampleNoiceConfiguration> {

    private int maxNoice = 5;
    private int actNoice;
    private int minNoice = 1;

    @Override
    public ExampleNoiceConfiguration createRandomConfiguration() {
        ExampleNoiceConfiguration config = new ExampleNoiceConfiguration();
        config.setActNoice(createRandomNumber(minNoice, maxNoice));
        return config;
    }

    @Override
    public ExampleNoiceConfiguration createBestConfiguration() {
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