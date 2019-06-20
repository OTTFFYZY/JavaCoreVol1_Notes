public class HashCodeAPI {
    public static void main(String[] args) {
        // java.util.Object
        // int hashCode()

        // java.util.Objects
        // static int hash(Object... objects)
        // 返回一个散列码 由所有对象的散列码组合而成（为所有对象调用下面的hashCode）
        
        // java.util.Objects
        // static int hashCode(Object a)
        // 如果 a 是 null 返回 0 否则返回 a.hashCode()

        // java.lang.(Integer|Long|Short|Byte|Double|Float|Character|Boolean)
        // static int hashCode((int|long|short|byte|double|float|char|boolean) value)
        // 返回给定基本类型值的散列码

        // java.util.Arrays
        // static int hashCode(type[] a)
        // 返回 a 数组的散列码
    }
}