package pgdp.carol;

import static pgdp.MiniJava.*;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CarolPfadfinder {
    public static char[] shortestInstructions;

//||||||||||||||||||||||MAIN|FUNCTION|||||||||MAIN|FUNCTION|||||||||||||MAIN|FUNCTION|||||||||||||||MAIN|FUNCTION|||||||||||||||||MAIN|FUNCTION||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    public static int getFrontX(int direction, int x) {
        int FrontX = 0;
        switch (direction) {
            case 0 -> {
                FrontX = x + 1;
            }
            case 1, 3 -> {
                FrontX = x;
            }
            case 2 -> {
                FrontX = x - 1;
            }
        }
        return FrontX;

    }

    public static int getFrontY(int direction, int y) {
        int FrontY = 0;
        switch (direction) {
            case 0, 2 -> {
                FrontY = y;
            }
            case 1 -> {
                FrontY = y + 1;
            }
            case 3 -> {
                FrontY = y - 1;
            }
        }
        return FrontY;
    }

    public static boolean gameFunc(int[][] Space, int x, int y, int Direction, int Blocks, char instruction) {
        int FrontX = 0;
        int FrontY = 0;
//--------------------------------------------------------------------------------------------DETECTING FrontX & FrontY
        if (Direction == 0) {
            FrontX = x + 1;
            FrontY = y;
        }
        if (Direction == 1) {
            FrontX = x;
            FrontY = y + 1;
        }
        if (Direction == 2) {
            FrontX = x - 1;
            FrontY = y;
        }
        if (Direction == 3) {
            FrontX = x;
            FrontY = y - 1;
        }
//- - - - - - - - - - - - - - - - - - - - - - - - - - 'r' TURN RIGHT
        switch (instruction) {
            case 'r', 'R' -> Direction = (Direction + 1) % 4;
        }
//- - - - - - - - - - - - - - - - - - - - - - - - - - 'l' TURN LEFT
        switch (instruction) {
            case 'l', 'L' -> Direction = (Direction + 3) % 4;
        }
//- - - - - - - - - - - - - - - - - - - - - - - - - - 's' STEP
        if (instruction == 's' || instruction == 'S') {
            int xPrev = x;
            int yPrev = y;
            switch (Direction) {
                case 0 -> x += 1;
                case 1 -> y += 1;
                case 2 -> x -= 1;
                case 3 -> y -= 1;
            }
            if ((x == Space.length  || x < 0) || (y == Space[0].length || y < 0)) {
                x = xPrev;
                y = yPrev;
//-------------------------------------Carol cannot leave the field
                return false;
            }
            if (((Space[x][y] - Space[xPrev][yPrev] > 1) || (Space[x][y] - Space[xPrev][yPrev] < -1))) {
                x = xPrev;
                y = yPrev;
//-------------------------------------Carol cannot go to the next field because the difference in height is too great.
                return false;
            }
        }
//- - - - - - - - - - - - - - - - - - - - - - - - - - 'n' - TAKE BLOCK
        if (instruction == 'n' || instruction == 'N') {

            if (Blocks > 9 || Space[x][y] == -1) {
//-------------------------------------Carol can't take a block of ice, she's already carrying ten.
                return false;

            } else if ((Direction == 0 && FrontX == Space.length) || (Direction == 1 && FrontY == Space[0].length ) || (Direction == 2 && FrontX < 0) || (Direction == 3 && FrontY < 0)) {
//--------------------------------------Carol cannot take blocks of ice off the field.
                return false;

            } else if (Space[FrontX][FrontY] < 0) {
//--------------------------------------Carol can't take a block of ice, there aren't any left.
                return false;

            } else {
//                Space[FrontX][FrontY] -= 1;
//                Blocks += 1;
            }
        }
        if (instruction == 'p' || instruction == 'P') {

            if (Blocks < 1) {
                //Carol can't lay a block of ice because she isn't carrying one.
                return false;

            } else if (Space[x][y] == -1) {
                //Carol cannot lay blocks of ice in the water.
                return false;
            }

            if ((Direction == 0 && x + 1 == Space.length) || (Direction == 1 && y + 1 == Space[0].length) || (Direction == 2 && x - 1 < 0) || (Direction == 3 && y - 1 < 0)) {
                //Carol cannot put blocks of ice off the field of play.
                return false;
            }
            if (Space[FrontX][FrontY] == 9) {
                //Carol can't lay a block of ice, there are already ten blocks of ice on the field.
                return false;
            }}
        return true;
    }
//||||||||||||||||||||||MAIN|FUNCTION|||||||||MAIN|FUNCTION|||||||||||||MAIN|FUNCTION|||||||||||||||MAIN|FUNCTION|||||||||||||||||MAIN|FUNCTION||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

//---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static int getMinimalTurns(int x, int y, int direction, int findX, int findY) {
        int turns = 0;
        int XX = x - findX;
        int YY = y - findY;
