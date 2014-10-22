/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication32;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author user
 */
public class MyIntegerGenerator implements MyItemGenerator<Integer> {

    AtomicInteger integer;

    public MyIntegerGenerator(int i) {
        integer = new AtomicInteger(i);
    }

    @Override
    public Integer generate() {
        return integer.incrementAndGet();
    }

}
