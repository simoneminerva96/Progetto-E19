package states.test;

import game.DifficultySettings;
import game.LocalGame;
import game.ObstacleGeneratorType;
import game.SoundPlayer;
import graphics.Canvas;
import graphics.Screen;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SingleplayerState extends BasicGameState {
    private LocalGame game;
    private Canvas gameCanvas;
    private DifficultySettings settings = new DifficultySettings(1, ObstacleGeneratorType.MEDIUM);
    private SoundPlayer soundPlayer;

    @Override
    public int getID() {
        return 10;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Screen screen= new Screen(gameContainer.getWidth()/2, gameContainer.getHeight(), gameContainer.getWidth()/4, 0);
        gameCanvas= new Canvas(screen, gameContainer.getGraphics());
        soundPlayer= new SoundPlayer();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        this.game= new LocalGame(gameCanvas, settings);
        this.game.addListener(soundPlayer);


    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        game.render();
        gameCanvas.clipScreen();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        game.update(i);
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key==Input.KEY_SPACE){
            game.playerJump();
        }
    }
}