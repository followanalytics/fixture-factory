package br.com.six2six.fixturefactory.util;

import br.com.six2six.fixturefactory.Property;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class PropertySorterTest {

    @SuppressWarnings("serial")
    @Test
    public void shouldSortWithDependencies() {
        final Property firstName = new Property("firstName", "diego");
        final Property lastName = new Property("lastName", "domingues");
        final Property email = new Property("email", "${firstName}.${lastName}@gmail.com");
        final Property emailConfirm = new Property("emailConfirmation", "${email}");

        Set<Property> sortedProperties = new PropertySorter(new LinkedHashSet<Property>(){{
            add(emailConfirm);
            add(firstName);
            add(lastName);
            add(email);
        }}).sort();


        Assert.assertTrue(sortedProperties.contains(firstName));
        Assert.assertTrue(sortedProperties.contains(lastName));
        Assert.assertTrue(sortedProperties.contains(email));
        Assert.assertTrue(sortedProperties.contains(emailConfirm));
    }

    @SuppressWarnings("serial")
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereIsCyclicDependency()  {
        final Property name = new Property("name", "diego");
        final Property email = new Property("email", "${name_confirmation}");
        final Property emailConfirm = new Property("emailConfirmation", "${email}");

        new PropertySorter(new LinkedHashSet<Property>(){{
            add(emailConfirm);
            add(name);
            add(email);
        }}).sort();
    }
}
