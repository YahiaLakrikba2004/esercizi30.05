package Main;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import esercizio0.Customer;
import esercizio0.Product;
import esercizio0.Order;
import java.util.Map;
import com.github.javafaker.Faker;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();

        // clienti
        Customer customer1 = new Customer(1L, faker.lordOfTheRings().character(), 1);
        Customer customer2 = new Customer(2L, faker.lordOfTheRings().character(), 2);
        Customer customer3 = new Customer(3L, faker.lordOfTheRings().character(), 1);

        // prodotti
        Product product1 = new Product(1L, faker.commerce().productName(), "Elettronica", faker.number().randomDouble(2, 100, 500 ));
        Product product2 = new Product(2L, faker.commerce().productName(), "Elettronica", faker.number().randomDouble(2, 100, 300 ));
        Product product3 = new Product(3L, faker.commerce().productName(), "Elettronica", faker.number().randomDouble(2, 100, 600));
        Product product4 = new Product(4L, faker.commerce().productName(), "Elettronica", faker.number().randomDouble(2, 100, 150 ));
        Product product5 = new Product(5L, faker.commerce().productName(), "Elettronica", faker.number().randomDouble(2, 100, 75));

        // ordini
        Order order1 = new Order(1L, "shipped", LocalDate.of(2023, 1, 10), LocalDate.of(2023, 1, 15),
                Arrays.asList(product1, product2), customer1);
        Order order2 = new Order(2L, "delivered", LocalDate.of(2023, 2, 5), LocalDate.of(2023, 2, 10),
                Arrays.asList(product3), customer2);
        Order order3 = new Order(3L, "processing", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 5),
                Arrays.asList(product4, product5), customer3);
        Order order4 = new Order(4L, "shipped", LocalDate.of(2023, 3, 15), LocalDate.of(2023, 3, 20),
                Arrays.asList(product1, product3), customer1);
        Order order5 = new Order(5L, "delivered", LocalDate.of(2023, 4, 10), LocalDate.of(2023, 4, 15),
                Arrays.asList(product2, product5), customer2);

        List<Order> orders = Arrays.asList(order1, order2, order3, order4);


        System.out.println("----------------------------------------------------------------");
        testEsercizio1(orders);
        System.out.println("----------------------------------------------------------------");
        testEsercizio2(orders);
        System.out.println("----------------------------------------------------------------");
        testEsercizio3(Arrays.asList(product1, product2, product3, product4, product5));
        System.out.println("----------------------------------------------------------------");
        testEsercizio4(orders);
        System.out.println("----------------------------------------------------------------");
    }

    // esercizio 1
    public static void testEsercizio1(List<Order> orders) {
        Map<Customer, List<Order>> ordersByCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        ordersByCustomer.forEach((customer, ordersList) -> {
            System.out.println("Cliente: " + customer);
            ordersList.forEach(order -> System.out.println("  Ordine: " + order));
        });
    }

    //esercizio 2
    public static void testEsercizio2(List<Order> orders) {
        Map<Customer, Double> totalSalesByCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.summingDouble(order -> order.getProducts().stream()
                                .mapToDouble(Product::getPrice).sum())
                ));

        totalSalesByCustomer.forEach((customer, total) -> {
            System.out.println(customer + ", Totale vendite: " + total);
        });
    }

    // esercizio 3
    public static void testEsercizio3(List<Product> products) {
        List<Product> mostExpensiveProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(5)
                .collect(Collectors.toList());

        mostExpensiveProducts.forEach(product ->
                System.out.println( product + ", Prezzo: " + product.getPrice()));
    }

    //esercizio 4
    public static void testEsercizio4(List<Order> orders) {
        Double averageOrderValue = orders.stream()
                .mapToDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice).sum())
                .average()
                .orElse(0.0);

        System.out.println("Valore medio degli ordini: " + averageOrderValue);
    }
}
