package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class EmployeeApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Prompt user for input/output files
        System.out.print("Enter the name of the employee file to process: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter the name of the payroll file to create: ");
        String outputFile = scanner.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            // Skip header
            reader.readLine();

            String line;

            // Check if JSON output
            boolean isJson = outputFile.toLowerCase().endsWith(".json");

            if (isJson) {
                writer.write("[\n");
            } else {
                writer.write("id|name|gross pay\n");
            }

            boolean first = true;

            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split("\\|");

                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double hoursWorked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee emp = new Employee(employeeId, name, hoursWorked, payRate);

                if (isJson) {
                    // Add comma between objects
                    if (!first) {
                        writer.write(",\n");
                    }

                    writer.write(String.format(
                            "  { \"id\": %d, \"name\": \"%s\", \"grossPay\": %.2f }",
                            emp.getEmployeeId(),
                            emp.getName(),
                            emp.getGrossPay()
                    ));

                    first = false;

                } else {
                    // CSV
                    writer.write(String.format("%d|%s|%.2f\n",
                            emp.getEmployeeId(),
                            emp.getName(),
                            emp.getGrossPay()));
                }
            }

            if (isJson) {
                writer.write("\n]");
            }

            reader.close();
            writer.close();

            System.out.println("Payroll file created successfully!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}