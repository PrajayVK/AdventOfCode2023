
// import java.io.*;
// import java.nio.file.*;
// import java.util.*;

// public class SnowIslandGame {

// static class GameRound {
// int red, green, blue;

// GameRound(int red, int green, int blue) {
// this.red = red;
// this.green = green;
// this.blue = blue;
// }
// }

// static class Game {
// int id;
// List<GameRound> rounds;

// Game(int id, List<GameRound> rounds) {
// this.id = id;
// this.rounds = rounds;
// }
// }

// public static void main(String[] args) {
// // Replace this with the path to your input file
// String filePath = "Games.txt";

// try {
// String gameData = new String(Files.readAllBytes(Paths.get(filePath)));
// List<Game> games = parseGameData(gameData);

// int sumOfPossibleGames = calculateSumOfPossibleGames(games, 12, 13, 14);
// System.out.println("Sum of possible games: " + sumOfPossibleGames);

// int totalPower = calculateTotalPower(games);
// System.out.println("Total power: " + totalPower);

// } catch (IOException e) {
// e.printStackTrace();
// }
// }

// private static List<Game> parseGameData(String data) {
// List<Game> games = new ArrayList<>();
// String[] gameEntries = data.split("\n");

// for (String entry : gameEntries) {
// String[] parts = entry.split(": ");
// int gameId = Integer.parseInt(parts[0].split(" ")[1]);
// String[] roundsData = parts[1].split("; ");
// List<GameRound> rounds = new ArrayList<>();

// for (String round : roundsData) {
// int red = 0, green = 0, blue = 0;
// String[] cubeCounts = round.split(", ");
// for (String count : cubeCounts) {
// String[] countParts = count.split(" ");
// int number = Integer.parseInt(countParts[0]);
// switch (countParts[1]) {
// case "red":
// red = number;
// break;
// case "green":
// green = number;
// break;
// case "blue":
// blue = number;
// break;
// }
// }
// rounds.add(new GameRound(red, green, blue));
// }

// games.add(new Game(gameId, rounds));
// }

// return games;
// }

// private static int calculateSumOfPossibleGames(List<Game> games, int
// redCubes, int greenCubes, int blueCubes) {
// int sum = 0;
// for (Game game : games) {
// if (isGamePossible(game, redCubes, greenCubes, blueCubes)) {
// sum += game.id;
// }
// }
// return sum;
// }

// private static boolean isGamePossible(Game game, int redCubes, int
// greenCubes, int blueCubes) {
// for (GameRound round : game.rounds) {
// if (round.red > redCubes || round.green > greenCubes || round.blue >
// blueCubes) {
// return false;
// }
// }
// return true;
// }

// private static int calculateTotalPower(List<Game> games) {
// int totalPower = 0;
// for (Game game : games) {
// GameRound minCubes = findMinimumCubes(game);
// totalPower += minCubes.red * minCubes.green * minCubes.blue;
// }
// return totalPower;
// }

// private static GameRound findMinimumCubes(Game game) {
// int minRed = 0, minGreen = 0, minBlue = 0;
// for (GameRound round : game.rounds) {
// minRed = Math.max(minRed, round.red);
// minGreen = Math.max(minGreen, round.green);
// minBlue = Math.max(minBlue, round.blue);
// }
// return new GameRound(minRed, minGreen, minBlue);
// }
// }
