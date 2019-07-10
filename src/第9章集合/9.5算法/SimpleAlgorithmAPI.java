public class SimpleAlgorithmAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <T extends Comparable<? super T>> T min(Collection<T> elements)
        // static <T extends Comparable<? super T>> T max(Collection<T> elements)
        // static <T> T min(Collection<T> elements, Comparator<? super T> c)
        // static <T> T max(Collection<T> elements, Comparator<? super T> c)
        // 返回集合最小，最大的元素

        // static <T> void copy(List<? super T> to, List<T> from)
        // 复制，目标位置长度至少和原列表一样

        // static <T> void fill(List<? super T> list, T value)
        // list 所有元素都用 value 填充
        
        // static <T> boolean addAll(Collection<? super T> c, T... values)
        // 所有 values 添加到 c 如果 c 改变了返回 true

        // static <T> boolean replaceAll(List<T> list, T oldValue, T newValue)

        // static int indexOfSubList(List<?> list, List<?> s)
        // static int lastIndexOfSubList(List<?> list, List<?> s)
        // 返回第一个或者最后一个等于子列表的索引
        // 没有则返回 -1

        // static void swap(List<?> list, int i, int j)
        // 交换元素

        // static void reverse(List<?> list)

        // static void rotate(List<?> list, int d)
        // 将 i 条目移动到 (i + d) % list.size() 的位置

        // static int frequency(Collection<?> c, Object o)
        // c 中与 o 数量相同的元素个数

        // boolean disjoint(Collection<?> c1, Collection<?> c2)
        // 两个集合没有公共元素 返回 true


        // java.util.Collection<T>
        // default boolean removeIf(Predicate<? super E> filter)
        // 删除所有 filter 返回 true 的元素
        
        // default void replaceAll(UnaryOperator<E> op)
        // 列表中所有元素应用 op 操作
    }
}