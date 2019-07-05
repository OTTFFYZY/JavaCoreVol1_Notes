public class ClassAPI {
    public static void main(String[] args) {
        // java.lang.Class<T>
        // T newInstance()
        // 用无参构造器构建一个新的 T 对象

        // T cast(Object obj)
        // obj 是 null 或可以转化成 T 的类型则返回 obj
        // 否则抛出 BadCastException 异常

        // T[] getEnumConstants()
        // 如果 T 是枚举类型（或是枚举值数组），返回所有值组成的数组

        // Class<? super T> getSuperclass()
        // 返回这个类的超类。如果 T 不是一个类或是 Object 类则返回 null

        // Constructor<T> getConstructor(Class... parameterTypes)
        // Constructor<T> getDeclaredConstructor(Class... parameterTypes)
        // 获得给定参数类型的构造器
    }
}