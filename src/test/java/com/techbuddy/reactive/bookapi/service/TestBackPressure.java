package com.techbuddy.reactive.bookapi.service;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class TestBackPressure {
    @Test
    public void testBackPressure(){
        Flux<Integer> numbers = Flux.range(1,100).log();

        numbers.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if (value == 3)
                    cancel();
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Completed!!");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }
        });
    }

    @Test
    public void testBackPressureDrop(){
        Flux<Integer> numbers = Flux.range(1,100).log();

        numbers
                .onBackpressureDrop(integer -> System.out.println("Dropped Values = " + integer))
                .subscribe(new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(3);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    System.out.println("value = " + value);
                    if (value == 3)
                        hookOnCancel();
                }

                @Override
                protected void hookOnComplete() {
                    System.out.println("Completed!!");
                }

                @Override
                protected void hookOnError(Throwable throwable) {
                    super.hookOnError(throwable);
                }
        });
    }

    @Test
    public void testBackPressureBuffer(){
        Flux<Integer> numbers = Flux.range(1,100).log();

        numbers
                .onBackpressureBuffer(10, integer -> System.out.println("Buffered Values = " + integer))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if (value == 3)
                            hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Completed!!");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }
                });
    }

    @Test
    public void testBackPressureError(){
        Flux<Integer> numbers = Flux.range(1,100).log();

        numbers
                .onBackpressureError()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if (value == 3)
                            hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Completed!!");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        System.out.println("throwable = " + throwable);
                    }
                });
    }
}
