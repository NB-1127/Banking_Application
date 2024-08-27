package com.BankingAppSpringBoot.BankingApplication.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BankingAppSpringBoot.BankingApplication.dto.AccountDto;
import com.BankingAppSpringBoot.BankingApplication.mapper.AccountMapper;
import com.BankingAppSpringBoot.BankingApplication.model.Account;
import com.BankingAppSpringBoot.BankingApplication.repository.AccountRepository;
import com.BankingAppSpringBoot.BankingApplication.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	
	 private AccountRepository accountRepository;
	 
	 
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}


	@Override
	public AccountDto cretateAccount(AccountDto accountDto) {
		// TODO Auto-generated method stub
		Account account=AccountMapper.maptoAccount (accountDto); 
		Account saveAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(saveAccount);
		
	}


	@Override
	public AccountDto getAccountById(Long id) {
		
		
		Account account=accountRepository.findById(id).orElseThrow(() ->new RuntimeException("Account does not exist"));
		
		
		return AccountMapper.mapToAccountDto(account);
	}


	@Override
	public AccountDto deposit(Long id, double amount) {
		
		Account account=accountRepository.findById(id).orElseThrow(() ->new RuntimeException("Account does not exist"));
		
		
		double totalBalance=account.getBalance()+ amount;
		account.setBalance(totalBalance);
		Account saveAccount=accountRepository.save(account);
		
		return AccountMapper.mapToAccountDto(saveAccount);
	}


	@Override
	public AccountDto withdraw(Long id, double amount) {
		
		Account account=accountRepository.findById(id).orElseThrow(() ->new RuntimeException("Account does not exist"));
		
		if(account.getBalance()<amount) {
			
			throw new RuntimeException("Insufficient Balance");
		}
		
		double totalamount=account.getBalance()-amount;
		account.setBalance(totalamount);
		Account saveAccount=accountRepository.save(account);
		
		return AccountMapper.mapToAccountDto(saveAccount);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
		
		return accountRepository.findAll().stream().map((account)-> AccountMapper.mapToAccountDto(account)).
		collect(Collectors.toList());
		
		  
	}


	@Override
	public void deleteAccount(Long id) {
		Account account=accountRepository.findById(id).orElseThrow(() ->new RuntimeException("Account does not exist"));
		
		accountRepository.delete(account);
		
	}

}
