package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Francisco", "Frutos", "francisco_frutos@outlook.com");
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account("VIN003", LocalDate.now(), 15230740);
			Account account4 = new Account("VIN004", LocalDate.now(), 2.50);
			Transaction transaction1 = new Transaction(CREDIT, 100, "Description A", LocalDate.now().minusDays(1));
			Transaction transaction2 = new Transaction(CREDIT, 20, "Description B", LocalDate.now().minusDays(2));
			Transaction transaction3 = new Transaction(DEBIT, 200, "Description C", LocalDate.now().minusDays(5));
			Transaction transaction4 = new Transaction(DEBIT, 230, "Description D", LocalDate.now().minusDays(8));
			Transaction transaction5 = new Transaction(CREDIT, 20, "Description E", LocalDate.now().minusDays(3));
			Transaction transaction6 = new Transaction(DEBIT, 0.25, "Description F", LocalDate.now().minusDays(4));
			Transaction transaction7 = new Transaction(DEBIT, 99.99, "Description G", LocalDate.now().minusDays(10));
			Transaction transaction8 = new Transaction(CREDIT, 1000, "Description H", LocalDate.now().minusDays(20));

			repository.save(client1);
			repository.save(client2);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account3.addTransaction(transaction5);
			account3.addTransaction(transaction6);
			account4.addTransaction(transaction7);
			account4.addTransaction(transaction8);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);




		});
	}
}
