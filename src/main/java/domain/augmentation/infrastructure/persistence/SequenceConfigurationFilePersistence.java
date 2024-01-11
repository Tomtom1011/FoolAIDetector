package domain.augmentation.infrastructure.persistence;

import java.io.*;

public class SequenceConfigurationFilePersistence {

    public static final String PERSISTENCE_PATH_DEFAULT = "src/main/resources/persistedSequence/bestResult.txt";

    public static void persistDataToFile(String data) {
        File file = new File(PERSISTENCE_PATH_DEFAULT);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void persistGADataToFile(String picture, String data) {
        File file = new File("src/main/resources/persistedSequence/" + picture + "/bestResultGA.txt");
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double readBestResult() {
        double result = 100;
        File file = new File(PERSISTENCE_PATH_DEFAULT);
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
