package com.amigoscode.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PersonRepository {

    private static final AtomicInteger idCounter = new AtomicInteger(0);

    @Bean
    CommandLineRunner commandLineRunner(ObjectMapper objectMapper) throws JsonProcessingException {
        String personString = "{\"id\":1,\"name\":\"John Doe\",\"age\":23}";
        Person person = objectMapper.readValue(personString, Person.class);
        System.out.println(person);
        return args -> {

        };
    }

    private static final List<Person> people = new ArrayList<>();

    static {
        people.add(new Person(idCounter.incrementAndGet(), "John", 33, Gender.MALE));
        people.add(new Person(idCounter.incrementAndGet(), "Jennifer", 44, Gender.FEMALE));
        people.add(new Person(idCounter.incrementAndGet(), "Jenna", 20, Gender.FEMALE));
    }

    public AtomicInteger getIdCounter() {
        return idCounter;
    }

    public  List<Person> getPeople() {
        return people;
    }
}
