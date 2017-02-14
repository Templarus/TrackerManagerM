/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trekermanager;

/**
 *
 * @author Tester
 */
import java.util.*;

public class ThreadPool extends Thread {

    private static final int DEFAULT_NUM_WORKERS = 15;
    private LinkedList workerPool = new LinkedList(),
    taskList = new LinkedList();
    private boolean stopped = false;

    public ThreadPool() {
        this(DEFAULT_NUM_WORKERS);
    }

    public ThreadPool(int numOfWorkers) {
        for (int i = 0; i < numOfWorkers; i++) {
            workerPool.add(new Worker("" + i, this));
        }
        start();
    }

    @Override
    public void run() {
        try {
            while (!stopped) {
                if (taskList.isEmpty()) {
                    synchronized (taskList) {
                  // Если очередь пустая, подождать, пока будет добавлена
                        // задача
                        taskList.wait();
                    }
                } else if (workerPool.isEmpty()) {
                    synchronized (workerPool) {
                  // Если нет рабочих нитей, подождать, пока
                        // пока не появится
                        workerPool.wait();
                    }
                }
                // Запускаем следующую задачу из расписания задач
                getWorker().setTask((Runnable) taskList.removeLast());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTask(Runnable task) {
        taskList.addFirst(task);
        synchronized (taskList) {
            taskList.notify(); // Если добавлена новая задача, уведомляем
        }
    }

    public void putWorker(Worker worker) {
        workerPool.addFirst(worker);
      // Здесь может быть случай, когда вы будете иметь пул из 5 нитей,
        // а будет требоваться больше. Это происходит тогда, когда требуется
        // рабочая нить,
        // но ее нет (свободной), тогда просто блокируем пул нитей.
        // Это событие, при котором появляется свободная рабочая нить в пуле
        // нитей
        // Поэтому эта нить посылает уведомление и разблокирует
        // нить ThreadPool, ожидающую пул нитей
        synchronized (workerPool) {
            workerPool.notify();
        }
    }

    private Worker getWorker() {
        return (Worker) workerPool.removeLast();
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stopThreads() {
        stopped = true;
        Iterator it = workerPool.iterator();
        while (it.hasNext()) {
            Worker w = (Worker) it.next();
            synchronized (w) {
                w.notify();
            }
        }
    } // Junit test

    public void testThreadPool() {
        ThreadPool tp = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            tp.addTask(new Runnable() {
                public void run() {
                    System.out.println("A");
                }
            });
        }
        tp.stopThreads();
    }
} // /:~
