/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication32;

import java.util.HashSet;
import java.util.Set;
import org.multiverse.api.StmUtils;

/**
 *
 * @author user
 * @param <T>
 */
public class Consumer<T> extends Thread {

    private final MySTMStack<T> buffer;
    T item;
    private final Set<T> set;
    private int count;

    /**
     *
     * @param buffer
     */
    public Consumer(MySTMStack<T> buffer, int count) {
        this.buffer = buffer;
        this.set = new HashSet<>();
        this.count = count;
    }

    /**
     *
     */
    @Override
    public void run() {
        for (int i = 0; i < count; i++) {

            StmUtils.atomic(new Runnable() {
                @Override
                public void run() {
                    item = buffer.pop();
                    set.add(item);
                    
                }
            });

        }
    }

    /**
     *
     * @return
     */
    public Set<T> getSet() {
        return set;
    }

}