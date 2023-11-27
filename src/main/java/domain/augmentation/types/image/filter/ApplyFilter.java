package domain.augmentation.types.image.filter;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ApplyFilter extends ImageAugmentation {

    @Override
    public void transform(AugmentationData data) {

        List<double[][][]> segmentMatrices = data.getSegmentMatrices();

        for (double[][][] segmentMatrix  : segmentMatrices) {
            // Convert the segment to a matrix
            double[][][] filteredMatrix = applyFilter(segmentMatrix );
            data.getFilteredMatrices().add(filteredMatrix);
        }

    }

    private double[][][] applyFilter(double[][][] matrix) {
        return matrix;
    }

}