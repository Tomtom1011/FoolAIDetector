package domain;

import config.ProgramConfig;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.types.image.filter.FilterAugmentationLux;
import domain.augmentation.types.image.filter.FilterConfigurationLux;
import domain.augmentation.types.image.noice.ExampleNoiceAugmentation;
import domain.geneticalgorithm.GeneticAlgorithm;
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

//            double[][] filter = new double[][]
//                    {
//                        {0.28, -0.17, 0.14},
//                        {0.47, 0.11, -0.18},
//                        {0.2, 0.06, -0.14}
//                    };
//            FilterConfigurationLux filterConfig = FilterConfigurationLux.builder()
//                    .filter(filter).build();

            sequence.addAugmentation(new FilterAugmentationLux());
//            sequence.addAugmentation(new ExampleNoiceAugmentation());

            AugmentationSequence<?> best = GeneticAlgorithm.solve(sequence, data.copy());

            // run augmentation sequence
            best.run(data);

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