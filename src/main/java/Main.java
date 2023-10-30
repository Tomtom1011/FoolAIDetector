import config.ProgramConfig;

import java.io.FileNotFoundException;

public class Main {

    public Main(String[] args) {
        try {

            ProgramConfig config = new ParamChecker(args).parseParameter();

            // start the generating pipe reading file,
            // afterward pipe manipulate x
            // at last sink pipe create output file

        } catch (IllegalArgumentException | FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Main m = new Main(args);
    }
}