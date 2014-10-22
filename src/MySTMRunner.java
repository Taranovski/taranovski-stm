/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MySTMRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("the results are not deterministic");
        System.out.println("generally stm is working...");
        try {
            stmTestOneProdOneCons();
            stmTestOneProdManyCons();
            stmTestManyProdOneCons();
            stmTestManyProdManyCons();
        } catch (InterruptedException ex) {
            Logger.getLogger(MySTMRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stmTestOneProdOneCons() throws InterruptedException {
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

        if (consumed.containsAll(producted)) {
            System.out.println("all ok");
        } else {
            System.out.println("all is not ok");
        }
    }

    public static void stmTestManyProdOneCons() throws InterruptedException {
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

        if (consumed.containsAll(producted)) {
            System.out.println("all ok");
        } else {
            System.out.println("all is not ok");
        }
    }

    public static void stmTestOneProdManyCons() throws InterruptedException {
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

        if (consumed.containsAll(producted)) {
            System.out.println("all ok");
        } else {
            System.out.println("all is not ok");
        }
    }

    public static void stmTestManyProdManyCons() throws InterruptedException {
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

        if (consumed.containsAll(producted)) {
            System.out.println("all ok");
        } else {
            System.out.println("all is not ok");
        }
    }
}
