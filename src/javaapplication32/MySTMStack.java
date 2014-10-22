/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication32;

import java.util.concurrent.Callable;
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
    private TxnRef<Node<T>> head = StmUtils.newTxnRef(null);

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
        StmUtils.atomic(new Runnable() {
            @Override
            public void run() {
                head.set(new Node<>(item, head.get()));
                size.increment();
            }
        });
    }

    public T pop() {
        return StmUtils.atomic(new Callable<T>() {
            @Override
            public T call() throws Exception {
                if (size.get() != 0 & head.get() != null) {
                    Node<T> node = head.get();
                    T item = node.getItem();
                    head.set(node.getNext());
                    size.decrement();
                    return item;
                } else {
                    return null;
                }
            }
        });

    }

}
