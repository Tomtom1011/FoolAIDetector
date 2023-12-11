package domain.augmentation.types.image.filter;

import domain.augmentation.infrastructure.AugmentationConfiguration;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.ImageAugmentation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public class FilterAugmentationLux extends ImageAugmentation {

    private final FilterConfigurationLux config;

    public FilterAugmentationLux() {
        config = new FilterConfigurationLux().createRandomConfiguration();
    }

    @Override
    public void transform(AugmentationData data) {
        BufferedImage source = data.getImage();
        BufferedImage target = copyBufferedImage(source);

        int[][] filter = config.getFilter();
        int width = source.getWidth();
        int height = source.getHeight();
        int filterSize = config.getFilterSize();
        int middle = 1 + filterSize/2;

        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++) {
                int scalarProduct = 0;

                for(int fi=0;fi<filter.length;fi++) {
                    for(int fj=0;fj<filter[0].length;fj++) {
                        int ii = i - middle + 1 + fi;
                        int jj = j - middle + 1 + fj;

                        if(ii>=0 && ii<width && jj>=0 && jj<height) {
                            int color = source.getRGB(ii, jj);
                            scalarProduct += color*filter[fi][fj];
                        }
                    }
                }
                target.setRGB(i, j, scalarProduct);
            }
        }
        data.setImage(target);
    }

    @Override
    public AugmentationConfiguration<?> getConfiguration() {
        return config;
    }

    private BufferedImage copyBufferedImage(BufferedImage originalImage) {
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
