package domain.augmentation.types.image.brighter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.ImageAugmentation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BrighterAugmentation extends ImageAugmentation {

    private BrighterConfiguration config;
    private Random random = new Random();

    public BrighterAugmentation() {
        config = new BrighterConfiguration().createRandomConfiguration();
    }

    public BrighterAugmentation(BrighterConfiguration config) {
        this.config = config;
    }

    @Override
    public void transform(AugmentationData data) {
        BufferedImage image = data.getImage();
        double factor = 1.2;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                    int color = image.getRGB(x, y);

                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;
                    int alpha = (color & 0xff000000) >>> 24;

                    blue = (int) Math.min(255, blue * factor);
                    green = (int) Math.min(255, green * factor);
                    red = (int) Math.min(255, red * factor);

                    alpha = 127;
                    image.setRGB(x, y, new Color(red, green, blue, alpha).getRGB());
            }
        }

    }

    @Override
    public AugmentationConfiguration<?> getConfiguration() {
        return config;
    }

    @Override
    public void setConfiguration(AugmentationConfiguration config) {
        this.config = (BrighterConfiguration) config;
    }
}