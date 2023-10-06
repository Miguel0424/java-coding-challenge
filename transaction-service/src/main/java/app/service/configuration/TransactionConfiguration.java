package app.service.configuration;

import app.service.domain.CreateTransactionCommand;
import app.service.domain.GetCustomerTransactionsQuery;
import app.service.domain.GetTransactionQuery;
import app.service.store.DatabaseTransactionStore;
import app.service.store.TransactionRepository;
import app.service.store.TransactionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfiguration {
    @Bean
    public TransactionStore transactionStore(TransactionRepository transactionRepository) {
        return new DatabaseTransactionStore(transactionRepository);
    }

    @Bean
    public GetTransactionQuery getTransactionQuery(TransactionStore transactionStore) {
        return new GetTransactionQuery(transactionStore);
    }

    @Bean
    public GetCustomerTransactionsQuery getCustomerTransactionsQuery(TransactionStore transactionStore) {
        return new GetCustomerTransactionsQuery(transactionStore);
    }

    @Bean
    public CreateTransactionCommand createTransactionCommand(TransactionStore transactionStore) {
        return new CreateTransactionCommand(transactionStore);
    }
}
