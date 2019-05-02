package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        
        LOG.debug("---id = " + compensation.getEmployeeId());
        LOG.debug("---compensation = " + compensation.getSalary());
        LOG.debug("---effective on " + compensation.getEffectiveDate());

        //TODO probably should verify if valid employee id 

        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);

        Compensation comp = compensationRepository.findByEmployeeId(id);

        if (null == comp) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        LOG.debug("Found compensation of " + comp.getSalary() + " for " + id);
        
        return comp;
    }
}
