package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("Ivan", "Negative", "vanya@mail.ru", new Car("BMW", 1)));
      userService.add(new User("Petr", "Positive", "petya@mail.ru", new Car("Haval", 3)));
      userService.add(new User("Vladimir", "Neutral", "vova@mail.ru", new Car("Audi", 7)));
      userService.add(new User("Vladislav", "Lalala", "vlad@mail.ru", new Car("Tesla", 4)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      User userWithCar = userService.findByCar("Haval", 3);
      if (userWithCar != null) {
         System.out.println("owner: " + userWithCar.getFirstName() + " " + userWithCar.getLastName());
      } else {
         System.out.println("owner not found");
      }

      context.close();
   }
}
