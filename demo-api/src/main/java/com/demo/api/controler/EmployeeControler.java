package com.demo.api.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.api.model.Employee;
import com.demo.api.repository.EmployeeRepository;

@RestController
public class EmployeeControler {

	private final EmployeeRepository employeeRepository;
	
	EmployeeControler(EmployeeRepository employeeRepository){
		this.employeeRepository = employeeRepository;
	}
	
	@GetMapping("/employee")
	List<Employee>all() {
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employee")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return employeeRepository.save(newEmployee);
	}
	
	@GetMapping("/employee/{id}")
	Optional<Employee> getByID(@PathVariable(value = "id") Long id) {
		return employeeRepository.findById(id);
	}
	
	@PutMapping("/employee/{id}")
	Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return employeeRepository.findById(id)
				.map(employee -> {
					employee.setFirstName(newEmployee.getFirstName());
					employee.setLastName(newEmployee.getLastName());
					return employeeRepository.save(employee);
				})
				.orElseGet(()->{
					newEmployee.setId(id);
					return employeeRepository.save(newEmployee);
				});
	}
	
	@DeleteMapping("/employee/{id}")
	void deleteEmployee(@PathVariable long id) {
		employeeRepository.deleteById(id);
	}
	
}