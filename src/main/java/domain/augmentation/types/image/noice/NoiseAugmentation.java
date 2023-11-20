package domain.augmentation.types.image.noice;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NoiseAugmentation extends ImageAugmentation {

    private final NoiseConfiguration config;

    public NoiseAugmentation() {
        config = new NoiseConfiguration().createRandomConfiguration();
    }

    public NoiseAugmentation(NoiseConfiguration config) {
        this.config = config;
    }

    @Override
    public void transform(AugmentationData data) {
        BufferedImage image = data.getImage();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // You can apply noise or other subtle effects here
                // For example, add some random noise to each color channel
                red += (int) (Math.random() * 25);
                green += (int) (Math.random() * 25);
                blue += (int) (Math.random() * 25);

                // Ensure color values are in the valid range (0-255)
                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newRGB);
            }
        }

    }

}