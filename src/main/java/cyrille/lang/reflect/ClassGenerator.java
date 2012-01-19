/*
 * Created on Sep 20, 2004
 */
package cyrille.lang.reflect;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class ClassGenerator {

    private static final Log log = LogFactory.getLog(ClassGenerator.class);

    /**
     * 
     */
    public ClassGenerator() {
        super();
    }

    public void generate(Class clazz, PrintWriter out) {
        out.println(clazz.getPackage().getName() + ";");
        out.println();
        out.println("public class " + clazz.getName() + "Impl {");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Class[] parameterTypes;
            Class parameterType;
            String parameterName;
            generateMethod(method, out);

        }
        out.println("}");
    }

    /**
     * @param method
     * @param out
     */
    private void generateMethod(Method method, PrintWriter out) {
        out.print("\tpublic " + method.getReturnType().getName() + " " + method.getName() + "(");
        Class[] parameterTypes = method.getParameterTypes();
        for (int j = 0; j < parameterTypes.length; j++) {
            if (j > 0) {
                out.print(", ");
            }
            Class parameterType = parameterTypes[j];
            String parameterName;
            generateParameter(parameterType, j, out);
        }
        out.println(") {");

        out.println("\t}");
    }

    /**
     * @param parameterType
     * @param j
     * @param out
     */
    private void generateParameter(Class parameterType, int j, PrintWriter out) {
        log.debug(parameterType);
        String parameterName;
        if (parameterType.getName().indexOf('.') == -1) {
            parameterName = parameterType.getName();
        } else {
            parameterName = StringUtils.substringAfterLast(parameterType.getName(), ".");
            parameterName = StringUtils.lowerCase(parameterName.substring(0, 1)) + parameterName.substring(1);
        }
        if (parameterType.isArray()) {
            out.print(parameterType.getName());
        } else {
            out.print(parameterType.getName());
        }
        out.print(" " + parameterName + j);
    }

}