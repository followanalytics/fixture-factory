package br.com.six2six.fixturefactory.util;

import static org.junit.Assert.assertNotNull;

import br.com.six2six.fixturefactory.model.Immutable;
import br.com.six2six.fixturefactory.model.Owner;
import br.com.six2six.fixturefactory.model.SimpleProposal;
import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest {

	@Test
	public void shouldFindDefaultConstructorForTopLevelClass() {
        Assert.assertTrue(ReflectionUtils.hasDefaultConstructor(Owner.class));
	}
	
	@Test
	public void shouldFindDefaultConstructorForInnerClass() {
        Assert.assertTrue(ReflectionUtils.hasDefaultConstructor(Owner.Inner.class));
	}
	
	@Test
	public void shouldntFindAnyDefaultConstructorForTopLevelClass() {
	    Assert.assertFalse(ReflectionUtils.hasDefaultConstructor(Immutable.class));
	}
	
	@Test
	public void shouldntFindDefaultConstructorForInnerClass() {
	    Assert.assertFalse(ReflectionUtils.hasDefaultConstructor(Immutable.ImmutableInner.class));
	}
	
	@Test
	public void shouldFindRecursiveFieldWithInheritanceBaseClass() {
		assertNotNull(ReflectionUtils.invokeRecursiveField(SimpleProposal.class, "item.order.id"));
	}
}
