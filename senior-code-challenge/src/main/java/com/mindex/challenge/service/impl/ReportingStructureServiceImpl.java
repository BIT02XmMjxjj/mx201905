package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import java.util.UUID;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
    
    @Autowired
    private EmployeeRepository employeeRepository;

    private Set<String> allReportsIds = null; 
    private void addAllReports(Employee emp) {
        // LOG.debug (" checking reports of " + emp.getEmployeeId());

        List<Employee> reports = emp.getDirectReports();
        if (null == reports) {
            // LOG.debug (" >>>> no reports for " + emp.getEmployeeId());
            return; // no reports to process
        }

        // for every direct report 
        for (Employee e : reports) {
            // retreive the report from the repository
            Employee newReport = employeeRepository.findByEmployeeId(e.getEmployeeId());

            // add their id to the set of all reports
            allReportsIds.add(newReport.getEmployeeId());

            // get any reports of the direct reports
            addAllReports(newReport);
        }
    }

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("reading Reporting structure for employee with id [{}]", id);

        ReportingStructure reportingStructure = new ReportingStructure();
        if (null == reportingStructure) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        Employee manager = employeeRepository.findByEmployeeId(id);
        if (null == manager) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        reportingStructure.setEmployee(manager);
        allReportsIds = new HashSet<>(); // start with a clean set

        addAllReports(manager); // fill in the allReportsIds hasSet recursively
        LOG.debug("number of reports is " + allReportsIds.size());

        reportingStructure.setNumberOfReports(allReportsIds.size());
        allReportsIds = null; // free up memory

        return reportingStructure;
    }
}
