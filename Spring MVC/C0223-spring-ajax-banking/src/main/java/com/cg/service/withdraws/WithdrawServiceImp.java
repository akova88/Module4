package com.cg.service.withdraws;

import com.cg.model.Withdraws;
import com.cg.repository.WithdrawsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class WithdrawServiceImp implements IWithdrawService{
@Autowired
private WithdrawsRepository withdrawsRepository;
    @Override
    public List<Withdraws> findAll() {
        return null;
    }

    @Override
    public Optional<Withdraws> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Withdraws save(Withdraws withdraws) {
        return withdrawsRepository.save(withdraws);
    }

    @Override
    public void delete(Withdraws withdraws) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
