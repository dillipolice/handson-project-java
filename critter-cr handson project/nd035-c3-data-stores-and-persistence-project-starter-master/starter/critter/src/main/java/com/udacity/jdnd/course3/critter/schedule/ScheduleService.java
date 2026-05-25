package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           PetRepository petRepository,
                           EmployeeRepository employeeRepository) {

        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
    }

    public Schedule save(Schedule schedule,
                         List<Long> petIds,
                         List<Long> employeeIds) {

        List<Pet> pets = petRepository.findAllById(petIds);

        List<Employee> employees =
                employeeRepository.findAllById(employeeIds);

        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
    public List<Schedule> getScheduleForEmployee(Long employeeId) {

        return scheduleRepository.findAll()
                .stream()
                .filter(schedule ->
                        schedule.getEmployees()
                                .stream()
                                .anyMatch(employee ->
                                        employee.getId().equals(employeeId)))
                .toList();
    }
    public List<Schedule> getScheduleForPet(Long petId) {

        return scheduleRepository.findAll()
                .stream()
                .filter(schedule ->
                        schedule.getPets()
                                .stream()
                                .anyMatch(pet ->
                                        pet.getId().equals(petId)))
                .toList();
    }
    public List<Schedule> getScheduleForCustomer(Long customerId) {

        return scheduleRepository.findAll()
                .stream()
                .filter(schedule ->
                        schedule.getPets()
                                .stream()
                                .anyMatch(pet ->
                                        pet.getOwner() != null &&
                                                pet.getOwner().getId().equals(customerId)))
                .toList();
    }
}