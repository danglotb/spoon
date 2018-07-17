package spoon.support.compiler;

import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.visitor.filter.TypeFilter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 17/07/18
 */
public class SnippetCompilationHelperTest {

    @Test
    public void testCompileAndReplaceSnippetsIn() throws Exception {

        /*
            contract:
                We have a method, and we have a CodeSnippetStatement.
                In the code snippet, there is a reference to a declared variable, e.g. an object.
                After the call to CtType().compileAndReplaceSnippetsIn,
                The snippet must be replaced by the reference of the good object.
         */

        final Launcher launcher = new Launcher();
        launcher.addInputResource("src/test/resources/snippet/SnippetResources.java");
        launcher.buildModel();

        final Factory factory = launcher.getFactory();

        final CtCodeSnippetStatement codeSnippetStatement = factory.createCodeSnippetStatement("s.method(23)");
        final CtClass<?> snippetClass = launcher.getFactory().Class().get("snippet.test.resources.SnippetResources");
        final CtMethod<?> staticMethod = snippetClass.getMethodsByName("staticMethod").get(0);
        staticMethod.getBody().insertEnd(codeSnippetStatement);

        snippetClass.compileAndReplaceSnippets(); // should not throw any exception
        assertTrue(staticMethod.getBody().getLastStatement() instanceof CtInvocation<?>); // the last statement, i.e. the snippet, has been replaced by its real node: a CtInvocation
        final CtInvocation<?> lastStatement = (CtInvocation<?>) staticMethod.getBody().getLastStatement();
        final CtLocalVariableReference<?> reference = staticMethod.getElements(new TypeFilter<>(CtLocalVariable.class)).get(0).getReference();
        assertEquals(factory.createVariableRead(reference, false), lastStatement.getTarget()); // the target of the inserted invocation has been resolved as the reference of the declared object "s"
    }

}
