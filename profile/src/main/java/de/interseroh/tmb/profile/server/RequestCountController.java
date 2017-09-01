package de.interseroh.tmb.profile.server;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RequestCountController {

	private static AtomicLong requestCounts = new AtomicLong(0l);

	@GetMapping("/count")
	public Long nextValue() {
		return requestCounts.incrementAndGet();
	}
}
