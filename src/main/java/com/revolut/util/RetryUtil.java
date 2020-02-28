package com.revolut.util;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.function.Function.identity;

public class RetryUtil {


    public static final int MAX_RETRIES = 3;

    @Slf4j
    public static class RetryExecutor<T, R> {

        private int maxAttampts;
        private int currentAttempt;
        private Class<? extends RuntimeException> throwableType;
        private Function<RuntimeException, RuntimeException> wrapper;
        private long backOffPeriod;

        public RetryExecutor(int maxAttampts, int currentAttempt) {
            this.maxAttampts = maxAttampts;
            this.currentAttempt = currentAttempt;
            this.throwableType = RuntimeException.class;
            this.wrapper = identity();
            this.backOffPeriod = 0;
        }

        public RetryExecutor<T, R> retryOn(Class<? extends RuntimeException> throwableType) {
            this.throwableType = throwableType;
            return this;
        }

        public RetryExecutor<T, R> wrapErrorWith(Function<RuntimeException, RuntimeException> wrapper) {
            this.wrapper = wrapper;
            return this;
        }

        public RetryExecutor<T, R> backOfferiod(long backOffPeriod) {
            this.backOffPeriod = backOffPeriod > 5000 ? 5000 : backOffPeriod;
            return this;
        }


        public R execute(Supplier<R> execution) {
            R result = null;
            boolean failed = false;
            do {
                try {
                    result = execution.get();
                    failed = false;
                } catch (RuntimeException e) {
                    failed = true;
                    if (!throwableType.isAssignableFrom(e.getClass())) {
                        throw e;
                    } else if (currentAttempt >= maxAttampts) {
                        throw wrapper.apply(e);
                    }
                    this.currentAttempt++;
                    if (backOffPeriod > 50) {
                        try {
                            Thread.sleep(backOffPeriod);
                        } catch (InterruptedException e1) {
                            throw new RetryBackOffException(e1);
                        }
                    }
                    log.warn("execution failed on {}: retrying attempt {}/{}", e.getClass().getName(), currentAttempt, maxAttampts);
                }
            } while (failed);

            return result;
        }

    }

    public static <T, R> RetryExecutor<T, R> withRetries(int maxRetries) {
        return new RetryExecutor<>(maxRetries, 0);
    }

    public static <T, R> RetryExecutor<T, R> defaultExecutor() {
        return withRetries(MAX_RETRIES);
    }
}
