package com.amigoscode.person;

import com.amigoscode.validation.Foo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record NewPersonRequest(
        @NotEmpty(message = "Name must not be empty") String name,
        @Min(16) Integer age,
        @NotNull(message = "Gender must not be null") Gender gender) {

    public NewPersonRequest(String name, Integer age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NewPersonRequest person = (NewPersonRequest) o;
        return Objects.equals(name, person.name) && Objects.equals(age, person.age) && gender == person.gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
