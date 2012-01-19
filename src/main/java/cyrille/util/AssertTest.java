package cyrille.util;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class AssertTest {

    @Test
    public void testIsTrueSuccess() {
        Assert.isTrue(true, "my message");
    }

    @Test
    public void testIsTrueFailure() {
        try {
            Assert.isTrue(false, "value is %s", "not supported");
            throw new AssertionError();
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("value is not supported", actual);
        }
    }

    @Test
    public void testIsFalseSuccess() {
        Assert.isFalse(false, "my message");
    }

    @Test
    public void testIsFalseFailure() {
        try {
            Assert.isFalse(true, "value is %s: '%s'", "not supported");
            throw new AssertionError();
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("value is not supported: 'true'", actual);
        }
    }

    @Test
    public void testIsEqualStringSuccess() {
        Assert.isEqual("a", "a", "my message");
    }

    @Test
    public void testIsEqualStringFailure() {
        try {
            Assert.isEqual("a", "b", "strings are different %s %s");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("strings are different a b", actual);
        }
    }

    @Test
    public void testIsNotEqualSuccess() {
        Assert.isNotEqual("a", "b", "my message");
    }

    @Test
    public void estIsNotEqualFailure() {
        try {
            Assert.isNotEqual("a", "a", "strings are equal %s %s");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("strings are equal a a", actual);
        }
    }

    @Test
    public void testIsEmptyCollectionSuccess() {
        Assert.isEmpty(Collections.EMPTY_LIST, "my message");
    }

    @Test
    public void testIsEmptyCollectionSuccessWithNull() {
        Assert.isEmpty((Collection<?>) null, "my message");
    }

    @Test
    public void testIsEmptyCollectionFailure() {
        try {
            List<String> list = new ArrayList<String>() {
                private static final long serialVersionUID = 1L;

                @Override
                public String toString() {
                    return "my-to-string";
                }
            };
            list.add("a");
            Assert.isEmpty(list, "collection is %s: %s", "not empty");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "collection is not empty: my-to-string";
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testIsNotEmptyCollectionSuccess() {
        Assert.isNotEmpty(Arrays.asList("a"), "my message");
    }

    @Test
    public void testIsNotEmptyCollectionFailure() {
        try {
            Assert.isNotEmpty(Collections.EMPTY_LIST, "collection is empty");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "collection is empty";
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testIsNotEmptyCollectionFailureWithNull() {
        try {
            Assert.isNotEmpty((Collection<?>) null, "collection is empty %s '%s'", "or null");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "collection is empty or null 'null'";
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testIsEmptyStringSuccess() {
        Assert.isEmpty("", "my message");
    }

    @Test
    public void testIsEmptyStringSuccessWithNull() {
        Assert.isEmpty((String) null, "my message");
    }

    @Test
    public void testIsEmptyStringFailure() {
        try {
            Assert.isEmpty("foo", "string %s not empty :'%s'", "is");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("string is not empty :'foo'", actual);
        }
    }

    @Test
    public void testIsNotEmptyStringSuccess() {
        Assert.isNotEmpty("bla bla", "my message");
    }

    @Test
    public void testIsNotEmptyStringFailure() {
        try {
            Assert.isNotEmpty("", "string %s", "is empty");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("strings is empty", actual);
        }
    }

    @Test
    public void testIsNotEmptyStringFailureWithNull() {
        try {
            Assert.isNotEmpty("", "string %s", "is empty");
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            assertEquals("strings is empty", actual);
        }
    }

}
