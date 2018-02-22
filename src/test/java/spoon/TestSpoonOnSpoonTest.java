package spoon;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class TestSpoonOnSpoonTest {

    @Test
    public void testBuild() throws Exception {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setOutputType(OutputType.CLASSES);
        launcher.addInputResource("src/test/java/spoon/generating");
        launcher.buildModel();
        // failing
        assertFalse(launcher.getFactory()
                .Class()
                .get("spoon.generating.meta.RoleHandlerTemplate")
                .getNestedTypes()
                .isEmpty()
        );
        // failing
        assertFalse(
                launcher.getFactory()
                        .Class()
                        .getAll()
                        .stream()
                        .anyMatch(ctType ->
                                "spoon.generating.meta.$Role$".equals(ctType.getQualifiedName())
                        )
        );
    }
}
