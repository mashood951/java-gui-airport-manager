public interface QueueInterface {
    public void enqueue(Object value);
    public Object dequeue();
    public boolean isEmpty();
}