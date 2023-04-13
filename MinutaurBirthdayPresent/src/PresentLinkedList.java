package src;

import java.util.concurrent.locks.ReentrantLock;

public class PresentLinkedList {
    private PresentNode head;
    private final ReentrantLock lock = new ReentrantLock();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        PresentNode current = head;
        while (current != null) {
            sb.append(current.getPresent().getTag());
            if (current.getNext() != null) {
                sb.append(" -> ");
            }
            current = current.getNext();
        }
        return sb.toString();
    }

    public void insert(Present present) {
        lock.lock();
        try {
            PresentNode newNode = new PresentNode(present);
            if (head == null || head.getPresent().getTag() > present.getTag()) {
                newNode.setNext(head);
                head = newNode;
            } else {
                PresentNode current = head;
                while (current.getNext() != null && current.getNext().getPresent().getTag() < present.getTag()) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
        } finally {
            lock.unlock();
        }
    }

    public Present remove(int tag) {
        lock.lock();
        try {
            if (head == null) {
                return null;
            }
            if (head.getPresent().getTag() == tag) {
                Present present = head.getPresent();
                head = head.getNext();
                return present;
            }

            PresentNode current = head;
            while (current.getNext() != null && current.getNext().getPresent().getTag() != tag) {
                current = current.getNext();
            }

            if (current.getNext() != null) {
                Present present = current.getNext().getPresent();
                current.setNext(current.getNext().getNext());
                return present;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(int tag) {
        lock.lock();
        try {
            PresentNode current = head;
            while (current != null) {
                if (current.getPresent().getTag() == tag) {
                    return true;
                }
                current = current.getNext();
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