//------------------------------------------------------------ X <> 0
        if (XX != 0 && YY != 0) {
            if (XX > 0) {
                switch (direction) {
                    case 0 -> turns = 2;
                    case 2 -> turns = 1;
                }
            } else {
                switch (direction) {
                    case 0 -> turns = 1;
                    case 2 -> turns = 2;
                }
            }
        }
//------------------------------------------------------------ Y <> 0
        if (YY != 0 && XX != 0) {
            if (YY > 0) {
                switch (direction) {
                    case 3 -> turns = 1;
                    case 1 -> turns = 2;
                }
            } else {
                switch (direction) {
                    case 3 -> turns = 2;
                    case 1 -> turns = 1;
                }
            }
        }
//------------------------------------------------------------ (X - FIND X) == 0
        if (XX == 0 && YY != 0) {
            switch (direction) {
                case 0, 2 -> turns = 1;
            }
            if (YY > 0) {
                switch (direction) {
                    case 3 -> turns = 0;
                    case 1 -> turns = 2;
                }
            } else {
                switch (direction) {
                    case 3 -> turns = 2;
                    case 1 -> turns = 0;
                }
            }
        }
//------------------------------------------------------------- (Y - FIND Y) == 0
        if (YY == 0 && XX != 0) {
            switch (direction) {
                case 3, 1 -> turns = 1;
            }
            if (XX > 0) {
                switch (direction) {
                    case 0 -> turns = 2;
                    case 2 -> turns = 0;
                }
            } else {
                switch (direction) {
                    case 0 -> turns = 0;
                    case 2 -> turns = 2;
                }
            }
        }
        if (XX == 0 && YY == 0)
            return 0;
        return turns;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {

        int[][] playground = { //
                {0, -1, -1, -1, -1}, //
                {-1, -1, -1, -1, -1}, //
                {-1, -1, 7, 8, 9}, //
                {-1, -1, 8, 3, 5}, //
                {-1, -1, 9, 5, 3} //
        };
        int startX = 2;
        int startY = 1;
        int startDir = 0;
        int startBlocks = 1;

        printPlayground(playground, startX, startY, startDir, startBlocks);

        int findX = 4;
        int findY = 4;

        // this is expected to have an optimal solution with exactly 40 instructions
        char[] instructions = null;
        		instructions = findOptimalSolution(playground, startX, startY, startDir, startBlocks, findX, findY, 40); // TODO implement
        boolean success = instructions != null;

        if (success) {
            System.out.println("SUCCESS");
            System.out.println(Arrays.toString(instructions));
        } else {
            System.out.println("FAILED");
        }
    }
    //--------------------------------------------------------------------------------------LAST INSTRUCTIONS USELESS
    static boolean lastTurnsAreUseless(char[] Instructions, int filled) {
        if (filled < 2)
            return false;
        if ((Instructions[filled - 1] == 'l'  && Instructions[filled - 2] == 'r') || (Instructions[filled - 1] == 'r'  && Instructions[filled - 2] == 'l') || (Instructions[filled - 1] == 'r' && Instructions[filled - 2] == 'r'))
            return true;
        if (filled > 2)
            return Instructions[filled - 1] == 'l' & Instructions[filled - 2] == 'l' & Instructions[filled - 3] == 'l';
        return false;
    }

    //--------------------------------------------------------------------------------------LAST INSTRUCTIONS USELESS
    static boolean lastTurnsAreUseless2(char[] Instructions, int filled) {
        if (filled < 2)
            return false;
        return (Instructions[filled - 1] == 'p' && Instructions[filled - 2] == 'n') || (Instructions[filled - 1] == 'n' && Instructions[filled - 2] == 'p');
    }
    //--------------------------------------------------------------------------------------WAS THERE BEFORE

    static boolean wasThereBefore(char[] Instructions, int filled) {
        int x = 0;
        int y = 0;
        int direction = 0;

        if (filled == 0 || Instructions.length == 0) return false;

        for (int i = filled - 1; i >= 0; i--) {
//--------------------------------------------------------- P & N
            if (Instructions[i] == 'p' || Instructions[i] == 'n') return false;
            else switch (Instructions[i]) {
//-------------------------------------------------------------- L
                case 'l' -> direction = (direction + 1) % 4;
//-------------------------------------------------------------- R
                case 'r' -> direction = (direction + 3) % 4;
//-------------------------------------------------------------- S
                case 's' -> {
                    switch (direction) {
                        case 0 -> x++;
                        case 1 -> y++;
                        case 2 -> x--;
                        case 3 -> y--;
                    }
                }
            }
            if (x == 0 & y == 0) return true;
        }
        return false;
    }
//------------------------------------------------------------------------------------------------------------ getMinimalStepsAndTurns
    static int getMinimalStepsAndTurns(int x, int y, int direction, int findX, int findY) {
        return (Math.abs(x - findX) + Math.abs(y - findY) + getMinimalTurns(x, y, direction, findX, findY));
    }
