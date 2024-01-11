package domain.augmentation.infrastructure;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

public abstract class AugmentationConfiguration<T> implements Configurable<T> {

    public abstract String getConfigurationToPersist();

    protected int createRandomInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(min, max);
    }

    protected double createRandomDouble(double min, double max) {
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols newDecimalSymbol = new DecimalFormatSymbols();
        newDecimalSymbol.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(newDecimalSymbol);
        return Double.parseDouble(df.format(rand.nextDouble(min, max)));
    }
}
