package com.jobjournal.JobJournal.shared.helpers;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

// TODO: Finish implementing rate limiting service on all endpoints!
public class RateLimitingService {
    private final Bucket bucket;
    private final Bandwidth limit;

    public RateLimitingService(int bucketCapacity, int bucketRefillRate, int duration) {
        Bandwidth limit = Bandwidth.classic(bucketCapacity, Refill.greedy(bucketRefillRate, Duration.ofMinutes(1)));
        this.limit = limit;
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    public Bucket getBucket() {
        return bucket;
    }

    public Bandwidth getLimit() {
        return limit;
    }
}
