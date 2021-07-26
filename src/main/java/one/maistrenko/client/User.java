package one.maistrenko.client;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private long userid;
    private String username;
    private String password;
}
