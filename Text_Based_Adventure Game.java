import java.util.*;
import java.io.*;

class Player implements Serializable {
    int health = 100;
    int level = 1;
    int score = 0;
    ArrayList<String> inventory = new ArrayList<>();

    void status() {
        System.out.println("\n====== PLAYER STATUS ======");
        System.out.println("Health: " + health);
        System.out.println("Level: " + level);
        System.out.println("Score: " + score);
        System.out.println("Inventory: " + inventory);
    }
}

class Game {

    Scanner sc = new Scanner(System.in);
    Player player = new Player();
    Random rand = new Random();

    void start() {

        System.out.println("====== LOST ISLAND RPG ======");

        player.inventory.add("Knife");

        while (player.health > 0) {

            showMap();
            player.status();

            System.out.println("\nChoose action:");
            System.out.println("1 Explore Forest");
            System.out.println("2 Enter Cave");
            System.out.println("3 River");
            System.out.println("4 Boss Battle");
            System.out.println("5 Save Game");
            System.out.println("6 Load Game");
            System.out.println("7 Exit");

            int choice = sc.nextInt();

            switch(choice) {

                case 1: forest(); break;
                case 2: cave(); break;
                case 3: river(); break;
                case 4: bossBattle(); break;
                case 5: saveGame(); break;
                case 6: loadGame(); break;
                case 7: 
                    System.out.println("Thanks for playing!");
                    return;

                default:
                    System.out.println("Invalid option");
            }

            if(player.score >= 200) {
                goodEnding();
                return;
            }
        }

        badEnding();
    }

    void showMap() {

        System.out.println("\n===== ISLAND MAP =====");
        System.out.println("[1] Forest");
        System.out.println("[2] Cave");
        System.out.println("[3] River");
        System.out.println("[4] Boss Lair");
    }

    void forest() {

        System.out.println("\nYou explore the forest...");

        if(rand.nextBoolean()) {

            System.out.println("A wild wolf attacks!");

            player.health -= 20;
            player.score += 20;

            System.out.println("You defeated it!");
        }
        else {

            System.out.println("You found food!");

            player.health += 10;
            player.score += 10;
        }
    }

    void cave() {

        System.out.println("\nYou entered a dark cave.");

        if(!player.inventory.contains("Treasure")) {

            System.out.println("You discovered hidden treasure!");

            player.inventory.add("Treasure");
            player.score += 50;
        }
        else {

            System.out.println("The cave is empty now.");
        }
    }

    void river() {

        System.out.println("\nYou reach the river.");

        System.out.println("1 Swim");
        System.out.println("2 Search supplies");

        int c = sc.nextInt();

        if(c == 1) {

            System.out.println("Strong current!");

            player.health -= 15;
            player.score += 10;
        }
        else {

            System.out.println("You found a healing potion!");

            player.inventory.add("Potion");
            player.health += 20;
        }
    }

    void bossBattle() {

        System.out.println("\n🔥 BOSS BATTLE: Island Guardian!");

        int bossHealth = 80;

        while(bossHealth > 0 && player.health > 0) {

            System.out.println("1 Attack");
            System.out.println("2 Use Potion");

            int c = sc.nextInt();

            if(c == 1) {

                int damage = rand.nextInt(20) + 10;

                bossHealth -= damage;
                player.health -= 15;

                System.out.println("You hit boss for " + damage);
            }

            else if(c == 2 && player.inventory.contains("Potion")) {

                player.health += 30;
                player.inventory.remove("Potion");

                System.out.println("Potion used!");
            }
        }

        if(player.health > 0) {

            System.out.println("🏆 Boss defeated!");

            player.score += 100;
            player.level++;
        }
    }

    void saveGame() {

        try {

            FileOutputStream file = new FileOutputStream("savegame.dat");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(player);
            out.close();

            System.out.println("Game Saved!");
        }

        catch(Exception e) {

            System.out.println("Save failed.");
        }
    }

    void loadGame() {

        try {

            FileInputStream file = new FileInputStream("savegame.dat");
            ObjectInputStream in = new ObjectInputStream(file);

            player = (Player) in.readObject();
            in.close();

            System.out.println("Game Loaded!");
        }

        catch(Exception e) {

            System.out.println("Load failed.");
        }
    }

    void goodEnding() {

        System.out.println("\n🎉 LEGENDARY ENDING!");
        System.out.println("You escaped the island with treasure!");
    }

    void badEnding() {

        System.out.println("\n💀 You died on the island.");
        System.out.println("Game Over.");
    }
}

public class AdventureGame {

    public static void main(String[] args) {

        Game game = new Game();
        game.start();
    }
}