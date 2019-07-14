package network.test.commands;

import game.OnlineLocalGame;
import game.RemoteGame;

public class GameOverCommand extends Command {
    private static final long serialVersionUID = -539210512249000009L;

    @Override
    public void execute(RemoteGame remoteGame, OnlineLocalGame localGame) {
        remoteGame.gameOver();
    }
}