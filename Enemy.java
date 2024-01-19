public class Enemy {
   private int maxHP, curHP, armor;
   private String type, name;
   private String[] inventory;
   
   // CONSTRUCTOR METHODS
   public Enemy(int health, String name, int ac, String[] inv, String nm) {
      maxHP = health;
      curHP = health;
      type = name;
      armor = ac;
      inventory = inv;
      name = nm;
   }
   public Enemy() {
      maxHP = 0;
      curHP = 0;
      type = "none";
      armor = 0;
      inventory = new String[0];
      name = "Air";
   }


   // MODIFIER METHODS
   public void setHP(int newHP) {
      curHP = newHP;
   }
   public void damage(int dmg) {
      curHP -= dmg;
   }
   public void setInv(String[] newInv) {
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
   
   // ACCESSOR METHODS
   public String[] getInv() {
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
      return "HP: "+curHP+", Max: "+maxHP+", type: "+type+", AC: "+armor;
   }
   public String toString() {
      return "Enemy "+name+" ("+type+") has "+curHP+"/"+maxHP+" hp.";
   }
   public String getName() {
      return name;
   }
   
   
   public void startTurn() {
      SideQuest.wait(500);
      if (maxHP - curHP >= 10 && SideQuest.getItem(inventory, "Healing Potion") != -1) {
         SideQuest.addItem(inventory, "Healing Potion",-1);
         damage(-10);
         SideQuest.print(type + " healed 10 HP with a Healing Potion.",true);
      }
      if (type.equals("Goblin")) {
         // goblin AI
      }
      if (type.equals("Dragon")) {
         // dragon AI
      }
      if (type.equals("Test")) {
         // why is this  here
      }
   }
}