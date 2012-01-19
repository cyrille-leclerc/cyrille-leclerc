package cyrille.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class MyBeanUtilsTest extends TestCase {

    public void testIsSimpleType() {
        assertTrue("String", MyBeanUtils.isSimpleType("a string"));
        assertTrue("date", MyBeanUtils.isSimpleType(new Date()));
        assertTrue("Character", MyBeanUtils.isSimpleType(new Character('a')));
        assertTrue("integer", MyBeanUtils.isSimpleType(new Integer(1)));
    }

    public void testIsEqual() throws Exception {
        List<String> listOfStrings = new ArrayList<String>();
        listOfStrings.add("a 1st string (in list");
        listOfStrings.add("a 2nd string (in list");

        List<Child> listOfChildren = new ArrayList<Child>();

        listOfChildren.add(new Child("a 4th child (in list)", listOfStrings));
        listOfChildren.add(new Child("a 5th child (in list)", listOfStrings));

        Parent firstParent = new Parent("a-parent", new Date(), Integer.valueOf(1), 2, new StringBuffer("a string buffer"), listOfChildren,
                new Child("a child", listOfStrings), new Child[] { new Child("a 2nd child (in array)", listOfStrings),
                        new Child("a third child (in array)", listOfStrings) });
        Parent secondParent = new Parent("a-parent", new Date(), Integer.valueOf(1), 2, new StringBuffer("a string buffer"),
                listOfChildren, new Child("a child", listOfStrings), new Child[] { new Child("a 2nd child (in array)", listOfStrings),
                        new Child("a third child (in array)", listOfStrings) });

        MyBeanUtils.assertRecursiveEquals(firstParent, secondParent);

    }

    public void testIsNotEqualSimpleString() throws Exception {
        try {
            MyBeanUtils.assertRecursiveEquals("a 5th child (in list)", "a 5th child (in list) thatIsDifferent");
        } catch (AssertionFailedError e) {
            e.printStackTrace();
            return;
        }
        fail();
    }

    public void testIsNotEqualSimpleBean() throws Exception {
        List<String> listOfStrings = new ArrayList<String>();
        listOfStrings.add("a 1st string (in list");
        listOfStrings.add("a 2nd string (in list");
        try {
            MyBeanUtils.assertRecursiveEquals(new Child("a 5th child (in list)", listOfStrings), new Child(
                    "a 5th child (in list) thatIsDifferent", listOfStrings));
        } catch (AssertionFailedError e) {
            e.printStackTrace();
            return;
        }
        fail();
    }

    public void testIsNotEqual() throws Exception {
        List<String> listOfStrings = new ArrayList<String>();
        listOfStrings.add("a 1st string (in list");
        listOfStrings.add("a 2nd string (in list");

        List<Child> firstListOfChildren = new ArrayList<Child>();
        firstListOfChildren.add(new Child("a 4th child (in list)", listOfStrings));
        firstListOfChildren.add(new Child("a 5th child (in list)", listOfStrings));

        List<Child> secondListOfChildren = new ArrayList<Child>();
        secondListOfChildren.add(new Child("a 4th child (in list)", listOfStrings));
        secondListOfChildren.add(new Child("a 5th child (in list) thatIsDifferent", listOfStrings));

        Parent firstParent = new Parent("a-parent", new Date(), Integer.valueOf(1), 2, new StringBuffer("a string buffer"),
                firstListOfChildren, new Child("a child", listOfStrings), new Child[] { new Child("a 2nd child (in array)", listOfStrings),
                        new Child("a third child (in array)", listOfStrings) });
        Parent secondParent = new Parent("a-parent", new Date(), Integer.valueOf(1), 2, new StringBuffer("a string buffer"),
                secondListOfChildren, new Child("a child", listOfStrings), new Child[] {
                        new Child("a 2nd child (in array)", listOfStrings), new Child("a third child (in array)", listOfStrings) });

        try {
            MyBeanUtils.assertRecursiveEquals(firstParent, secondParent);
        } catch (AssertionFailedError e) {
            e.printStackTrace();
            return;
        }
        fail();

    }

    public static class Parent {

        String aString;

        Date aDate;

        Integer anInteger;

        int aPrimitiveInt;

        StringBuffer aStringBuffer;

        List aListOfChildren = new ArrayList();

        Child aChild;

        Child[] anArrayOfChildren;

        public Parent(String string, Date date, Integer anInteger, int primitiveInt, StringBuffer stringBuffer, List listOfChildren,
                Child child, Child[] anArrayOfChildren) {
            super();
            this.aString = string;
            this.aDate = date;
            this.anInteger = anInteger;
            this.aPrimitiveInt = primitiveInt;
            this.aStringBuffer = stringBuffer;
            this.aListOfChildren = listOfChildren;
            this.aChild = child;
            this.anArrayOfChildren = anArrayOfChildren;
        }

        public Child getAChild() {
            return this.aChild;
        }

        public void setAChild(Child child) {
            this.aChild = child;
        }

        public Date getADate() {
            return this.aDate;
        }

        public void setADate(Date date) {
            this.aDate = date;
        }

        public List getAListOfChildren() {
            return this.aListOfChildren;
        }

        public void setAListOfChildren(List listOfChildren) {
            this.aListOfChildren = listOfChildren;
        }

        public Child[] getAnArrayOfChildren() {
            return this.anArrayOfChildren;
        }

        public void setAnArrayOfChildren(Child[] anArrayOfChildren) {
            this.anArrayOfChildren = anArrayOfChildren;
        }

        public Integer getAnInteger() {
            return this.anInteger;
        }

        public void setAnInteger(Integer anInteger) {
            this.anInteger = anInteger;
        }

        public int getAPrimitiveInt() {
            return this.aPrimitiveInt;
        }

        public void setAPrimitiveInt(int primitiveInt) {
            this.aPrimitiveInt = primitiveInt;
        }

        public String getAString() {
            return this.aString;
        }

        public void setAString(String string) {
            this.aString = string;
        }

        public StringBuffer getAStringBuffer() {
            return this.aStringBuffer;
        }

        public void setAStringBuffer(StringBuffer stringBuffer) {
            this.aStringBuffer = stringBuffer;
        }

    }

    public static class Child {
        String aString;

        List aListOfStrings = new ArrayList();

        public Child(String string, List listOfStrings) {
            super();
            this.aString = string;
            this.aListOfStrings = listOfStrings;
        }

        public List getAListOfStrings() {
            return this.aListOfStrings;
        }

        public void setAListOfStrings(List listOfStrings) {
            this.aListOfStrings = listOfStrings;
        }

        public String getAString() {
            return this.aString;
        }

        public void setAString(String string) {
            this.aString = string;
        }

    }
}
