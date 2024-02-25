package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.Commands;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserView {
    private final UserController userController;
    private Object delete;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run() {
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ");
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    User u = userController.createUser();
                    userController.saveUser(u);
                    System.out.println("Пользователь создан.");
                    break;
                case NONE:
                    break;
                case READ:
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case LIST:
                    List<User> users = userController.readAll();
                    // Сортируем список по id
                    users.stream()
                            .sorted(Comparator.comparingLong(User::getId))
                            .forEach(System.out::println);
                    break;
                case UPDATE:
                    String userId = prompt("Enter user id: ");
                    try {
                        // Попытка прочитать пользователя по id
                        User existingUser = userController.readUser(Long.parseLong(userId));
                        if (existingUser != null) {
                            // Если пользователь существует, обновляем его данные
                            User newUserDetails = userController.createUser();
                            userController.updateUser(userId, newUserDetails);
                            System.out.println("Пользователь обновлен.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Идентификатор пользователя должен быть числом.");
                    } catch (Exception e) {
                        System.out.println("Пользователь с идентификатором " + userId + " не найден.");
                    }
                    break;
                case DELETE:
                    userId = prompt("Enter user id: ");
                    try {
                        // Попытка прочитать пользователя по id
                        User existingUser = userController.readUser(Long.parseLong(userId));
                        if (existingUser != null) {
                            // Если пользователь существует, удаляем его данные
                            userController.deleteUser(userId, existingUser);
                            System.out.println("Пользователь удален.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Идентификатор пользователя должен быть числом.");
                    } catch (Exception e) {
                        System.out.println("Пользователь с идентификатором " + userId + " не найден.");
                    }
                    break;
            }
        }
    }

    public static String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
