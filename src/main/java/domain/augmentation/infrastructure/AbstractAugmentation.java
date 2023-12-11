package domain.augmentation.infrastructure;

public abstract class AbstractAugmentation {

    public abstract void transform(AugmentationData data);

    public abstract AugmentationConfiguration<?> getConfiguration();

}
