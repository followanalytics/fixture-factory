package br.com.six2six.fixturefactory;

import static org.junit.Assert.assertNotNull;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FixtureUserTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureAnyUser() {
		User user = Fixture.from(User.class).gimme("anyValidUser");
		assertNotNull("User should not be null", user);
	}

	@Test
	public void fixtureFemaleUser() {
		User user = Fixture.from(User.class).gimme("validFemaleUser");
		assertNotNull("User should not be null", user);
	}

    @Test
    public void fixtureValidWithRulesOutOfOrder() {
        User user = Fixture.from(User.class).gimme("validWithRulesOutOfOrder");
        assertNotNull("User should not be null", user);
        Assert.assertEquals(user.getLogin(), user.getName());
        Assert.assertTrue(user.getEmail().contains(user.getLogin()));
    }
}
