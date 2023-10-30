package config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProgramConfig {

    private final String sourcePath;
    private final String targetPath;

}
