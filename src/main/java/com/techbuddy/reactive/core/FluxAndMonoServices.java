package com.techbuddy.reactive.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoServices {
    public Flux<String> fruitsFlux(){
        return Flux.fromIterable(Arrays.asList("Apple", "Orange", "Banana")).log();
    }

    public Mono<String> fruitMono(){
        return Mono.just("Papaya").log();
    }

    public static void main(String[] args){
        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
        fluxAndMonoServices.fruitsFlux().subscribe(s -> System.out.println("Flux Item : " + s));

        fluxAndMonoServices.fruitMono().subscribe(s -> System.out.println("Mono Item : " + s));
    }
}
