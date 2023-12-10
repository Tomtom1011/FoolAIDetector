package domain.geneticalgorithm;

import domain.augmentation.infrastructure.AugmentationData;
import domain.sequence.AugmentationSequence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Individual {

    private final AugmentationSequence<?> sequence;

    private double fitness;


    public Individual(AugmentationSequence<?> sequence) {
        this.sequence = sequence;
    }

    public void calculateFitness(AugmentationData data) {
        sequence.run(data);

    }
}
