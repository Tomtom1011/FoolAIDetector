package domain.augmentation.types.image.filter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import lombok.*;

@Getter
@Setter
@Builder
public class FilterConfigurationLux extends AugmentationConfiguration<FilterConfigurationLux> {

    private final double filterMin = -0.2;
    private final double filterMax = 0.4;
    private final int filterSize = 3;
    private final double maxChangeValue = 1.5;
    private double[][] filter;

    @Override
    public String getConfigurationToPersist() {
        return toString();
    }

    @Override
    public FilterConfigurationLux createRandomConfiguration() {
        double[][] filter = initializeArray();
        return new FilterConfigurationLux(filter);
    }

    @Override
    public String toString() {
        return "{" +
                "filterMin=" + filterMin +
                ", filterMax=" + filterMax +
                ", filterSize=" + filterSize +
                ", filter:\n" + printTwoDimArray(filter) +
                '}';
    }

    @Override
    public FilterConfigurationLux createBestConfiguration() {
        return null;
    }

    private String printTwoDimArray(double[][] array) {
        StringBuilder filter = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                filter.append(array[i][j]).append(" ");
            }
            filter.append("\n");
        }
        return filter.toString();
    }

    private double[][] initializeArray() {
        double[][] randomArray = new double[filterSize][filterSize];
        double sum = 0.0;

        for (int x = 0; x < filterSize; x++) {
            for (int y = 0; y < filterSize; y++) {
                double randomValue = createRandomDouble(filterMin, filterMax);
                randomArray[x][y] = randomValue;

                sum += randomValue;
                if (sum > maxChangeValue) {
                    randomArray[x][y] -= (sum - maxChangeValue);
                    sum = maxChangeValue;
                }
            }
        }
        return randomArray;
    }
}
