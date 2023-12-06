package com.laca;

import com.laca.BL.FactoryUsers.FactoryUsers;
import com.laca.UnitTransportImplementation.*;
import com.laca.entity.Interfaces.IConstructUser;
import com.laca.entity.Interfaces.LogisticsRoad;
import com.laca.entity.Interfaces.Production;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.RouteC.*;
import com.laca.entity.concretCreator.ProductLogistics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Scanner;

@SpringBootApplication
@EntityScan(basePackages = {"domain"} )
public class LatinAmericanCarriersAssociationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LatinAmericanCarriersAssociationApplication.class, args);
		String opcion = "";
		while(!opcion.equals("s")) {
			mostrarMenu();
			System.out.println("Ingrese la opción deseada: ");
			opcion = scanner.nextLine();
			ejecutarOpcion(opcion);
		}
	}
	static Scanner scanner = new Scanner(System.in);

	public static void mostrarMenu(){
		System.out.println("\u001b[33mMenú del programa:\u001b[0m");
		System.out.println("1. Prototype Unidades de transporte");
		System.out.println("2. Prototype Rutas de transporte");
		System.out.println("3. Single Factory usuarios");
		System.out.println("4. Factory method Productos");
		System.out.println("s. Salir");
	}
	public static void ejecutarOpcion(String opcion) {
		switch(opcion){
			case "1":
				PrototypeUnitTransporter();
				break;
			case "2":
				PrototypeUnitRute();
				break;
			case "3":
				mostrarMenuUsers();
				System.out.println("Cual usuario quiere crear?: ");
				String type = scanner.nextLine();
				SingleFactoryUser(type);
				break;
			case "4":
				concretProducts();
				break;
			case "s":
				System.out.println("Cerrando sesión");
				break;
		}
	}
	public static void PrototypeUnitTransporter(){
		UnitTransporterAbstract unitTranspot = new Motorcycle();
		unitTranspot.setName("Diego");
		unitTranspot.setPlate("22223434");
		unitTranspot.setHigh(1.72);
		unitTranspot.setWidth(0.30);

		UnitTransporterAbstract unitTransporter = new Walk();
		unitTransporter.setName("Brayan");
		unitTransporter.setPlate("22223434");
		unitTransporter.setHigh(1.72);
		unitTransporter.setWidth(0.30);
		System.out.println(unitTranspot);
		UnitTransporterAbstract personsClone = (Walk) unitTransporter.clonar();
		if (personsClone != null){
			System.out.println(personsClone);
		}
		System.out.println(unitTransporter);
		System.out.println(unitTransporter==personsClone);
	}
	public static  void PrototypeUnitRute(){
		RouteManager manager = new RouteManager();

		Route originalRoute = new Route("Short", "Route 1", "Description 1",
				new Point("Start", "Start Desc", new Coordinates(20.0, 30.0)),
				new Point("End", "End Desc", new Coordinates(21.0, 31.0))
		);
		manager.addRoute("routeKey1", originalRoute);

		Route clonedRoute = manager.getRoute("routeKey1");
		clonedRoute.setName("Cloned Route");
		System.out.println("Original Route: " + originalRoute);
		System.out.println("Cloned Route: " + clonedRoute);
	}
	public static void SingleFactoryUser(String type){
		String opcion = "";

		switch (type){
			case "1":
				opcion= "AdminUser";
				break;
			case "2":
				opcion= "ClientUser";
				break;
			case "3":
				opcion= "RutesUser";
				break;
			case "4":
				opcion= "TransportUser";
				break;
			case "5":
				opcion= "VisualizatorPackagesInProgress";
				break;
			case "s":
				System.out.println("Cerrando sesión");
				break;


		}                         /////Cliente
		//IConstructUser user= FactoryUsers.createUser(opcion);
		//user.construction();
	}
	public static void mostrarMenuUsers(){
		System.out.println("\u001b[33mMenú del programa:\u001b[0m");
		System.out.println("1. Admin User");
		System.out.println("2. Cliente User");
		System.out.println("3. Rute User");
		System.out.println("4. Transport User");
		System.out.println("5. Visualizator Package In Progress");
		System.out.println("s. Salir");
	}

	public static void concretProducts(){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el tipo de paquete: ");
		String type = scanner.nextLine();
		System.out.print("Ingrese el peso del paquete (en kg): ");
		double weight = scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Ingrese el nombre del paquete: ");
		String name = scanner.nextLine();
		System.out.print("Ingrese la descripción del paquete: ");
		String description = scanner.nextLine();
		System.out.print("Ingrese el precio del paquete (en dólares): ");
		double price = scanner.nextDouble();
		System.out.print("Ingrese la altura del paquete (en cm): ");
		double height = scanner.nextDouble();
		System.out.print("Ingrese el ancho del paquete (en cm): ");
		double width = scanner.nextDouble();

		LogisticsRoad productLogistics = new ProductLogistics();
		Production productLogisticsPackage = productLogistics.createPackage(type, weight, name, description, price, height, width);
		productLogisticsPackage.create();
	}

}
