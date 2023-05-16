package com.example.dmuit.info;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
	@GetMapping("/info")
	public String projectInfo() {
		return "Project name is DmuIT";
	}
}
