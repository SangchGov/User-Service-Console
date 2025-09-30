package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Entity.DTO.UserDTO;
import org.example.Entity.User;
import org.example.Service.UserService;

import static org.example.AppConstants.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class AppConsole {

    private static final Logger logger = LogManager.getLogger(AppConsole.class);

    private static final UserService userService = new UserService();


    public static void main(String[] args) {

        logger.info(APP_RUNNING);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("""
                Chose a command
                "create" -> Create a new user
                "update" -> Update an existing user
                "delete" -> Delete a user by ID
                "getUserById" -> Find a user by ID
                "getAllUser" -> List all users
                "turnOff" -> Exit the application
                """);

        while (running) {
            String controls = scanner.nextLine();

            switch (controls) {

                case CREATE -> createUser(scanner);
                case UPDATE -> updateUser(scanner);
                case DELETE -> deleteUser(scanner);
                case GET_BY_ID -> getUserById(scanner);
                case GET_ALL -> getAllUsers();
                case EXIT -> {
                    logger.warn(SHUTTING_DOWN);
                    running = false;
                }
                default -> {
                    logger.warn(UNKNOWN_ACTION);
                    System.out.println(UNKNOWN_ACTION);
                }

            }

        }

    }

    private static void createUser(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Age: ");
        String ageInput = scanner.nextLine();
        Integer age = ageInput.isBlank() ? null : Integer.parseInt(ageInput);

        UserDTO dto = new UserDTO(name, email, age);
        User user = userService.createUser(dto);

        System.out.println("User created with ID: " + user.getId());
        logger.info("User created: name {}, email {}, age {}", name, email, age);
    }


    private static void getUserById(Scanner scanner) {

        System.out.println("Id:");
        Long id = Long.valueOf(scanner.nextLine());
        Optional<User> user = userService.getUserById(id);
        System.out.println(user.map(User::toString).orElse(USER_NOT_FOUND));

        logger.info("User found by id {}: {}", id, user.orElse(null));
    }

    private static void getAllUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);

        logger.info("{} users are listed", users.size());
    }

    private static void deleteUser(Scanner scanner) {


        System.out.println("Id:");
        Long id = Long.valueOf(scanner.nextLine());
        boolean status = userService.deleteUser(id);
        if (status) {
            System.out.println("User with id " + id + " is deleted");
        } else {
            System.out.println(USER_NOT_FOUND);
        }

        logger.info("Delete User with id: {} status -> {}", id, status);
    }

    private static void updateUser(Scanner scanner) {
        System.out.println("Id:");
        Long id = Long.valueOf(scanner.nextLine());

        System.out.print("New name: ");
        String name = scanner.nextLine();
        name = name.isBlank() ? null : name;

        System.out.print("New email: ");
        String email = scanner.nextLine();
        email = email.isBlank() ? null : email;

        System.out.print("New age: ");
        String ageInput = scanner.nextLine();
        Integer age = ageInput.isBlank() ? null : Integer.parseInt(ageInput);

        UserDTO dto = new UserDTO(name, email, age);

        Optional<User> updatedUser = userService.updateUser(id, dto);
        System.out.println("User with id " + id + " updated: " + updatedUser.map(User::toString).orElse("User not found"));

        logger.info("User updated id={} -> name={}, email={}, age={}", id, name, email, age);
    }

}