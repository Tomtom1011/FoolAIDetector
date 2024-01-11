package domain.augmentation.types.image.noice;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ExampleNoiceAugmentation extends ImageAugmentation {

    private ExampleNoiceConfiguration config;
    private Random random = new Random();

    public ExampleNoiceAugmentation() {
        config = new ExampleNoiceConfiguration().createRandomConfiguration();
    }

    public ExampleNoiceAugmentation(ExampleNoiceConfiguration config) {
        this.config = config;
    }

    @Override
    public void transform(AugmentationData data) {
        BufferedImage image = data.getImage();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);

                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                int alpha = (color & 0xff000000) >>> 24;

                blue = Math.min(blue + random.nextInt(100), 255);
                green = Math.min(green + random.nextInt(100), 255);
                red = Math.min(red + random.nextInt(100), 255);

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
        this.config = (ExampleNoiceConfiguration) config;
    }
}