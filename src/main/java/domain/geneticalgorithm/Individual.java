package domain.geneticalgorithm;

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
}
