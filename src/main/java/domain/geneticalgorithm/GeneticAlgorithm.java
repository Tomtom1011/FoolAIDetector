package domain.geneticalgorithm;

import domain.sequence.AugmentationSequence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeneticAlgorithm {

    private int numberOfIndividuals = 5;
    private int iterationNumber = 100;

    private AugmentationSequence rootSequence;

    public void solve(AugmentationSequence rootSequence) {
        this.rootSequence = rootSequence;
        for (int i = 0; i < iterationNumber; i++) {

            List<Individual> individuals = generateIndividuals();

            List<Individual> best = selectIndividualsForNextGeneration(individuals);

            List<Individual> children = crossOver(best);

            mutate(children);
        }
    }

    private List<Individual> generateIndividuals() {
        ArrayList<Individual> individuals = new ArrayList<>();

        for (int i = 0; i < numberOfIndividuals; i++) {
            individuals.add(new Individual(sequence));
        }
        return individuals;
    }

    private List<Individual> selectIndividualsForNextGeneration(List<Individual> individuals) {
        return individuals.stream()
                .sorted(Comparator.comparing(Individual::getFitness))
                .limit(2)
                .toList();
    }

    private List<Individual> crossOver(List<Individual> parents) {
        ArrayList<Individual> children = new ArrayList<>();

        for (int i = 0; i < parents.size(); i=i+2) {
            Individual parent1 = parents.get(i);
            Individual parent2 = parents.get(i+1);

            List<AugmentationSequence<?>> childSequences = parent1.getSequence().crossOver(parent2.getSequence());
            for (AugmentationSequence<?> seq : childSequences) {
                children.add(new Individual(seq));
            }
        }
        return children;
    }

    private void mutate(List<Individual> children) {
        children.stream()
                .map(Individual::getSequence)
                .forEach(AugmentationSequence::mutate);
    }
}
