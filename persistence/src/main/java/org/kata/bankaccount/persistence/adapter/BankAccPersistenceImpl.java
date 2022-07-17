package org.kata.bankaccount.persistence.adapter;

import org.kata.bankaccount.domain.model.Operation;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;
import org.kata.bankaccount.persistence.entity.Transaction;
import org.kata.bankaccount.persistence.repository.TransactionRepository;
import org.kata.bankaccount.persistence.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccPersistenceImpl implements BankAccPersistencePort {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Operation> loadOperations() {
        return transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "date")).stream().map(EntityConverter::toOperationModel).toList();
    }

    @Override
    public Operation saveOperation(Operation operation) {
        Transaction savedEntity = transactionRepository.save(EntityConverter.fromOperationModel(operation));
        return EntityConverter.toOperationModel(savedEntity);
    }
}
