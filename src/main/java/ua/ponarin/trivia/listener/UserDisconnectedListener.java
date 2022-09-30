package ua.ponarin.trivia.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ua.ponarin.trivia.service.PlayersService;

@Component
@RequiredArgsConstructor
public class UserDisconnectedListener implements ApplicationListener<SessionDisconnectEvent> {
    private final PlayersService playersService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        playersService.playerDisconnected(event.getUser());
    }
}
