package com.BankingAppSpringBoot.BankingApplication.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankingAppSpringBoot.BankingApplication.dto.AccountDto;
import com.BankingAppSpringBoot.BankingApplication.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
	
	private AccountService accountServices;
	
	
	public AccountController(AccountService accountServices) {
		super();
		this.accountServices = accountServices;
	}

	// add account rest api
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
		
		return new ResponseEntity<>(accountServices.cretateAccount(accountDto), HttpStatus.CREATED); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		
		AccountDto accountDto=accountServices.getAccountById(id);
		
		return ResponseEntity.ok(accountDto);
	}
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id,@RequestBody Map<String, Double> request){
		
		Double amount=request.get("amount"); 
		AccountDto accountDto=accountServices.deposit(id, amount);
		
		return ResponseEntity.ok(accountDto);
	}
	//withdraw
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,@RequestBody Map<String, Double> request)
	{
		Double amount=request.get("amount"); 
		AccountDto accountDto=accountServices.withdraw(id, amount);
		return ResponseEntity.ok(accountDto);
		
	}
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		
		List<AccountDto> accountDto= accountServices.getAllAccounts();
		
		return ResponseEntity.ok(accountDto);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAccount(@PathVariable Long id){
		
		accountServices.deleteAccount(id);
		Map<String, Boolean>response= new HashMap<String, Boolean>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
