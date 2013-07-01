package ro.utcluj.quartz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class SchedulerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3057617029143049937L;

	Logger						log					= Logger.getLogger(SchedulerServlet.class);

	public void init() throws ServletException {

		try {
			log.info("Initializing NewsLetter PlugIn");
			// JobScheduler js = new JobScheduler();

		} catch (Exception e) {
			log.error(e.getClass() + ": " + e.getMessage(), e);
		}

	}

}
