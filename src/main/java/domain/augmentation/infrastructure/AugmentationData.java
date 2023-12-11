package domain.augmentation.infrastructure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
}