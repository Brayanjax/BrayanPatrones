package com.laca;

import com.laca.BL.FactoryUsers.FactoryUsers;
import com.laca.UnitTransportImplementation.*;
import com.laca.entity.ConcreteDecorator.Transportation;
import com.laca.entity.Interfaces.*;
import com.laca.entity.PackageUnitAbstract.UnitTransporterAbstract;
import com.laca.entity.PackageUnitAbstract.Users;
import com.laca.entity.RouteC.*;
import com.laca.entity.concretCreator.ProductLogistics;
import com.laca.entity.concretProduct.Product;
import com.laca.entity.concretUsers.TransportUser;
import com.laca.facade.FacadeSend;
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
		System.out.println("////////////////////////////////");
		System.out.println("5.Decorator unidad de transporte");
		System.out.println("6.Observer usuarios unidades de transportes");
		System.out.println("7.Facade Send para actualizar el estado de paquete");
		System.out.println("s. Salir");
	}
	public static void ejecutarOpcion(String opcion) {
		switch(opcion){
			case "1":
				PrototypeUnitTransporter();
				break;
			case "2":
				PrototypeUnitRoute();
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
			case "5":
				decorarUnidadesTransporte();
				break;
			case "6":
				observarUnidadesTransporte();
				break;
			case "7":
				facadeSend();
				break;
			case "s":
				System.out.println("Cerrando sesión");
				break;
		}
	}
	public  static  void  observarUnidadesTransporte(){
		Users users = new TransportUser();

		UnitTransporterAbstract unitTransporterAbstract = new UnitTransporterAbstract();
		unitTransporterAbstract.addObserver(users);
		unitTransporterAbstract.notifyObserver();

	}
	public static void decorarUnidadesTransporte(){
		UnitTransporterAbstract unitTranspot = new Motorcycle();
		unitTranspot.setName("Diego");
		unitTranspot.setPlate("22223434");
		unitTranspot.setHigh(1.72);
		unitTranspot.setWidth(0.30);

		System.out.println("Quieres agregar la capacidad de transportar a su unidad?: ");
		boolean response= Boolean.parseBoolean(scanner.nextLine());
		if (response){
			UnitTransDecorator unitTransTransport = new Transportation(unitTranspot);
			System.out.println(unitTransTransport.Transport());

		}
		System.out.println(unitTranspot);
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
	public static  void PrototypeUnitRoute(){
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


		}
		/////Cliente
		System.out.println("Ingresar Nombre: ");
		String name = scanner.nextLine();
		System.out.println("Ingresar la identificacion: ");
		String identification= scanner.nextLine();
		System.out.println("Ingresar El nombre de la empresa: ");
		String factoryName= scanner.nextLine();
		IConstructUser user= FactoryUsers.createUser(name,identification,factoryName,opcion);
		System.out.println(user);

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

	public static void facadeSend() {
		FacadeSend facade = new FacadeSend();
		String opcion = "";

		while (!opcion.equals("3")) {
			System.out.println("1. Enviar Paquete");
			System.out.println("2. Marcar Paquete como Entregado");
			System.out.println("3. Salir");
			System.out.println("Ingrese su opción: ");
			opcion = scanner.nextLine();

			switch (opcion) {
				case "1":
					sendPackage(facade);
					break;
				case "2":
					packageDelivered(facade);
					break;
				case "3":
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida");
			}
		}
	}

	public static void sendPackage(FacadeSend facade) {
		try {

			Product product = new Product("Electrónica", 2.5, "Laptop", "Laptop Dell XPS 15", 1200.00, 2.0, 30.0);
			Route route = new Route("Urbana", "Ruta 101", "Ruta desde la ciudad A a la ciudad B",
					new Point("Ciudad A", "Inicio en la ciudad A", new Coordinates(10.0, 20.0)),
					new Point("Ciudad B", "Fin en la ciudad B", new Coordinates(11.0, 21.0)));
			UnitTransporterAbstract unitTransporter = new Walk();
			unitTransporter.setName("Caminador");
			unitTransporter.setPlate("22223434");
			unitTransporter.setHigh(1.72);
			unitTransporter.setWidth(0.30);


			facade.prepareShipment(product, unitTransporter, route);
			facade.sendPackage();

			System.out.println("Paquete enviado correctamente.");
		} catch (Exception e) {
			System.err.println("Error al enviar el paquete: " + e.getMessage());
		}
	}

	public static void packageDelivered(FacadeSend facade) {

		facade.packageDelivered();
		System.out.println("Paquete marcado como entregado.");
	}

}
