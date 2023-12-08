package domain.augmentation.infrastructure.persistence;

import java.io.*;

public class SequenceConfigurationFilePersistence {

    public static final String PERSISTENCE_PATH = "src/main/resources/persistedSequence/bestResult.txt";

    public static void persistDataToFile(String data) {
        File file = new File(PERSISTENCE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double readBestResult() {
        double result = 100;
        File file = new File(PERSISTENCE_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ( (line = reader.readLine()) != null) {
                if (line.contains("Result:")) {
                    result = Double.parseDouble(line.replace("Result:", ""));
                    break;
                }
            }

        } catch (IOException ignored) {}
        System.out.println("Read bestResult = " + result);
        return result;
    }
}
