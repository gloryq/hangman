package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.Guess;
import ca.cmpt213.a4.onlinehangman.model.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Controller for the Online Hangman game.
 * Interacts with Thymeleaf template html pages.
 * Randomly generates a word when a new game is created.
 * Takes in a Guess object filled in by the client to update a Game from the
 * list of Games.
 */
@Controller
public class HangmanController {
    private final List<Game> games = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong();
    private long currentGame;

    private final List<String> words = new ArrayList<>();

    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    public void hangmanControllerInit() {
        try {
            Scanner scanner = new Scanner(new File("src/commonWords.txt"));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                words.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("commonWords.txt not found");
            e.printStackTrace();
        }
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {
        Game newGame = new Game();

        Collections.shuffle(words);
        newGame.setWord(words.get(0));

        newGame.setId(nextId.incrementAndGet());

        games.add(newGame);
        currentGame = newGame.getId();

        return "welcome";
    }

    @PostMapping("/game")
    public String showGamePage(@ModelAttribute("newGuess") Guess guess,
                               Model model) {
        boolean notFinished = updateGame(guess, model);

        if (notFinished) {
            return "game";
        } else {
            return "gameover";
        }
    }

    @GetMapping("/game/{id}")
    public String showGameIDPage(@PathVariable("id") long gameID,
                                 Model model,
                                 @ModelAttribute("newGuess") Guess guess ) {
        currentGame = gameID;

        try {
            // check if game has finished yet
            boolean notFinished = updateGame(guess, model);
            if (notFinished) {
                return "game";
            } else {
                return "gameover";
            }
        // can't find game? Redirect to gamenotfound page
        } catch (Exception e) {
            return "gamenotfound";
        }
    }

    private boolean updateGame(Guess guess, Model model) {
        Game game = checkID(currentGame);
        game.setGuess(guess.getGuess());
        model.addAttribute("game", game);

        return game.getStatus() == Status.ACTIVE;
    }

    private Game checkID(long id) {
        for (Game nextGame : games) {
            if (nextGame.getId() == id) {
                return nextGame;
            }
        }

        throw new GameNotFoundException("Game ID not found.");
    }
}