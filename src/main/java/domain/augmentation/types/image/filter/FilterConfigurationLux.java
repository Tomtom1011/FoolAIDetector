package domain.augmentation.types.image.filter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterConfigurationLux extends AugmentationConfiguration<FilterConfigurationLux> {

    private final int filterMin = 0;
    private final int filterMax = 3;
    private final int filterSize = 3;
    private int[][] filter;

    @Override
    public String getConfigurationToPersist() {
        return toString();
    }

    @Override
    public FilterConfigurationLux createRandomConfiguration() {
        FilterConfigurationLux config = new FilterConfigurationLux();
        int[][] filter = new int[filterSize][filterSize];
        for (int x = 0; x < filter.length; x++) {
            for (int y = 0; y < filter[x].length; y++) {
                filter[x][y] = createRandomNumber(filterMin, filterMax);
            }
        }
        config.setFilter(filter);
        return config;
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

    private String printTwoDimArray(int[][] array) {
        StringBuilder filter = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                filter.append(array[i][j]).append(" ");
            }
            filter.append("\n");
        }
        return filter.toString();
    }
}
