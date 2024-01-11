package geneticAlgorithm;

import domain.augmentation.infrastructure.AugmentationData;
import domain.augmentation.infrastructure.persistence.SequenceConfigurationFilePersistence;
import domain.augmentation.types.image.filter.FilterAugmentationLux;
import domain.augmentation.types.image.filter.FilterConfigurationLux;
import domain.sequence.TemporaryResultChecker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class FilterGA {

    private static final int INITIAL_POP_SIZE = 8;
    private static final int MAX_ITERATIONS = 100;
    public static final int MAX_SELECTION_SIZE = 4;
    private Random random = new Random();
    private static final String pictureName = "breakfast";

    public static void main(String[] args) throws IOException {
        FilterGA ga = new FilterGA();

        AugmentationData data = ga.readImage(new File("src/main/resources/example_data/ai_gen_images/" + pictureName + ".jpg"));
        ga.solve(data);
    }

    private AugmentationData readImage(File image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image);
        return new AugmentationData(bufferedImage);
    }

    public void solve(AugmentationData data) {
        List<FilterConfigurationLux> individuals = new ArrayList<>();
        fillIndividuals(individuals);
        FilterConfigurationLux allbest = individuals.get(0);

        for (int iteration = 1; iteration <= MAX_ITERATIONS; iteration++) {
            calculateIndividuals(individuals, data, iteration);

            List<FilterConfigurationLux> bestOfPopulation = selectIndividualsForNextGeneration(individuals);
            FilterConfigurationLux best = bestOfPopulation.get(0);
            System.out.println("Temporary best: " + best);
            writeImage(best.getChanged(), iteration, pictureName, "Best_" + best.getResult() + "_" + pictureName);


            if (best.getResult() < allbest.getResult()) {
                writeImage(best.getChanged(), 0, pictureName, "AlltimeBest_" + best.getResult() + "_" + pictureName);
                SequenceConfigurationFilePersistence.persistGADataToFile(pictureName, best.getConfigurationToPersist());
            }

            List<FilterConfigurationLux> children = crossOver(bestOfPopulation);

            mutate(children);

            individuals = children;
            fillIndividuals(individuals);
        }
    }

    private void calculateIndividuals(List<FilterConfigurationLux> individuals, AugmentationData data, int iteration) {
        FilterAugmentationLux augment = new FilterAugmentationLux();
        individuals.forEach(i -> {
            augment.setConfiguration(i);
            AugmentationData copyData = data.copy();
            augment.transform(copyData);
            i.setChanged(copyData);
            System.out.println("transformed");
            boolean notChecked = true;
            int tryCount = 0;
            do {
                try {
                    double result = TemporaryResultChecker.checkResult(copyData.getImage());
                    writeImage(copyData, iteration, pictureName, result + "_" + pictureName);
                    System.out.println("checked " + result);
                    i.setResult(result);
                    notChecked = false;
                } catch (IOException e) {
                    if (tryCount > 2) {
                        i.setResult(100);
                        System.out.println("Could not check for result");
                        e.printStackTrace();
                        break;
                    }
                    tryCount++;
                }
            } while (notChecked);
        });
    }

    private void fillIndividuals(List<FilterConfigurationLux> individuals) {
        while (individuals.size() <= INITIAL_POP_SIZE) {
            FilterConfigurationLux config = new FilterConfigurationLux();
            individuals.add(config);
        }
    }

    private List<FilterConfigurationLux> selectIndividualsForNextGeneration(List<FilterConfigurationLux> individuals) {
        return individuals.stream()
                .sorted(Comparator.comparing(FilterConfigurationLux::getResult))
                .limit(MAX_SELECTION_SIZE)
                .toList();
    }

    private List<FilterConfigurationLux> crossOver(List<FilterConfigurationLux> parents) {
        ArrayList<FilterConfigurationLux> children = new ArrayList<>();

        for (int i = 0; i < parents.size(); i=i+2) {
            double[][] parentFilter1 = parents.get(i).getFilter();
            double[][] parentFilter2 = parents.get(i+1).getFilter();

            double[][] filterChild1 = new double[parentFilter1.length][parentFilter1.length];
            double[][] filterChild2 = new double[parentFilter2.length][parentFilter2.length];

            int counter = 0;
            for (int x = 0; x < filterChild1.length; x++) {
                for (int y = 0; y < filterChild1.length; y++) {
                    if ((counter++ % 2) == 0) {
                        filterChild1[x][y] = parentFilter1[x][y];
                        filterChild2[x][y] = parentFilter2[x][y];
                    } else {
                        filterChild1[x][y] = parentFilter2[x][y];
                        filterChild2[x][y] = parentFilter1[x][y];
                    }
                }
            }

            FilterConfigurationLux child1 = new FilterConfigurationLux(filterChild1);
            FilterConfigurationLux child2 = new FilterConfigurationLux(filterChild2);

            children.add(child1);
            children.add(child2);
        }
        return children;
    }

    private void mutate(List<FilterConfigurationLux> children) {
        children.forEach(this::mutateConfig);
    }

    private void mutateConfig(FilterConfigurationLux config) {
        double[][] filter = config.getFilter();
        double changeProbability = (double) 2 / (filter.length * filter.length);

        for (int x = 0; x < filter.length; x++) {
            for (int y = 0; y < filter.length; y++) {
                if (Math.random() < changeProbability) {
                    if (Math.random() <= 0.5) {
                        filter[x][y] = filter[x][y] + 0.1;
                    } else {
                        filter[x][y] = filter[x][y] - 0.1;
                    }
                }
            }
        }
    }

    private void writeImage(AugmentationData data, int iteration, String picture, String name) {
        try {
            File target = new File("src/main/resources/transformed/" + picture + "/" + iteration + "\\" + name + ".jpg");

            target.getParentFile().mkdirs();
            if (!target.exists()) {
                target.createNewFile();
            }

            ImageIO.write(data.getImage(), "jpg", target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}