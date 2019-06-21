import java.lang.reflect.*;
public class ClassReflectAPI {
    public static void main(String[] args) {
        // java.lang.Class
        // Field[] getFields()
        // Field[] getDeclaredFields()
        // getFields 方法包含了超类公有域
        
        // Field getField(String name)
        // Field getDeclaredField(String name)

        // Method[] getMethods()
        // Method[] getDeclaredMethods()

        // Constructor[] getConstructors()
        // Constructor[] getDeclaredConstructors()


        // java.lang.reflect.Field
        // java.lang.reflect.Method
        // java.lang.reflect.Constructor
        // Class getDecleringClass()
        // 返回一个用于描述类中定义的构造器、方法或域的 Class

        // Class[] getExceptionTypes() (Method, Constructor)
        // 异常类型的 Class 类数组

        // int getModifiers()

        // String getName()

        // Class[] getParameterTypes() (Method, Constructor)
        // 参数类型

        // Class getReturnType() (Method)
        // 返回类型

        // Object get(Object obj) (Field)
        // 返回 obj 对象中 Field 对象表示的值域

        // void set(Object obj, Object newValue) (Field)
        // 设置 obj 对象中 Field 对象表示的值域的值为 newValue

        // public Object invoke(Object implicitParameter, Object... explicitParamenters)
        // (Method)


        // java.lang.reflect.Modifier
        // static String toString(int modifiers)

        // static boolean isAbstract(int modifiers)
        // static boolean isFinal(int modifiers)
        // static boolean isInterface(int modifiers)
        // static boolean isNative(int modifiers)
        // static boolean isPrivate(int modifiers)
        // static boolean isProtected(int modifiers)
        // static boolean isPublic(int modifiers)
        // static boolean isStatic(int modifiers)
        // static boolean isStrict(int modifiers)
        // static boolean isSynchronized(int modifiers)
        // static boolean isVolatile(int modifiers)


        // java.lang.reflect.AccessibleObject
        // void setAccessible(Boolean flag)

        // boolean isAccessible()

        // static void setAccessible(AccessibleObject[] array, boolean flag)


        // java.lang.reflect.Array
        // static Object get(Object array, int index)
        // static xxx getxxx(Object array, int index)
        // xxx 是基本类型，返回对应位置的内容

        // static void set(Object array, int index, Object newValue)
        // static void setxxx(Object array, int index, xxx newValue)

        // static int getLength(Object array)
        
        // static Object newInstance(Class componentType, int length)
        // static Object newInstance(Class componentType, int[] lengths)
        // 返回给定类型，给定维数的数组
    }
}