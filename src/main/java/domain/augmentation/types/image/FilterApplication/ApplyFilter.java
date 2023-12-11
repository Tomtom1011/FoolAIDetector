package domain.augmentation.types.image.FilterApplication;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ApplyFilter extends ImageAugmentation {

    @Override
    public void transform(AugmentationData data) {
        float[][] filterMatrix = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };

        BufferedImage image = data.getImage();

        BufferedImage filteredImage = applyFilter(image, filterMatrix);

        data.setImage(filteredImage);

    }


        private static BufferedImage applyFilter(BufferedImage inputImage, float[][] filterMatrix) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                float[] rgb = applyFilterAtPixel(inputImage, x, y, filterMatrix);
                outputImage.setRGB(x, y, convertToRGB(rgb));
            }
        }

        return outputImage;
    }

    private static float[] applyFilterAtPixel(BufferedImage image, int x, int y, float[][] filterMatrix) {
        float[] result = new float[3];

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int pixel = image.getRGB(x + i, y + j);
                Color color = new Color(pixel);

                result[0] += color.getRed() * filterMatrix[i + 1][j + 1];
                result[1] += color.getGreen() * filterMatrix[i + 1][j + 1];
                result[2] += color.getBlue() * filterMatrix[i + 1][j + 1];
            }
        }

        return result;
    }

    private static int convertToRGB(float[] values) {
        int alpha = 255;
        int red = Math.min(255, Math.max(0, Math.round(values[0])));
        int green = Math.min(255, Math.max(0, Math.round(values[1])));
        int blue = Math.min(255, Math.max(0, Math.round(values[2])));

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

}