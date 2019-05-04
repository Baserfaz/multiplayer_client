package core;

import ui.GuiElementManager;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable {

    private final String ADDRESS = "127.0.0.1";
    private final int PORT = 26265;

    public int WIDTH = 1920, HEIGHT = 1080;

    public static final String CUSTOM_FONT_NAME = "coders_crux";
    public static final String CUSTOM_FONT_EXTENSION = ".ttf";
    public static final String CUSTOM_FONT_FOLDER = "coders_crux";

    public static final int SPRITE_GRID_SIZE = 16;
    public static final int SPRITE_SIZE_MULT = 4;

    public static final String SPRITESHEET_PATH = "/images/spritesheet.png";
    public static final String FRAME_ICON_PATH = "/images/icon.png";

    private final double FRAME_CAP = 60.0;

    public static int FPS;
    public static Game instance;

    private boolean running = false;
    private GameState gameState;
    private GameState lastGameState;

    private Font customFont;

    private Window window;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private Renderer renderer;
    private GuiElementManager guiElementManager;

    private SpriteCreator spriteCreator;
    private SpriteStorage spriteStorage;

    private ServerConnection serverConnection;

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    private Game() {

        if(instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException(
                    "Trying to create another game instance.");
        }

        this.window = new Window(
                this.WIDTH, this.HEIGHT,
                "Multiplayer client",
                this);

        System.setProperty("sun.java2d.opengl", "true");

        Utils.loadCustomFont();

        this.guiElementManager = new GuiElementManager();

        this.spriteCreator = new SpriteCreator(SPRITESHEET_PATH);
        this.spriteStorage = new SpriteStorage();

        this.spriteStorage.loadSprites();

        this.keyInput = new KeyInput();
        this.mouseInput = new MouseInput(guiElementManager);

        this.addKeyListener(this.keyInput);
        this.addMouseMotionListener(this.mouseInput);
        this.addMouseListener(this.mouseInput);

        this.createBufferStrategy(2);
        this.renderer = new Renderer(
                getBufferStrategy().getDrawGraphics(),
                this.guiElementManager
        );

        this.gameState = GameState.MAINMENU;
    }

    @Override
    public void run() {
        this.serverConnection = new ServerConnection(ADDRESS, PORT);
        this.running = true;
        this.gameLoop();
    }

    private void gameLoop() {

        long lastTime = System.nanoTime();
        double unprocessedTime = 0.0;

        int frames = 0;
        long frameCounter = 0;

        final double frameTime = 1 / FRAME_CAP;
        final long SECOND = 1000000000L;

        long now, passedTime;

        while (running) {

            // calculate passed time between frames
            now = System.nanoTime();
            passedTime = now - lastTime;
            lastTime = now;

            unprocessedTime += passedTime / (double) SECOND;
            frameCounter += passedTime;

            // Consume the left over time of handling the frame.
            // When fps cap is set to 60, one frame has 1/60 seconds (0.016s = 16ms) to draw.
            // Can tick the game multiple times if there is time to do that.
            while (unprocessedTime > frameTime) {
                unprocessedTime -= frameTime;
                this.tick();
            }

            // render the scene
            this.render();
            frames++;

            // this is only for counting the frame rate
            if (frameCounter >= SECOND) {
                Game.FPS = frames;
                frames = 0;
                frameCounter = 0;
            }
        }
    }

    private void onGameStateChange() {
        this.guiElementManager.activateGuiElementsInGameState(this.gameState);
    }

    private void tick() {

        // handle game state change.
        if (this.lastGameState != this.gameState) {
            this.onGameStateChange();
        }

        if (this.gameState == GameState.INGAME) {
            // handler.tickGameObjects();
        }

        // always tick gui
        this.guiElementManager.tick(this.gameState);

        // cache last frame's state
        this.lastGameState = this.gameState;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        this.renderer.preRender(g);
        g.dispose();
        bs.show();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Font getCustomFont() {
        return customFont;
    }

    public void setCustomFont(Font customFont) {
        this.customFont = customFont;
    }

    public SpriteCreator getSpriteCreator() {
        return spriteCreator;
    }

    public void setSpriteCreator(SpriteCreator spriteCreator) {
        this.spriteCreator = spriteCreator;
    }

    public SpriteStorage getSpriteStorage() {
        return spriteStorage;
    }

    public void setSpriteStorage(SpriteStorage spriteStorage) {
        this.spriteStorage = spriteStorage;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }
}
