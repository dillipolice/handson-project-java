package com.udacity.jdnd.course3.critter.user;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
public class UsersService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public UsersService(CustomerRepository customerRepository,
                        EmployeeRepository employeeRepository,
                        PetRepository petRepository) {

        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    // CUSTOMER METHODS

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Long petId) {

        Pet pet = petRepository.findById(petId).orElse(null);

        if (pet != null) {
            return pet.getOwner();
        }

        return null;
    }

    // EMPLOYEE METHODS

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable,
                                Long employeeId) {

        Employee employee =
                employeeRepository.findById(employeeId).orElse(null);

        if (employee != null) {

            employee.setDaysAvailable(daysAvailable);

            employeeRepository.save(employee);
        }
    }

    public List<Employee> findEmployeesForService(EmployeeSkill skill,
                                                  DayOfWeek day) {

        return employeeRepository.findAll()
                .stream()
                .filter(employee ->
                        employee.getDaysAvailable().contains(day)
                                &&
                                employee.getSkills().contains(skill))
                .toList();
    }
    public List<Employee> findEmployeesForService(EmployeeRequestDTO requestDTO) {

        return employeeRepository.findAll()
                .stream()
                .filter(employee ->
                        employee.getDaysAvailable()
                                .contains(requestDTO.getDate().getDayOfWeek())
                                &&
                                employee.getSkills()
                                        .containsAll(requestDTO.getSkills()))
                .toList();
    }
}