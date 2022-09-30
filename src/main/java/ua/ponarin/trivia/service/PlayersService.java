package ua.ponarin.trivia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.ponarin.trivia.model.Player;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlayersService {
    private static final Map<String, Player> PLAYERS_MAP = new LinkedHashMap<>();

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${ws.topic.players}")
    private String playersTopic;

    public void playerConnected(Principal principal) {
        var login = getLogin(principal);
        PLAYERS_MAP.put(login, new Player().setName(login));
        notifyPlayersListChanged();
    }

    public void playerDisconnected(Principal principal) {
        var login = getLogin(principal);
        PLAYERS_MAP.remove(login);
        notifyPlayersListChanged();
    }

    private String getLogin(Principal principal) {
        return principal.getName();
    }

    private void notifyPlayersListChanged() {
        simpMessagingTemplate.convertAndSend(playersTopic, PLAYERS_MAP.values());
    }
}
