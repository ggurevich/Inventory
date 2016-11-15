package org.penultimo.controller;

import javax.validation.Valid;

import org.penultimo.beans.Transaction;
import org.penultimo.repository.CustomerRepository;
import org.penultimo.repository.ProductRepository;
import org.penultimo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private TransactionRepository transactionRepo;

	/*
	 * CREATE METHODS
	 */

	// create transaction page view
	@GetMapping("/transaction/create")
	public String transactionCreate(Model model) {
		model.addAttribute(new Transaction());
		return "transaction/transaction_create";
	}

	// saves the transaction after creating it
	@PostMapping("/transaction/create")
	public String transactionCreate(@ModelAttribute @Valid Transaction transaction, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "transaction/transaction_create";
		} else {
			transactionRepo.save(transaction);
			return "redirect:/transactions";
		}
	}

	/*
	 * Edit Methods
	 */
	@GetMapping("/transaction/{id}/edit")
	public String transactionEdit(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Transaction t = transactionRepo.findOne(id);
		model.addAttribute("transaction", t);
		model.addAttribute("customers", customerRepo.findAll());
		model.addAttribute("products", productRepo.findAll());
		return "transaction/transaction_edit";
	}
	
	// goes to list with new details after editing
		@PostMapping("/transaction/{id}/edit")
		public String transactionEditSave(@PathVariable(name = "id") long id, @ModelAttribute @Valid Transaction transaction,
				BindingResult result, Model model) {
			if (result.hasErrors()) {
				model.addAttribute("transaction", transaction);
				return "transaction/transaction_edit";
			} else {
				transactionRepo.save(transaction);
				return "redirect:/transaction/" + transaction.getId();
			}
		}

	/*
	 * READ Methods
	 */
		
		// Gets all transactions
		@GetMapping("/transactions")
		public String getTransactions(Model model) {
			model.addAttribute("transactions", transactionRepo.findAll());
			return "transaction/transactions";
		}

		// Gets individual products by id
		@GetMapping("/transaction/{id}")
		public String transaction(Model model, @PathVariable(name = "id") long id) {
			model.addAttribute("id", id);
			Transaction t = transactionRepo.findOne(id);
			model.addAttribute("transaction", t);
			return "transaction/transaction_detail";
		}

	/*
	 * DELETE Methods
	 */
	
	// delete page view
		@GetMapping("/transaction/{id}/delete")
		public String transactionDelete(Model model, @PathVariable(name = "id") long id) {
			model.addAttribute("id", id);
			Transaction t = transactionRepo.findOne(id);
			model.addAttribute("transaction", t);
			return "transaction/transaction_delete";
		}

		// deletes the product after submit is pressed
		@PostMapping("/transaction/{id}/delete")
		public String transactionDeleteSave(@PathVariable(name = "id") long id, @ModelAttribute @Valid Transaction transaction,
				BindingResult result, Model model) {
			if (result.hasErrors()) {
				model.addAttribute("transaction", transaction);
				return "transaction/transaction";
			} else {
				transactionRepo.delete(transaction);
				return "redirect:/transactions";
			}
		}
}
