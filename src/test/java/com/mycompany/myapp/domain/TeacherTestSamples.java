package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TeacherTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Teacher getTeacherSample1() {
        return new Teacher().id(1L).firstName("firstName1").lastName("lastName1").email("email1").phoneNumber("phoneNumber1").salary(1L);
    }

    public static Teacher getTeacherSample2() {
        return new Teacher().id(2L).firstName("firstName2").lastName("lastName2").email("email2").phoneNumber("phoneNumber2").salary(2L);
    }

    public static Teacher getTeacherRandomSampleGenerator() {
        return new Teacher()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .salary(longCount.incrementAndGet());
    }
}
