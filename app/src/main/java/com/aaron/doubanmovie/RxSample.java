package com.aaron.doubanmovie;

import java.util.Arrays;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Chenll on 2016/10/19.
 */

public class RxSample {

    public static void main(String[] args) throws InterruptedException {
        new RxSample().testSubscribeOn();

        Thread.sleep(Long.MAX_VALUE);

        System.out.println("App finished.");
    }

    public void testSubscribeOn() {
        Observable
                .create(subscriber -> {
                    System.out.println("produce on " + Thread.currentThread().getName());
                    subscriber.onNext("1");
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(s -> {
                    System.out.println("got " + s + " on " + Thread.currentThread().getName());
                    return Observable.just(s);
                })
                .subscribe(s -> {
                    System.out.println("got " + s + " on " + Thread.currentThread().getName());
                });
    }

    public void testFlatMap() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("1");
                        subscriber.onCompleted();
                    }
                })
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                subscriber.onNext(Integer.valueOf(s));
                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    public void testLift() {
        Observable
                .just("1")
                .lift(new Observable.Operator<Integer, String>() {
                    @Override
                    public Subscriber<? super String> call(Subscriber<? super Integer> subscriber) {
                        return new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(String s) {
                                subscriber.onNext(Integer.valueOf(s));
                            }
                        };
                    }
                })
                .subscribe(System.out::println);
    }

    public void testReduce() {
        Observable
                .from(Arrays.asList(1, 2, 3, 4, 5))
                .reduce((integer1, integer2) -> {
                    System.out.println("Receive new item " + integer2 + ", sum " + integer2);
                    return integer1 + integer2;
                })
                .subscribe(System.out::println);
    }

    public void testFrom() {
        Observable
                .from(Arrays.asList("Hello", "World"))
                .subscribe(word -> {
                    System.out.println("Got word " + word);
                });
    }

    public void testMap() {
        Observable
                .create(subscriber -> {
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                })
                .map(integer -> {
                    return String.valueOf(integer);
                })
                .subscribe(value -> {
                    System.out.println("got " + value + " , type is " + value.getClass().getCanonicalName());
                });
    }

    public void testJust() {
        Observable
                .just("Hello world")
                .subscribe(word -> {
                    System.out.println("Got word " + word);
                });
    }

    public void testCreate() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("1");
                        subscriber.onCompleted();
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }
                });
    }

    public void subscribeOn() {
        Observable
                .just("1")
                .subscribeOn(Schedulers.immediate())
                .flatMap(s -> {
                    System.out.println("Thread 2 " + Thread.currentThread().getName());
                    return Observable.just("2");
                })
                .subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    System.out.println("Thread 3 " + Thread.currentThread().getName());
                    return Observable.just("3");
                })
                .subscribeOn(Schedulers.newThread())
                .flatMap(s -> {
                    System.out.println("Thread 4 " + Thread.currentThread().getName());
                    return Observable.just("4");
                })
                .subscribe();
    }

    public void testSusbcribe() {
        Observable
                .just("")
                .flatMap(word -> Observable.create(subscriber -> {
                    if ("".equalsIgnoreCase(word)) {
                        subscriber.onError(new Exception("Word is empty."));
                        return;
                    }

                    subscriber.onNext(word);
                    subscriber.onCompleted();
                }))
                .subscribe();
    }

}
