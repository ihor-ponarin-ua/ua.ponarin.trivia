const wsHandshakeEndpoint = '/ws/trivia';
const wsUserTopic = '/ws/topic/players'

$(function () {
    connect();
});

function connect() {
    const stompClient = Stomp.over(new SockJS(wsHandshakeEndpoint));

    stompClient.connect({'token': 'eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOjIsInVzZXJSb2xlIjoiVVNFUiIsImlzcyI6InRyaXZpYS1hdXRoZW50aWNhdG9yIiwiaWF0IjoxNjY1MDY5Nzg4LCJleHAiOjE2NjUwNzY5ODh9.1NMI9TXH36eeF3Ru7GU8JrL7UPZYUUaygubs-eiatZyLwQY71utUk0VmV28aCIiz' }, () => {
        stompClient.subscribe(wsUserTopic, event => playersListChanged(JSON.parse(event.body)))
    });
}

function playersListChanged(players) {
    const playersNames = players
        .filter(player => player.voteStatus === 'IN_PROGRESS')
        .map(player => `<li>${player.name}</li>`);
    $('#playersList').html(playersNames);
}