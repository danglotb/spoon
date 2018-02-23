package spoon;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 22/02/18
 */
public class TestBuildSpoonWithSpoon {

    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    private static boolean compile(String pathToSources, String dependencies, File binaryOutputDirectory) {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setLevel("DEBUG");
        launcher.getEnvironment().setNoClasspath(false);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().setOutputType(OutputType.CLASSES);
        String[] sourcesArray = (pathToSources + PATH_SEPARATOR).split(PATH_SEPARATOR);
        Arrays.stream(sourcesArray).forEach(launcher::addInputResource);
        if (!dependencies.isEmpty()) {
            String[] dependenciesArray = dependencies.split(PATH_SEPARATOR);
            launcher.getModelBuilder().setSourceClasspath(dependenciesArray);
        }
        launcher.buildModel();
        SpoonModelBuilder modelBuilder = launcher.getModelBuilder();
        modelBuilder.setBinaryOutputDirectory(binaryOutputDirectory);
        return modelBuilder.compile(SpoonModelBuilder.InputType.CTTYPES);
    }

    @Test
    public void buildSpoon() throws Exception {
        String dependencies = ""; // put here, the output of mvn dependency:build-classpath
        compile("src/main/java", dependencies, new File("target/out-classes"));
        compile("src/test/java", dependencies + PATH_SEPARATOR + "target/out-classes", new File("out-test-classes"));
    }
}
