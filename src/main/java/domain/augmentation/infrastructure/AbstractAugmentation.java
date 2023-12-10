package domain.augmentation.infrastructure;

import java.util.Queue;

public abstract class AbstractAugmentation {

    public abstract void transform(AugmentationData data);

    public abstract AugmentationConfiguration<?> getConfiguration();

    public abstract void setConfiguration(AugmentationConfiguration<?> config);

    public abstract void mutate();
}
