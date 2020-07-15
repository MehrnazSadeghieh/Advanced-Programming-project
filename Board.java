public class Board {

    char[] board = new char[9];

    public Board() {

    }

    public Board(char[] board) {
        this.board = board;
    }

    public char[] initializeBoard(char[] board){
        for(int i = 0; i < 9; i++){
            this.board[i] = board[i];
        }

        return this.board;
    }

    public boolean checkMovesLeft(){
        for(int i = 0 ; i < this.board.length; i++){
            if(this.board[i] != 'x' && this.board[i] != 'o')
                return true;
        }

        return false;
    }

    public char[] getBoard() {
        return board;
    }

    public void setBoard(char[] board) {
        this.board = board;
    }

    public void setBoardPosition(int position,char input){
        this.board[position] = input;
    }
}