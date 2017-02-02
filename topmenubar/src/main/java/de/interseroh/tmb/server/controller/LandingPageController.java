package de.interseroh.tmb.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by idueppe on 01.02.17.
 */
@Controller
public class LandingPageController {

	@GetMapping(path = "*.html")
	public String doGet(HttpServletRequest request, Model model) {
		model.addAttribute("applauncherServer",request.getServerName());
		return "topmenubar";
	}

}
