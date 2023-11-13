package domain.sequence;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class SequenceConfiguration {

    public static final SequenceConfiguration DEFAULT = new SequenceConfiguration();

    private final int maxAugmentations = 5;

}
