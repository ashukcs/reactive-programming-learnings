package com.techbuddy.reactive.core;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoServicesTest {

    private FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
    @Test
    void fruitsFlux() {
        Flux<String> fruitsFlux = fluxAndMonoServices.fruitsFlux();
        StepVerifier.create(fruitsFlux).expectNext("Apple", "Orange", "Banana").verifyComplete();
    }

    @Test
    void fruitMono() {
        Mono<String> fruitMono = fluxAndMonoServices.fruitMono();
        StepVerifier.create(fruitMono).expectNext("Papaya").verifyComplete();
    }

    @Test
    void fruitsFluxMap() {
        Flux<String> fruitsFlux = fluxAndMonoServices.fruitsFluxMap();

        StepVerifier.create(fruitsFlux).expectNext("APPLE", "ORANGE", "BANANA").verifyComplete();

    }

    @Test
    void fruitsFluxFilter() {
        Flux<String> fruitsFluxFilter = fluxAndMonoServices.fruitsFluxFilter(5);
        StepVerifier.create(fruitsFluxFilter).expectNext("Orange", "Banana").verifyComplete();
    }

    @Test
    void fruitsFluxFilterMap() {
        Flux<String> fruitsFluxFilterMap = fluxAndMonoServices.fruitsFluxFilterMap(5);
        StepVerifier.create(fruitsFluxFilterMap).expectNext("ORANGE", "BANANA").verifyComplete();
    }

    @Test
    void fruitMonoFlatMap() {
        Mono<List<String>> listMono = fluxAndMonoServices.fruitMonoFlatMap();
        StepVerifier.create(listMono).expectNextCount(1).verifyComplete();
    }

    @Test
    void fruitFluxFlatMapAsync() {
        Flux<String> listFlux = fluxAndMonoServices.fruitFluxFlatMapAsync();
        StepVerifier.create(listFlux).expectNextCount(17).verifyComplete();
    }

    @Test
    void fruitFluxConcatMap() {
        Flux<String> listFlux = fluxAndMonoServices.fruitFluxConcatMap();
        StepVerifier.create(listFlux).expectNextCount(17).verifyComplete();
    }

    @Test
    void fruitFluxFlatMapMany() {
        Flux<String> listStringFlux = fluxAndMonoServices.fruitFluxFlatMapMany();
        StepVerifier.create(listStringFlux).expectNextCount(5).verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        Flux<String> transform = fluxAndMonoServices.fruitsFluxTransform(5);
        StepVerifier.create(transform).expectNext("Orange","Banana").verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {
        Flux<String> transform = fluxAndMonoServices.fruitsFluxTransformDefaultIfEmpty(10);
        StepVerifier.create(transform).expectNext("default").verifyComplete();
    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        Flux<String> transformSwitchIfEmpty = fluxAndMonoServices.fruitsFluxTransformSwitchIfEmpty(8);
        StepVerifier.create(transformSwitchIfEmpty)
                .expectNext("Pineapple","Jack Fruit")
                .verifyComplete();
    }

    @Test
    void fruitFluxConcat() {
        Flux<String> concatFlux = fluxAndMonoServices.fruitFluxConcat();
        StepVerifier.create(concatFlux)
                .expectNext("Pineapple", "Orange", "Jack Fruit", "Tomato")
                .verifyComplete();
    }

    @Test
    void fruitFluxConcatWith() {
        Flux<String> concatFlux = fluxAndMonoServices.fruitFluxConcatWith();
        StepVerifier.create(concatFlux)
                .expectNext("Pineapple", "Orange", "Jack Fruit", "Tomato")
                .verifyComplete();
    }

    @Test
    void fruitMonoConcatWith() {
        Flux<String> concatMono = fluxAndMonoServices.fruitMonoConcatWith();
        StepVerifier.create(concatMono)
                .expectNext("Pineapple", "Tomato")
                .verifyComplete();
    }

    @Test
    void givenFluxes_whenCombileLatestIsInvoked_thenCombineLatest() {
        Flux<Integer> combineLatest = fluxAndMonoServices.givenFluxes_whenCombileLatestIsInvoked_thenCombineLatest();

        StepVerifier.create(combineLatest).expectNextCount(3).verifyComplete();
    }

    @Test
    void fruitsFluxMerge() {

        Flux<String> fluxMerge = fluxAndMonoServices.fruitsFluxMerge();
        StepVerifier.create(fluxMerge)
                .expectNext("Pineapple","Orange","Jack Fruit", "Tomato")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergeWith() {
        Flux<String> fluxMerge = fluxAndMonoServices.fruitsFluxMerge();
        StepVerifier.create(fluxMerge)
                .expectNext("Pineapple","Orange","Jack Fruit", "Tomato")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergeWithSequential() {
        Flux<String> fluxMerge = fluxAndMonoServices.fruitsFluxMergeWithSequential();
        StepVerifier.create(fluxMerge)
                .expectNext("Pineapple","Orange","Jack Fruit", "Tomato")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterDoOn() {
        Flux<String> fruitsFluxFilter = fluxAndMonoServices.fruitsFluxFilterDoOn(5);
        StepVerifier.create(fruitsFluxFilter).expectNext("Orange", "Banana").verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorReturn() {
        Flux<String> fruitsFluxOnErrorReturn = fluxAndMonoServices.fruitsFluxOnErrorReturn().log();
        StepVerifier.create(fruitsFluxOnErrorReturn).expectNext("Pineapple", "Orange", "Mango").verifyComplete();

    }

    @Test
    void fruitsFluxOnErrorContinue() {
        Flux<String> fruitsFluxOnErrorContinue = fluxAndMonoServices.fruitsFluxOnErrorContinue();
        StepVerifier.create(fruitsFluxOnErrorContinue)
                .expectNext("PINEAPPLE", "ORANGE", "Mango")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void fruitsFluxOnErrorMap() {
        Flux<String> fruitsFluxOnErrorMap = fluxAndMonoServices.fruitsFluxOnErrorMap();
        StepVerifier.create(fruitsFluxOnErrorMap)
                .expectNext("PINEAPPLE")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void fruitsFluxDoOnError() {
        Flux<String> fruitsFluxDoOnError = fluxAndMonoServices.fruitsFluxDoOnError();
        StepVerifier.create(fruitsFluxDoOnError)
                .expectNext("PINEAPPLE")
                .expectError(RuntimeException.class)
                .verify();
    }
}