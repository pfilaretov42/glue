package dev.pfilaretov42.glue.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BoardUpdateTimeLogger {
    private static final Logger LOG = LoggerFactory.getLogger(BoardUpdateTimeLogger.class);

    private volatile UpdatesHolder updatesHolder = new UpdatesHolder(Long.MAX_VALUE, Long.MIN_VALUE, 0, 0);

    public void saveBoardUpdateMillis(long millis) {
        LOG.debug("Saving board update time [millis={}]", millis);

        long minMillis;
        long maxMillis;
        long totalMillis;
        long updatesNumber;
        if (updatesHolder.totalMillis() + millis > 0) {
            minMillis = Math.min(updatesHolder.minMillis(), millis);
            maxMillis = Math.max(updatesHolder.maxMillis(), millis);
            totalMillis = updatesHolder.totalMillis() + millis;
            updatesNumber = updatesHolder.updatesNumber() + 1;
        } else {
            LOG.info("Overflowed total millis, resetting");
            minMillis = millis;
            maxMillis = millis;
            totalMillis = millis;
            updatesNumber = 1;
        }

        updatesHolder = new UpdatesHolder(minMillis, maxMillis, totalMillis, updatesNumber);
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void logBoardUpdateTime() {
        var holder = updatesHolder;
        if (holder.updatesNumber() == 0) {
            return;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Board updated {} time(s), update time: min={} ms, avg={} ms, max={} ms",
                holder.updatesNumber(),
                holder.minMillis(),
                holder.totalMillis() / holder.updatesNumber(),
                holder.maxMillis());
        }
    }
}

record UpdatesHolder(long minMillis, long maxMillis, long totalMillis, long updatesNumber) {
}