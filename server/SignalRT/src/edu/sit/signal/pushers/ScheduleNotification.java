package edu.sit.signal.pushers;

import edu.sit.signal.apis.DbApis;
import edu.sit.signal.app.Helpers;
import java.util.ArrayList;
import java.util.HashMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Suthat
 */
public class ScheduleNotification implements Job {

    private DbApis dbApis = new DbApis();
    private Helpers helpers = new Helpers();
    private static final int NTHREDS = 10;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {

        dbApis.connect();
        
        String date = helpers.getCurrentTimestamp("date");
        String time = helpers.getCurrentTimestamp("time");
        String dow = helpers.getCurrentTimestamp("dow");
        
        ArrayList<HashMap> notifications = new ArrayList<HashMap>();
        ArrayList<HashMap> basicNotifications = dbApis.filterBasicScheduleNotification(date + " " + time + ":00");
        ArrayList<HashMap> complexNotifications = dbApis.filterComplexScheduleNotification(time, dow);
        
        System.out.println("Load basic notification trigger on " + date + " " + time + ":00");
        System.out.println("There is " + basicNotifications.size() + " basic notifications to deliver at this time");
        
        System.out.println("Load complex notification trigger time " + time + " repeat on " + dow);
        System.out.println("There is " + complexNotifications.size() + " complex notifications to deliver at this time");
        
        // combine all arraylists to a bigone and send it to Notifier
        notifications.addAll(basicNotifications);
        notifications.addAll(complexNotifications);
   
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        for (HashMap notification : notifications) {
            dbApis.setNDataState(notification.get("token").toString(), 2);
            Runnable notifier = new Notifier(notification);
            executor.execute(notifier);
        }
        
        // This will make the executor accept no new threads
        // and fhreads in the queueinish all existing t
        executor.shutdown();
        
        // Wait until all threads are finish
        try{
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        dbApis.disconnect();
        System.out.println("Finished all threads");
    }
}
