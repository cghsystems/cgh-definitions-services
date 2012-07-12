package net.cghsystems.definitions.web.ioc

import groovy.util.logging.Log4j

@Log4j
class TestLogger {

    void log(definition) {
        log.info("Recieved ${definition}")
    }
}
