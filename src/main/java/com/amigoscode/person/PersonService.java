package com.amigoscode.person;

import com.amigoscode.SortingOrder;
import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPerson(
            SortingOrder sort
    ) {
        if (sort == SortingOrder.ASC) {
            return personRepository.getPeople().stream().sorted(Comparator.comparing(Person::getId)).collect(Collectors.toList());
        }
        return personRepository.getPeople().stream().sorted(Comparator.comparing(Person::getId).reversed()).collect(Collectors.toList());
    }

    public Person getPersonById(
            Integer id
    ) {
        return personRepository.getPeople().stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " does not exist"));

    }

    public void deletePersonById(Integer id) {
        Person person = personRepository.getPeople().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " does not exist"));

        personRepository.getPeople().remove(person);
    }

    public void addPerson(NewPersonRequest person) {


        if (person.name() != null && !person.name().isEmpty()) {
            boolean exist = personRepository.getPeople().stream()
                    .anyMatch(p -> p.getName().equals(person.name()));

            if(exist){
                throw new DuplicateResourceException("Name taken");
            }
        }

        personRepository.getPeople().add(new Person(
                personRepository.getIdCounter().incrementAndGet(),
                person.name(),
                person.age(),
                person.gender()
        ));
    }

    public void updatePerson(
            Integer id,
            PersonUpdate update
    ) {
        Person p = personRepository.getPeople().stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()

                .orElseThrow(() -> new ResourceNotFoundException("Person with id: " + id + " does not exist"));

        var index = personRepository.getPeople().indexOf(p);

        if (update.name() != null && !update.name().isEmpty() && !update.name().equals(p.getName())) {
            Person person = new Person(
                    p.getId(),
                    update.name(),
                    p.getAge(),
                    p.getGender()
            );
            personRepository.getPeople().set(index, person);
        }
        if (update.age() != null && update.age() > 0 && !update.age().equals(p.getAge())) {
            Person person = new Person(
                    p.getId(),
                    p.getName(),
                    update.age(),
                    p.getGender()
            );
            personRepository.getPeople().set(index, person);
        }

    }
}
