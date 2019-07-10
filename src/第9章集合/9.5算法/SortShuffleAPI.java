public class SortShuffleAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <T extends Comparable<? super T>> void sort(List<T> elements)
        // 稳定排序 nlogn 复杂度

        // static void shuffle(List<?> elements)
        // static void shuffle(List<?> elements, Random r)
        // 随机打乱元素，时间复杂度 n*a(n), a(n)是元素平均访问时间

        
        // java.util.List<E>
        // default void sort(Comparator<? super T> comparator)
        // 用给定比较器给列表排序

        
        // java.util.Comparator<T>
        // static <T extends Comparable<? super T>> Comparator<T> reverseOrder()
        // 生成一个比较器，逆置 Comparable 的顺序

        // default Compartor<T> reversed()
        // 逆置这个比较器的顺序
    }
}