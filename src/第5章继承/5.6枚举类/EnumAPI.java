public class EnumAPI {
    public static void main(String[] args) {
        // java.lang.Enum <E>
        // static Enum valueOf(Class enumClass, String name)
        // 返回指定 name 给定 enumClass 的枚举常量

        // static E[] values()
        // 返回全部枚举值的数组

        // String toString()
        // 返回枚举常量名，去静态 valueOf 互逆

        // int ordinal
        // 枚举常量声明时的位置，从 0 开始计数

        // int compareTo(E other)
        // 枚举常量出现在 other 之前返回负值，this == other 返回 0，否则返回正值
    }
}