package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;
import service.ProductService;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		System.out.print("Enter salary: ");
		Double salary = sc.nextDouble();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Employee> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
				line = br.readLine();
			}

			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salary) + ": ");

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> emails = list.stream().filter(e -> e.getSalary() > salary).map(e -> e.getEmail()).sorted(comp)
					.collect(Collectors.toList());

			emails.forEach(System.out::println);

			char frLetter = 'A';

			double sum = new ProductService().filteredSum(list, e -> e.getName().charAt(0) == frLetter);

			System.out.print(
					"Sum of salary of people whose name starts with '" + frLetter + "': " + String.format("%.2f", sum));

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}

}
