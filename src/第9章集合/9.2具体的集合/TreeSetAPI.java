public class TreeSetAPI {
    public static void main(String[] args) {
        // java.util.TreeSet<E> 
        // TreeSet()
        // TreeSet(Comparator<? super E> comparator)
        // TreeSet(Collection<? extends E> elements)
        // TreeSet(SortedSet<E> s)


        // java.util.SortedSet<E>
        // Comparator<? super E> comparator()
        // 返回对元素排序的比较器
        // 使用 Comparable 接口的 compareTo 则返回 null

        // E first()
        // E last()


        // java.util.NavigableSet<E>
        // E higher(E value)
        // 大于 value 的最小元素

        // E lower(E value)
        // 小于 value 的最大元素

        // E ceiling(E value)
        // 小于等于 value 的最大元素
        
        // E floor(E value)
        // 大于等于 value 的最小元素

        // E poolFirst()
        // E poolLast()
        // 删除并返回集中的最大（最小）元素，集为空时返回 null

        // Iterator<E> descendingIterator()
        // 递减顺序遍历的迭代器
    }
}