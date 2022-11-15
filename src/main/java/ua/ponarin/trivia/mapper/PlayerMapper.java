package ua.ponarin.trivia.mapper;

import org.mapstruct.Mapper;
import ua.ponarin.trivia.model.Player;
import ua.ponarin.trivia.model.User;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    Player toPlayer(User user);
}
