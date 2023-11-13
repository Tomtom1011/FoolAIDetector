package domain;

import config.FileType;
import config.ProgramConfig;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

@AllArgsConstructor
public class ParamChecker {

    private final String[] args;

    public ProgramConfig parseParameter() throws IllegalArgumentException, FileNotFoundException {
        checkForCorrectParameter();
        File source = new File(args[0]);
        return ProgramConfig.builder().sourceFile(source).targetPath(args[1]).type(getType(source)).build();
    }

    private void checkForCorrectParameter() throws IllegalArgumentException, FileNotFoundException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Not enough parameter.\n" +
                    "Expected parameter: <Source file path> <Target file path>.\n" +
                    "Given parameter: " + Arrays.toString(args));
        }

        File f = new File(args[0]);
        if (!f.exists()) {
            throw new FileNotFoundException("Sourcefile does not exist" + args[0]);
        }
    }

    private FileType getType(File sourceFile) {
        if (sourceFile.getName().contains(".txt")) {
            return FileType.TEXT;
        } else {
            return FileType.IMAGE;
        }
    }
}