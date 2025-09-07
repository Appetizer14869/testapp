package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ClassRoomTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ClassRoom getClassRoomSample1() {
        return new ClassRoom().id(1L).roomNumber("roomNumber1").capacity(1);
    }

    public static ClassRoom getClassRoomSample2() {
        return new ClassRoom().id(2L).roomNumber("roomNumber2").capacity(2);
    }

    public static ClassRoom getClassRoomRandomSampleGenerator() {
        return new ClassRoom()
            .id(longCount.incrementAndGet())
            .roomNumber(UUID.randomUUID().toString())
            .capacity(intCount.incrementAndGet());
    }
}
