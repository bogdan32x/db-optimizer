package ro.utcluj.controllers;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.utcluj.service.SchemaParserUtils;

@Controller
@RequestMapping("/home")
public class ApplicationController {

	SchemaParserUtils		parserService	= new SchemaParserUtils();

	protected static Logger	logger			= Logger.getLogger("GreetingController");

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String getMainPage() {
		return "index";
	}

	@RequestMapping(value = "/home.do", method = RequestMethod.POST)
	public String showAllGreetings(@RequestParam(value = "schemaText", required = true) final String schemaText,
			final Map<String, Object> model) {
		ApplicationController.logger.info("entering showAllGreetings");
		model.put("schemaText", schemaText);

		this.parserService.parseSchema(schemaText, "");
		// This will resolve to /WEB-INF/jsp/home.jsp
		return "home";
	}

	@RequestMapping(value = "/addgreeting.do", method = RequestMethod.GET)
	public String showAddGreetingPage() {
		ApplicationController.logger.info("entering showAddGreetingPage");

		// This will resolve to /WEB-INF/jsp/addgreeting.jsp
		return "addgreeting";
	}

}
