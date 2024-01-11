package domain.augmentation.infrastructure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

@Getter
@Setter
public class AugmentationData {

    private BufferedImage image;

    private String text;
    
    private List<BufferedImage> segments;

    private int segmentSize;

    private List<double[][][]> segmentMatrices = new ArrayList();

    List<double[][][]> filteredMatrices = new ArrayList();

    public AugmentationData(String text) {
        this.text = text;
    }

    public AugmentationData(BufferedImage image) {
        this.image = image;
    }

    public AugmentationData copy() {
        if (image != null) {
            BufferedImage imageCopy = AugmentationData.copyBufferedImage(image);
            return new AugmentationData(imageCopy);
        } else  {
            return new AugmentationData(text);
        }
    }

    public static BufferedImage copyBufferedImage(BufferedImage originalImage) {
        BufferedImage copy = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                originalImage.getType());

        Raster originalRaster = originalImage.getRaster();
        DataBufferByte originalData = (DataBufferByte) originalRaster.getDataBuffer();
        byte[] originalPixels = originalData.getData();

        Raster copyRaster = copy.getRaster();
        DataBufferByte copyData = (DataBufferByte) copyRaster.getDataBuffer();
        byte[] copyPixels = copyData.getData();

        System.arraycopy(originalPixels, 0, copyPixels, 0, originalPixels.length);

        return copy;
    }
}