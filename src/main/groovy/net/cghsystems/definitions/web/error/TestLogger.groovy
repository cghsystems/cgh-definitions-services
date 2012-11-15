package net.cghsystems.definitions.web.error

import groovy.util.logging.Log4j

@Log4j
class TestLogger {

    void logError(definition) {
        log.error("Recieved Error ${definition}", definition)
    }

    void logInfo(message) {
        log.info(message)
    }
}
