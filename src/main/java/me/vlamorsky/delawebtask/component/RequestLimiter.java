package me.vlamorsky.delawebtask.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RequestLimiter {

    private final Map<String, Counter> userRequestCounter;

    public RequestLimiter() {
        userRequestCounter = new HashMap<>();
    }

    public void process(UsernamePasswordAuthenticationToken authenticationToken) {

        final String username = ((UserDetails) authenticationToken.getPrincipal()).getUsername();
        final Instant requestInstant = Instant.now();

        if (userRequestCounter.containsKey(username)) {

            if (!userRequestCounter.get(username).ableToResponse(requestInstant)) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
            }
        } else {

            userRequestCounter.put(username, new Counter(requestInstant));
        }
    }


    static class Counter {

        private Instant firstRequestInstant;
        private Instant secondRequestInstant;

        public Counter(Instant requestInstant) {
            firstRequestInstant = requestInstant;
            secondRequestInstant = requestInstant.minus(1, ChronoUnit.DAYS);
        }

        public synchronized boolean ableToResponse(Instant currentRequestInstant) {

            final Duration firstInstantDuration = Duration.between(firstRequestInstant, currentRequestInstant);
            final Duration secondInstantDuration = Duration.between(secondRequestInstant, currentRequestInstant);

            if (!firstInstantDuration.isNegative() && firstInstantDuration.getSeconds() > 60) {
                firstRequestInstant = currentRequestInstant;
                return true;
            }

            if (!secondInstantDuration.isNegative() && secondInstantDuration.getSeconds() > 60) {
                secondRequestInstant = currentRequestInstant;
                return true;
            }

            return false;
        }
    }
}
