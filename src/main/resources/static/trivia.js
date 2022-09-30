const wsHandshakeEndpoint = '/ws/trivia';
const wsUserTopic = '/ws/topic/players'

$(function () {
    connect();
});

function connect() {
    const stompClient = Stomp.over(new SockJS(wsHandshakeEndpoint));

    stompClient.connect({}, () => {
        stompClient.subscribe(wsUserTopic, event => playersListChanged(JSON.parse(event.body)))
    });
}

function playersListChanged(players) {
    const playersNames = players
        .filter(player => player.voteStatus === 'IN_PROGRESS')
        .map(player => `<li>${player.name}</li>`);
    $('#playersList').html(playersNames);
}