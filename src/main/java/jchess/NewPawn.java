package jchess;

import java.util.ArrayList;

/**
 *
 */
public class NewPawn extends Pawn {

    NewPawn(Chessboard chessboard, Player player)
    {
        super(chessboard, player);
        //this.setImages("Pawn-W.png", "Pawn-B.png");
        this.symbol = "";
        this.setImage();
    }

    @Override
    public ArrayList allMoves()
    {
        //System.out.println(this.player.goDown);//4test
        ArrayList list = new ArrayList();
        Square sq;
        Square sq1;
        int first = this.square.pozY - 1;//number where to move
        int second = this.square.pozY - 2;//number where to move (only in first move)
        if (this.player.goDown)
        {//check if player "go" down or up
            first = this.square.pozY + 1;//if yes, change value
            second = this.square.pozY + 2;//if yes, change value
        }
        if (this.isout(first, first))
        {//out of bounds protection
            return list;//return empty list
        }
        sq = chessboard.squares[this.square.pozX][first];
        if (sq.piece == null)
        {//if next is free
            //list.add(sq);//add
            if (this.player.color == Player.colors.white)
            {//white

                if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][first]))
                {
                    list.add(chessboard.squares[this.square.pozX][first]);
                }
            }
            else
            {//or black

                if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][first]))
                {
                    list.add(chessboard.squares[this.square.pozX][first]);
                }
            }

            {
                sq1 = chessboard.squares[this.square.pozX][second];
                if (sq1.piece == null)
                {
                    //list.add(sq1);//only in first move
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][second]))
                        {
                            list.add(chessboard.squares[this.square.pozX][second]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX][second]))
                        {
                            list.add(chessboard.squares[this.square.pozX][second]);
                        }
                    }
                }
            }
        }
        if (!this.isout(this.square.pozX - 1, this.square.pozY)) //out of bounds protection
        {
            //capture
            sq = chessboard.squares[this.square.pozX - 1][first];
            if (sq.piece != null)
            {//check if can hit left
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[this.square.pozX - 1][this.square.pozY];
            if (sq.piece != null
                    && this.chessboard.twoSquareMovedPawn != null
                    && sq == this.chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX - 1][first]);
                        }
                    }
                }
            }
        }
        if (!this.isout(this.square.pozX + 1, this.square.pozY))
        {//out of bounds protection

            //capture
            sq = chessboard.squares[this.square.pozX + 1][first];
            if (sq.piece != null)
            {//check if can hit right
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    { //white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[this.square.pozX + 1][this.square.pozY];
            if (sq.piece != null
                    && this.chessboard.twoSquareMovedPawn != null
                    && sq == this.chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (this.player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (this.player.color == Player.colors.white)
                    {//white

                        if (this.chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (this.chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(this.square, chessboard.squares[this.square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[this.square.pozX + 1][first]);
                        }
                    }
                }
            }
        }

        return list;
    }
}
