package com.whong.demojdk8;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class DockerDemoController {
	 @GetMapping("/hello")
	    public String hello() {
			System.out.println( "running springboot on docker jenkins" );
	        return "running springboot on docker jenkins";
	    }
}
