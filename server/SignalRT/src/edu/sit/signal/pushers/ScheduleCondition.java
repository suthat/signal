package edu.sit.signal.pushers;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Suthat
 */
public class ScheduleCondition implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("Finished all threads");
    }
    
}
