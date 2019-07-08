public class QueueAPI {
    public static void main(String[] args) {
        // java.util.Queue
        // boolean add(E element)
        // boolean offer(E element)
        // 尾部添加元素，并返回 true
        // 不能添加时 add 抛出 IllegalStateException 异常，offer 返回 false

        // E remove()
        // E poll()
        // 删除并返回队首第一个元素
        // 队列为空时，remove 抛出 NoSuchElementException 异常，poll 返回 null

        // E element()
        // E peek()
        // 返回队首第一个元素
        // 队列为空时，element 抛出 NoSuchElementException 异常，peek 返回 null


        // java.util.Deque<E>
        // void addFirst(E element)
        // void addLast(E element)
        // boolean offerFirst(E element)
        // boolean offerLast(E element)

        // E removeFirst()
        // E removeLast()
        // E pollFirst()
        // E pollLast()

        // E getFirst()
        // E getLast()
        // E peekFirst()
        // E peekLast()

        // java.util.ArrayDeque<E>
        // ArrayDeque()
        // ArrayDeque(int initialCapacity)
    }
}