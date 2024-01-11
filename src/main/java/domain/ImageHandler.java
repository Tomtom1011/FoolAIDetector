package domain;

import config.ProgramConfig;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.types.image.filter.FilterAugmentationLux;
import domain.augmentation.types.image.filter.FilterConfigurationLux;
import domain.augmentation.types.image.noice.ExampleNoiceAugmentation;
import domain.sequence.AugmentationSequence;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler implements TypeHandler {

    @Override
    public void handleFileType(ProgramConfig config) {

        try {
            // read image
            AugmentationData data = readImage(config.getSourceFile());

            // create a augmentation sequence
            AugmentationSequence<ImageAugmentation> sequence = new AugmentationSequence<>();

            double[][] filter = new double[][]
                    {
                        {-0.060000000000000005, 0.03, 0.23},
                        {0.1, 0.05, -0.03},
                        {0.03, 0.06, -0.13}
                    };
            FilterConfigurationLux filterConfig = new FilterConfigurationLux(filter);

            sequence.addAugmentation(new FilterAugmentationLux(filterConfig));
            sequence.addAugmentation(new ExampleNoiceAugmentation());

            // run augmentation sequence
            sequence.run(data);

            // write image
            writeImage(data, config);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private AugmentationData readImage(File image) throws IOException {
        // read image from path
        BufferedImage bufferedImage = ImageIO.read(image);
        return new AugmentationData(bufferedImage);
    }

    private void writeImage(AugmentationData data, ProgramConfig programConfig) {
        try {
            File target = new File(programConfig.getTargetPath());

            if (!target.exists()) {
                target.createNewFile();
            }

            ImageIO.write(data.getImage(), getFileEnding(target.getName()), target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileEnding(String fileName) {
        return fileName.split("\\.")[1];
    }

}