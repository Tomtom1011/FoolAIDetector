package domain.augmentation.types.image.filter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.ImageAugmentation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public class FilterAugmentationLux extends ImageAugmentation {

    private final FilterConfigurationLux config;

    public FilterAugmentationLux() {
        config = FilterConfigurationLux.builder().build().createRandomConfiguration();;
    }

    public FilterAugmentationLux(FilterConfigurationLux config) {
        this.config = config;
    }

    @Override
    public void transform(AugmentationData data) {
        BufferedImage source = data.getImage();
        BufferedImage target = AugmentationData.copyBufferedImage(source);

        double[][] filter = config.getFilter();
        int width = source.getWidth();
        int height = source.getHeight();
        int filterSize = config.getFilterSize();
        int middle = 1 + filterSize/2;

        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++) {
                double scalarProductR = 0;
                double scalarProductG = 0;
                double scalarProductB = 0;

                Color color = null;
                for(int fi=0;fi<filter.length;fi++) {
                    for(int fj=0;fj<filter[0].length;fj++) {
                        int ii = i - middle + 1 + fi;
                        int jj = j - middle + 1 + fj;

                        if(ii>=0 && ii<width && jj>=0 && jj<height) {
                            color = new Color(source.getRGB(ii, jj));
                            scalarProductR = Math.min(scalarProductR + color.getRed()*filter[fi][fj], 255);
                            scalarProductG = Math.min(scalarProductG + color.getGreen()*filter[fi][fj], 255);
                            scalarProductB = Math.min(scalarProductB + color.getBlue()*filter[fi][fj], 255);
                        }
                    }
                }
                target.setRGB(i, j, mergeRGB(scalarProductR, scalarProductG, scalarProductB));
            }
        }
        data.setImage(target);
    }

    public static int mergeRGB(double red, double green, double blue) {
        return ((int) red << 16) | ((int) green << 8) | (int) blue;
    }

    @Override
    public AugmentationConfiguration<?> getConfiguration() {
        return config;
    }

    @Override
    public void mutate() {

    }
}
