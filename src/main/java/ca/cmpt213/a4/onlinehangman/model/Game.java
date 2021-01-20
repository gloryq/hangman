package ca.cmpt213.a4.onlinehangman.model;

/**
 * Represents a Hangman Game.
 * Stores the game id, word, total guesses and incorrect guesses, and status of the game.
 * Also stores the progress of the game and most recent guess.
 * Updates total guesses and incorrect guesses as a new guess is set.
 */
public class Game {
    private long id;
    private String word;
    private int totalGuesses = 0;
    private int incorrectGuesses = 0;
    private final int maxIncorrect = 7;
    private Status status;

    public String progress;

    public Game() {
        this.id = -1;
        progress = "";
        status = Status.ACTIVE;
    }

    public Game(long id, String word, Character guess, String progress, int totalGuesses, int incorrectGuesses) {
        this.id = id;
        this.word = word;
        this.progress = progress;
        this.totalGuesses = totalGuesses;
        this.incorrectGuesses = incorrectGuesses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
        setInitialProgress();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setGuess(String guess) {

        if (!guess.equals("")) {
            totalGuesses += 1;
            setProgress(guess);
        }
    }

    public String getProgress() {
        String show = "";

        int length = word.length();

        for (int i = 0; i < length; i++) {
            show = show.concat(progress.charAt(i) + " ");
        }

        return show;
    }

    public void setInitialProgress() {
        int length = word.length();

        for (int i = 0; i < length; i++) {
            progress = progress.concat("_");
        }
    }

    public void setProgress(String guess) {
        int found = word.indexOf(guess);

        if (found < 0) {
            incorrectGuesses += 1;
        } else {
            int length = word.length();

            for (int i = found; i < length; i++) {
                if (word.charAt(i) == guess.charAt(0)) {
                    progress = progress.substring(0, i) + guess + progress.substring(i + 1);
                }
            }
        }

        if (progress.equals(word)) {
            setStatus(Status.WON);
        } else if (incorrectGuesses > maxIncorrect) {
            setStatus(Status.LOST);
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", totalGuesses=" + totalGuesses +
                ", incorrectGuesses=" + incorrectGuesses +
                ", status='" + status + '\'' +
                ", progress='" + progress + '\'' +
                '}';
    }
}
