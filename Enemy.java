public class Enemy {
   private int maxHP, curHP, armor;
   private String type, name;
   private Inventory inventory;

   // CONSTRUCTOR METHODS
   /**
    * @param health HP to give the enemy
    * @param name   Type of the enemy (not displayed)
    * @param ac     Armor class of the enemy
    * @param inv    Inventory
    * @param nm     Name displayed to the player
    */
   public Enemy(int health, String name, int ac, Inventory inv, String nm) {
      maxHP = health;
      curHP = health;
      type = name;
      armor = ac;
      inventory = inv;
      this.name = nm;
   }

   public Enemy(String dataString) {
      this();
      setWithDataString(dataString);
   }

   public Enemy() {
      maxHP = 0;
      curHP = 0;
      type = "none";
      armor = 0;
      inventory = new Inventory();
      name = "Air";
   }

   // MODIFIER METHODS
   public void setHP(int newHP) {
      curHP = newHP;
   }

   public void damage(int dmg) {
      curHP -= dmg;
   }

   public void setInv(Inventory newInv) {
      inventory = newInv;
   }

   public void setArmor(int armorC) {
      armor = armorC;
   }

   public void setType(String nType) {
      type = nType;
   }

   public void setMaxHP(int newHP) {
      maxHP = newHP;
   }

   public void setName(String nm) {
      name = nm;
   }

   public void setWithDataString(String dataString) {
      curHP = (Integer
            .parseInt(dataString.substring(dataString.indexOf("hp=") + 3, dataString.indexOf(","))));
      dataString = dataString.substring(dataString.indexOf(",") + 1);
      maxHP = (Integer
            .parseInt(dataString.substring(dataString.indexOf("mhp=") + 4, dataString.indexOf(","))));
      dataString = dataString.substring(dataString.indexOf(",") + 1);
      name = (dataString.substring(dataString.indexOf("ty=") + 3, dataString.indexOf(",")));
      dataString = dataString.substring(dataString.indexOf(",") + 1);
      armor = (Integer
            .parseInt(dataString.substring(dataString.indexOf("ac=") + 3, dataString.indexOf(","))));
      dataString = dataString.substring(dataString.indexOf(",") + 1);
      name = (dataString.substring(dataString.indexOf("nm=") + 3));
   }

   // ACCESSOR METHODS
   public Inventory getInv() {
      return inventory;
   }

   public int getHP() {
      return curHP;
   }

   public int getMaxHP() {
      return maxHP;
   }

   public String getType() {
      return type;
   }

   public int getAC() {
      return armor;
   }

   public String getAllData() {
      return "HP: " + curHP + ", Max: " + maxHP + ", type: " + type + ", AC: " + armor;
   }

   public String toString() {
      return "Enemy " + name + " (" + type + ") has " + curHP + "/" + maxHP + " hp.";
   }

   public String getName() {
      return name;
   }

   public String getDataString() {
      return "hp=" + curHP + ",mhp=" + maxHP + ",ty=" + type + ",ac=" + armor + ",nm=" + name;
   }

   public void startTurn() {
      SideQuest.wait(500);
      if (maxHP - curHP >= 10 && inventory.getItem("Healing Potion") != -1) {
         inventory.addItem("Healing Potion", -1);
         damage(-10);
         SideQuest.print(type + " healed 10 HP with a Healing Potion.", true);
      }
      if (type.equals("Goblin")) {
         // goblin AI
      }
      if (type.equals("Dragon")) {
         // dragon AI
      }
      if (type.equals("Test")) {
         // why is this here
      }
   }
}