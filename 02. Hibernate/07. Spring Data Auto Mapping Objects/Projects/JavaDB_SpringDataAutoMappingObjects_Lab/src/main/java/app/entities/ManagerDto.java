package app.entities;

import java.util.List;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public int getEmployeeCount() {
        return this.employees.size();
    }

    @Override
    public String toString() {
        StringBuilder employeesInfo = new StringBuilder();
        this.employees.forEach(e -> employeesInfo.append(System.lineSeparator()).append("    - ").append(e));
        return String.format("%s %s | Employees: %d%s",
                this.getFirstName(), this.getLastName(), this.getEmployeeCount(), employeesInfo);
    }
}
