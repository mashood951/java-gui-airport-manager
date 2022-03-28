public class LinkedQueue implements QueueInterface {
    private class Node {
        Object data;
        Node next;
    }

    private Node front, rear;

    LinkedQueue() {
        front = rear = null;
    }

    public void enqueue(Object data) {
        Node temp = new Node();
        temp.data = data;
        temp.next = null;
        if (isEmpty()) {
            front = temp;
            rear = temp;
        }
        else {
            rear.next = temp;
            rear = temp;
        }
    }

    public Object dequeue() {
        Object data = front.data;
        front = front.next;
        if (isEmpty()) {
            rear = null;
        }
        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }
}