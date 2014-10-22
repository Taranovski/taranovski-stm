/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashSet;
import java.util.Set;
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
        MyIntegerGenerator myIntegerGenerator = new MyIntegerGenerator(0);

        int count = 4000;

        Producer<Integer> producer = new Producer<>(mySTMStack, myIntegerGenerator, count);
        Consumer<Integer> consumer = new Consumer<>(mySTMStack, count * 2);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        Set<Integer> producted = new HashSet<>();
        Set<Integer> consumed = new HashSet<>();

        producted.addAll(producer.getSet());
        consumed.addAll(consumer.getSet());

        assertTrue(consumed.containsAll(producted));
    }

    @Test
    public void stmTestManyProdOneCons() throws InterruptedException {
        MySTMStack<Integer> mySTMStack = new MySTMStack<>();

        int count = 4000;
        int prodCount = 10;

        MyIntegerGenerator[] generators = new MyIntegerGenerator[prodCount];
        for (int i = 0; i < prodCount; i++) {
            generators[i] = new MyIntegerGenerator(i * count);
        }

        Producer[] producers = new Producer[prodCount];
        for (int i = 0; i < prodCount; i++) {
            producers[i] = new Producer<>(mySTMStack, generators[i], count);
        }

        for (int i = 0; i < prodCount; i++) {
            producers[i].start();
        }

        Consumer<Integer> consumer = new Consumer<>(mySTMStack, count * 2 * prodCount);

        consumer.start();

        for (int i = 0; i < prodCount; i++) {
            producers[i].join();
        }
        consumer.join();

        Set<Integer> producted = new HashSet<>();
        Set<Integer> consumed = new HashSet<>();

        for (int i = 0; i < prodCount; i++) {
            producted.addAll(producers[i].getSet());
        }
        consumed.addAll(consumer.getSet());

        assertTrue(consumed.containsAll(producted));
    }

    @Test
    public void stmTestOneProdManyCons() throws InterruptedException {
        MySTMStack<Integer> mySTMStack = new MySTMStack<>();
        MyIntegerGenerator myIntegerGenerator = new MyIntegerGenerator(0);

        int count = 40000;
        int consCount = 10;

        Producer<Integer> producer = new Producer<>(mySTMStack, myIntegerGenerator, count);

        Consumer[] consumers = new Consumer[consCount];
        for (int i = 0; i < consCount; i++) {
            consumers[i] = new Consumer(mySTMStack, count * 2);
        }

        producer.start();

        for (int i = 0; i < consCount; i++) {
            consumers[i].start();
        }

        producer.join();
        for (int i = 0; i < consCount; i++) {
            consumers[i].join();
        }

        Set<Integer> producted = new HashSet<>();
        Set<Integer> consumed = new HashSet<>();

        producted.addAll(producer.getSet());

        for (int i = 0; i < consCount; i++) {
            consumed.addAll(consumers[i].getSet());
        }

        assertTrue(consumed.containsAll(producted));
    }

    @Test
    public void stmTestManyProdManyCons() throws InterruptedException {
        MySTMStack<Integer> mySTMStack = new MySTMStack<>();

        int count = 4000;
        int prodCount = 10;
        int consCount = 10;

        MyIntegerGenerator[] generators = new MyIntegerGenerator[prodCount];
        for (int i = 0; i < prodCount; i++) {
            generators[i] = new MyIntegerGenerator(i * count);
        }

        Producer[] producers = new Producer[prodCount];
        for (int i = 0; i < prodCount; i++) {
            producers[i] = new Producer<>(mySTMStack, generators[i], count);
        }

        Consumer[] consumers = new Consumer[consCount];
        for (int i = 0; i < consCount; i++) {
            consumers[i] = new Consumer(mySTMStack, count * 2 * prodCount);
        }

        for (int i = 0; i < prodCount; i++) {
            producers[i].start();
        }

        for (int i = 0; i < consCount; i++) {
            consumers[i].start();
        }

        for (int i = 0; i < prodCount; i++) {
            producers[i].join();
        }

        for (int i = 0; i < consCount; i++) {
            consumers[i].join();
        }

        Set<Integer> producted = new HashSet<>();
        Set<Integer> consumed = new HashSet<>();

        for (int i = 0; i < prodCount; i++) {
            producted.addAll(producers[i].getSet());
        }

        for (int i = 0; i < consCount; i++) {
            consumed.addAll(consumers[i].getSet());
        }

        assertTrue(consumed.containsAll(producted));
    }
}
