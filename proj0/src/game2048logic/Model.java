package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;

/** The state of a game of 2048. */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        // TODO: Task 2. Fill in this function.
        for (int x = 0; x < size(); x++) {
            for (int y = 0; y < size(); y++) {
                if (tile(x, y) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Returns true if any tile is equal to the maximum valid value. */
    public boolean maxTileExists() {
        // TODO: Task 3. Fill in this function.
        for (int x = 0; x < size(); x++) {
            for (int y = 0; y < size(); y++) {
                Tile t = tile(x, y);
                if (t != null && t.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Returns true if there are any valid moves on the board. */
    public boolean atLeastOneMoveExists() {
        // TODO: Fill in this function.
        if (emptySpaceExists()) {
            return true;
        }
        for (int x = 0; x < size(); x++) {
            for (int y = 0; y < size(); y++) {
                Tile t = tile(x, y);
                if (x < size() - 1 && t.value() == tile(x + 1, y).value()) {
                    return true;
                }
                if (y < size() - 1 && t.value() == tile(x, y + 1).value()) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Moves the tile at position (x, y) as far up as possible. */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        // TODO: Tasks 5, 6, and 10. Fill in this function
        Tile tile = board.tile(x, y);
        int targetY = y;
        if (tile != null) {
            for (int i = y + 1; i < size(); i++) {
                Tile t = board.tile(x, i);
                if (t == null) {
                    targetY += 1;
                }
            }
            for (int i = y + 1; i < size(); i++) {
                Tile t = board.tile(x, i);
                if (t != null) {
                    if (t.value() != tile.value()) {
                        break;
                    }
                    if (t.value() == tile.value() && t.wasMerged()) {
                        break;
                    }
                    if (t.value() == tile.value() && !t.wasMerged()) {
                        targetY += 1;
                        score+=t.value()*2;
                        break;
                    }
                }
            }
            if (targetY != y)
                board.move(x,targetY, tile);
        }
    }

    /** Handles the movements of the tilt in column x of the board. */
    public void tiltColumn(int x) {
        // TODO: Task 7. Fill in this function.
        for (int y = size() - 2; y >= 0; y--) {
            Tile currTile = board.tile(x, y);
            if (currTile != null) {
                moveTileUpAsFarAsPossible(x, y);
            }
        }
    }

    /** Tilts the entire board toward SIDE. */
    public void tilt(Side side) {
        // TODO: Tasks 8 and 9. Fill in this function.
        board.setViewingPerspective(side);
        for (int x = 0; x < size(); x++) {
            tiltColumn(x);
        }
        board.setViewingPerspective(Side.NORTH);
    }

    /** Tilts every column of the board toward SIDE. */
    public void tiltWrapper(Side side) {
        board.resetMerged();  // 重置所有合并标记
        tilt(side);           // 调用 tilt 方法执行实际的倾斜逻辑
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
