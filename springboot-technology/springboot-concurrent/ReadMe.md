## 说明：
这个工程是介绍java并发编程的，如同每个包名，此工程涵盖了原子操作、同步容器、重入锁、
synchronized及volatiled关键字等，在一些包中，为了方便比较差别，我注释了对应的代码，大家
在自己测试的时候记得打开注释，如下是相关笔记
## 1. synchronized 关键字 

锁：锁对象 ==》 this、临界资源对象Object o必须为Object类型，就是说这个对象要可以被所有的对象调用，所以要为Object类型 、class对象

Java 虚拟机中的同步(Synchronization)基于进入和退出管程(Monitor)对象实现。同步方 法 并不是由 monitor enter 和 monitor exit 指令来实现同步的，而是由方法调用指令读取运 行时常量池中方法的 ACC_SYNCHRONIZED 标志来隐式实现的

```java
#synchronized(this)和synchronized方法都是锁当前对象。
public synchronized void testSync3(){}

#静态同步方法，锁的是当前类型的类对象。在本代码中就是Test_02.class
public static synchronized void testSync4(){}

#加锁的目的： 就是为了保证操作的原子性

#同步方法只影响锁定同一个锁对象的同步方法。不影响其他线程调用非同步方法，或调用其他锁资源的同步方法。

#同步方法只能保证当前方法的原子性，不能保证多个业务方法之间的互相访问的原子性。所以可能会出现脏读问题

#锁可重入。同一个线程，多次调用同步代码，锁定同一个锁对象，可重入。注意条件，多线程不可以

#当同步方法中发生异常的时候，自动释放锁资源。不会影响其他线程的执行。

#在定义同步代码块时，不要使用常量对象作为锁对象。
```

1. 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；
2. 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
3. 修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；
4. 修改一个类，其作用的范围是synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象。

## 2. volatile 

```java
#通知OS操作系统底层，在CPU计算过程中，都要检查内存中数据的有效性。保证最新的内存数据被使用。

#volatile， 只能保证可见性，不能保证原子性。

#同步代码一旦加锁后，那么会有一个临时的锁引用执行锁对象，和真实的引用无直接关联。在锁未释放之前，修改锁对象引用，不会影响同步代码的执行。
```

## 3. CountDownLatch

```
/** * 门闩 - CountDownLatch * 可以和锁混合使用，或替代锁的功能。 * 在门闩未完全开放之前等待。当门闩完全开放后执行。 * 避免锁的效率低下问题。 */
```

```java
#自定义容器，提供新增元素（add）和获取元素数量（size）方法。
启动两个线程。线程1向容器中新增10个数据。线程2监听容器元素数量，当容器元素数量为5时，线程2输出信息并终止。

#注意CountDownLatch的await和countDown的使用时机
public class Demo {
  LinkedList<Object> list = new LinkedList<>();

  void add(Object o){ list.add(o);}
  void size(){list.size();}

  public static void main(String[] args) {
    Demo demo = new Demo();
    CountDownLatch count = new CountDownLatch(5);

    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10; i++) {
          demo.add(i);
          System.out.println("容器增加 ：" + i);
          count.countDown();
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          count.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("容器元素数量为5");
      }
    }).start();
  }
}

```

## 4. ReentrantLock

重入锁：避免使用synchronized关键字

lock、unlock

 尝试锁:tryLock

​    ![1572686618051](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572686618051.png)

 公平锁 
// 定义一个公平锁 private static ReentrantLock lock = new ReentrantLock(true);

重入锁，建议应用的同步方式。相对效率比 synchronized 高。量级较轻。 synchronized 在 JDK1.5 版本开始，尝试优化。到 JDK1.7 版本后，优化效率已经非常好了。 在绝对效率上，不比 reentrantLock 差多少。 使用重入锁， 必须 必须必须 手工释放锁标记。一般都是在 finally 代码块中定义释放锁标 记的 unlock 方法。 

## 5. wait/notify与while

wait/notify都是和while配合应用的。可以避免多线程并发判断逻辑失效问题。而不是用if

在多线程操作中，我们常常会遇到需要先判断信号量状态是否就绪，然后执行后续操作的场景。这里对状态的判断使用的是while而不是单线程下常用的if

在线程中notify或者notifyAll会唤醒一个或多个线程，当线程被唤醒后，被唤醒的线程继续执行阻塞后的操作。

这里分析一下get操纵： 当某个线程得到锁时storage为空，此时它应该wait，下次被唤醒时（任意线程调用notify），storage可能还是空的。因为有可能其他线程清空了storage。如果此时用的是if它将不再判断storage是否为空，直接继续，这样就引起了错误。但如果用while则每次被唤醒时都会先检查storage是否为空再继续，这样才是正确的操作；生产也是同一个道理。

 ![1572689301635](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572689301635.png)

![1572689461344](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572689461344.png)

## 6.  锁的底层实现 

​       Java 虚拟机中的同步(Synchronization)基于进入和退出管程(Monitor)对象实现。同步方 法 并不是由 monitor enter 和 monitor exit 指令来实现同步的，而是由方法调用指令读取运 行时常量池中方法的 ACC_SYNCHRONIZED 标志来隐式实现的。 

