package domain.sequence;

import domain.augmentation.infrastructure.AbstractAugmentation;
import domain.augmentation.infrastructure.AugmentationData;

import java.util.LinkedList;
import java.util.Queue;

public class AugmentationSequence<T extends AbstractAugmentation> {

    private final Queue<T> augmentations = new LinkedList<>();

    public void addAugmentation(T augment) {
        augmentations.add(augment);
    }

    public boolean removeAugmentation(T augment) {
        return augmentations.remove(augment);
    }

    public void run(AugmentationData data) {
        augmentations.forEach(a -> a.transform(data));

    }
}