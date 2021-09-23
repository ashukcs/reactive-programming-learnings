package com.techbuddy.reactive.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoServices {
    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana")).log();
    }

    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .map(String::toUpperCase)
                .log();
    }

    public static void main(String[] args) {
        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
        fluxAndMonoServices.fruitsFlux().subscribe(s -> System.out.println("Flux Item : " + s));

        fluxAndMonoServices.fruitMono().subscribe(s -> System.out.println("Mono Item : " + s));
    }

    public Flux<String> fruitsFluxFilter(int number) {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .log();
    }

    public Flux<String> fruitsFluxFilterMap(int number) {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .map(String::toUpperCase)
                .log();
    }

    public Mono<String> fruitMono() {
        return Mono.just("Papaya").log();
    }

    public Flux<String> fruitFluxFlatMapAsync() {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)
                )).log();
    }

    public Flux<String> fruitFluxConcatMap() {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .concatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)
                )).log();
    }

    public Mono<List<String>> fruitMonoFlatMap() {
        return Mono.just("Papaya").flatMap(s -> Mono.just(Arrays.asList(s.split("")))).log();
    }

    public Flux<String> fruitFluxFlatMapMany() {
        return Mono.just("Apple")
                .flatMapMany(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxTransform(int number) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .transform(filterData).log();
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .transform(filterData)
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("Pineapple", "Jack Fruit").transform(filterData))
                .log();
    }

    public Flux<String> fruitFluxConcat() {
        Flux<String> fruits = Flux.just("Pineapple", "Orange");
        Flux<String> veggies = Flux.just("Jack Fruit", "Tomato");

        return Flux.concat(fruits, veggies).log();
    }

    public Flux<String> fruitFluxConcatWith() {
        Flux<String> fruits = Flux.just("Pineapple", "Orange");
        Flux<String> veggies = Flux.just("Jack Fruit", "Tomato");

        return fruits.concatWith(veggies);
    }

    public Flux<String> fruitMonoConcatWith() {
        Mono<String> fruits = Mono.just("Pineapple");
        Mono<String> veggies = Mono.just("Tomato");

        return fruits.concatWith(veggies);
    }

    public Flux<Integer> givenFluxes_whenCombileLatestIsInvoked_thenCombineLatest() {
        Flux<Integer> evenNumbers = Flux.just(2, 4, 6);
        Flux<Integer> oddNumbers = Flux.just(1, 3, 5);

        return Flux.combineLatest(evenNumbers, oddNumbers, (a, b) -> a + b).log();

    }

    public Flux<String> fruitsFluxMerge() {
        Flux<String> fruits = Flux.just("Pineapple", "Orange");
        Flux<String> veggies = Flux.just("Jack Fruit", "Tomato");

        return Flux.merge(fruits, veggies).log();
    }

    public Flux<String> fruitsFluxMergeWith() {
        Flux<String> fruits = Flux.just("Pineapple", "Orange").delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Jack Fruit", "Tomato").delayElements(Duration.ofMillis(75));

        return fruits.mergeWith(veggies).log();
    }

    public Flux<String> fruitsFluxMergeWithSequential() {
        Flux<String> fruits = Flux.just("Pineapple", "Orange").delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Jack Fruit", "Tomato").delayElements(Duration.ofMillis(75));

        return Flux.mergeSequential(fruits, veggies).log();
    }

    public Flux<String> fruitsFluxFilterDoOn(int number) {
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .filter(s -> s.length() > number)
                .doOnNext(s -> System.out.println(s))
                .doOnSubscribe(subscription -> {
                    System.out.println("Subsription - " + subscription.toString());
                })
                .doOnComplete(() -> System.out.println("Completed!!"))
                .log();
    }

    public Flux<String> fruitsFluxOnErrorReturn() {
        Flux<String> fruits = Flux.just("Pineapple", "Orange");

        return fruits.concatWith(Flux.error(new RuntimeException("Runtime Exception"))
        ).onErrorReturn("Mango");
    }

    public Flux<String> fruitsFluxOnErrorContinue() {
        return Flux.just("Pineapple", "Orange")
                .map(s -> {
                    if (s.equalsIgnoreCase("orange")) {
                        throw new RuntimeException("Exception Occured!!");
                    }
                    return s.toUpperCase();
                })
                .onErrorContinue((e, f) -> {
                    System.out.println("e-->" + e);
                    System.out.println("f-->" + f);
                }).log();
    }

    public Flux<String> fruitsFluxOnErrorMap() {
        return Flux.just("Pineapple", "Orange")
                .map(s -> {
                    if (s.equalsIgnoreCase("Orange")) {
                        throw new RuntimeException("Exception Occured!!");
                    }
                    return s.toUpperCase();
                })
                .onErrorMap(throwable -> {
                    System.out.println("throwable"+throwable);
                    return new IllegalStateException("Illegal State Exception Occured!!");
                }).log();
    }

    public Flux<String> fruitsFluxDoOnError() {
        return Flux.just("Pineapple", "Orange")
                .map(s -> {
                    if (s.equalsIgnoreCase("Orange")) {
                        throw new RuntimeException("Exception Occured!!");
                    }
                    return s.toUpperCase();
                })
                .doOnError(throwable -> {
                    System.out.println("throwable"+throwable);
                }).log();
    }
}
