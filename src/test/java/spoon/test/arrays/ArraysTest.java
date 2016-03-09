package spoon.test.arrays;

import org.junit.Test;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtArrayTypeReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static spoon.testing.utils.ModelUtils.build;

public class ArraysTest {

	@Test
	public void testArrayReferences() throws Exception {
		CtType<?> type = build("spoon.test.arrays", "ArrayClass");
		assertEquals("ArrayClass", type.getSimpleName());
		assertEquals("int[][][]", type.getField("i").getType().toString());
		assertEquals(3, ((CtArrayTypeReference<?>) type.getField("i").getType()).getDimensionCount());
		final CtArrayTypeReference<?> arrayTypeReference = (CtArrayTypeReference<?>) type.getField("i").getDefaultExpression().getType();
		assertEquals(1, arrayTypeReference.getArrayType().getAnnotations().size());
		assertEquals("@spoon.test.arrays.ArrayClass.TypeAnnotation(integer = 1)" + System.lineSeparator(), arrayTypeReference.getArrayType().getAnnotations().get(0).toString());

		CtField<?> x = type.getField("x");
		assertTrue(x.getType() instanceof CtArrayTypeReference);
		assertEquals("Array", x.getType().getSimpleName());
		assertEquals("java.lang.reflect.Array", x.getType().getQualifiedName());
		assertEquals("int", ((CtArrayTypeReference<?>) x.getType()).getComponentType().getSimpleName());
		assertTrue(((CtArrayTypeReference<?>) x.getType()).getComponentType().getActualClass().equals(int.class));
	}

}
