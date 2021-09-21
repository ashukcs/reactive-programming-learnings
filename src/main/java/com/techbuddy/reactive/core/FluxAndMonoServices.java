package com.techbuddy.reactive.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoServices {
    public Flux<String> fruitsFlux(){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana")).log();
    }

    public Flux<String> fruitsFluxMap(){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> fruitsFluxFilter(int number){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .filter(s->s.length()>number)
                .log();
    }

    public Flux<String> fruitsFluxFilterMap(int number){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .filter(s->s.length()>number)
                .map(String::toUpperCase)
                .log();
    }

    public Mono<String> fruitMono(){
        return Mono.just("Papaya").log();
    }

    public Flux<String> fruitFluxFlatMapAsync(){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .flatMap(s->Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)
                )).log();
    }

    public Flux<String> fruitFluxConcatMap(){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .concatMap(s->Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)
                )).log();
    }

    public Mono<List<String>> fruitMonoFlatMap(){
        return Mono.just("Papaya").flatMap(s->Mono.just(Arrays.asList(s.split("")))).log();
    }

    public Flux<String> fruitFluxFlatMapMany(){
        return Mono.just("Apple")
                .flatMapMany(s->Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxTransform(int number){
        Function<Flux<String>,Flux<String>> filterData = data->data.filter(s->s.length()>number);
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .transform(filterData).log();
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number){
        Function<Flux<String>,Flux<String>> filterData = data->data.filter(s->s.length()>number);
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana"))
                .transform(filterData)
                .defaultIfEmpty("default")
                .log();
    }
    public static void main(String[] args){
        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
        fluxAndMonoServices.fruitsFlux().subscribe(s -> System.out.println("Flux Item : " + s));

        fluxAndMonoServices.fruitMono().subscribe(s -> System.out.println("Mono Item : " + s));
    }
}
