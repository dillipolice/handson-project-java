package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository,
                      CustomerRepository customerRepository) {

        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet save(Pet pet, Long ownerId) {

        Customer customer =
                customerRepository.findById(ownerId).orElse(null);

        pet.setOwner(customer);

        Pet savedPet = petRepository.save(pet);

        if (customer != null) {
            customer.getPets().add(savedPet);
            customerRepository.save(customer);
        }

        return savedPet;
    }

    public Pet getPet(Long petId) {
        return petRepository.findById(petId).orElse(null);
    }

    public List<Pet> getPetsByOwner(Long ownerId) {

        Customer customer =
                customerRepository.findById(ownerId).orElse(null);

        if (customer != null) {
            return customer.getPets();
        }

        return List.of();
    }
}