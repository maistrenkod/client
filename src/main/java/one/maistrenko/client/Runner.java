package one.maistrenko.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Component("runner")
@Slf4j
public class Runner implements CommandLineRunner {
    @Value("http://localhost:8090/api/v1/user")
    static String URL;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        while(true){
            System.out.println("Enter command:");
            switch (scanner.nextLine()){
                case "create-user":
                    createUser(scanner1);
                    break;
                case "update-user":
                    updateUser(scanner1, scanner2);
                    break;
                case "remove-user":
                    removeUser(scanner1);
                    break;
                case "get-user":
                    getUser(scanner1);
                    break;
                case "show-users":
                    showAllUsers();
                    break;
                case "exit":
                    System.exit(0);
                default:
                    System.out.println("This command not exists. Try again.");
            }
        }

    }

    public User createUser(Scanner scanner){
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestBody = new HttpEntity<>(new User(0, username, password), headers);
        User user1 = restTemplate.postForObject(URL, requestBody, User.class);
        System.out.println(user1);
        return user1;
    }

    public User updateUser(Scanner scanner, Scanner scanner1){
        System.out.println("Enter userid:");
        long userid = scanner1.nextLong();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestBody = new HttpEntity<>(new User(userid, username, password), headers);
        User user1 = restTemplate.patchForObject(URL, requestBody, User.class);
        System.out.println(user1);
        return user1;
    }

    public void removeUser(Scanner scanner){
        System.out.println("Enter userid:");
        String userid = scanner.nextLine();
        restTemplate.delete(URL + "/" + userid);
    }

    public User getUser(Scanner scanner){
        System.out.println("Enter userid:");
        String userid = scanner.nextLine();
        User user = restTemplate.getForObject(URL + "/" + userid, User.class);
        System.out.println(user);
        return user;
    }

    public void showAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String  users = restTemplate.getForObject(URL, String.class, headers);
        System.out.println(users);
    }
}
