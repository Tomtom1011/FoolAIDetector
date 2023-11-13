import config.ProgramConfig;
import domain.ParamChecker;

import java.io.FileNotFoundException;

public class FoolAIDetector {

    public static void main(String[] args) {

        try {
            ProgramConfig config = new ParamChecker(args).parseParameter();
            config.getType().getHandler().handleFileType(config);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}