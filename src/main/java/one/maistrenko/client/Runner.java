package one.maistrenko.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component("runner")
@Slf4j
public class Runner implements CommandLineRunner {
    static final String URL = "http://localhost:8090/api/v1/user";
    @Override
    public void run(String... args) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        while(true){
            System.out.println("Enter command:");
            switch (scanner.nextLine()){
                case "create-user":
                    createUser(scanner1, restTemplate);
                    break;
                case "update-user":
                    updateUser(scanner1, scanner2, restTemplate);
                    break;
                case "remove-user":
                    removeUser(scanner1, restTemplate);
                    break;
                case "get-user":
                    getUser(scanner1, restTemplate);
                    break;
                case "show-users":
                    showAllUsers(restTemplate);
                    break;
                case "exit":
                    System.exit(0);
                default:
                    System.out.println("This command not exists. Try again.");
            }
        }

    }

    public static User createUser(Scanner scanner, RestTemplate restTemplate){
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

    public static User updateUser(Scanner scanner, Scanner scanner1, RestTemplate restTemplate){
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

    public static void removeUser(Scanner scanner, RestTemplate restTemplate){
        System.out.println("Enter userid:");
        String userid = scanner.nextLine();
        restTemplate.delete(URL + "/" + userid);
    }

    public static User getUser(Scanner scanner, RestTemplate restTemplate){
        System.out.println("Enter userid:");
        String userid = scanner.nextLine();
        User user = restTemplate.getForObject(URL + "/" + userid, User.class);
        System.out.println(user);
        return user;
    }

    public static void showAllUsers(RestTemplate restTemplate){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String  users = restTemplate.getForObject(URL, String.class, headers);
        System.out.println(users);
    }
}
