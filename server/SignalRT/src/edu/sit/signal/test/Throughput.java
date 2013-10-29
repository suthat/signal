package edu.sit.signal.test;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.pushers.Notifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Suthat
 */
public class Throughput {

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        ArrayList<HashMap> notifications = new ArrayList<HashMap>();
        DbApis dbApis = new DbApis();
        dbApis.connect();
        ArrayList<HashMap> basicNotifications = dbApis.filterBasicScheduleNotification("2013-10-06 22:47:00");
        notifications.addAll(basicNotifications);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (HashMap notification : notifications) {
            dbApis.setNDataState(notification.get("token").toString(), 2);
            Runnable notifier = new Notifier(notification);
            executor.execute(notifier);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbApis.disconnect();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        double seconds = (double) duration / 1000000000.0;
        System.out.println(seconds);
    }
}
