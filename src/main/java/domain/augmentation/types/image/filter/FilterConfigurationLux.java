package domain.augmentation.types.image.filter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import domain.augmentation.infrastructure.AugmentationData;
import lombok.*;

@Getter
@Setter
public class FilterConfigurationLux extends AugmentationConfiguration<FilterConfigurationLux> {

    private final double filterMin = -0.05;
    private final double filterMax = 0.3;
    private final int filterSize = 3;
    private final double maxChangeValue = 1.5;
    private double[][] filter;
    private double result;
    private AugmentationData changed;

    public FilterConfigurationLux() {
        setFilter(createRandomConfiguration().getFilter());
    }

    public FilterConfigurationLux(double[][] filter) {
        setFilter(filter);
    }

    @Override
    public String getConfigurationToPersist() {
        return toString();
    }

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
                ", result=" + result +
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
                filter.append(array[i][j]).append(", ");
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
