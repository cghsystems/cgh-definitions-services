package net.cghsystems.definitions.web.ioc


import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource


/**
 * Spring MVC core configuration object.
 *
 * @author chris
 *
 */
@Configuration
@ImportResource(["classpath:META-INF/spring/definitions-si-context.xml",
	"classpath:META-INF/spring/definitions-mongo-context.xml"])
class DefinitionsControllerApplicationContext {
}
