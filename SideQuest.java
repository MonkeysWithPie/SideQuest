import java.util.*;
import java.io.*;

public class SideQuest {

   public static Inventory playerInv;
   public static Enemy currentEnemy = new Enemy();
   public static int gameState;
   public static int playerHP, playerMaxHP;
   public static Scanner inputScanner = new Scanner(System.in);

   public static void main(String args[]) {
      playerInv = new Inventory();
      load();
      print("\n--------------------------\n       { WELCOME! }\n   Welcome to SideQuest!\n--------------------------\n",
            true);
      // Enemy testingEn = new Enemy(20, "Test", 0, new String[]{"Healing Potion;1"});
      // // Spawns a new enemy with 20 hp, the name "Test", 0 armor class and an
      // inventory of 1 Healing potion.
      // testingEn.damage(15);
      // testingEn.startTurn();
      // currentEnemy = testingEn;
      Inventory arr = new Inventory();
      arr.add(new Item("Healing Potion", 1));
      currentEnemy = new Enemy(100, "Test", 100, arr, "Tester Bot");
      currentEnemy.damage(100);
      reloadBars(25);
      wait(2500);
      options(gameState, "");
   }

   // Save data stuffs
   public static void save(boolean warning) {
      try {
         if (warning) {
            print("\n--------------------------\n      { SAVING... }\n Do NOT exit the program!\n--------------------------\n",
                  false);
         }
         File saveObj = new File("SideQuestData.txt");
         saveObj.delete();
         saveObj.createNewFile();
         FileWriter writeFile = new FileWriter("SideQuestData.txt");
         writeFile.write("in:");
         for (int i = 0; i < playerInv.size(); i++) {
            if (playerInv.get(i) == null) {
               writeFile.write("null,\n");
            }
            writeFile.write(playerInv.get(i).toDataString() + ",\n");
         }
         writeFile.write("inx");
         writeFile.write("\n" + gameState);
         if (currentEnemy != null && currentEnemy.getHP() != 0) { // save enemy data
            writeFile.write("\nen:");
            for (int i = 0; i < currentEnemy.getInv().size(); i++) {
               writeFile.write(currentEnemy.getInv().get(i).toDataString() + ",\n");
            }
            writeFile.write("enx");
            writeFile.write("\n" + currentEnemy.getDataString());
         } else {
            writeFile.write("\nnoen");
         }
         writeFile.close();
         wait(500);
      } catch (IOException e) {
         print("{!X!} An error occurred while trying to save data!", true);
         e.printStackTrace();
      }
   }

   public static void load() {
      try {
         print("\n--------------------------\n      { LOADING... }\n", false);
         File saveObj = new File("SideQuestData.txt");
         if (saveObj.createNewFile()) {
            // create a new save if one does not exist
            print("{!} A new save was created because one does not exist.", true);
            playerInv.add(new Item("Wooden Broadsword;1"));
            playerInv.add(new Item("Healing Potion;1"));
            gameState = -1;
            save(false);
         } else {
            // make a bunch of vars because yeah
            Scanner readFile = new Scanner(saveObj);
            int loadStatus = 0;
            ArrayList<Item> enemyInv = new ArrayList<>();
            while (readFile.hasNextLine()) {
               String currentSaveData = readFile.nextLine();

               if (loadStatus == 0) { // loadStatus of 0 = loading inventory
                  if (currentSaveData.indexOf("in:") != -1) {
                     currentSaveData = currentSaveData.substring(currentSaveData.indexOf("in:") + 3);
                  }
                  if (currentSaveData.equals("inx")) { // checks for inventory end
                     loadStatus = 1;
                     continue;
                  } else {
                     if (currentSaveData.indexOf(",") != -1) { // kill the commas
                        currentSaveData = currentSaveData.substring(0, currentSaveData.indexOf(","));
                     }
                     if (currentSaveData.indexOf("null") != -1) {
                        playerInv.add(null);
                     } else {
                        playerInv.add(new Item(currentSaveData));
                     }
                  }
               }

               if (loadStatus == 1) { // loadStatus of 1 = loading game state
                  loadStatus = 2;
                  gameState = Integer.parseInt(currentSaveData);
                  continue;
               }

               if (loadStatus == 2) { // loadStatus of 2 = loading enemy inventory
                  // ughhhhhhhhhh

                  if (currentSaveData.equals("noen")) { // handles no enemy
                     loadStatus = 3;
                     continue;
                  }
                  if (currentSaveData.indexOf("en:") != -1) { // checks for the "en:" and removes it if needed (idk why
                                                              // this exists)
                     currentSaveData = currentSaveData.substring(currentSaveData.indexOf("en:") + 3);
                  }
                  if (currentSaveData.equals("enx")) { // checks for ended inventory
                     loadStatus = 3;
                     int enInLen = 0;
                     // im not entirely sure what this does but it works!
                     for (int i = 0; i < enemyInv.size(); i++) {
                        if (enemyInv.get(i) != null) {
                           enInLen++;
                        }
                     }
                     if (enInLen != 0) {
                        Inventory newEnemyInv = new Inventory();
                        for (int i = 0; i < enemyInv.size(); i++) {
                           if (enemyInv.get(i) != null) {
                              newEnemyInv.add(enemyInv.get(i));
                           }
                        }
                        currentEnemy.setInv(newEnemyInv);
                     }
                     continue;
                  } else {
                     if (currentSaveData.indexOf(",") != -1) {
                        // kill the commas
                        currentSaveData = currentSaveData.substring(0, currentSaveData.indexOf(","));
                     }
                     if (currentSaveData.indexOf("null") != -1) { // handle null properly
                        enemyInv.add(null);
                     } else {
                        enemyInv.add(new Item(currentSaveData));
                     }
                  }
               }

               if (loadStatus == 3) { // loadStatus of 3 = loading other enemy data (hp, ac, etc.)
                  // i couldn't be bothered to make this more efficient
                  currentEnemy.setWithDataString(currentSaveData);
               }
            }
            readFile.close();
            wait(250);
         }
      } catch (IOException | ArrayIndexOutOfBoundsException e) {
         // damn looks like something broke
         print("{!X!} An error occurred while trying to load data! Your save may be corrupted.", true);
         e.printStackTrace();
         ask("Press enter to exit the program.");
         System.exit(-1);
      }
   }

