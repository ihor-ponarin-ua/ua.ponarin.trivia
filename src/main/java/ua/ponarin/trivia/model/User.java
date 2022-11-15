package ua.ponarin.trivia.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String login;
    private String role;
    private String firstName;
    private String lastName;
    private Date createdOn;
    private Date updatedOn;
}
