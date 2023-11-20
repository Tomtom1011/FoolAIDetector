package domain.augmentation.types.image.matricization;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Matricization extends ImageAugmentation {

    @Override
    public void transform(AugmentationData data) {

        BufferedImage image = data.getImage();

        List<BufferedImage> segments = data.getSegments();


        for (BufferedImage segment : segments) {
            // Convert the segment to a matrix
            double[][][] segmentMatrix = convertSegmentToMatrix(segment);
            data.getSegmentMatrices().add(segmentMatrix);
        }

    }

    private double[][][] convertSegmentToMatrix(BufferedImage segment) {
        int width = segment.getWidth();
        int height = segment.getHeight();

        double[][][] matrix = new double[width][height][3];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = segment.getRGB(x, y);

                // Extract RGB values
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Store RGB values in the matrix
                matrix[x][y][0] = red;
                matrix[x][y][1] = green;
                matrix[x][y][2] = blue;
            }
        }

        return matrix;
    }

}