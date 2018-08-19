package log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class Mdc {

    public static void main(String[] args) {
        MDC.put("mdc_track_id", "MDC track id test.");

        /**
         * [main] INFO  log.Mdc - [MDC track id test.] this is a test msg.
         */
        log.info("this is a test msg.");
    }
    
}
