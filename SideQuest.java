import java.util.*;
import java.io.*;

public class SideQuest {

   public static String[] playerInv;
   public static Enemy currentEnemy = new Enemy();
   public static int gameState;
   public static int playerHP, playerMaxHP;
   
   public static void main(String args[]) {
      playerInv = new String[5];
      load();
      print("\n--------------------------\n       { WELCOME! }\n   Welcome to SideQuest!\n--------------------------\n",true);
      // Enemy testingEn = new Enemy(20, "Test", 0, new String[]{"Healing Potion;1"}); // Spawns a new enemy with 20 hp, the name "Test", 0 armor class and an inventory of 1 Healing potion.
      // testingEn.damage(15);
      // testingEn.startTurn();
      // currentEnemy = testingEn;
      reloadBars(25);
      wait(2500);
      options(gameState,"");
   }
   
   
   // Save data stuffs
   public static void save(boolean warning) {
   try {
      if (warning) { print("\n--------------------------\n      { SAVING... }\n Do NOT exit the program!\n--------------------------\n",false); }
      File saveObj = new File("SideQuestData.txt");
      saveObj.delete();
      saveObj.createNewFile();
      FileWriter writeFile = new FileWriter("SideQuestData.txt");
      writeFile.write("in:");
      for (int i = 0; i < playerInv.length; i++) {
         writeFile.write(playerInv[i]+",\n");
      }
      writeFile.write("inx");
      writeFile.write("\n"+gameState);
      if (currentEnemy != null && currentEnemy.getHP() != 0) { // save enemy data
         writeFile.write("\nen:");
         for (int i = 0; i < currentEnemy.getInv().length; i++) {
            writeFile.write(currentEnemy.getInv()[i]+",\n");
         }
         writeFile.write("enx");
         writeFile.write("\nhp="+currentEnemy.getHP()+",mhp="+currentEnemy.getMaxHP()+",ty="+currentEnemy.getType()+",ac="+currentEnemy.getAC()+",nm="+currentEnemy.getName());
      } else {
         writeFile.write("\nnoen");
      }
      writeFile.close();
      wait(500);
   } catch (IOException e) {
      print("{!X!} An error occurred while trying to save data!",true);
      e.printStackTrace();
   }
   }
   public static void load() {
      try {
         print("\n--------------------------\n      { LOADING... }\n",false);
         File saveObj = new File("SideQuestData.txt");
         if (saveObj.createNewFile()) {
            // create a new save if one does not exist
            print("{!} A new save was created because one does not exist.",true);
            playerInv[0] = "Wooden Broadsword;1";
            playerInv[1] = "Healing Potion;1";
            gameState = -1;
            save(false);
         } else {
            // make a bunch of vars because yeah
            Scanner readFile = new Scanner(saveObj);
            int loadStatus = 0;
            int invIndex = 0;
            int envIndex = 0;
            String[] enemyInv = new String[10];
            while (readFile.hasNextLine()) {
               String currentSaveData = readFile.nextLine();
               
               if (loadStatus == 0) { // loadStatus of 0 = loading inventory
                  if (currentSaveData.indexOf("in:") != -1) {
                     currentSaveData = currentSaveData.substring(currentSaveData.indexOf("in:")+3);
                  }
                  if (currentSaveData.equals("inx")) { // checks for inventory end
                     loadStatus = 1;
                     continue;
                  } else {
                  if (currentSaveData.indexOf(",") != -1) { // kill the commas
                     currentSaveData = currentSaveData.substring(0,currentSaveData.indexOf(","));
                  }
                  if (currentSaveData.indexOf("null") != -1) {
                     playerInv[invIndex] = null;
                  } else {
                     playerInv[invIndex] = currentSaveData;
                  }
                  }
                  invIndex++;
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
                  if (currentSaveData.indexOf("en:") != -1) { // checks for the "en:" and removes it if needed (idk why this exists)
                     currentSaveData = currentSaveData.substring(currentSaveData.indexOf("en:")+3);
                  }
                  if (currentSaveData.equals("enx")) { // checks for ended inventory
                     loadStatus = 3;
                     int enInLen = 0;
                     // im not entirely sure what this does but it works!
                     for (int i = 0; i < enemyInv.length; i++) {
                        if (enemyInv[i] != null) { enInLen++; }
                     }
                     if (enInLen != 0) { 
                        String[] newEnemyInv = new String[enInLen];
                        int nEnInvInd = 0;
                        for (int i = 0; i < enemyInv.length; i++) {
                           if (enemyInv[i] != null) { newEnemyInv[nEnInvInd] = enemyInv[i]; nEnInvInd++; }
                        }
                     currentEnemy.setInv(newEnemyInv);
                     }
                     continue;
                  } else {
                  if (currentSaveData.indexOf(",") != -1) {
                     // kill the commas
                     currentSaveData = currentSaveData.substring(0,currentSaveData.indexOf(","));
                  }
                  if (currentSaveData.indexOf("null") != -1) { // handle null properly
                     enemyInv[envIndex] = null;
                  } else {
                     enemyInv[envIndex] = currentSaveData;
                  }
                  }
                  envIndex++;
               }
               
               if (loadStatus == 3) { // loadStatus of 3 = loading other enemy data (hp, ac, etc.)
                  // i couldn't be bothered to make this more efficient
                  currentEnemy.setHP(Integer.parseInt(currentSaveData.substring(currentSaveData.indexOf("hp=")+3,currentSaveData.indexOf(","))));
                  currentSaveData = currentSaveData.substring(currentSaveData.indexOf(",")+1);
                  currentEnemy.setMaxHP(Integer.parseInt(currentSaveData.substring(currentSaveData.indexOf("mhp=")+4,currentSaveData.indexOf(","))));
                  currentSaveData = currentSaveData.substring(currentSaveData.indexOf(",")+1);
                  currentEnemy.setType(currentSaveData.substring(currentSaveData.indexOf("ty=")+3,currentSaveData.indexOf(",")));
                  currentSaveData = currentSaveData.substring(currentSaveData.indexOf(",")+1);
                  currentEnemy.setArmor(Integer.parseInt(currentSaveData.substring(currentSaveData.indexOf("ac=")+3,currentSaveData.indexOf(","))));
                  currentSaveData = currentSaveData.substring(currentSaveData.indexOf(",")+1);
                  currentEnemy.setName(currentSaveData.substring(currentSaveData.indexOf("nm=")+3));
               }
            }
            readFile.close();
            wait(250);
         }
      } catch (IOException|ArrayIndexOutOfBoundsException e) {
         // damn looks like something broke
         print("{!X!} An error occurred while trying to load data! Your save may be corrupted.",true);
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
      for (int i = currentEnemy.getMaxHP(); i >= 0; i -= (currentEnemy.getMaxHP()/enmBarSize)) {
         if (i <= currentEnemy.getHP()) {
            bar = bar.concat("=");
         } else {
            space = space.concat(" ");
         }
      }
      print("["+bar+space+"] "+currentEnemy.getName()+": "+currentEnemy.getHP()+"/"+currentEnemy.getMaxHP(),true);
   }
   /**
    * @param ms time to wait in milliseconds
    */
   public static void wait(int ms)
   { try { Thread.sleep(ms); }
    catch(InterruptedException ex)
    { Thread.currentThread().interrupt(); }
   }
   
   /**
    * @param input what to print
    * @param newLn whether to add a newline or not
    */
   public static void print(String input, boolean newLn) {
      if (newLn) { System.out.println(input);
      } else { System.out.print(input); }
   }
   
   /**
    * @param question what to ask
    * @return answer to the question
    */
   public static String ask(String question) {
      Scanner asker = new Scanner(System.in);
      print(question + " ",false);
      String res = asker.nextLine().trim();
      asker.close();
      return res;
   }
   
   /**
    * @param list inventory to sort through
    * @param itemName name to look through
    * @return the amount of that item (-1 if not found)
    */
   public static int getItem(String[] list, String itemName) {
      for (int i = 0; i < list.length; i++) {
         if (list[i] == null) { continue; }
         if (list[i].indexOf(itemName) != -1) {
            String afterSemi = list[i].substring(list[i].indexOf(";")+1);
            if (Integer.parseInt(afterSemi) == 0) { return -1; }         
            return Integer.parseInt(afterSemi);
         }
      }
      return -1;
   }
   
   /**
    * @param list inventory to set
    * @param name name of the item to change
    * @param value count to set item to
    */
   public static void setItem(String[] list, String name, int value) {
      int nonNullPos = 0;
      for (int i = 0; i < list.length; i++) {
         if (list[i] == null) { continue; }
         nonNullPos = i;
         if (list[i].indexOf(name) != -1) {
            list[i] = name+";"+value;
            return;
         }
      }
      list[nonNullPos+1] = name+";"+value;
   }
   
   /**
    * @param list inventory to modify
    * @param name name of the item to change
    * @param toAdd amount to add (also supports negatives)
    */
   public static void addItem(String[] list, String name, int toAdd) {
      if (getItem(list,name) != -1) {
         setItem(list,name,(getItem(list,name)+toAdd));
      } else { 
         setItem(list,name,toAdd);
      }
   }
   
   public static void printInv() {
      print("\n[ Your inventory ]",true);
      for (int i = 0; i < playerInv.length; i++) {
         if (playerInv[i] == null) {continue;}
         int itemStack = Integer.parseInt(playerInv[i].substring(playerInv[i].indexOf(";")+1));
         print("  > "+playerInv[i].substring(0,playerInv[i].indexOf(";"))+" x"+itemStack,true);
      }
      print("",true);
   }
   
   public static void options(int state, String infoMsg) {
      int stateLen = (state + "").length();
      String centeredSpace = "    ";
      centeredSpace = centeredSpace.substring(0,4-stateLen);
      print("\n--------------------------\n\n       { PAUSED }         \n",false);
      // print("       State:"+centeredSpace+state,true);
      print("",true);
      if (!infoMsg.equals("")) {
         String centeredSpace2 = "            ";
         try {
            centeredSpace2 = centeredSpace2.substring(0,12-(infoMsg.length()/2));
            print(centeredSpace2+infoMsg+"\n",true);
            } catch(StringIndexOutOfBoundsException e) {
            print(infoMsg,true);
            }
      }
      
      // State -1 = not battling
      if (state == -1) {
         print("[ Inventory ]  [ Resume ]  [ Settings ]  [ Save ]  [ Quit ]",true);
         String choice = ask("What do you want to do?");
         if (choice.toLowerCase().equals("inventory") || choice.toLowerCase().equals("inv")) { 
            printInv();
            // Wait for the player to press a button
            ask("(Press Enter to continue.)");
            options(state,"");
            return; 
         }
         if (choice.toLowerCase().equals("resume")) { return; }
         if (choice.toLowerCase().equals("quit")) { options(-10,"Are you sure you want to quit?"); return; }
         if (choice.toLowerCase().equals("save")) { save(true); options(state,"Save complete!"); return; }
         if (choice.toLowerCase().equals("settings")) { options(-2,""); return; }
         options(state,"Invalid option.");
      }

      // State 0 = battling
      else if (state == 0) {
         print("[ Resume ]  [ Settings ]  [ Quit ]",true);
         String choice = ask("What do you want to do?");
         if (choice.toLowerCase().equals("resume")) { return; }
         if (choice.toLowerCase().equals("quit")) { options(-10,"Are you sure you want to quit?"); return; }
         if (choice.toLowerCase().equals("settings")) { options(-2,""); return; }
         options(state,"Invalid option.");
      }

      // State -10 = check before quit
      else if (state == -10) {
         print("[ Yes ]  [ No ]",true);
         String choice = ask("").toLowerCase();
         if (choice.equals("yes") || choice.equals("y")) {
            print("Saving...",true);
            save(false);
            print("Exiting...",true);
            System.exit(0);
            return;
         } else {
            options(gameState,"");
            return;
         }
      }

      // State -2 = changing settings
      else if (state == -2) {
         print("there would be settings here, but I can't be bothered to add them right now",true);
         wait(1000);
         options(gameState,"");
         return;
      }
      
      else {
         print("Looks like you have an invalid state.",true);
         print("Resuming automatically...",true);
      }
   }
}