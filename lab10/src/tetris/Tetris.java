package tetris;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.TERenderer;
import tileengine.Tileset;

import java.util.*;

/**
 *  Provides the logic for Tetris.
 *
 *  @author Erik Nelson, Omar Yu, Noah Adhikari, Jasmine Lin
 */


public class Tetris {

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    // Tetrominoes spawn above the area we display, so we'll have our Tetris board have a
    // greater height than what is displayed.
    private static int GAME_HEIGHT = 25;

    // Contains the tiles for the board.
    private TETile[][] board;

    // Helps handle movement of pieces.
    private Movement movement;

    // Checks for if the game is over.
    private boolean isGameOver;

    // The current Tetromino that can be controlled by the player.
    private Tetromino currentTetromino;

    // The current game's score.
    private int score = 0;

    /**
     * Checks for if the game is over based on the isGameOver parameter.
     * @return boolean representing whether the game is over or not
     */
    private boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Renders the game board and score to the screen.
     */
    private void renderBoard() {
        ter.drawTiles(board);
        renderScore();
        StdDraw.show();

        if (auxFilled) {
            auxToBoard();
        } else {
            fillBoard(Tileset.NOTHING);
        }
    }

    /**
     * Creates a new Tetromino and updates the instance variable
     accordingly. Flags the game to end if the top of the board
     is filled and the new piece cannot be spawned.
     */
    private void spawnPiece() {
        // The game ends if this tile is filled
        if (board[4][19] != Tileset.NOTHING) {
            isGameOver = true;
        }

        // Otherwise, spawn a new piece and set its position to the spawn point
        currentTetromino = Tetromino.values()[bagRandom.getValue()];
        currentTetromino.reset();
    }

    /**
     * Updates the board based on the user input. Makes the appropriate moves
     * depending on the user's input.
     */
    private void updateBoard() {
        // 检查定时器是否达到下落时间间隔
        if (actionDeltaTime() > 1000) {
            movement.dropDown(); // 让当前方块下落一格
            resetActionTimer();
            // 如果当前方块为空，则生成新的方块
            if (currentTetromino == null) {
                spawnPiece();
            }
        }

        // 检查是否有按键输入
        if (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();

            // 按键处理逻辑
            switch (c) {
                case 'a': // 向左移动
                    if (movement.canMove(currentTetromino.pos.x - 1, currentTetromino.pos.y)) {
                        movement.tryMove(currentTetromino.pos.x - 1, currentTetromino.pos.y);
                    }
                    break;
                case 'd': // 向右移动
                    if (movement.canMove(currentTetromino.pos.x + 1, currentTetromino.pos.y)) {
                        movement.tryMove(currentTetromino.pos.x + 1, currentTetromino.pos.y);
                    }
                    break;
                case 's': // 向下移动
                    if (movement.canMove(currentTetromino.pos.x, currentTetromino.pos.y + 1)) {
                        movement.tryMove(currentTetromino.pos.x, currentTetromino.pos.y + 1);
                    }
                    break;
                case 'q': // 左旋转
                    if (movement.canRotate(currentTetromino.shape)) {
                        movement.rotate(Movement.Rotation.LEFT);
                    }
                    break;
                case 'w': // 右旋转
                    if (movement.canRotate(currentTetromino.shape)) {
                        movement.rotate(Movement.Rotation.RIGHT);
                    }
                    break;
                default:
                    break; // 处理非指定按键
            }
        }

        // 更新并重新绘制当前方块
        Tetromino.draw(currentTetromino, board, currentTetromino.pos.x, currentTetromino.pos.y);
    }

    /**
     * Increments the score based on the number of lines that are cleared.
     *
     * @param linesCleared
     */
    private void incrementScore(int linesCleared) {
        // TODO: Increment the score based on the number of lines cleared.
        if (linesCleared == 0) {
            score += 0;
        }
        if (linesCleared == 1) {
            score += 100;
        }
        if (linesCleared == 2) {
            score += 300;
        }
        if (linesCleared == 3) {
            score += 500;
        }
        if (linesCleared == 4) {
            score += 800;
        }
    }

