package ca.cmpt213.a4.onlinehangman.model;

/**
 * Represents a Guess.
 * User inputted guess is stored here to transfer over to
 * Game class.
 */
public class Guess {
    private String guess;

    public Guess() {
        this.guess = "";
    }

    public Guess(String guess) {
        this.guess = guess;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
