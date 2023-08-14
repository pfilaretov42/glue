# glue
The G.L.U.E.

# How to run

1. Run spring boot application with active profiles: `thread-pool,virtual-threads,cell-delay`
   - Press start button
   - Program runs fine
   - Resources: `Screenshot 2023-07-28 at 15.56.13 - GLUE - thread-pool, virtual-thread-pool, cell-delay`
2. Run spring boot application with active profiles: `physical-threads,cell-delay`
   - Press start button
   - Resources: `Screenshot 2023-07-28 at 15.58.52 - GLUE - thread-pool, physical-thread-pool, cell-delay`
   - Program crashes with OOM - cannot create more than 4044 threads:
     ```
     [18.128s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
[18.128s][warning][os,thread] Failed to start the native thread for java.lang.Thread "pool-2-thread-4042"
java.util.concurrent.ExecutionException: java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
at java.base/java.util.concurrent.FutureTask.report(FutureTask.java:122)
at java.base/java.util.concurrent.FutureTask.get(FutureTask.java:191)
at java.desktop/javax.swing.SwingWorker.get(SwingWorker.java:613)
at dev.pfilaretov42.glue.calculation.LifeCalculator.done(LifeCalculator.java:83)
at java.desktop/javax.swing.SwingWorker$5.run(SwingWorker.java:750)
at java.desktop/javax.swing.SwingWorker$DoSubmitAccumulativeRunnable.run(SwingWorker.java:848)
at java.desktop/sun.swing.AccumulativeRunnable.run(AccumulativeRunnable.java:112)
at java.desktop/javax.swing.SwingWorker$DoSubmitAccumulativeRunnable.actionPerformed(SwingWorker.java:858)
at java.desktop/javax.swing.Timer.fireActionPerformed(Timer.java:311)
at java.desktop/javax.swing.Timer$DoPostEvent.run(Timer.java:243)
at java.desktop/java.awt.event.InvocationEvent.dispatch(InvocationEvent.java:318)
at java.desktop/java.awt.EventQueue.dispatchEventImpl(EventQueue.java:773)
at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:720)
at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:714)
at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:87)
at java.desktop/java.awt.EventQueue.dispatchEvent(EventQueue.java:742)
at java.desktop/java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:203)
at java.desktop/java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:124)
at java.desktop/java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:113)
at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:109)
at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
at java.desktop/java.awt.EventDispatchThread.run(EventDispatchThread.java:90)
Caused by: java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
at java.base/java.lang.Thread.start0(Native Method)
at java.base/java.lang.Thread.start(Thread.java:1593)
at java.base/java.lang.System$2.start(System.java:2543)
at java.base/jdk.internal.vm.SharedThreadContainer.start(SharedThreadContainer.java:160)
at java.base/java.util.concurrent.ThreadPoolExecutor.addWorker(ThreadPoolExecutor.java:953)
at java.base/java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1375)
at java.base/java.util.concurrent.CompletableFuture.asyncSupplyStage(CompletableFuture.java:1782)
at java.base/java.util.concurrent.CompletableFuture.supplyAsync(CompletableFuture.java:2005)
at dev.pfilaretov42.glue.calculation.ThreadPoolCalculationStrategy.calculateCell(ThreadPoolCalculationStrategy.java:24)
at dev.pfilaretov42.glue.calculation.LifeCalculator.doInBackground(LifeCalculator.java:49)
at dev.pfilaretov42.glue.calculation.LifeCalculator.doInBackground(LifeCalculator.java:15)
at java.desktop/javax.swing.SwingWorker$1.call(SwingWorker.java:304)
at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
at java.desktop/javax.swing.SwingWorker.run(SwingWorker.java:343)
at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
at java.base/java.lang.Thread.run(Thread.java:1623)
[46.930s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
[46.930s][warning][os,thread] Failed to start the native thread for java.lang.Thread "SpringApplicationShutdownHook"
     ```