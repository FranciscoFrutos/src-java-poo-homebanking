package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

//	@Autowired
//	private PasswordEncoder passwordEncoder;

//	@Bean
//	public CommandLineRunner initData(
//			ClientRepository repository,
//			AccountRepository accountRepository,
//			TransactionRepository transactionRepository,
//			LoanRepository loanRepository,
//			ClientLoanRepository clientLoanRepository,
//			CardRepository cardRepository
//	) {
//		return (args -> {
//			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("*123"));
//			Client client2 = new Client("Francisco", "Frutos", "francisco_frutos@outlook.com", passwordEncoder.encode("*abc"));
//			Client admin = new Client("admin", "admin", "admin@admin", passwordEncoder.encode("admin"));
//
//			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
//			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
//			Account account3 = new Account("VIN003", LocalDate.now(), 15230740);
//			Account account4 = new Account("VIN004", LocalDate.now(), 2.50);
//			Transaction transaction1 = new Transaction(CREDIT, 100, "Description A", LocalDate.now().minusDays(1));
//			Transaction transaction2 = new Transaction(CREDIT, 20, "Description B", LocalDate.now().minusDays(2));
//			Transaction transaction3 = new Transaction(DEBIT, 200, "Description C", LocalDate.now().minusDays(5));
//			Transaction transaction4 = new Transaction(DEBIT, 230, "Description D", LocalDate.now().minusDays(8));
//			Transaction transaction5 = new Transaction(CREDIT, 20, "Description E", LocalDate.now().minusDays(3));
//			Transaction transaction6 = new Transaction(DEBIT, 0.25, "Description F", LocalDate.now().minusDays(4));
//			Transaction transaction7 = new Transaction(DEBIT, 99.99, "Description G", LocalDate.now().minusDays(10));
//			Transaction transaction8 = new Transaction(CREDIT, 1000, "Description H", LocalDate.now().minusDays(20));
//
//			repository.save(client1);
//			repository.save(client2);
//			repository.save(admin);
//
//			client1.addAccount(account1);
//			client1.addAccount(account2);
//			client2.addAccount(account3);
//			client2.addAccount(account4);
//
//			accountRepository.save(account1);
//			accountRepository.save(account2);
//			accountRepository.save(account3);
//			accountRepository.save(account4);
//
//			account1.addTransaction(transaction1);
//			account1.addTransaction(transaction2);
//			account2.addTransaction(transaction3);
//			account2.addTransaction(transaction4);
//			account3.addTransaction(transaction5);
//			account3.addTransaction(transaction6);
//			account4.addTransaction(transaction7);
//			account4.addTransaction(transaction8);
//
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//			transactionRepository.save(transaction5);
//			transactionRepository.save(transaction6);
//			transactionRepository.save(transaction7);
//			transactionRepository.save(transaction8);
//
//			Loan mortgage = new Loan("Mortgage", 500000, List.of(12,24,36,48,60));
//			Loan personal = new Loan("Personal", 100000, List.of(6, 12, 24));
//			Loan automotive = new Loan("Automotive", 300000, List.of(6,12,24,36));
//
//			loanRepository.save(mortgage);
//			loanRepository.save(personal);
//			loanRepository.save(automotive);
//
//            ClientLoan clientLoan1 = new ClientLoan(client1, mortgage, 400000, 60);
//            ClientLoan clientLoan2 = new ClientLoan(client1, personal, 50000, 12);
//            ClientLoan clientLoan3 = new ClientLoan(client2, personal, 100000, 24);
//            ClientLoan clientLoan4 = new ClientLoan(client2, automotive, 200000, 36);
//
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			clientLoanRepository.save(clientLoan3);
//			clientLoanRepository.save(clientLoan4);
//
//			Card card1 = new Card(client1.getFirstName() +" "+ client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "4485436912345678", 101, LocalDate.now(), LocalDate.now().plusYears(5) );
//			Card card2 = new Card(client1.getFirstName() +" "+ client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "5404123456789012", 111, LocalDate.now(), LocalDate.now().plusYears(5) );
//			Card card3 = new Card(client2.getFirstName() +" "+ client2.getLastName(), CardType.DEBIT, CardColor.SILVER, "6454126456382044", 176, LocalDate.now(), LocalDate.now().plusYears(5) );
//
//			client1.addCard(card1);
//			client1.addCard(card2);
//			client2.addCard(card3);
//
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
//
//
//		});
//	}
}
