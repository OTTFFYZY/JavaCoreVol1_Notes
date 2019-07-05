public class GenericReflectionAPI {
    public static void main(String[] args) {
        // java.lang.Class<T>
        // TypeVariable[] getTypeParameters()
        // 如果类型是泛型类型 获得泛型类型变量，否则返回0长数组

        // Type getGenericSuperclass()
        // 返回泛型的超类类型，如果是 Object 或不是一个类类型（class type） 则返回 null

        // Type[] getGenericInterfaces()
        // 返回这个类型的接口的泛型类型，如果没有实现接口返回 0 长数组

        // java.lang.reflect.Method
        // TypeVarible[] getTypeParameters()
        // 方法的泛型类型变量，非泛型方法返回 0 长数组

        // Type getGenericReturnType()
        // 获得返回类型

        // Type[] getGenericParameterTypes()
        // 方法被声明的泛型参数类型，没有参数则返回 0 长数组

        // java.lang.reflect.TypeVariable
        // String getName()
        // 获得类型变量的名字

        // Type[] getBounds()
        // 获得类型变量的子类限定，无限定则返回 0 长数组

        // java.lang.reflect.WildcardType
        // Type[] getUpperBounds()
        // 得到子类限定
        
        // Type[] getLowerBounds()
        // 得到超类限定

        // java.lang.reflect.ParameterizedType
        // Type getRawType()
        // 获得参数化类型的原始类型

        // Type[] getActualTypeArguments()
        // 获得参数化类型声明时所使用的类型参数

        // Type getOwnerType()
        // 如果是内部类型，则返回其外部类型，顶级类型返回 null

        // java.lang.reflect.GenericArrayType
        // Type getGenericComponentType()
        // 获得声明该数组乐行的泛型组件类型
    }
}