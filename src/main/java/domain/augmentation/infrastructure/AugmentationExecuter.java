package domain.augmentation.infrastructure;

import config.ProgramConfig;

public class AugmentationExecuter {

    public static void startAugmenting(ProgramConfig config) {
        config.getType().getHandler().handleFileType(config);
    }
}