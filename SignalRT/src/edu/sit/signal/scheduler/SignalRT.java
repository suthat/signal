package edu.sit.signal.scheduler;

import edu.sit.signal.pushers.ScheduleNotification;
import edu.sit.signal.pushers.ScheduleCondition;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobDetail;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import org.quartz.Trigger;

/**
 *
 * @author Suthat
 */
public class SignalRT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SchedulerException {
        new SignalRT().run();
    }

    public void run() throws SchedulerException { 
        // Grab the Scheduler instance from the Factory 
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Start it off
        scheduler.start();

        // Define the job and tie it to ScheduleNotification class
        JobDetail job = newJob(ScheduleNotification.class)
                .withIdentity("BBot", "ScheduleNotification")
                .build();
        // Trigger the job to run now, and then repeat every 20 seconds
        Trigger trigger = newTrigger()
                .withIdentity("BBotTrigger", "ScheduleNotification")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(20).repeatForever())
                .build();
        
        // Define the job and tie it to ScheduleCondition class
        JobDetail job2 = newJob(ScheduleCondition.class)
                .withIdentity("CBot", "ScheduleCondition")
                .build();
        // Trigger the job to run now, and then repeat every a minute
        Trigger trigger2 = newTrigger()
                .withIdentity("CBotTrigger", "ScheduleCondition")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInMinutes(1).repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
        scheduler.scheduleJob(job2, trigger2);

        // !IMPORTANT
        //scheduler.shutdown();
    }
}
