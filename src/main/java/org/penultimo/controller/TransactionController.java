package org.penultimo.controller;

import javax.validation.Valid;

import org.penultimo.beans.Transaction;
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
	private TransactionRepository transactionRepo;

	// gets all transactions
	@GetMapping("/transactions")
	public String getTransactions(Model model) {
		model.addAttribute("transactions", transactionRepo.findAll());
		return "transactions/transactions";
	}

	// create transaction page view
		@GetMapping("/transaction/create")
		public String transactionCreate(Model model) {
			model.addAttribute(new Transaction());
			return "transactions/transaction_create";
		}

		// saves the transaction after creating it
		@PostMapping("/transaction/create")
		public String transactionCreate(@ModelAttribute @Valid Transaction transaction, BindingResult result, Model model) {

			if (result.hasErrors()) {
				model.addAttribute("transaction", transaction);
				return "transactions/product_create";
			} else {
				transactionRepo.save(transaction);
				return "redirect:/transactions";
			}

		}
	//delete transaction view
	@GetMapping("/transaction/{id}/delete")
	public String transactionDelete(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Transaction t = transactionRepo.findOne(id);
		model.addAttribute("transaction", t);
		return "transactions/transaction_delete";
	}

	//removes deleted transaction
	@PostMapping("/transaction/{id}/delete")
	public String transactionDeleteSave(@PathVariable(name = "id") long id,
			@ModelAttribute @Valid Transaction transaction, BindingResult result, Model model) {

		transactionRepo.delete(transaction);
		return "redirect:/transactions";

	}

	// get detailed view for transactions
	@GetMapping("/transaction/{id}")
	public String transaction(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Transaction t = transactionRepo.findOne(id);
		model.addAttribute("transaction", t);
		return "transactions/transaction_detail";
	}

	// Edit transaction view
	@GetMapping("/transaction/{id}/edit")
	public String transactionEdit(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Transaction t = transactionRepo.findOne(id);
		model.addAttribute("transaction", t);
		return "transactions/transaction_edit";
	}

	// goes to list with new details after editing
	@PostMapping("/transaction/{id}/edit")
	public String transactionEditSave(@PathVariable(name = "id") long id,
			@ModelAttribute @Valid Transaction transaction, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("transaction", transaction);
			return "transactions/transaction_edit";
		} else {
			transactionRepo.save(transaction);
			return "redirect:/transactions/" + transaction.getId();
		}
	}

}
