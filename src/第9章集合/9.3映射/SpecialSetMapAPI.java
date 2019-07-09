public class SpecialSetMapAPI {
    public static void main(String[] args) {
        // java.util.WeakHashMap<K, V>
        // WeakHashMap()
        // WeakHashMap(int initialCapacity)
        // WeakHashMap(int initialCapacity, float loadFactor)


        // java.util.LinkedHashSet<E>
        // LinkedHashSet()
        // LinkedHashSet(int initialCapacity)
        // linkedHashSet(int initialCapacity, float loadFactor)


        // java.util.LinkedHashMap<K, V>
        // LinkedHashMap()
        // LinkedHashMap(int initialCapacity)
        
        // LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
        // accessOrder true 表示访问顺序，false 表示插入顺序
        
        // prodtected boolean removeEldestEntry(Map.Entry<K, V> eldest)
        // eldest 是预期要删除的参数
        // 默认是总返回 false


        // java.util.EnumSet<E extends Enum<E>>
        // static <E extends Enum<E>> EnumSet<E> allOf(Class<E> enumType)
        // static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> enumType)
        // static <E extends Enum<E>> EnumSet<E> range(E from, E to)
        // static <E extends Enum<E>> EnumSet<E> of(E value)
        // static <E extends Enum<E>> EnumSet<E> of(E value, E... values)


        // java.util.EnumMap<K extends Enum<K>>
        // EnumMap(Class<K> keyType)


        // java.util.IdentityHashMap<K, V>
        // IdentityHashMap()
        // IdentityHashMap(int expectedMaxSize)


        // java.lang.System
        // static int identityHashCode(Object obj)
    }
}