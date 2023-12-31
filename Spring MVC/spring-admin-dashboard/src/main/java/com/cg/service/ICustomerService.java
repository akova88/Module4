package com.cg.service;

import com.cg.model.Customer;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long>{
    List<Customer> findAllByFullNameLike(String fullName);
    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone);

}
