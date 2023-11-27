package domain.augmentation.types.image.reconstruction;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reconstruct extends ImageAugmentation {

    @Override
    public void transform(AugmentationData data) {
        convertMatricesToImage(data);

    }

    private void convertMatricesToImage(AugmentationData data) {
        int column = -1;
        // Iterate through the matrices and modify the original image
        for (int i = 0; i < (data.getFilteredMatrices().size()); i++) {
            
            double[][][] matrix = data.getFilteredMatrices().get(i);

            if (i%(data.getImage().getHeight()/data.getSegmentSize()) == 0){
                column++;
            }

            for (int x = 0; x < matrix.length; x++) {
                for (int y = 0; y < matrix[0].length ; y++) {
                    int rgb = ((int) matrix[x][y][0] << 16) | ((int) matrix[x][y][1] << 8) | (int) matrix[x][y][2];
                    data.getImage().setRGB(((column*data.getSegmentSize()) + x), ((i%(data.getImage().getHeight()-2))+y), rgb);
                    //System.out.println(((column*data.getSegmentSize()) + x) + ", " + ((i%(data.getImage().getHeight()-2))+y));
                }

            }
        }
    }

}