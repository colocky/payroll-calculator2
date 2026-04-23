package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeApp {
    public static void main(String[] args) {

        String fileName = "employees.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // Skip header line
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                // Split
                String[] tokens = line.split("\\|");

                // Convert tokens into variables
                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                // Create Employee object
                Employee emp = new Employee(employeeId, name, hoursWorked, payRate);

                // Display output
                System.out.printf("ID: %d | Name: %s | Gross Pay: $%.2f%n",
                        emp.getEmployeeId(),
                        emp.getName(),
                        emp.getGrossPay());
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}