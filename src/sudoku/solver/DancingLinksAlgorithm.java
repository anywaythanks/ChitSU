package sudoku.solver;

import java.util.Arrays;

/**
 * ### Main ideas
 * The idea of DLX is based on the observation that in a circular doubly linked list of nodes,
 *
 * @code x.left.right ← x.right;
 * x.right.left ← x.left;
 * @endcode will remove node x from the list, while
 * @code x.left.right ← x.right;
 * x.right.left ← x.left;
 * @endcode will restore x's position in the list, assuming that x.right and x.left have been left unmodified. This works regardless of the number of elements in the list, even if that number is 1.
 * <p>
 * Knuth observed that a naive implementation of his Algorithm X would spend an inordinate amount of time searching for 1's. When selecting a column, the entire matrix had to be searched for 1's. When selecting a row, an entire column had to be searched for 1's. After selecting a row, that row and a number of columns had to be searched for 1's. To improve this search time from complexity O(n) to O(1), Knuth implemented a sparse matrix where only 1's are stored.
 * <p>
 * At all times, each node in the matrix will point to the adjacent nodes to the left and right (1's in the same row), above and below (1's in the same column), and the header for its column (described below). Each row and column in the matrix will consist of a circular doubly-linked list of nodes.
 */
public class DancingLinksAlgorithm {
    private final int BOARD_SIZE;
    private final int SUBSECTION_SIZE;
    private final int NO_VALUE = 0;
    private final int CONSTRAINTS = 4;
    private final int MIN_VALUE = 1;
    private final int MAX_VALUE;
    private final int COVER_START_INDEX = 1;

    public DancingLinksAlgorithm(int sizeBox) {
        SUBSECTION_SIZE = sizeBox;
        BOARD_SIZE = sizeBox * sizeBox;
        MAX_VALUE = BOARD_SIZE;
    }

    public int[][] solve(int[][] board, int requiredQ) {
        boolean[][] cover = initializeExactCoverBoard(board);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver(BOARD_SIZE, requiredQ);
        return dlx.getResult();
    }

    public int getQuantitySolve(int[][] board, int constraint) {
        boolean[][] cover = initializeExactCoverBoard(board);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver(BOARD_SIZE, constraint);
        return dlx.getQ();
    }

    private int getIndex(int row, int column, int num) {
        return (row - 1) * BOARD_SIZE * BOARD_SIZE + (column - 1) * BOARD_SIZE + (num - 1);
    }

    private boolean[][] createExactCoverBoard() {
        boolean[][] coverBoard = new boolean[BOARD_SIZE * BOARD_SIZE * MAX_VALUE][BOARD_SIZE * BOARD_SIZE * CONSTRAINTS];

        int hBase = 0;
        hBase = checkCellConstraint(coverBoard, hBase);
        hBase = checkRowConstraint(coverBoard, hBase);
        hBase = checkColumnConstraint(coverBoard, hBase);
        checkSubsectionConstraint(coverBoard, hBase);

        return coverBoard;
    }

    private void checkSubsectionConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row += SUBSECTION_SIZE) {
            for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column += SUBSECTION_SIZE) {
                for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                    for (int rowDelta = 0; rowDelta < SUBSECTION_SIZE; rowDelta++) {
                        for (int columnDelta = 0; columnDelta < SUBSECTION_SIZE; columnDelta++) {
                            int index = getIndex(row + rowDelta, column + columnDelta, n);
                            coverBoard[index][hBase] = true;
                        }
                    }
                }
            }
        }
    }

    private int checkColumnConstraint(boolean[][] coverBoard, int hBase) {
        for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
            for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkRowConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
            for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkCellConstraint(boolean[][] coverBoard, int hBase) {
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
            for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++, hBase++) {
                for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++) {
                    int index = getIndex(row, column, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private boolean[][] initializeExactCoverBoard(int[][] board) {
        boolean[][] coverBoard = createExactCoverBoard();
        for (int row = COVER_START_INDEX; row <= BOARD_SIZE; row++) {
            for (int column = COVER_START_INDEX; column <= BOARD_SIZE; column++) {
                int n = board[row - 1][column - 1];
                if (n != NO_VALUE) {
                    for (int num = MIN_VALUE; num <= MAX_VALUE; num++) {
                        if (num != n) {
                            Arrays.fill(coverBoard[getIndex(row, column, num)], false);
                        }
                    }
                }
            }
        }
        return coverBoard;
    }
}