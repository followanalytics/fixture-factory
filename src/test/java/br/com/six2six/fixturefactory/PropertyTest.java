package br.com.six2six.fixturefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.base.Range;
import br.com.six2six.fixturefactory.function.impl.IdentityFunction;
import br.com.six2six.fixturefactory.function.impl.RandomFunction;
import org.junit.Assert;
import org.junit.Test;

public class PropertyTest {

    @Test
    public void shoudNotAllowNullName() {
        try {
            new Property(null, null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().contains("name"));
        }
    }
    
    @Test
    public void shoudNotAllowNullFunction() {
        try {
            new Property("attr", null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().contains("function"));
        }
    }
    
    @Test
    public void shouldReturnValueFromIdentityFunction() {
        String value = "some name";
        Property property = new Property("attr", new IdentityFunction(value));
        assertEquals(value, property.getValue());
    }

    @Test
    public void shouldReturnValue() {
        String value = "some name";
        Property property = new Property("attr", value);
        assertEquals(value, property.getValue());
    }

    @Test
    public void shouldReturnNullFromIdentityFunction() {
        Property property = new Property("attr", new IdentityFunction(null));
        assertNull(property.getValue());
    }
    
    @Test
    public void shouldReturnNull() {
        Property property = new Property("attr", (Object) null);
        assertNull(property.getValue());
    }
    
    @Test
    public void shouldGenerateAValue() {
        Long start = 1L;
        Long end = 10L;
        Property property = new Property("someNumber", new RandomFunction(Long.class, new Range(start, end)));
        Long value = (Long) property.getValue();  
        assertTrue(start <= value && value <= end);
    }
}
