package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Entity.User;
import org.example.Service.UserService;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AppConsole {

    private static final Logger logger = LogManager.getLogger(AppConsole.class);

    private static final UserService userService = new UserService();

    public static void main(String[] args){

        logger.info("Application running");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running){
            String controls = scanner.nextLine();

            switch (controls){

                case "create" -> createUser(scanner);
                case "update" ->updateUser(scanner);
                case "delete" ->deleteUser(scanner);
                case "getUserById" ->getUserById(scanner);
                case "getAllUser" ->getAllUsers();
                case "turn off" ->{
                    logger.warn("Shutting down");
                    running=false;
                }
                default -> {
                    logger.warn("UNKNOWN ACTION");
                    throw new RuntimeException("Action not found");
                }

            }

        }

    }

    private static void createUser(Scanner scanner){

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        userService.createUser(name, email, age, java.time.LocalDateTime.now());
        System.out.println("User created ");

        logger.info("User created: name {}, email {}, age {}", name, email, age);
    }

    private static void getUserById(Scanner scanner){

        System.out.println("Id:");
        Long id = Long.valueOf(scanner.nextLine());
        Optional<User> u = userService.getUserById(id);
        System.out.println(u.orElse(null));

        logger.info("User found by id {}: {}", id, u.orElse(null));
    }

    private static void getAllUsers(){
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);

        logger.info("{} users are listed", users.size());
    }

    private static void deleteUser(Scanner scanner){

        System.out.println("Id:");
        Long id = Long.valueOf(scanner.nextLine());
        boolean status = userService.deleteUser(id);
        System.out.println("Status: " + status);

        logger.info("Delete User with id: {} status -> {}",id, status);
    }

    private static void updateUser(Scanner scanner){
        System.out.println("Id:");
        Long id = Long.valueOf(scanner.nextLine());

        System.out.print("New name: ");
        String name = scanner.nextLine();
        name = name.isBlank() ? null : name;

        System.out.print("New email: ");
        String email = scanner.nextLine();
        email = email.isBlank() ? null : email;

        System.out.print("New age: ");
        int age = Integer.parseInt(scanner.nextLine());

        userService.updateUser(id, name, email, age);
        logger.info("User updated id={} -> name={}, email={}, age={}", id, name, email, age);
    }

}