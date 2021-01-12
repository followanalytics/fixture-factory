package br.com.six2six.fixturefactory;

import static java.lang.String.format;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.BeanWithPlaceholder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BeanWithPlaceholderTest {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }
    
    @Test
    public void shouldSubstituteOnePlaceholderViaConstructor() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Immutable.class).gimme("one-placeholder");
        Assert.assertEquals(result.getAttrOne(), result.getAttrThree());
    }
    
    @Test
    public void shouldSubstituteOnePlaceholderViaSetter() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Mutable.class).gimme("one-placeholder");
        Assert.assertEquals(result.getAttrOne(), result.getAttrThree());
    }    
    
    @Test
    public void shouldSubstituteTwoPlaceholdersViaConstructor() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Immutable.class).gimme("two-placeholders");
        Assert.assertEquals(result.getAttrThree(), format("%s %s", result.getAttrOne(), result.getAttrTwo()));
    }
    
    @Test
    public void shouldSubstituteTwoPlaceholdersViaSetters() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Mutable.class).gimme("two-placeholders");
        Assert.assertEquals(result.getAttrThree(), format("%s %s", result.getAttrOne(), result.getAttrTwo()));
    }   
    
    @Test
    public void shouldSubstituteTheSamePlaceholdersTwiceViaConstructor() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Immutable.class).gimme("same-placeholder-twice");
        Assert.assertEquals(result.getAttrThree(), format("%s %s", result.getAttrOne(), result.getAttrOne()));

    }
    
    @Test
    public void shouldSubstituteTheSamePlaceholdersTwiceViaSetters() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Mutable.class).gimme("same-placeholder-twice");
        Assert.assertEquals(result.getAttrThree(), format("%s %s", result.getAttrOne(), result.getAttrOne()));
    }     
}