    /**
     * Clears lines/rows on the provided tiles/board that are horizontally filled.
     Repeats this process for cascading effects and updates score accordingly.
     @param tiles
     */
    public void clearLines(TETile[][] tiles) {
        // Keeps track of the current number lines cleared
        int linesCleared = 0;

        // TODO: Check.
        for (int i = tiles.length - 1; i >= 0; i--) {
            boolean isFull = true;
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == Tileset.NOTHING) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) { // 清除当前行并将上方行下移
                for (int k = i; k > 0; k--) {
                    System.arraycopy(tiles[k - 1], 0, tiles[k], 0, tiles[k].length);
                }
                Arrays.fill(tiles[0], Tileset.NOTHING); // 顶行清空
                linesCleared++;
                i++; // 再次检查当前位置
            }
        }
        // TODO: Increment the score based on the number of lines cleared.
        incrementScore(linesCleared);
        fillAux();
    }

    /**
     * Where the game logic takes place. The game should continue as long as the game isn't
     * over.
     */
    public void runGame() {
        resetActionTimer();
        spawnPiece();
        while (!isGameOver()) {
            updateBoard();
            renderBoard();
            clearLines(board);
            StdDraw.pause(100); // 控制帧率，避免过快运行
            continueGame();
        }
    }

    public void continueGame() {
        for (int j = 0; j < WIDTH; j++) {
            if (board[0][j] != Tileset.NOTHING) {
                isGameOver = true;
                return;
            }
        }
        isGameOver = false;
    }
    /**
     * Renders the score using the StdDraw library.
     */
    private void renderScore() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH-3, HEIGHT-1, "Score:  " + getScore()); // 显示在右侧
    }

    /**
     * Use this method to run Tetris.
     * @param args
     */
    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
        Tetris tetris = new Tetris(seed);
        tetris.runGame();
    }

    /**
     * Everything below here you don't need to touch.
     */

    // This is our tile rendering engine.
    private final TERenderer ter = new TERenderer();

    // Used for randomizing which pieces are spawned.
    private Random random;
    private BagRandomizer bagRandom;

    private long prevActionTimestamp;
    private long prevFrameTimestamp;

    // The auxiliary board. At each time step, as the piece moves down, the board
    // is cleared and redrawn, so we keep an auxiliary board to track what has been
    // placed so far to help render the current game board as it updates.
    private TETile[][] auxiliary;
    private boolean auxFilled;

    public Tetris() {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(new Random().nextLong());
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    public Tetris(long seed) {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(seed);
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);

        ter.initialize(WIDTH, HEIGHT);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    // Setter and getter methods.

    /**
     * Returns the current game board.
     * @return
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * Returns the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current auxiliary board.
     * @return
     */
    public TETile[][] getAuxiliary() {
        return auxiliary;
    }


    /**
     * Returns the current Tetromino/piece.
     * @return
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    /**
     * Sets the current Tetromino to null.
     * @return
     */
    public void setCurrentTetromino() {
        currentTetromino = null;
    }

    /**
     * Sets the boolean auxFilled to true;
     */
    public void setAuxTrue() {
        auxFilled = true;
    }

    /**
     * Fills the entire board with the specific tile that is passed in.
     * @param tile
     */
    private void fillBoard(TETile tile) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }

    /**
     * Copies the contents of the src array into the dest array using
     * System.arraycopy.
     * @param src
     * @param dest
     */
    private static void copyArray(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
    }

    /**
     * Copies over the tiles from the game board to the auxiliary board.
     */
    public void fillAux() {
        copyArray(board, auxiliary);
    }

    /**
     * Copies over the tiles from the auxiliary board to the game board.
     */
    private void auxToBoard() {
        copyArray(auxiliary, board);
    }

    /**
     * Calculates the delta time with the previous action.
     * @return the amount of time between the previous Tetromino movement with the present
     */
    private long actionDeltaTime() {
        return System.currentTimeMillis() - prevActionTimestamp;
    }

    /**
     * Resets the action timestamp to the current time in milliseconds.
     */
    private void resetActionTimer() {
        prevActionTimestamp = System.currentTimeMillis();
    }

}
