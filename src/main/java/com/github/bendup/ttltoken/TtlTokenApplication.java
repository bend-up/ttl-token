package com.github.bendup.ttltoken;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TtlTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TtlTokenApplication.class, args);
	}

	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
		tomcat.addContextCustomizers((context) -> {
			StandardRoot standardRoot = new StandardRoot(context);
			standardRoot.setCacheMaxSize(64 * 1024);
		});

		return tomcat;
	}

}
