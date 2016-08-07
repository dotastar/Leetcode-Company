package projects.crawler.task;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Created by yazhoucao on 10/31/15.
 */
@Slf4j
public class TaskControl {
    public TaskControl(BaseTask crawlTask) {
        Thread crawlThread = new Thread(crawlTask);
        crawlThread.start();
        log.info(crawlTask.getClass().getSimpleName() + " has started");
        Scanner scanner = new Scanner(System.in);
        while (!crawlTask.isTerminating() && !crawlTask.isTerminated()) {
            String command = scanner.next();
            switch (command) {
                case "stop":
                case "Stop":
                case "shutdown":
                case "Shutdown":
                case "ShutDown":
                    crawlTask.shutdown();
                    break;
                case "print":
                case "Print":
                case "status":
                case "Status":
                case "print status":
                case "Print Status":
                    crawlTask.printStatus();
                    break;
                case "exit":
                case "Exit":
                    crawlTask.shutdown();
                    return;
                default:
                    System.out.printf("Unrecognizable command: " + command);
                    break;
            }
        }
        log.info("Main thread exit");
    }
}
