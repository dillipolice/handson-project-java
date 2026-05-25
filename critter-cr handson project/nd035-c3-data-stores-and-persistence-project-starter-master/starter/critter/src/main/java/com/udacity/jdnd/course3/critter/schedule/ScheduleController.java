package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")

public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        Schedule savedSchedule = scheduleService.save(
                schedule,
                scheduleDTO.getPetIds(),
                scheduleDTO.getEmployeeIds()
        );

        ScheduleDTO dto = new ScheduleDTO();

        dto.setId(savedSchedule.getId());
        dto.setDate(savedSchedule.getDate());
        dto.setActivities(savedSchedule.getActivities());

        dto.setPetIds(
                savedSchedule.getPets()
                        .stream()
                        .map(Pet::getId)
                        .toList()
        );

        dto.setEmployeeIds(
                savedSchedule.getEmployees()
                        .stream()
                        .map(Employee::getId)
                        .toList()
        );

        return dto;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.getAllSchedules()
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.getScheduleForPet(petId)
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return scheduleService.getScheduleForEmployee(employeeId)
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        return scheduleService.getScheduleForCustomer(customerId)
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDate(schedule.getDate());
                    dto.setActivities(schedule.getActivities());

                    dto.setPetIds(
                            schedule.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    dto.setEmployeeIds(
                            schedule.getEmployees()
                                    .stream()
                                    .map(Employee::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }
}
