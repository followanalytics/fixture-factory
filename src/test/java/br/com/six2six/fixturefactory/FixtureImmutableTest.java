package br.com.six2six.fixturefactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Child;
import br.com.six2six.fixturefactory.model.Immutable;
import br.com.six2six.fixturefactory.model.Route;
import br.com.six2six.fixturefactory.model.RoutePlanner;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.CombinableMatcher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixtureImmutableTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}
	
	@Test
	public void shouldCreateImmutableObjectUsingCorrectPartialConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("twoParameterConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals("default", result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
		assertNull(result.getDate());
		assertNull(result.getAddress());
	}

	@Test
	public void shouldCreateImmutableObjectUsingAnotherPartialConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("threeParameterConstructor");
		
		assertEquals("default", result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertNotNull(result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
		assertNull(result.getDate());
		assertNotNull(result.getAddress());
	}
	
	@Test
	public void shouldCreateImmutableObjectUsingFullConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("fullConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals(result.getPropertyA() + " based", result.getPropertyC());
		assertNotNull(result.getDate());
		assertNotNull(result.getAddress());
	}
	
	@Test
	public void shouldWorkWhenReceivingRelationsInTheConstructor() {
		Route route = Fixture.from(Route.class).gimme("valid");
		assertEquals(Long.valueOf(1L), route.getId().getValue());
		assertEquals(Long.valueOf(100L), route.getId().getSeq());
		assertNotNull(route.getCities().get(0).getName());
	}
	
    @Test 
    public void shouldWorkWhenChainingProperties() { 
        Route route = Fixture.from(Route.class).gimme("chainedId"); 
        assertEquals(Long.valueOf(2L), route.getId().getValue());
        assertEquals(Long.valueOf(200L), route.getId().getSeq());
        assertNotNull(route.getCities().get(0).getName()); 
    }
    
    @Test
    public void shouldWorkWhenChainingPropertiesUsingRelations() {
        RoutePlanner routePlanner = Fixture.from(RoutePlanner.class).gimme("chainedRoutePlanner");
        CombinableMatcher<Long> m1 = CoreMatchers.either(equalTo(3L)).or(equalTo(4L));
        Assert.assertTrue(m1.matches(routePlanner.getRoute().getId().getValue()));
        CombinableMatcher<Long> m2 = CoreMatchers.either(equalTo(300L)).or(equalTo(400L));
        Assert.assertTrue(m2.matches(routePlanner.getRoute().getId().getSeq()));
        assertNotNull(routePlanner.getRoute().getCities().get(0).getName());
    }

    @Test
    public void shouldWorkWithInheritance() {
        Child child = Fixture.from(Child.class).gimme("valid");
        Assert.assertEquals(8, child.getParentAttribute().getValue().length());
    	Assert.assertEquals(16, child.getChildAttribute().length());
    }
    
    @Test
    public void shouldWorkWhenChainingInheritedProperty() {
        Child child = Fixture.from(Child.class).gimme("chained");
        Assert.assertEquals(8, child.getParentAttribute().getValue().length());
        Assert.assertEquals(16, child.getChildAttribute().length());
    }
    
    @Test
    public void shouldOverrideNestedObjectAttribute() {
    	Immutable result = Fixture.from(Immutable.class).gimme("fullConstructor", new Rule() {{
    		add("address.street", "Rua do Nykolas");
    	}});

    	Assert.assertEquals("Rua do Nykolas", result.getAddress().getStreet());
    }
    
}