![1572711181943](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572711181943.png)

![1572711381165](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572711381165.png)

![1572711532393](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572711532393.png)

## 7. 锁的种类

​        Java 中锁的种类大致分为偏向锁，自旋锁，轻量级锁，重量级锁。 锁的使用方式为：先提供偏向锁，如果不满足的时候，升级为轻量级锁，再不满足，升 级为重量级锁。
​         自旋锁是一个过渡的锁状态，不是一种实际的锁类型。 
​         锁只能升级，不能降级。

1.4.1 重量级锁 
在 1.3 中解释的就是重量级锁。


1.4.2 偏向锁 
是一种编译解释锁。如果代码中不可能出现多线程并发争抢同一个锁的时候，JVM 编译 代码，解释执行的时候，会自动的放弃同步信息。消除 synchronized 的同步代码结果。使用 锁标记的形式记录锁状态。在 Monitor 中有变量 ACC_SYNCHRONIZED。当变量值使用的时候， 代表偏向锁锁定。可以避免锁的争抢和锁池状态的维护。提高效率。 

1.4.3 轻量级锁 
过渡锁。当偏向锁不满足，也就是有多线程并发访问，锁定同一个对象的时候，先提升 为轻量级锁。也是使用标记 ACC_SYNCHRONIZED 标记记录的。ACC_UNSYNCHRONIZED 标记记 录未获取到锁信息的线程。就是只有两个线程争抢锁标记的时候，优先使用轻量级锁。 两个线程也可能出现重量级锁。


1.4.4 自旋锁 
是一个过渡锁，是偏向锁和轻量级锁的过渡。 当获取锁的过程中，未获取到。为了提高效率，JVM 自动执行若干次空循环，再次申请 锁，而不是进入阻塞状态的情况。称为自旋锁。自旋锁提高效率就是避免线程状态的变更。

## 8. ThreadLocal

 就是一个Map：key ----->  Thread.getCurrentThread().  
                             value ---> 线程需要保存的变量。

```
* ThreadLocal.set(value) -> map.put(Thread.getCurrentThread(), value);
* ThreadLocal.get() -> map.get(Thread.getCurrentThread());

#使用ThreadLocal的时候，一定注意回收资源问题，每个线程结束之前，将当前线程保存的线程变量一定要删除，不然就会出现泄漏或者溢出
ThreadLocal.remove();
```

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572713962964.png" alt="1572713962964" style="zoom:45%;" />

## 9. 同步容器

解决并发情况下的容器线程安全问题的。给多线程环境准备一个线程安全的容器对象。
 线程安全的容器对象： Vector, Hashtable。线程安全容器对象，都是使用 synchronized 方法实现的。 concurrent 包中的同步容器，大多数是使用系统底层技术实现的线程安全。类似 native。 Java8 中使用 CAS

1 Map/Set 
1.1 ConcurrentHashMap/ConcurrentHashSet 
底层哈希实现的同步 Map(Set)。效率高，线程安全。使用系统底层技术实现线程安全。
 量级较 synchronized 低。key 和 value 不能为 null。 
1.2 ConcurrentSkipListMap/ConcurrentSkipListSet 
底层跳表（SkipList）实现的同步 Map(Set)。有序，效率比 ConcurrentHashMap 稍低。 

2 List 
2.1 CopyOnWriteArrayList 
写时复制集合。写入效率低，读取效率高。每次写入数据，都会创建一个新的底层数组。就是1、12、123这种，只要写就新空间，写低读高
保证线程安全，以牺牲空间的代价来实现

3 Queue 
3.1 ConcurrentLinkedQueue 
基础链表同步队列。 
3.2 LinkedBlockingQueue 
阻塞队列，队列容量不足自动阻塞，队列容量为 0 自动阻塞。 
3.3 ArrayBlockingQueue 
底层数组实现的有界队列。自动阻塞。根据调用 API（add/put/offer）不同，有不同特 性。 
当容量不足的时候，有阻塞能力。 
add 方法在容量不足的时候，抛出异常。 
put 方法在容量不足的时候，阻塞等待。 
offer 方法， 单参数 offer 方法，不阻塞。容量不足的时候，返回 false。当前新增数据操作放弃。 三参数 offer 方法（offer(value,times,timeunit)），容量不足的时候，阻塞 times 时长（单 位为 timeunit），如果在阻塞时长内，有容量空闲，新增数据返回 true。如果阻塞时长范围 内，无容量空闲，放弃新增数据，返回 false。 

3.4 DelayQueue 
延时队列。根据比较机制，实现自定义处理顺序的队列。常用于定时任务。 如：定时关机。 
3.5 LinkedTransferQueue 
转移队列，使用 transfer 方法，实现数据的即时处理。没有消费者，就阻塞。 
3.6 SynchronusQueue 
同步队列，是一个容量为 0 的队列。是一个特殊的 TransferQueue。 必须现有消费线程等待，才能使用的队列。 add 方法，无阻塞。若没有消费线程阻塞等待数据，则抛出异常。 put 方法，有阻塞。若没有消费线程阻塞等待数据，则阻塞。 