//------------------------------------------------------------------------------------------------------------ getMinimalStepsAndTurns
    public static boolean findInstructions(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions) {
        if (instructions.length < 1) return false;

        if (playground.length == 1 && playground[0].length == 1){
            for (int i = 0; i < instructions.length; i++){
                instructions[i] = 'e';
            }
            shortestInstructions = instructions;
            return true;
        }
        if (findInstructionsHelper(playground, x, y, direction, blocks, findX, findY, instructions, 1, 's')) {
            shortestInstructions = instructions;
            return true;
        }

        return false;
    }
    public static boolean findInstructionsHelper(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, char[] instructions, int filled, char instrCurrent) {

        if (getMinimalStepsAndTurns(x, y, direction, findX, findY) > instructions.length - filled + 1)
            return false;
//        printPlayground(playground, x, y, direction, blocks);

        if (lastTurnsAreUseless(instructions, filled - 1) || lastTurnsAreUseless2(instructions, filled - 1))
            return false;

        if (x == findX & y == findY){
            for (int i = filled - 1; i < instructions.length; i++){
                instructions[i] = 'e';
            }
            return true;
        }
        if (filled - 1 == instructions.length) return false;

        int prevX = x;
        int prevY = y;

        int prevDirection = direction;
        boolean A;
        instrCurrent = 'l';
        instructions[filled - 1] = instrCurrent;

        if (instrCurrent == 'l') {
            if (!lastTurnsAreUseless(instructions, filled)) {
                direction = (direction + 1) % 4;
                A = findInstructionsHelper(playground, x, y, direction, blocks, findX, findY, instructions, filled + 1, instrCurrent);
                if (A) {
                    return true;
                } else {
                    direction = prevDirection;
                }
            }
        }
        instrCurrent = 'r';
        instructions[filled - 1] = instrCurrent;

        if (instrCurrent == 'r') {

            if (!lastTurnsAreUseless(instructions, filled)) {
                direction = (direction + 3) % 4;

                A = findInstructionsHelper(playground, x, y, direction, blocks, findX, findY, instructions, filled + 1, instrCurrent);
                if (A) {
                    return true;
                } else {
                    direction = prevDirection;
                }
            }
        }
/////////////////////////////////////////////////////////
        instrCurrent = 'p';
        instructions[filled - 1] = instrCurrent;

        if (instrCurrent == 'p') {

            int FrontX = getFrontX(direction, x);
            int FrontY = getFrontY(direction, y);

            if (gameFunc(playground, x, y, direction, blocks, instrCurrent)) {

                playground[FrontX][FrontY]++;
                blocks--;

                A = findInstructionsHelper(playground, x, y, direction, blocks, findX, findY, instructions, filled + 1, instrCurrent);
                if (A) {
                    playground[FrontX][FrontY]--;
                    blocks ++;
                    return true;

                } else {
                    playground[FrontX][FrontY]--;
                    blocks = blocks + 1;
                }
            }
        }
        instrCurrent = 'n';
        instructions[filled - 1] = instrCurrent;

        if (instrCurrent == 'n') {
            int FrontX = getFrontX(direction, x);
            int FrontY = getFrontY(direction, y);
            if (gameFunc(playground, x, y, direction, blocks, instrCurrent)) {
                playground[FrontX][FrontY]--;
                blocks++;

                A = findInstructionsHelper(playground, x, y, direction, blocks, findX, findY, instructions, filled + 1, instrCurrent);
                if (A) {
                    playground[FrontX][FrontY]++;
                    blocks = blocks - 1;
                    return true;

                } else {
                    playground[FrontX][FrontY]++;
                    blocks = blocks - 1;
                }
            }
        }
        instrCurrent = 's';
        instructions[filled - 1] = instrCurrent;

        if (instrCurrent == 's') {

            if (gameFunc(playground, x, y, direction, blocks, instrCurrent) && !wasThereBefore(instructions, filled)) {

                switch (direction) {
                    case 0 -> x++;
                    case 1 -> y++;
                    case 2 -> x--;
                    case 3 -> y--;
                }

                A = findInstructionsHelper(playground, x, y, direction, blocks, findX, findY, instructions, filled + 1, instrCurrent);
                if (A) {
                    return true;

                }else{
                    x = prevX;
                    y = prevY;
                }
            }
        }
        return false;
    }
    public static char[] findOptimalSolution(int[][] playground, int x, int y, int direction, int blocks, int findX, int findY, int searchLimit) {
        int bottomBorder = 0;
        char [] instructionsChar = new char[0];

        if (x == findX && y == findY) {
            shortestInstructions = instructionsChar;
            return shortestInstructions;
        }

        for (int i = 0; i <= searchLimit; i++) {
            instructionsChar = new char[i];

            if (findInstructions(playground, x, y, direction, blocks, findX, findY, instructionsChar)) {
                return shortestInstructions;
            }
        }
        return null;
    }
}
