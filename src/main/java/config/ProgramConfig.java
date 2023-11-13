package config;

import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Getter
@Builder
public class ProgramConfig {

    private final File sourceFile;
    private final String targetPath;
    private final FileType type;

}