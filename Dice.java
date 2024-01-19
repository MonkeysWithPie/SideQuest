/** A dice object with not only sides and a value but also a color and a name. 
  * @author Rainer Mueller
  * @version 1.4
*/
public class Dice {
   private String name;
   private int sides, value;
   public Dice() {
      name = "d6"; sides = 6; value = 1;
   }
   public Dice(String initName, int initSides, int initValue) {
      name = initName;
      sides = initSides;
      value = initValue;
   }
   public String getName() {
      return name;
   }
   public int getSides() {
      return sides;
   }
   public int getValue() {
      return value;
   }  
   public void setValue(int newVal) { value = newVal; }
   public int roll() {
      value = (int) (Math.random()*sides)+1;
      return value;
   }
   public String toString() {
      return name+" ("+"sides="+sides+" value="+value+")";
   }
}