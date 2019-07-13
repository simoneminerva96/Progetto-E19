package graphics.GUI;

import graphics.Screen;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import resources.PathHandler;
import resources.ResourcePacks;
import resources.Resources;
import states.MultiplayerLoading;

public class MultiplayerLoadingGUI extends AbstractMenuGUI{
    private MultiplayerLoading state;
    private Animation loadingAnimation;
    private Animation threeTwoOneAnimation;
    private Image background;
    private int buttonDimension;
    private boolean connected;


    public MultiplayerLoadingGUI(GameContainer container, Screen screen, MultiplayerLoading state) throws SlickException {
        super(container, screen);
        this.state = state;

        SpriteSheet threeTwoOneSheet;
        SpriteSheet loadingSheet;

        connected = false;

        buttonDimension = container.getWidth()/5;

        background = new Image(PathHandler.getInstance().getPath(ResourcePacks.SPRITES, Resources.BACKGROUND)).getScaledCopy(screen.getWidth(), screen.getHeight());

        loadingSheet = new SpriteSheet(PathHandler.getInstance().getPath(ResourcePacks.VARIOUS, Resources.LOADINGSHEET),64,64);
        loadingAnimation = new Animation(loadingSheet,100);
        loadingAnimation.stopAt(loadingAnimation.getFrameCount());
        threeTwoOneSheet = new SpriteSheet(PathHandler.getInstance().getPath(ResourcePacks.VARIOUS, Resources.THREETWOONE),288,288);
        threeTwoOneAnimation = new Animation(threeTwoOneSheet,50);
    }

    @Override
    public void reload() {
        connected=false;
        loadingAnimation.restart();
    }

    @Override
    public void render() throws SlickException {
        background.draw();
        if(!connected) {
            loadingAnimation.draw((getContainer().getWidth() - buttonDimension) / 2f, (getContainer().getHeight() - buttonDimension)/2f, buttonDimension, buttonDimension);
        }else{
            threeTwoOneAnimation.draw((getContainer().getWidth() - buttonDimension)/2f, (getContainer().getHeight() - buttonDimension) / 2f, buttonDimension, buttonDimension);
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }

    public void connected(){
        connected=true;
    }
}
