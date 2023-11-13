package domain.augmentation.infrastructure;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
public class AugmentationData {

    private BufferedImage image;

    private String text;

    public AugmentationData(String text) {
        this.text = text;
    }

    public AugmentationData(BufferedImage image) {
        this.image = image;
    }
}