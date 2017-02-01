package de.interseroh.tmb.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by idueppe on 01.02.17.
 */
@Controller
public class WebController {

	@RequestMapping(path = "tmb.html")
	public String doGet() {
		return "tmb.jsp";
	}

}
