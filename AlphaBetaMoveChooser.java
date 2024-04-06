import java.util.ArrayList;

/**
 * Solution code for Comp24011 Reversi lab
 *
 * @author g49678gs
 */

public class AlphaBetaMoveChooser extends MoveChooser {
    public Move mv = null;

    /**
     * MoveCooser implementation AlphaBetaMoveChooser(int)
     *
     * @param searchDepth The requested depth for minimax search
     */

    public AlphaBetaMoveChooser(int searchDepth) {
        // Add object initialisation code...
        super("Computer", searchDepth);
    }

    /**
     * Need to implement chooseMove(BoardState,Move)
     *
     * @param boardState The current state of the game board
     *
     * @param hint       Skip move or board location clicked on the UI
     *                   This parameter should be ignored!
     *
     * @return The move chosen by alpha-beta pruning as discussed in the course
     */
    public Move chooseMove(BoardState boardState, Move hint) {
        int checkMax = Integer.MIN_VALUE;
        int checkMin = Integer.MAX_VALUE;
        // Add alpha-beta pruning code...
        ArrayList<Move> lst = new ArrayList<>(boardState.getLegalMoves());
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (boardState.colour == 1) {
            // move for white
            for (Move x : lst) {
                BoardState b0 = boardState.deepCopy();
                b0.makeLegalMove(x);
                int zx = alphabeta2(searchDepth - 1, b0, alpha, beta);
                if (zx > checkMax) {
                    checkMax = zx;
                    mv = x;
                }
            }
            Move ans = mv;
            return ans;
        } else {
            // move for black
            for (Move x : lst) {
                BoardState b0 = boardState.deepCopy();
                b0.makeLegalMove(x);
                int zb = alphabeta2(searchDepth - 1, b0, alpha, beta);
                if (zb < checkMin) {
                    checkMin = zb;
                    mv = x;
                }
            }
            Move ans = mv;
            return ans;
        }
    }

    /**
     * Need to implement boardEval(BoardState)
     *
     * @param boardState The current state of the game board
     *
     * @return The value of the board using Norvig's weighting of squares
     */
    public int boardEval(BoardState boardState) {
        // Add board evaluation code...
        int ans = 0;
        int[][] boardval = { { 120, -20, 20, 5, 5, 20, -20, 120 },
                { -20, -40, -5, -5, -5, -5, -40, -20 },
                { 20, -5, 15, 3, 3, 15, -5, 20 },
                { 5, -5, 3, 3, 3, 3, -5, 5 },
                { 5, -5, 3, 3, 3, 3, -5, 5 },
                { 20, -5, 15, 3, 3, 15, -5, 20 },
                { -20, -40, -5, -5, -5, -5, -40, -20 },
                { 120, -20, 20, 5, 5, 20, -20, 120 }
        };
        for (int i = 0; i < boardval.length; i++) {
            for (int j = 0; j < boardval[i].length; j++) {
                int x = boardState.getContents(i, j);
                if (x == -1)
                    ans = ans - boardval[i][j];
                else if (x == 1)
                    ans = ans + boardval[i][j];
                else
                    continue;
            }
        }
        return ans;
    }

    public int alphabeta2(int depth, BoardState b0, int alpha, int beta) {
        if (depth == 0 || b0.gameOver())
            return boardEval(b0);
        // if Maximizing
        else if (b0.colour == 1) {
            int maxval = Integer.MIN_VALUE;
            for (Move m : b0.getLegalMoves()) {
                BoardState b = b0.deepCopy();
                // copy board state making its move
                b.makeLegalMove(m);
                int y = alphabeta2(depth - 1, b, alpha, beta);
                maxval = Math.max(maxval, y);
                alpha = Math.max(alpha, maxval);
                // pruning the !important part
                if (maxval >= beta) {
                    return maxval;
                }

            }

            return maxval;

        }
        // if minimizing
        else {
            int minval = Integer.MAX_VALUE;
            for (Move m : b0.getLegalMoves()) {
                BoardState b = b0.deepCopy();
                // copy board state making its move
                b.makeLegalMove(m);
                int z = alphabeta2(depth - 1, b, alpha, beta);
                minval = Math.min(minval, z);
                beta = Math.min(beta, minval);
                // pruning the !important part
                if (minval <= alpha) {
                    return minval;
                }

            }

            return minval;
        }
    }
};
/* vim:set et ts=4 sw=4: */
