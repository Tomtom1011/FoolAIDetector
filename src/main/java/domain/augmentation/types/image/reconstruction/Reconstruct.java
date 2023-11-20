package domain.augmentation.types.image.reconstruction;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Reconstruct extends ImageAugmentation {

    @Override
    public void transform(AugmentationData data) {

        List<double[][][]> filteredMatrices = data.getFilteredMatrices();
        BufferedImage reconstructedImage = convertMatricesToImage(filteredMatrices);
        data.setImage(reconstructedImage);

    }

    private BufferedImage convertMatricesToImage(List<double[][][]> filteredMatrices) {
        int width = filteredMatrices.size();  // Use the length of the list
        int height = filteredMatrices.get(0)[0].length;

        // Create a new image
        BufferedImage reconstructedImage = new BufferedImage(width * height, height, BufferedImage.TYPE_INT_RGB);

        // Iterate through the matrices and fill in the image
        for (int i = 0; i < filteredMatrices.size(); i++) {
            double[][][] matrix = filteredMatrices.get(i);

            // Iterate through the matrix and set pixel values in the image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = ((int) matrix[x][y][0] << 16) | ((int) matrix[x][y][1] << 8) | (int) matrix[x][y][2];
                    reconstructedImage.setRGB(i * width + x, y, rgb);
                }
            }
        }

        return reconstructedImage;
    }

}