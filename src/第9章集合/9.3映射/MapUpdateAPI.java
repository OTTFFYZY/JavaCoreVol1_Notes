public class MapUpdateAPI {
    public static void main(String[] args) {
        // java.util.Map<K, V>
        // default V merge(K key, V value, 
        //     BiFunction<? super V, ? super V, ? extends V> remappingFunction)
        // 如果 key 与非 null 值关联，将函数应用到 v 和 value，将 key 与结果关联
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default V compute(K key, 
        //     BiFunction<? super K, ? super V, ? extends V> remappingFunction)
        // 将函数作用于 key 和 v 结果绑定到 key
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default V computeIfPresent(K key, 
        //     BiFunction<? super K, ? super V, ? extends V> remappingFunction)
        // 如果 key 和一个非 null 值绑定，将函数作用于 key 和 v 结果绑定到 key
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default V computeIfAbsent(K key, 
        //     Function<? super K, ? extends V> mappingFunction)
        // 将函数应用到 key 除非 key 已经与非 null 值绑定，结果绑定到 key
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)
        // 所有项上应用函数，将键与非 null 结果关联，null 结果删除对应项
    }
}