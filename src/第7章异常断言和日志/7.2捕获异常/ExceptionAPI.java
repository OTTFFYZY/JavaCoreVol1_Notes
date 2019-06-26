public class ExceptionAPI {
    public static void main(String[] args) {
        // java.lang.Throwable
        // Throwable(Throwable cause)
        // Throwable(String message, Throwable cause)
        
        // Throwable initCause(Throwable cause);
        // 设置原因，已经设置原因时会报异常
        // 返回 this

        // Throwable getCause()

        // StackTraceElement[] getStackTrace()

        // void addSuppressed(Throwable t)
        // Throwable[] getSuppressed()


        // java.lang.Exception
        // Exception(Throwable cause)
        // Exception(String message, Throwable cause)


        // java.lang.RuntimeException
        // RuntimeException(Throwable cause)
        // RuntimeException(String message, Throwable cause)


        // java.lang.StackTraceElement
        // String getFileName()
        // 源文件名

        // int getLineNumber()
        // 元素运行时对应的行号

        // String getClassName()
        // 对应类的完全限定名

        // String getMethodName()
        // 方法名，构造器对应 <init>，静态初始化器是 <clinit>
        // 无法区分同名的重载方法

        // boolean isNativeMethod()
        // 元素运行时在一个本地方法中返回 true

        // String toString()
        // 返回包含类名，方法名，文件名和行数的格式化字符串
    }
}