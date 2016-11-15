package org.penultimo.controller;

import javax.validation.Valid;

import org.penultimo.beans.Product;
import org.penultimo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepo;

	// Gets all products
	@GetMapping("/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productRepo.findAll());
		return "product/products";
	}

	// Gets individual products by id
	@GetMapping("/product/{id}")
	public String product(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Product p = productRepo.findOne(id);
		model.addAttribute("product", p);
		return "product/product_detail";
	}

	// Edit product view
	@GetMapping("/product/{id}/edit")
	public String productEdit(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Product p = productRepo.findOne(id);
		model.addAttribute("product", p);
		return "product/product_edit";
	}

	// goes to list with new details after editing
	@PostMapping("/product/{id}/edit")
	public String productEditSave(@PathVariable(name = "id") long id, @ModelAttribute @Valid Product product,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", product);
			return "product/product_edit";
		} else {
			productRepo.save(product);
			return "redirect:/product/" + product.getId();
		}
	}

	// delete page view
	@GetMapping("/product/{id}/delete")
	public String productDelete(Model model, @PathVariable(name = "id") long id) {
		model.addAttribute("id", id);
		Product p = productRepo.findOne(id);
		model.addAttribute("product", p);
		return "product/product_delete";
	}

	// deletes the product after submit is pressed
	@PostMapping("/product/{id}/delete")
	public String productDeleteSave(@PathVariable(name = "id") long id, @ModelAttribute @Valid Product product,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", product);
			return "product/products";
		} else {
			productRepo.delete(product);
			return "redirect:/products";
		}
	}

	// create product page view
	@GetMapping("/product/create")
	public String productCreate(Model model) {
		model.addAttribute(new Product());
		return "product/product_create";
	}

	// saves the product after creating it
	@PostMapping("/product/create")
	public String productCreate(@ModelAttribute @Valid Product product, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("product", product);
			return "product/product_create";
		} else {
			productRepo.save(product);
			return "redirect:/products";
		}

	}
}
