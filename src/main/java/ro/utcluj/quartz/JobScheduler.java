package ro.utcluj.quartz;

import org.apache.log4j.Logger;

public class JobScheduler {

	Logger	log	= Logger.getLogger(JobScheduler.class);

	public JobScheduler() {
		// try {
		// // specify the job' s details..
		// // JobDetail job = JobBuilder.newJob(SchemaProcessingJob.class).withIdentity("schemaProcessingJob").build();
		// // specify the running period of the job
		// Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
		// .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")).build();
		// // schedule the job
		// SchedulerFactory schFactory = new StdSchedulerFactory();
		// Scheduler sch = schFactory.getScheduler();
		// sch.start();
		// //sch.scheduleJob(job, trigger);
		//
		// } catch (SchedulerException e) {
		// log.error(e.getClass() + ": " + e.getMessage(), e);
		// } catch (ParseException e) {
		// log.error(e.getClass() + ": " + e.getMessage(), e);
		// }
	}
}
