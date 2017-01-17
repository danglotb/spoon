package spoon;

import static org.junit.Assert.assertTrue;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 1/13/17
 */
public class Test {

	@org.junit.Test
	public void testComments() throws Exception {
		Launcher l = new Launcher();
		l.addInputResource("src/test/resources/JavaFileTest.java");
		l.getEnvironment().setNoClasspath(true);
		l.getEnvironment().setCommentEnabled(true);
		l.buildModel();
		assertTrue(null != l.getFactory().Class().get("com.squareup.javapet.JavaFileTest"));
	}
}
