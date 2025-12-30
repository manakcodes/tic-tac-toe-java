import java.util.ArrayList;
import java.util.Scanner;

class UI
{
    public static final String PINK = "\u001B[35m";
    public static final String HOT_PINK = "\u001B[38;5;201m";
    public static final String RESET = "\u001B[0m";
}


class TicTacToe
{
    private final int ROWS = 3;
    private final int COLS = 3;
    private ArrayList<Integer> MOVES_USED = new ArrayList<>();

    private char[][] BOARD = {
            {'-', '-', '-'},
            {'-', '-', '-'},
            {'-', '-', '-'}
    };

    private void PrintBOARD(boolean WITH_ROWNUMS, String COLOR)
    {
        if (COLOR!=null)
        {
            System.out.println(COLOR);
        }

        int num = 1;

        System.out.printf("\n");

        for (int i = 0; i < ROWS; i++)
        {
            System.out.printf("+-----");
        }

        System.out.printf("+\n");

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                if (!WITH_ROWNUMS)
                {
                    System.out.printf("| %-4c", BOARD[i][j]);
                }
                else
                {
                    System.out.printf("| %-4d", num++);
                }
            }

            System.out.print("|\n");

            for (int k = 0; k < ROWS; k++)
            {
                System.out.printf("+-----");

            }
            System.out.printf("+\n");

        }

        System.out.printf("\n");
        System.out.println(UI.RESET);
    }

    private int GetValidInt(Scanner UserInput)
    {
        while (true)
        {
            String input = UserInput.nextLine();

            try
            {
                return Integer.parseInt(input);
            }

            catch (NumberFormatException e)
            {
                System.out.print("[ERROR] -> Enter a valid number: ");
            }

        }
    }

    private char GetUserSymbolChoice(Scanner UserInput)
    {
        while (true)
        {
            System.out.print("player 1 -> enter choice (X / O) : ");
            String input = UserInput.nextLine().trim();
            if (input.length()==1)
            {
                char choice = Character.toUpperCase(input.charAt(0));
                if (choice=='X' || choice=='O')
                {
                    return choice;
                }
            }
            System.out.println("[ERROR] -> enter a valid choice\nvalid choice -> [X / O]\n");
        }
    }

    public String IsSomeOneWinning(char Player)
    {
        for (int i = 0; i < 3; i++)
        {
            if (BOARD[i][0]==Player && BOARD[i][1]==Player && BOARD[i][2]==Player)
            {
                return "row " + (i + 1);
            }

            if (BOARD[0][i]==Player && BOARD[1][i]==Player && BOARD[2][i]==Player)
            {
                return "column " + (i + 1);
            }
        }

        if (BOARD[0][0]==Player && BOARD[1][1]==Player && BOARD[2][2]==Player)
        {
            return "main diagonal";
        }

        if (BOARD[0][2]==Player && BOARD[1][1]==Player && BOARD[2][0]==Player)
        {
            return "anti diagonal";
        }

        return null;
    }

    private void PrintWinningVisual(String reason, String COLOR)
    {
        if (COLOR!=null)
        {
            System.out.println(COLOR);
        }

        System.out.println("\nWINNING PATH HIGHLIGHT (-> " + reason + ") : ");

        for (int i = 0; i < 3; i++)
        {
            System.out.println("+-----+-----+-----+");

            for (int j = 0; j < 3; j++)
            {
                boolean part = false;

                if (reason.contains("row " + (i + 1)))
                {
                    part = true;
                }

                if (reason.contains("column " + (j + 1)))
                {
                    part = true;
                }

                if (reason.equals("main diagonal") && i==j)
                {
                    part = true;
                }

                if (reason.equals("anti diagonal") && i + j==2)
                {
                    part = true;
                }

                System.out.print(part ? "|  ^  ":"|     ");
            }

            System.out.println("|");
        }

        System.out.println("+-----+-----+-----+");

        System.out.println(UI.RESET);
    }


    private boolean IsMoveTaken(int move)
    {
        int size = MOVES_USED.size();
        for (int i = 0; i < size; i++)
        {
            if (MOVES_USED.get(i)==move)
            {
                return true;
            }
        }
        return false;
    }


    private void MakeMove(Scanner UserInput, char symbol)
    {
        while (true)
        {
            System.out.print("enter position [1 - 9] for " + symbol + " : ");

            int position = GetValidInt(UserInput);

            if (position >= 1 && position <= 9)
            {
                if (!IsMoveTaken(position))
                {
                    int row = (position - 1) / 3;
                    int col = (position - 1) % 3;

                    BOARD[row][col] = symbol;
                    MOVES_USED.add(position);
                    System.out.println("move accepted !");
                    break;
                }
                else
                {
                    System.out.println("[ERROR] -> position " + position + " is taken");
                    System.out.println("move REJECTED !\n");
                }
            }
            else
            {
                System.out.println("[ERROR] -> valid range is [1 - 9]");
                System.out.println("move REJECTED !\n");
            }
        }
    }

    public void execute(Scanner UserInput)
    {
        String ProjectGuide = "\nA minimal, simple, terminal-based Tic-Tac-Toe game written in Java,\nsupporting two-player gameplay with colored, clean console output,\ninput validation and win patterns (row, col, main-diagonal, anti-diagonal)\n\n";

        System.out.println(ProjectGuide);

        System.out.println("\nnumbers corresponding to each block in the grid : ");
        PrintBOARD(true, UI.PINK);
        char P1 = GetUserSymbolChoice(UserInput);
        char P2 = (P1=='X') ? 'O':'X';
        char CurrentPlayer = P1;
        int PlayerNum = 1;

        while (true)
        {
            System.out.println("\n----- PLAYER -> " + PlayerNum + " with choice -> (" + CurrentPlayer + ") -----");
            MakeMove(UserInput, CurrentPlayer);

            PrintBOARD(false, null);

            String WinStatus = IsSomeOneWinning(CurrentPlayer);

            if (WinStatus!=null)
            {
                PrintBOARD(false, UI.HOT_PINK);
                System.out.println("====================================");
                System.out.println("PLAYER -> " + PlayerNum + " with choice -> " + CurrentPlayer + " WINS !");
                PrintWinningVisual(WinStatus, UI.HOT_PINK);
                System.out.println("====================================");
                break;
            }

            if (MOVES_USED.size()==9)
            {
                System.out.println("----- MATCH DRAW ! -----");
                break;
            }

            CurrentPlayer = (CurrentPlayer==P1) ? P2:P1;
            PlayerNum = (PlayerNum==1) ? 2:1;
        }
    }

    private void ResetGame()
    {
        MOVES_USED.clear();
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                BOARD[i][j] = '-';
            }
        }
    }

    public TicTacToe()
    {
        Scanner UserInput = new Scanner(System.in);

        while (true)
        {
            ResetGame();

            execute(UserInput);

            String choice;

            while (true)
            {
                System.out.print("\nDO YOU WANT TO PLAY AGAIN? [Y / N] : ");
                choice = UserInput.nextLine().trim();

                if (choice.equals("y") || choice.equals("Y") || choice.equals("n") || choice.equals("N"))
                {
                    break;
                }

                System.out.println("[ERROR] -> INVALID INPUT !\nvalid input -> [Y / N]");
            }

            if (choice.equals("n") || choice.equals("N"))
            {
                System.out.println("THANKS FOR PLAYING\n");
                break;
            }
        }

        UserInput.close();
    }
}

public class Main
{
    public static void main(String[] args)
    {
        new TicTacToe();
    }
}