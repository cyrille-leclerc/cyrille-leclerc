package cyrille.util;

import java.util.Collection;

public class Assert {

    protected static Object[] concatArrayWithVarArgs(Object[] argsAsArray, Object... argsAsVarArgs) {
        Object[] messageFormatArgs = new Object[argsAsArray.length + argsAsVarArgs.length];
        System.arraycopy(argsAsArray, 0, messageFormatArgs, 0, argsAsArray.length);
        System.arraycopy(argsAsVarArgs, 0, messageFormatArgs, argsAsArray.length, argsAsVarArgs.length);
        return messageFormatArgs;
    }

    public static void isEmpty(Collection<?> collection, String messageFormat, Object... args) throws IllegalArgumentException {

        if (collection == null || collection.isEmpty()) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, collection);
        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);
    }

    public static void isEmpty(String string, String messageFormat, Object... args) throws IllegalArgumentException {

        if (string == null || string.length() == 0) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, string);
        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);
    }

    public static void isEqual(Object value1, Object value2, String messageFormat, Object... args) throws IllegalArgumentException {
        if (value1 == value2) {
            return;
        }

        if (value1 != null && value1.equals(value2)) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, value1, value2);
        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);
    }

    public static void isFalse(boolean expression, String messageFormat, Object... args) throws IllegalArgumentException {
        if (expression == false) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, expression);
        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);
    }

    public static void isNotEmpty(Collection<?> collection, String messageFormat, Object... args) throws IllegalArgumentException {

        if (collection != null && collection.isEmpty() == false) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, collection);
        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);

    }

    public static void isNotEmpty(String string, String messageFormat, Object... args) throws IllegalArgumentException {
        if (string != null || string.length() >= 0) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, string);
        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);
    }

    public static void isNotEqual(Object value1, Object value2, String messageFormat, Object... args) throws IllegalArgumentException {

        if (value1 == value2 || value1.equals(value2)) {

            Object[] messageFormatArgs = concatArrayWithVarArgs(args, value1, value2);

            String message = String.format(messageFormat, messageFormatArgs);
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, String messageFormat, Object... args) throws IllegalArgumentException {
        if (expression == true) {
            return;
        }
        Object[] messageFormatArgs = concatArrayWithVarArgs(args, expression);

        String message = String.format(messageFormat, messageFormatArgs);
        throw new IllegalArgumentException(message);
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String messageFormat, Object... args) {
        if (superType == null) {
            throw new IllegalArgumentException("Type to check against must not be null");
        }

        if (subType == null || !superType.isAssignableFrom(subType)) {

            Object[] messageFormatArgs = concatArrayWithVarArgs(args, superType, subType);

            String message = String.format(messageFormat, messageFormatArgs);

            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "Expected to be assignable to %s but was not: %s");
    }
}
