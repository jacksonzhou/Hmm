/* 
* added by MST
* organizes test statements based on indentation and prints them 
* an alternative to System.out.println() is Debug.println()  
*/

package proj;

public class Debug {

  private static final int debugFlag = 1;
  static int tabLevel = 0;
  private static int tabSpaces = 2;

  public static void printAddTab(String s) {
    if (debugFlag != 0) {
      Debug.println(s);
      ++tabLevel;
    }
  }

  public static void print() {
    System.out.print("");
  }

  public static void print(String s) {
    if(debugFlag != 0)
      System.out.print(createSpaces().concat(s));
  }

  public static void println() {
    System.out.println();
  }
  
  public static void println(Object o) {
    if(debugFlag != 0) {
      println(o.toString());
    }
  }
  
  public static void println(String s) {
    if(debugFlag != 0)
      System.out.println(createSpaces().concat(s));
  }

  public static void println(int i) {
    if(debugFlag != 0) {
      System.out.print(createSpaces());
      System.out.println(i);
    }
  }

  public static void println(boolean b) {
    if(debugFlag != 0) {
      System.out.print(createSpaces());
      System.out.println(b);
    }
  }

  public static void removeTab() {
    if (debugFlag != 0) {
      --tabLevel;
      if(tabLevel < 0) {
        tabLevel = 0;
      }
    }
  }
  
  public static void removeTab(int i) {
    if (debugFlag != 0) {
      tabLevel -= i;
      if(tabLevel < 0) {
        tabLevel = 0;
      }
    }
  }
  
  public static void addTab() {
    if (debugFlag != 0)
      ++tabLevel;
  }
  
  public static void addTab(int i) {
    if (debugFlag != 0)
      tabLevel += i;
  }
  
  private static String createSpaces() {
    if (debugFlag != 0 ) {
      String tab = " ";
      String ret = "";
      int i;
      for (i = 1; i < Debug.tabSpaces; ++i) {
        tab = tab.concat(" ");
      }
      for (i = 0; i < tabLevel; ++i) {
        ret = ret.concat(tab);
      }
      return ret;
    }
    return null;
  }

}
