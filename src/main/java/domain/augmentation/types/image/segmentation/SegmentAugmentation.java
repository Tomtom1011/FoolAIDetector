package domain.augmentation.types.image.segmentation;

import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SegmentAugmentation extends ImageAugmentation {

    @Override
    public void transform(AugmentationData data) {

        BufferedImage image = data.getImage();

        int segmentSize = 3;

        // Segment the image
        List<BufferedImage> segments = segmentImage(image, segmentSize);
        data.setSegments(segments);
    }

    // Segment the image into a list of smaller images
    private List<BufferedImage> segmentImage(BufferedImage image, int segmentSize) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        List<BufferedImage> segments = new ArrayList<>();

        for (int i = 0; i < imageHeight; i += segmentSize) {
            for (int j = 0; j < imageWidth; j += segmentSize) {
                int segmentWidth = Math.min(segmentSize, imageWidth - i);
                int segmentHeight = Math.min(segmentSize, imageHeight - j);

                // Skip segments with zero width or height
                if (segmentWidth <= 0 || segmentHeight <= 0) {
                    continue;
                }

                BufferedImage segment = image.getSubimage(i, j, segmentWidth, segmentHeight);
                segments.add(segment);
            }
        }

        return segments;
    }

}