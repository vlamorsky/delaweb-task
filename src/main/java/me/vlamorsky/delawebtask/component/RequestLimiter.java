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

        private final int LIMIT_PERIOD_IN_SECONDS = 60;
        private final int MAX_REQUESTS_PER_PERIOD = 2;

        private final Instant[] requestInstants;

        public Counter(Instant requestInstant) {

            requestInstants = new Instant[MAX_REQUESTS_PER_PERIOD];

            for (int i = 0; i < requestInstants.length; i++) {
                requestInstants[i] = requestInstant.minus(1, ChronoUnit.DAYS);
            }

            requestInstants[0] = requestInstant;
        }

        public synchronized boolean ableToResponse(Instant currentRequestInstant) {

            for (int i = 0; i < requestInstants.length; i++) {

                final Duration duration = Duration.between(requestInstants[i], currentRequestInstant);

                if (!duration.isNegative() && duration.getSeconds() > LIMIT_PERIOD_IN_SECONDS) {
                    requestInstants[i] = currentRequestInstant;
                    return true;
                }
            }

            return false;
        }
    }
}
