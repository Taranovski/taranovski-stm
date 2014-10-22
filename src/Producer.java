/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author user
 * @param <T>
 */
public class Producer<T> extends Thread {

    private final MySTMStack<T> buffer;
    private T item;
    private final MyItemGenerator<T> generator;
    private final Set<T> set;
    private int count;

    /**
     *
     * @param buffer
     * @param generator
     */
    public Producer(MySTMStack<T> buffer, MyItemGenerator<T> generator, int count) {
        this.buffer = buffer;
        this.generator = generator;
        this.set = new HashSet<>();
        this.count = count;
    }

    /**
     *
     */
    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            item = generator.generate();
            set.add(item);
            buffer.push(item);
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
