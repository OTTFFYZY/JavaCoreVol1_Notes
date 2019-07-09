public class CollectionsAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <E> Collection unmodifiableCollection(Collection<E> c)
        // static <E> List unmodifiableList(List<E> c)
        // static <E> Set unmodifiableSet(Set<E> c)
        // static <E> SortedSet unmodifiableSortedSet(SortedSet<E> c)
        // static <E> SortedSet unmodifiableNavigableSet(NavigableSet<E> c)
        // static <K, V> Map unmodifiableMap(Map<K, V> c)
        // static <K, V> SortedMap unmodifiableSortedMap(SortedMap<K, V> c)
        // static <K, V> SortedMap unmodifiableNavigableMap(NavigableMap<K, V> c)
        // 构建视图，更改器方法抛出 UnsupportedOperationException

        // static <E> Collection synchronizedCollection(Collection<E> c)
        // static <E> List synchronizedList(List<E> c)
        // static <E> Set synchronizedSet(Set<E> c)
        // static <E> SortedSet synchronizedSortedSet(SortedSet<E> c)
        // static <E> SortedSet synchronizedNavigableSet(NavigableSet<E> c)
        // static <K, V> Map synchronizedMap(Map<K, V> c)
        // static <K, V> SortedMap synchronizedSortedMap(SortedMap<K, V> c)
        // static <K, V> SortedMap synchronizedNavigableMap(NavigableMap<K, V> c)
        // 构建视图，视图的方法同步

        // static <E> Collection checkedCollection(Collection<E> c, Class<E> elementType)
        // static <E> List checkedList(List<E> c, Class<E> elementType)
        // static <E> Set checkedSet(Set<E> c, Class<E> elementType)
        // static <E> SortedSet checkedSortedSet(SortedSet<E> c, Class<E> elementType)
        // static <E> SortedSet checkedNavigableSet(NavigableSet<E> c, Class<E> elementType)
        // static <K, V> Map checkedMap(Map<K, V> c, Class<K> keyType, Class<V> valueType)
        // static <K, V> SortedMap checkedSortedMap(SortedMap<K, V> c, Class<K> keyType, Class<V> valueType)
        // static <K, V> SortedMap checkedNavigableMap(NavigableMap<K, V> c, Class<K> keyType, Class<V> valueType)
        // static <E> Queue<E> checkedQueue(Queue<E> queue, Class<E> elementType)
        // 构建视图，插入错误类型的元素将抛出 ClassCastException

        // static <E> List<E> nCopies(int n, E value)
        // static <E> Set<E> singleton(E value)
        // static <E> List<E> singletonList(E value)
        // static <K, V> Map<K, V> singletonMap(K key, V value)
        
        // static <E> List<E> emptyList()
        // static <E> Set<E> emptySet()
        // static <E> SortedSet<E> emptySortedSet()
        // static <E> NavigableSet<E> emptyNavigableSet()
        // static <K, V> Map<K, V> emptyMap()
        // static <K, V> SortedMap<K, V> emptySortedMap()
        // static <K, V> NavigableMap<K,V> emptyNavigableMap()
        // static <T> Enumeration<T> emptyEnumeration()
        // static <T> Iterator<T> emptyIterator()
        // static <T> ListIterator<T> emptyListIterator()


        // java.util.Arrays
        // static <E> List<E> asList(E... array)


        // java.util.List<E>
        // List<E> subList(int firstIncluded, int firstExcluded)


        // java.util.SortedSet<E>
        // SortedList<E> subSet(E firstIncluded, E firstExcluded)
        // SortedList<E> headSet(E firstExcluded)
        // SortedList<E> tailSet(E firstIncluded)


        // java.util.NavigableSet<E>
        // NavigableSet<E> subSet(E from, boolean fromInclusive, E to, boolean toInclusive)
        // NavigableSet<E> headSet(E to, boolean toInclusive)
        // NavigableSet<E> tailSet(E from, boolean fromInclusive)


        // java.util.SortedMap<K, V>
        // SortedMap<K, V> subMap(K firstIncluded, K firstExcluded)
        // SortedMap<K, V> headMap(K firstExcluded)
        // SortedMap<K, V> tailMap(K firstIncluded)


        // java.util.NavigableMap<K, V>
        // NavigableSortedMap<K, V> subMap(K firstIncluded, K firstExcluded)
        // NavigableSortedMap<K, V> headMap(K firstExcluded)
        // NavigableSortedMap<K, V> tailMap(K firstIncluded)
    }
}