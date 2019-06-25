public class ProxyAPI {
    public static void main(String[] args) {
        // java.lang.reflect.InvocationHandler
        // Object invoke(Object proxy, Mehtod method, Object[] args)
        // 定义了代理对象调用方法时希望执行的操作
        
        // java.lang.reflect.Proxy
        // static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces)
        // 返回实现指定接口的代理类

        // static Object newProxyInstance(ClassLoader loader, Class<?>[] interface, InvocationHandler handler)
        // 构造实现指定接口的代理类的一个实例
        // 所有方法都会调用给定处理器对象的 invoke 方法

        // static boolean isProxyClass(Class<?> cl)
        // cl 是代理类则返回 true
    }
}