   // Some useful functions
   public static void clearScreen() {
      // oh
   }

   public static void reloadBars(int enmBarSize) {
      clearScreen();
      String space = "";
      String bar = "";
      double ded = currentEnemy.getMaxHP()/((double) enmBarSize);
      double eqReq = 0;
      for (int i = 0; i < enmBarSize; i++) {
         if (currentEnemy.getHP() >= eqReq) { bar = bar + "="; } else { space = space + " "; };
         eqReq += ded;
      }
      print("[" + bar + space + "] " + currentEnemy.getName() + ": " + currentEnemy.getHP() + "/"
            + currentEnemy.getMaxHP(), true);
   }

   /**
    * @param ms time to wait in milliseconds
    */
   public static void wait(int ms) {
      try {
         Thread.sleep(ms);
      } catch (InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }

   /**
    * @param input what to print
    * @param newLn whether to add a newline or not
    */
   public static void print(Object input, boolean newLn) {
      if (newLn) {
         System.out.println(input.toString());
      } else {
         System.out.print(input.toString());
      }
   }

   /**
    * @param question what to ask
    * @return answer to the question
    */
   public static String ask(String question) {
      print(question + " ", false);
      String res = inputScanner.nextLine().trim();
      return res;
   }

   public static void printInv() {
      print("\n[ Your inventory ]", true);
      for (int i = 0; i < playerInv.size(); i++) {
         if (playerInv.get(i) == null) {
            continue;
         }
         print("  > " + playerInv.get(i), true);
      }
      print("", true);
   }

   public static void options(int state, String infoMsg) {
      int stateLen = (state + "").length();
      String centeredSpace = "    ";
      centeredSpace = centeredSpace.substring(0, 4 - stateLen);
      print("\n--------------------------\n\n       { PAUSED }         \n", false);
      // print(" State:"+centeredSpace+state,true);
      print("", true);
      if (!infoMsg.equals("")) {
         String centeredSpace2 = "            ";
         try {
            centeredSpace2 = centeredSpace2.substring(0, 12 - (infoMsg.length() / 2));
            print(centeredSpace2 + infoMsg + "\n", true);
         } catch (StringIndexOutOfBoundsException e) {
            print(infoMsg, true);
         }
      }

      // State -1 = not battling
      if (state == -1) {
         print("[ Inventory ]  [ Resume ]  [ Settings ]  [ Save ]  [ Quit ]", true);
         String choice = ask("What do you want to do?");
         if (choice.toLowerCase().equals("inventory") || choice.toLowerCase().equals("inv")) {
            printInv();
            // Wait for the player to press a button
            ask("(Press Enter to continue.)");
            options(state, "");
            return;
         }
         if (choice.toLowerCase().equals("resume")) {
            return;
         }
         if (choice.toLowerCase().equals("quit")) {
            options(-10, "Are you sure you want to quit?");
            return;
         }
         if (choice.toLowerCase().equals("save")) {
            save(true);
            options(state, "Save complete!");
            return;
         }
         if (choice.toLowerCase().equals("settings")) {
            options(-2, "");
            return;
         }
         options(state, "Invalid option.");
      }

      // State 0 = battling
      else if (state == 0) {
         print("[ Resume ]  [ Settings ]  [ Quit ]", true);
         String choice = ask("What do you want to do?");
         if (choice.toLowerCase().equals("resume")) {
            return;
         }
         if (choice.toLowerCase().equals("quit")) {
            options(-10, "Are you sure you want to quit?");
            return;
         }
         if (choice.toLowerCase().equals("settings")) {
            options(-2, "");
            return;
         }
         options(state, "Invalid option.");
      }

      // State -10 = check before quit
      else if (state == -10) {
         print("[ Yes ]  [ No ]", true);
         String choice = ask("").toLowerCase();
         if (choice.equals("yes") || choice.equals("y")) {
            print("Saving...", true);
            save(false);
            print("Exiting...", true);
            System.exit(0);
            return;
         } else {
            options(gameState, "");
            return;
         }
      }

      // State -2 = changing settings
      else if (state == -2) {
         print("there would be settings here, but I can't be bothered to add them right now", true);
         wait(1000);
         options(gameState, "");
         return;
      }

      else {
         print("Looks like you have an invalid state.", true);
         print("Resuming automatically...", true);
      }
   }
}