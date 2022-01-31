package com.leejs1030.connectfour.datastructure;

import com.leejs1030.connectfour.consts.Consts;
import com.leejs1030.connectfour.myexception.WrongInputException;

public class Board{

    private class Cell{
        private char val;
        public Cell(){this.val = Consts.BLANK;};
        public Cell(char val){this.val = val;};
        public Cell(Cell c) { this.val = c.val; }
    
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Cell) return this.val == ((Cell)obj).val;
            else return this.val == ((Character)obj);
        }
    
        @Override
        public String toString(){
            return String.valueOf(this.val);
        }
    
        public char set(char val){
            this.val = val;
            return this.val;
        }
    
        public char get(){
            return this.val;
        }
    }
    
    private Cell[][] board;

    public Board(){
        this.board = new Cell[Consts.MAXROW][Consts.MAXCOL];
        initializeBoard();
    }

    public Board(Board b){
        this.board = new Cell[Consts.MAXROW][Consts.MAXCOL];
        for(int i = 0; i < Consts.MAXROW; i++){
            for(int j = 0; j < Consts.MAXCOL; j++){
                this.board[i][j] = new Cell(b.board[i][j]);
            }
        }
    }

    private void initializeBoard(){
        for(int i = 0; i < Consts.MAXROW; i++){
            for(int j = 0; j < Consts.MAXCOL; j++){
                board[i][j] = new Cell();
            }
        }
    }

    public void showBoard(){
        for(int i = Consts.MAXROW - 1; i >= 0; i--){
            System.out.print((i + 1) + " ");
            for(int j = 0; j < Consts.MAXCOL; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("0 ");
        for(int j = 0; j < Consts.MAXCOL; j++) System.out.print(j + 1 + " ");
        System.out.println();
    }

    public char get(int row, int col){
        return this.board[row][col].get();
    }

    public int getTop(int col){
        int r;
        for(r = Consts.MAXROW; r > 0; r--){
            if(!this.board[r - 1][col].equals(Consts.BLANK)) break;
        }
        return r;
    }

    public int insertChip(int col, char val) throws WrongInputException {
        int r = getTop(col);
        if(r == Consts.MAXROW) throw new WrongInputException(1);
        this.board[r][col].set(val);
        return r;
    }

    public int insertChip(int col, Cell c) throws WrongInputException {
        return insertChip(col, c.get());
    }

    @Override
    public boolean equals(Object obj) {
        Board target = (Board)obj;
        for(int i = 0; i < Consts.MAXROW; i++){
            for(int j = 0; j < Consts.MAXCOL; j++){
                if(!this.board[i][j].equals(target.board[i][j])) return false;
            }
        }
        return true;
    }


    public boolean isFinished(int row, int col){
        char t = board[row][col].get();
        if(t == Consts.BLANK) return false;
        return checkCol(row, col, t) || checkRow(row, col, t) || checkDiagonal(row, col, t);
    }

    private boolean checkCol(int row, int col, char t){
        int cr, cl;
        for(cr = col + 1; cr < Consts.MAXCOL; cr++){
            if(board[row][cr].get() != t) break;
        }
        cr--;
        for(cl = col - 1; cl >= 0; cl--){
            if(board[row][cl].get() != t) break;
        }
        cl++;
        return (cr - cl + 1 >= 4);
    }

    private boolean checkRow(int row, int col, char t){
        int rr, rl;
        for(rr = row + 1; rr < Consts.MAXROW; rr++){
            if(board[rr][col].get() != t) break;
        }
        rr--;
        for(rl = row - 1; rl >= 0; rl--){
            if(board[rl][col].get() != t) break;
        }
        rl++;
        return (rr - rl + 1 >= 4);
    }

    private boolean checkDiagonal(int row, int col, char t){
        return checkUpperRight(row, col, t) || checkLowerRight(row, col, t);
    }

    private boolean checkUpperRight(int row, int col, char t){
        int ir, il;
        for(ir = 1; row + ir < Consts.MAXROW && col + ir < Consts.MAXCOL; ir++){
            if(board[row + ir][col + ir].get() != t) break;
        }
        ir--;
        for(il = -1; row + il >= 0 && col + il >= 0; il--){
            if(board[row + il][col + il].get() != t) break;
        }
        il++;
        return (ir - il + 1 >= 4);
    }

    private boolean checkLowerRight(int row, int col, char t){
        int ir, il;
        for(ir = 1; row + ir < Consts.MAXROW && col - ir >= 0; ir++){
            if(board[row + ir][col - ir].get() != t) break;
        }
        ir--;
        for(il = -1; row + il >= 0 && col - il < Consts.MAXCOL; il--){
            if(board[row + il][col - il].get() != t) break;
        }
        il++;
        return (ir - il + 1 >= 4);
    }
}
