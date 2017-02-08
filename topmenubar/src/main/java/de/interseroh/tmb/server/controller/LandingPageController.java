package de.interseroh.tmb.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by idueppe on 01.02.17.
 */
@Controller
public class LandingPageController {

    @Value("${applauncher.url:http://localhost:9014/applauncher}")
    private String applauncherUrl;

	@GetMapping(path = "*.html")
	public String doGet(HttpServletRequest request, Model model) {
        model.addAttribute("applauncherUrl", applauncherUrl);
        return "topmenubar";
	}

}
