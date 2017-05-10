package iqiyi.com.filedemo;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhenzhen on 2017/3/6.
 */

public class Test {

    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static int i = 100;

    public static void run1(){
        lock.readLock().lock();

        for (int j = 0; j < 100; j++) {

            i++;
            System.out.println("run1------>" + i);
        }
        lock.readLock().unlock();
    }

    public static void run2(){
        lock.writeLock().lock();
        for (int j = 0; j < 100; j++) {

            i--;
            System.out.println("run2------>" + i);
        }
        lock.writeLock().unlock();
  }
}
