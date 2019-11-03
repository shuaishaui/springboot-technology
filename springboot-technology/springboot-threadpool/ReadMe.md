## 说明：
此工程是演示java线程的相关操作

## 1. Executor 

线程池顶级接口。定义方法，void execute(Runnable)。方法是用于处理任务的一个服务 方法。
调用者提供 Runnable 接口的实现，线程池通过线程执行这个 Runnable。
服务方法无 返回值的。是 Runnable 接口中的 run 方法无返回值。 常用方法 - void execute(Runnable) 作用是： 启动线程任务的

 shutdown - 优雅关闭。 不是强行关闭线程池，回收线程池中的资源。而是不再处理新的任务，将已接收的任务处理完毕后再关闭

## 2 ExecutorService 
Executor 接口的子接口。
提供了一个新的服务方法，submit。有返回值（Future 类型）。 submit 方法提供了 overload 方法。其中有参数类型为 Runnable 的，不需要提供返回值的； 有参数类型为 Callable，可以提供线程执行后的返回值。 
Future，是 submit 方法的返回值。代表未来，也就是线程执行结束后的一种结果。如返 回值。 
常见方法 - void execute(Runnable)， Future submit(Callable)， Future submit(Runnable) 
线程池状态： Running， ShuttingDown， Termitnaed Running - 线程池正在执行中。活动状态。 ShuttingDown - 线程池正在关闭过程中。优雅关闭。一旦进入这个状态，线程池不再接 收新的任务，处理所有已接收的任务，处理完毕后，关闭线程池。 Terminated - 线程池已经关闭。 

## 3 Future 
未来结果，代表线程任务执行结束后的结果。获取线程执行结果的方式是通过 get 方法获取的。get 无参，阻塞等待线程执行结束，并得到结果。get 有参，阻塞固定时长，等待 线程执行结束后的结果，如果在阻塞时长范围内，线程未执行结束，抛出异常。 
常用方法： T get()  T get(long, TimeUnit) 

## 4 Callable 
可执行接口。 类似 Runnable 接口。也是可以启动一个线程的接口。其中定义的方法是 call。call 方法的作用和 Runnable 中的 run 方法完全一致。call 方法有返回值。 接口方法 ： Object call();相当于 Runnable 接口中的 run 方法。区别为此方法有返回值。 不能抛出已检查异常。 和 Runnable 接口的选择 - 需要返回值或需要抛出异常时，使用 Callable，其他情况可 任意选择。 

## 5 Executors 
工具类型。为 Executor 线程池提供工具方法。可以快速的提供若干种线程池。如：固定 容量的，无限容量的，容量为 1 等各种线程池。 
线程池是一个进程级的重量级资源。默认的生命周期和 JVM 一致。当开启线程池后， 直到 JVM 关闭为止，是线程池的默认生命周期。
如果手工调用 shutdown 方法，那么线程池 执行所有的任务后，自动关闭。 开始 - 创建线程池。 结束 - JVM 关闭或调用 shutdown 并处理完所有的任务。 类似 Arrays，Collections 等工具类型的调用。 

## 6 FixedThreadPool 
容量固定的线程池。活动状态和线程池容量是有上限的线程池。所有的线程池中，都有 一个任务队列。使用的是 BlockingQueue<Runnable>作为任务的载体。当任务数量大于线程 池容量的时候，没有运行的任务保存在任务队列中，当线程有空闲的，自动从队列中取出任 务执行。 
使用场景： 大多数情况下，使用的线程池，首选推荐 FixedThreadPool。OS 系统和硬件 是有线程支持上限。不能随意的无限制提供线程池。 线程池默认的容量上限是 Integer.MAX_VALUE。 常见的线程池容量： PC - 200。 服务器 - 1000~10000 queued tasks - 任务队列 completed tasks - 结束任务队列 

## 7 CachedThreadPool 
缓存的线程池。容量不限（Integer.MAX_VALUE）。自动扩容。容量管理策略：如果线程 池中的线程数量不满足任务执行，创建新的线程。每次有新任务无法即时处理的时候，都会 创建新的线程。当线程池中的线程空闲时长达到一定的临界值（默认 60 秒），自动释放线程。
 默认线程空闲 60 秒，自动销毁。 
应用场景： 内部应用或测试应用。 内部应用，有条件的内部数据瞬间处理时应用，如：电信平台夜间执行数据整理（有把握在短时间内处理完所有工作，且对硬件和软件有足够的 信心）。 测试应用，在测试的时候，尝试得到硬件或软件的最高负载量，用于提供 FixedThreadPool 容量的指导

## 8 ScheduledThreadPool 
计划任务线程池。可以根据计划自动执行任务的线程池。 scheduleAtFixedRate(Runnable, start_limit, limit, timeunit)
runnable - 要执行的任务。 start_limit - 第一次任务执行的间隔。 limit - 多次任务执行的间隔。 timeunit - 多次任务执行间隔的时间单位。 
使用场景： 计划任务时选用（DelaydQueue），如：电信行业中的数据整理，每分钟整 理，没消失整理，每天整理等。 

## 9 SingleThreadExceutor 
单一容量的线程池。 使用场景： 保证任务顺序时使用。如： 游戏大厅中的公共频道聊天。秒杀。 

## 10 ForkJoinPool 
分支合并线程池（mapduce 类似的设计思想）。适合用于处理复杂任务。 初始化线程容量与 CPU 核心数相关。 线程池中运行的内容必须是 ForkJoinTask 的子类型（RecursiveTask,RecursiveAction）。 ForkJoinPool - 分支合并线程池。 可以递归完成复杂任务。要求可分支合并的任务必须 是 ForkJoinTask 类型的子类型。其中提供了分支和合并的能力。ForkJoinTask 类型提供了两个 抽象子类型，RecursiveTask 有返回结果的分支合并任务,RecursiveAction 无返回结果的分支合 并任务。（Callable/Runnable）compute 方法：就是任务的执行逻辑。 ForkJoinPool 没有所谓的容量。默认都是 1 个线程。根据任务自动的分支新的子线程。 当子线程任务结束后，自动合并。所谓自动是根据 fork 和 join 两个方法实现的。 应用： 主要是做科学计算或天文计算的。数据分析的

## 11 WorkStealingPool 
JDK1.8
新增的线程池。工作窃取线程池。当线程池中有空闲连接时，自动到等待队列中 窃取未完成任务，自动执行。
 初始化线程容量与 CPU 核心数相关。 此线程池中维护的是精灵线程。
 ExecutorService.newWorkStealingPool(); 

## 12 ThreadPoolExecutor 
线程池底层实现。除 ForkJoinPool 外，其他常用线程池底层都是使用 ThreadPoolExecutor实现的。
 public ThreadPoolExecutor (int corePoolSize, // 核心容量，创建线程池的时候，默认有多少线程。也是线程池保持 的最少线程数 int maximumPoolSize, // 最大容量，线程池最多有多少线程  long keepAliveTime, // 生命周期，0 为永久。当线程空闲多久后，自动回收。 TimeUnit unit, // 生命周期单位，为生命周期提供单位，如：秒，毫秒 BlockingQueue<Runnable> workQueue // 任务队列，阻塞队列。
注意，泛型必须是 Runnable ); 
使用场景： 默认提供的线程池不满足条件时使用。如：初始线程数据 4，最大线程数 200，线程空闲周期 30 秒。 