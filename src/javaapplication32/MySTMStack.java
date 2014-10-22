/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication32;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnRef;

/**
 *
 * @author user
 * @param <T>
 */
public class MySTMStack<T> {

    private TxnInteger size = StmUtils.newTxnInteger(0);
    private TxnRef<Node<T>> head = null;

    private static final class Node<T> {

        private final T item;
        private final Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        public T getItem() {
            return item;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    public MySTMStack() {

    }

    public void push(final T item) {

        head.atomicCompareAndSet(head, StmUtils.newTxnRef(new Node<T>(item, head.get())));
        size++;

    }

    public T pop() {

        if (size != 0 & head != null) {
            T item = head.getItem();
            head = head.getNext();
            size--;
            return item;
        } else {
            return null;
        }
    }

}
