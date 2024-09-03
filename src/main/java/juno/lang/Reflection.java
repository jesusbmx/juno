package juno.lang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Reflection {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP;

    static {
        PRIMITIVE_WRAPPER_MAP = new HashMap<Class<?>, Class<?>>(18);
        PRIMITIVE_WRAPPER_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.class, short.class);
        PRIMITIVE_WRAPPER_MAP.put(Void.class, void.class);
    }

    public static Class<?> getPrimitiveTypeForWrapper(Class wrapperClass) {
        return PRIMITIVE_WRAPPER_MAP.get(wrapperClass);
    }

    public static boolean isWrapperType(Class clazz) {
        return PRIMITIVE_WRAPPER_MAP.containsKey(clazz);
    }

    public static boolean isWrapperType(Object source) {
        return isWrapperType(source.getClass());
    }

    public static Class<?>[] getParameterTypes(Object... params) {
        Class<?>[] types = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            Class<?> type = params[i].getClass();
            Class<?> primitiveType = getPrimitiveTypeForWrapper(type);
            types[i] = primitiveType == null ? type : primitiveType;
        }
        return types;
    }

    /**
     * Invoca un método de instancia
     *
     * @param <V>
     * @param obj
     * @param method
     * @param params
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <V> V invokeInstanceMethod(
            final Object obj,
            final String method,
            final Object... params
    ) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?>[] types = getParameterTypes(params);
        final Method declaredMethod = obj.getClass()
                .getDeclaredMethod(method, types);

        return (V) declaredMethod.invoke(obj, params);
    }

    /**
     * Invoca un método estático
     *
     * @param <V>
     * @param classOf
     * @param method
     * @param params
     * @return
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static <V> V invokeStaticMethod(
            final Class classOf,
            final String method,
            final Object... params
    ) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?>[] types = getParameterTypes(params);
        final Method declaredMethod = classOf
                .getDeclaredMethod(method, types);

        return (V) declaredMethod.invoke(null, params);
    }

    /**
     * Obtiene un método declarado en una clase específica
     *
     * @param classOf Desktop
     * @param name addAppEventListener
     * @param parameterTypes java.awt.desktop.SystemEventListener
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public static Method getDeclaredMethod(Class<?> classOf, String name, String... parameterTypes)
            throws ClassNotFoundException, NoSuchMethodException {

        Class<?>[] cParameterTypes = new Class<?>[parameterTypes.length];
        for (int i = 0; i < cParameterTypes.length; i++) {
            cParameterTypes[i] = Class.forName(parameterTypes[i]);
        }

        return classOf.getDeclaredMethod(name, cParameterTypes);
    }

    public static Object newInstance(Class<?> classOf, InvocationHandler handler) {
        return Proxy.newProxyInstance(
                classOf.getClassLoader(),
                new Class<?>[]{classOf},
                handler);
    }

    public static Object newInstance(String className, InvocationHandler handler)
            throws ClassNotFoundException {

        return newInstance(Class.forName(className), handler);
    }

}
