package com.shuai.synchronize;

public class Test_01 {

  private int count = 0;
  private final Object o = new Object();

  public void testSync1(){
    synchronized (o){
      System.out.println(Thread.currentThread().getName() + "count = " + count ++);
    }
  }

  public void testSync2(){
    synchronized (this){
      System.out.println(Thread.currentThread().getName() + "count = " + count ++);
    }
  }

  public synchronized void testSync3(){
    System.out.println(Thread.currentThread().getName() + "count = " + count ++);
  }

  public static void main(String[] args) {
    Test_01 t = new Test_01();
    t.testSync1();
    t.testSync2();
    t.testSync3();
  }
}
