package domain;

import config.ProgramConfig;
import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.TextAugmentation;
import domain.sequence.AugmentationSequence;

import java.io.*;

public class TextHandler implements TypeHandler {

    @Override
    public void handleFileType(ProgramConfig config) {

        // read string from file
        AugmentationData data = readText(config.getSourceFile());

        // TODO create random augmentation sequence

        // create a augmentation sequence
        AugmentationSequence<TextAugmentation> sequence = new AugmentationSequence<>();

        // run augmentation sequence
        sequence.run(data);

        // write file
        writeText(data, config.getTargetPath());

    }

    private AugmentationData readText(File image) {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(image))){

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new AugmentationData(builder.toString());
    }

    private void writeText(AugmentationData data, String targetPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetPath))){
            writer.write(data.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
