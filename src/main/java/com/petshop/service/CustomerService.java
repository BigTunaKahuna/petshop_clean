package com.petshop.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.petshop.dao.CustomerDao;

public class CustomerService {
	@Autowired
	CustomerDao customerDao;
	
}
