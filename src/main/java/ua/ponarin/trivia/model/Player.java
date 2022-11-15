package ua.ponarin.trivia.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Player {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private VoteStatus voteStatus = VoteStatus.IN_PROGRESS;

    private enum VoteStatus {
        IN_PROGRESS, DONE
    }
}
