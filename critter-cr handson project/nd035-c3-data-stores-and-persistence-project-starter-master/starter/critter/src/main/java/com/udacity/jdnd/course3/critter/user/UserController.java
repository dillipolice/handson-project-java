package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UsersService userService;

    public UserController(UsersService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());

        Customer savedCustomer = userService.saveCustomer(customer);

        CustomerDTO dto = new CustomerDTO();

        dto.setId(savedCustomer.getId());
        dto.setName(savedCustomer.getName());
        dto.setPhoneNumber(savedCustomer.getPhoneNumber());
        dto.setNotes(savedCustomer.getNotes());

        return dto;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {

        return userService.getAllCustomers()
                .stream()
                .map(customer -> {

                    CustomerDTO dto = new CustomerDTO();

                    dto.setId(customer.getId());
                    dto.setName(customer.getName());
                    dto.setPhoneNumber(customer.getPhoneNumber());
                    dto.setNotes(customer.getNotes());

                    dto.setPetIds(
                            customer.getPets()
                                    .stream()
                                    .map(Pet::getId)
                                    .toList()
                    );

                    return dto;
                })
                .toList();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        Employee savedEmployee = userService.saveEmployee(employee);

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(savedEmployee.getId());
        dto.setName(savedEmployee.getName());
        dto.setSkills(savedEmployee.getSkills());
        dto.setDaysAvailable(savedEmployee.getDaysAvailable());

        return dto;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        Employee employee = userService.getEmployee(employeeId);

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());

        return dto;
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {

        List<Employee> employees =
                userService.findEmployeesForService(employeeDTO);

        return employees.stream().map(employee -> {

            EmployeeDTO dto = new EmployeeDTO();

            dto.setId(employee.getId());
            dto.setName(employee.getName());
            dto.setSkills(employee.getSkills());
            dto.setDaysAvailable(employee.getDaysAvailable());

            return dto;
        }).toList();
    }
    

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable Long petId) {

        Customer customer = userService.getOwnerByPet(petId);

        CustomerDTO dto = new CustomerDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setNotes(customer.getNotes());

        List<Long> petIds = customer.getPets()
                .stream()
                .map(Pet::getId)
                .toList();

        dto.setPetIds(petIds);

        return dto;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) {

        userService.setAvailability(daysAvailable, employeeId);
    }
}