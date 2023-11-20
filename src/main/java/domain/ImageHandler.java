package domain;

import config.ProgramConfig;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.ImageAugmentation;
import domain.augmentation.types.image.noice.NoiseAugmentation;
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

            // TODO create random augmentation sequence
//        SequenceConfiguration seqConfig = SequenceConfiguration.DEFAULT;
//        AugmentationSequence<ImageAugmentation> sequence = SequenceFactory.createRandomImageAugmentSequence(seqConfig);

            // create a augmentation sequence
            AugmentationSequence<ImageAugmentation> sequence = new AugmentationSequence<>();
            sequence.addAugmentation(new NoiseAugmentation());

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