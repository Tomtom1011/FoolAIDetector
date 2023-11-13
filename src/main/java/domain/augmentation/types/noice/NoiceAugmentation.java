package domain.augmentation.types.noice;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NoiceAugmentation extends ImageAugmentation {

    private final NoiceConfiguration config;

    public NoiceAugmentation() {
        config = new NoiceConfiguration().createRandomConfiguration();
    }

    public NoiceAugmentation(NoiceConfiguration config) {
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

                alpha = 127;
                image.setRGB(x, y, new Color(red, green, blue, alpha).getRGB());
            }
        }

    }
}