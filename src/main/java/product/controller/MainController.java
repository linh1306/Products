package product.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import product.Model.Product;

@Controller
public class MainController {
	private List<Product> products = buildProducts();
	
	public List<Product> getProducts() {
		return products;
	}
	
	public int indexProduct(String code) {
		int index = 0;
		for(Product product : products) {
			if(code.equals(product.getCode())) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public void addProduct(Product product) {
		products.add(product);
	}
	
	public void removeProduct(String code) {
		for(Product product : products) {
			if(product.getCode() == code) {
				products.remove(product);
				break;
			}
		}
	}

	public static List<Product> buildProducts() {
	    List<Product> products = new ArrayList<>();
	    products.add(new Product("KI49", "CPU", 250));
	    products.add(new Product("DS21", "RAM", 100));
	    products.add(new Product("GT67", "Ổ cứng SSD", 150));
	    products.add(new Product("Gk72", "Ổ cứng HDD", 120));
	    products.add(new Product("UF82", "Card đồ họa", 350));
	    products.add(new Product("BR38", "Nguồn máy tính", 80));
	    products.add(new Product("TR55", "Vỏ case", 50));
	    products.add(new Product("PL75", "Bàn phím cơ", 120));
	    products.add(new Product("QW63", "Chuột gaming", 70));
	    products.add(new Product("XM94", "Mh gaming", 250));
	    products.add(new Product("YN72", "Mh 4K", 700));
	    products.add(new Product("LC72", "Lót chuột", 5));
	    products.add(new Product("JM72", "Bàn gaming", 70));
	    
	    
	    return products;
	}
	
	@GetMapping("/products")
	public String product(Model model) {
		model.addAttribute("products", getProducts());
		return "products";
	}
	
	@GetMapping("/edit/{productCode}")
	public String productEdit(Model model , @PathVariable String productCode) {
		if(productCode.equals("new")) {
			model.addAttribute("title", "New product");
			model.addAttribute("product", new Product("", "", 0));
		}else {
			model.addAttribute("title", "Change product");
			model.addAttribute("product", products.get(indexProduct(productCode)));
		}
		model.addAttribute("productCode", productCode);
		return "edit";
	}
	
	@GetMapping("/product/{productCode}")
	public String product(Model model , @PathVariable String productCode) {
		model.addAttribute("product", products.get(indexProduct(productCode)));
		model.addAttribute("productCode", productCode);
		return "delete";
	}
	
	@PostMapping("/save/{productCode}")
	public String editProduct(@ModelAttribute("product") Product product, @PathVariable String productCode, Model model) {
		if(productCode.equals("new")){
			model.addAttribute("title", "New product");
			int index = indexProduct(product.getCode());
			if(index >= 0) {
				model.addAttribute("errorCode", "san pham da ton tai");
				model.addAttribute("product", product);
				return "edit";
			}else {
				products.add(product);
			}
		}else {
			model.addAttribute("title", "Change product");
			int index = indexProduct(productCode);
			int indexP = indexProduct(product.getCode());
			if(index != indexP && indexP >= 0) {
				model.addAttribute("errorCode", "code bị trung");
				model.addAttribute("product", product);
				return "edit";
			}
			products.set(index, product);
		}
		return "redirect:/products";
	}
	
	@PostMapping("/delete/{productCode}")
	public String deleteProduct(@PathVariable String productCode, Model model) {
		int index = indexProduct(productCode);
		products.remove(index);
		return "redirect:/products";
	}
}
