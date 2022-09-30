package ua.ponarin.trivia.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Player {
    private String name;
    private VoteStatus voteStatus = VoteStatus.IN_PROGRESS;

    private enum VoteStatus {
        IN_PROGRESS, DONE
    }
}
