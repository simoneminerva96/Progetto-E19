package states;

import Main.GiocoAStati;
import graphics.GUI.MultiplayerLoadingGUI;
import graphics.Screen;
import network.ConnectionListener;
import network.NetworkHandle;
import network.test.CommandHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.Timer;
import java.util.TimerTask;

public class MultiplayerLoading extends AbstractMenuState implements ConnectionListener {
    private CommandHandler commandHandler;
    private GiocoAStati giocoAStati;
    private boolean connecting;
    private NetworkHandle networkHandle;
    @Override
    public int getID() {
        return GiocoAStati.MULTI_LOADING;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        getGui().reload();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        giocoAStati = (GiocoAStati) stateBasedGame;
        Screen screen = new Screen(gameContainer.getWidth(), gameContainer.getHeight(), 0, 0);
        setGui(new MultiplayerLoadingGUI(gameContainer, screen,this));

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        renderGui();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
    }

    public void keyPressed(int key, char c){
        if( key == Input.KEY_ESCAPE){
            System.exit(0);
        }

    }

    public void join(String ip, int port){
        networkHandle= new NetworkHandle();
        networkHandle.addConnectionListener(this);
        setCommandHandler(networkHandle);
        Thread connectionThread = new Thread(() -> networkHandle.setConnection(ip,port, giocoAStati.getPlayerInfo().getName()));
        connectionThread.start();
    }

    public void host(int port){
        networkHandle = new NetworkHandle();
        networkHandle.addConnectionListener(this);
        setCommandHandler(networkHandle);
        Thread connectionThread = new Thread(() -> networkHandle.setConnection(port, giocoAStati.getPlayerInfo().getName()));
        connectionThread.start();
    }

    private void startLoading(){
        (new Timer()).schedule( new TimerTask(){
            @Override
            public void run() {
 //               ((MultiplayerLoadingGUI)getGui()).connected();
                ((MultiplayerState) giocoAStati.getState(GiocoAStati.MULTIPLAYER)).setCommandHandler(commandHandler);
                connecting=true;
            }
        }, 1000);
        (new Timer()).schedule( new TimerTask(){
            @Override
            public void run() {
                giocoAStati.enterState(GiocoAStati.MULTIPLAYER);
                connecting=false;
            }
        }, 4000);
    }

    private void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }


    @Override
    public void connectionStatus(boolean connected) {
        commandHandler.removeListener(this);
        if(connected)
            startLoading();
        else {
            giocoAStati.enterState(GiocoAStati.CONNECTION_ERROR_MENU);

        }
    }

    public boolean isConnecting() {
        return connecting;
    }

    public void back(){
        if (networkHandle!=null)
            networkHandle.closeConnection();
        giocoAStati.enterState(GiocoAStati.MULTI_MENU, new FadeOutTransition(), new FadeInTransition());
    }
}
