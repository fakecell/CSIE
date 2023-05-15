package com.smartcycling.backend.service;

public interface Function<T, E> {

    T callback(E e);

}