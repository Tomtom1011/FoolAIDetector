package domain.geneticalgorithm;

import domain.augmentation.infrastructure.AugmentationData;
import domain.sequence.AugmentationSequence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeneticAlgorithm {

    private static final int INITIAL_POP_SIZE = 10;
    private static final int MAX_ITERATIONS = 100;
    public static final int MAX_SELECTION_SIZE = 4;

    public static AugmentationSequence<?> solve(GeneticSolvable solver, AugmentationData data) {
        List<Individual> individuals = generateIndividuals(solver);
        AugmentationSequence<?> best = individuals.get(0).getSequence();

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            calculateIndividuals(individuals, data);

            List<Individual> bestOfPopulation = selectIndividualsForNextGeneration(individuals);
            best = bestOfPopulation.get(0).getSequence();

            List<Individual> children = crossOver(solver, bestOfPopulation);

            mutate(children);

            individuals = children;
            fillIndividuals(solver, individuals);
        }
        return best;
    }

    private static void calculateIndividuals(List<Individual> individuals, AugmentationData data) {
        individuals.forEach(i -> i.calculateFitness(data.copy()));
    }

    private static List<Individual> generateIndividuals(GeneticSolvable solver) {
        ArrayList<Individual> individuals = new ArrayList<>();

        for (int individualIndex = 0; individualIndex < INITIAL_POP_SIZE; individualIndex++) {
            individuals.add(solver.createIndividual());
        }
        return individuals;
    }

    private static void fillIndividuals(GeneticSolvable solver, List<Individual> individuals) {
        while (individuals.size() <= INITIAL_POP_SIZE) {
            individuals.add(solver.createIndividual());
        }
    }

    private static List<Individual> selectIndividualsForNextGeneration(List<Individual> individuals) {
        return individuals.stream()
                .sorted(Comparator.comparing(Individual::getFitness))
                .limit(MAX_SELECTION_SIZE)
                .toList();
    }

    private static List<Individual> crossOver(GeneticSolvable solver, List<Individual> parents) {
        ArrayList<Individual> children = new ArrayList<>();

        for (int i = 0; i < parents.size(); i=i+2) {
            Individual parent1 = parents.get(i);
            Individual parent2 = parents.get(i+1);

            List<AugmentationSequence<?>> childSequences = solver.crossOver(parent1, parent2);
            for (AugmentationSequence<?> seq : childSequences) {
                children.add(new Individual(seq));
            }
        }
        return children;
    }

    private static void mutate(List<Individual> children) {
        children.stream()
                .map(Individual::getSequence)
                .forEach(AugmentationSequence::mutate);
    }
}
