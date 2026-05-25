package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = new Pet();

        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        Pet savedPet = petService.save(pet, petDTO.getOwnerId());

        PetDTO dto = new PetDTO();

        dto.setId(savedPet.getId());
        dto.setName(savedPet.getName());
        dto.setType(savedPet.getType());
        dto.setBirthDate(savedPet.getBirthDate());
        dto.setNotes(savedPet.getNotes());
        dto.setOwnerId(savedPet.getOwner().getId());

        return dto;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet pet = petService.getPet(petId);

        PetDTO dto = new PetDTO();

        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());

        if (pet.getOwner() != null) {
            dto.setOwnerId(pet.getOwner().getId());
        }

        return dto;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.getPetsByOwner(ownerId)
                .stream()
                .map(pet -> {

                    PetDTO dto = new PetDTO();

                    dto.setId(pet.getId());
                    dto.setName(pet.getName());
                    dto.setType(pet.getType());
                    dto.setBirthDate(pet.getBirthDate());
                    dto.setNotes(pet.getNotes());

                    if (pet.getOwner() != null) {
                        dto.setOwnerId(pet.getOwner().getId());
                    }

                    return dto;
                })
                .toList();
    }
}
