package com;

import java.util.Date;

public interface EmployeeRepositoryCustom {
	 
    public long getMaxEmpId();
     
    public long updateEmployee(String empNo, String fullName, Date hireDate);
     
}
