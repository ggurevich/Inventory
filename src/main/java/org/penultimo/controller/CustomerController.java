package org.penultimo.controller;

import javax.validation.Valid;

import org.penultimo.beans.Customer;
import org.penultimo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepo;

	// Gets all customers
	@GetMapping("/customers")
	public String getCustomers(Model model) {
		model.addAttribute("customers", customerRepo.findAll());
		return "customer/customers";
	}

	// Gets individual customers by id.
	@GetMapping("/customer/{id}")
	public String customer(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Customer c = customerRepo.findOne(id);
		model.addAttribute("customer", c);
		return "customer/customer_detail";
	}

	// Gets the edit view for a single customer
	@GetMapping("/customer/{id}/edit")
	public String customerEdit(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Customer c = customerRepo.findOne(id);
		model.addAttribute("customer", c);
		return "customer/customer_edit";
	}

	// After the user submits in the edit view, this posts the new details and
	// goes to the list.
	@PostMapping("/customer/{id}/edit")
	public String customerEditSave(@PathVariable(name = "id") long id, @ModelAttribute @Valid Customer customer,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "customer/customer_edit";
		} else {
			customerRepo.save(customer);
			return "redirect:/customer/" + customer.getId();
		}
	}

	// Gets the delete view.
	@GetMapping("/customer/{id}/delete")
	public String customerDelete(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Customer c = customerRepo.findOne(id);
		model.addAttribute("customer", c);
		return "customer/customer_delete";
	}

	// Deletes the customer after the user clicks submit.
	@PostMapping("/customer/{id}/delete")
	public String customerDeleteSave(@PathVariable(name = "id") long id, @ModelAttribute @Valid Customer customer,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "customer/customers";
		} else {
			customerRepo.delete(customer);
			return "redirect:/customers";
		}
	}

	// Goes to the create new user view.
	@GetMapping("/customer/create")
	public String customerCreate(Model model) {
		model.addAttribute(new Customer());
		return "customer/customer_create";
	}

	// Creates the new user.
	@PostMapping("/customer/create")
	public String customerCreate(@ModelAttribute @Valid Customer customer, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("customer", customer);
			return "customer/customer_create";
		} else {
			customerRepo.save(customer);
			return "redirect:/customers";
		}

	}

}
