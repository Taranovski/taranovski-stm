/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashSet;
import java.util.Set;
import javaapplication32.Consumer;
import javaapplication32.MyIntegerGenerator;
import javaapplication32.MySTMStack;
import javaapplication32.Producer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author user
 */
public class STMTest {

    public STMTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//     TODO add test methods here.
//     The methods must be annotated with annotation @Test. For example:
    @Test
    public void stmTestOneProdOneCons() throws InterruptedException {
        MySTMStack<Integer> mySTMStack = new MySTMStack<>();
        MyIntegerGenerator myIntegerGenerator = new MyIntegerGenerator();
        
        int count = 1000;
        
        Producer<Integer> producer = new Producer<>(mySTMStack, myIntegerGenerator, count);
        Consumer<Integer> consumer = new Consumer<>(mySTMStack, count);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        Set<Integer> producted = new HashSet<>();
        Set<Integer> consumed = new HashSet<>();

        producted.addAll(producer.getSet());
        consumed.addAll(consumer.getSet());
        
        System.out.println(producted);
        System.out.println("");
        System.out.println(consumed);
        assertTrue(consumed.containsAll(producted));
    }
}
