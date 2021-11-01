package ua.com.alevel.view;

import ua.com.alevel.entity.Product;
import ua.com.alevel.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProductController {

    private final ProductService productService = new ProductService();

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nSelect a number of action");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                crud(position, reader);
                position = reader.readLine();
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("1 - if you want enter a product to basket");
        System.out.println("2 - if you want update product");
        System.out.println("3 - if you want delete product from basket");
        System.out.println("4 - if you want find product by Id");
        System.out.println("5 - if you want find all products");
        System.out.println("0 - if you want exit");
        System.out.println();
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                create(reader);
                break;
            case "2":
                update(reader);
                break;
            case "3":
                delete(reader);
                break;
            case "4":
                findById(reader);
                break;
            case "5":
                findAll(reader);
                break;
            case "0":
                System.exit(0);
        }
        runNavigation();
    }

    private void create(BufferedReader reader) {
        try {
            System.out.println("Please, enter NAME of product");
            String name = reader.readLine();
            System.out.println("Please, enter COUNT of product");
            String countString = reader.readLine();
            int count = Integer.parseInt(countString);
            System.out.println("Please, enter PRICE of product");
            String priceString = reader.readLine();
            float price = Float.parseFloat(priceString);
            Product product = new Product();
            product.setName(name);
            product.setCount(count);
            product.setPrice(price);
            productService.create(product);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            System.out.println("Please, enter NAME of product");
            String name = reader.readLine();
            System.out.println("Please, enter COUNT of product");
            String countString = reader.readLine();
            int count = Integer.parseInt(countString);
            System.out.println("Please, enter PRICE of product");
            String priceString = reader.readLine();
            float price = Float.parseFloat(priceString);
            Product product = new Product();
            product.setId(id);
            product.setCount(count);
            product.setName(name);
            product.setPrice(price);
            productService.update(product);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            productService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            Product product = productService.findById(id);
            System.out.println("product = " + product);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAll(BufferedReader reader) {
        Product[] products = productService.findAll();
        if (products != null && products.length != 0) {
            int id = 0;
            for (Product product : products) {
                if(product != null){
                    id++;
                    System.out.println("product " + id + " = " + product);
                }
            }
        } else {
            System.out.println("product empty");
        }
    }
}
