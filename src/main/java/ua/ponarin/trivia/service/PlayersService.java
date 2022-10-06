package ua.ponarin.trivia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.ponarin.trivia.api.UserApiClient;
import ua.ponarin.trivia.model.Player;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlayersService {
    private static final Map<String, Player> PLAYERS_MAP = new LinkedHashMap<>();
    private final UserApiClient userApiClient;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${ws.topic.players}")
    private String playersTopic;

    public void playerConnected(Principal principal) {
        var fullUserName = getFullUserName(principal);
        PLAYERS_MAP.put(principal.getName(), new Player().setName(fullUserName));
        notifyPlayersListChanged();
    }

    public void playerDisconnected(Principal principal) {
        PLAYERS_MAP.remove(principal.getName());
        notifyPlayersListChanged();
    }

    private String getFullUserName(Principal principal) {
        var user = userApiClient.getById(principal.getName());
        return String.format("%s %s", user.getFirstName(), user.getLastName());
    }

    private void notifyPlayersListChanged() {
        simpMessagingTemplate.convertAndSend(playersTopic, PLAYERS_MAP.values());
    }
}
