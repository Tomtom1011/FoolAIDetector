import config.ProgramConfig;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;

@AllArgsConstructor
public class ParamChecker {

    private final String[] args;

    public ProgramConfig parseParameter() throws IllegalArgumentException, FileNotFoundException {
        checkForCorrectParamter();
        return ProgramConfig.builder().sourcePath(args[0]).targetPath(args[1]).build();
    }

    private void checkForCorrectParamter() throws IllegalArgumentException, FileNotFoundException {
        if (args.length == 2) {
            throw new IllegalArgumentException("Not enougth parameter " + args);
        }

        File f = new File(args[0]);
        if (!f.exists()) {
            throw new FileNotFoundException("Sourcefile does not exist" + args[0]);
        }
    }
}
