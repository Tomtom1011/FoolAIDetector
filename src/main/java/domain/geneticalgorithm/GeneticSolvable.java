package domain.geneticalgorithm;

import domain.sequence.AugmentationSequence;

import java.util.List;

public interface GeneticSolvable {

    Individual createIndividual();

    List<AugmentationSequence<?>> crossOver(Individual parent1, Individual parent2);

}
