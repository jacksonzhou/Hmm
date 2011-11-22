/* Generated By:JavaCC: Do not edit this line. ParserTokenManager.java */
package proj.parser;
import java.io.*;
import java.util.*;
import static proj.AbstractSyntax.*;
import proj.AbstractSyntax;

/** Token Manager. */
public class ParserTokenManager implements ParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x80000000000L) != 0L)
            return 0;
         if ((active0 & 0x2ff00000003fffc0L) != 0L)
         {
            jjmatchedKind = 64;
            return 7;
         }
         return -1;
      case 1:
         if ((active0 & 0x2ff00000003fff00L) != 0L)
         {
            if (jjmatchedPos != 1)
            {
               jjmatchedKind = 64;
               jjmatchedPos = 1;
            }
            return 7;
         }
         if ((active0 & 0xc0L) != 0L)
            return 7;
         return -1;
      case 2:
         if ((active0 & 0x2ff00000003ff300L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 2;
            return 7;
         }
         if ((active0 & 0xc80L) != 0L)
            return 7;
         return -1;
      case 3:
         if ((active0 & 0x8e100L) != 0L)
            return 7;
         if ((active0 & 0x2ff0000000371200L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 3;
            return 7;
         }
         return -1;
      case 4:
         if ((active0 & 0x2fd0000000260000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 4;
            return 7;
         }
         if ((active0 & 0x20000000111200L) != 0L)
            return 7;
         return -1;
      case 5:
         if ((active0 & 0x2c50000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 5;
            return 7;
         }
         if ((active0 & 0x380000000260000L) != 0L)
            return 7;
         return -1;
      case 6:
         if ((active0 & 0x2c40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 6;
            return 7;
         }
         if ((active0 & 0x10000000000000L) != 0L)
            return 7;
         return -1;
      case 7:
         if ((active0 & 0x2c40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 7;
            return 7;
         }
         return -1;
      case 8:
         if ((active0 & 0xc40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 8;
            return 7;
         }
         if ((active0 & 0x2000000000000000L) != 0L)
            return 7;
         return -1;
      case 9:
         if ((active0 & 0xc40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 9;
            return 7;
         }
         return -1;
      case 10:
         if ((active0 & 0xc40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 10;
            return 7;
         }
         return -1;
      case 11:
         if ((active0 & 0xc40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 11;
            return 7;
         }
         return -1;
      case 12:
         if ((active0 & 0xc40000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 12;
            return 7;
         }
         return -1;
      case 13:
         if ((active0 & 0xc00000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 13;
            return 7;
         }
         if ((active0 & 0x40000000000000L) != 0L)
            return 7;
         return -1;
      case 14:
         if ((active0 & 0x800000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 14;
            return 7;
         }
         if ((active0 & 0x400000000000000L) != 0L)
            return 7;
         return -1;
      case 15:
         if ((active0 & 0x800000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 15;
            return 7;
         }
         return -1;
      case 16:
         if ((active0 & 0x800000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 16;
            return 7;
         }
         return -1;
      case 17:
         if ((active0 & 0x800000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 17;
            return 7;
         }
         return -1;
      case 18:
         if ((active0 & 0x800000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 18;
            return 7;
         }
         return -1;
      case 19:
         if ((active0 & 0x800000000000000L) != 0L)
         {
            jjmatchedKind = 64;
            jjmatchedPos = 19;
            return 7;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         jjmatchedKind = 45;
         return jjMoveStringLiteralDfa1_0(0x100000000L);
      case 35:
         return jjStopAtPos(0, 60);
      case 37:
         return jjStopAtPos(0, 44);
      case 38:
         return jjMoveStringLiteralDfa1_0(0x400000L);
      case 39:
         return jjStopAtPos(0, 47);
      case 40:
         jjmatchedKind = 24;
         return jjMoveStringLiteralDfa1_0(0x4000000000000L);
      case 41:
         return jjStopAtPos(0, 25);
      case 42:
         return jjStopAtPos(0, 42);
      case 43:
         return jjStopAtPos(0, 41);
      case 44:
         return jjStopAtPos(0, 46);
      case 45:
         jjmatchedKind = 40;
         return jjMoveStringLiteralDfa1_0(0x8000000000000L);
      case 46:
         return jjStopAtPos(0, 48);
      case 47:
         return jjStartNfaWithStates_0(0, 43, 0);
      case 59:
         return jjStopAtPos(0, 30);
      case 60:
         jjmatchedKind = 38;
         return jjMoveStringLiteralDfa1_0(0xa00000000L);
      case 61:
         jjmatchedKind = 37;
         return jjMoveStringLiteralDfa1_0(0x80000000L);
      case 62:
         jjmatchedKind = 39;
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 67:
         return jjMoveStringLiteralDfa1_0(0x470000000000000L);
      case 68:
         return jjMoveStringLiteralDfa1_0(0x800000000000000L);
      case 73:
         return jjMoveStringLiteralDfa1_0(0x100000000000000L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x200000000000000L);
      case 91:
         return jjStopAtPos(0, 26);
      case 93:
         return jjStopAtPos(0, 27);
      case 94:
         return jjStopAtPos(0, 49);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x80000000000000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x101400L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x8c0L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x20000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x2000000000040000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x90000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x200L);
      case 123:
         return jjStopAtPos(0, 28);
      case 124:
         jjmatchedKind = 36;
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 125:
         return jjStopAtPos(0, 29);
      default :
         return jjMoveNfa_0(3, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x400000L) != 0L)
            return jjStopAtPos(1, 22);
         break;
      case 45:
         if ((active0 & 0x800000000L) != 0L)
            return jjStopAtPos(1, 35);
         break;
      case 61:
         if ((active0 & 0x80000000L) != 0L)
            return jjStopAtPos(1, 31);
         else if ((active0 & 0x100000000L) != 0L)
            return jjStopAtPos(1, 32);
         else if ((active0 & 0x200000000L) != 0L)
            return jjStopAtPos(1, 33);
         else if ((active0 & 0x400000000L) != 0L)
            return jjStopAtPos(1, 34);
         break;
      case 62:
         if ((active0 & 0x8000000000000L) != 0L)
            return jjStopAtPos(1, 51);
         break;
      case 92:
         if ((active0 & 0x4000000000000L) != 0L)
            return jjStopAtPos(1, 50);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000L);
      case 98:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x200000000200000L);
      case 102:
         if ((active0 & 0x40L) != 0L)
         {
            jjmatchedKind = 6;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x80L);
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000000000200L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000000008000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x420000000001100L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000000000800L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000006400L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0xc0000000080000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      case 124:
         if ((active0 & 0x800000L) != 0L)
            return jjStopAtPos(1, 23);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000000000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0xc0000000000000L);
      case 102:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(2, 7, 7);
         break;
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x4200L);
      case 106:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000000100000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000000000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x2400000000003000L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000L);
      case 114:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(2, 10, 7);
         return jjMoveStringLiteralDfa3_0(active0, 0x40000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x900000000008100L);
      case 116:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(2, 11, 7);
         return jjMoveStringLiteralDfa3_0(active0, 0x200000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0xc0000000001000L);
      case 100:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(3, 14, 7);
         break;
      case 101:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(3, 8, 7);
         else if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(3, 19, 7);
         return jjMoveStringLiteralDfa4_0(active0, 0x300000000020000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x40000L);
      case 108:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(3, 13, 7);
         return jjMoveStringLiteralDfa4_0(active0, 0x10200L);
      case 110:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000000000L);
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000000000000L);
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x420000000100000L);
      case 116:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(3, 15, 7);
         break;
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000L);
      case 119:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000000020000L);
      case 101:
         if ((active0 & 0x200L) != 0L)
            return jjStartNfaWithStates_0(4, 9, 7);
         else if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(4, 16, 7);
         else if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(4, 20, 7);
         return jjMoveStringLiteralDfa5_0(active0, 0x410000000000000L);
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000000000000L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000200000L);
      case 115:
         if ((active0 & 0x20000000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 53, 7);
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000000000000L);
      case 116:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(4, 12, 7);
         return jjMoveStringLiteralDfa5_0(active0, 0xc0000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 67:
         return jjMoveStringLiteralDfa6_0(active0, 0x400000000000000L);
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x800000000000000L);
      case 99:
         return jjMoveStringLiteralDfa6_0(active0, 0x10000000000000L);
      case 101:
         if ((active0 & 0x80000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 55, 7);
         return jjMoveStringLiteralDfa6_0(active0, 0x40000000000000L);
      case 103:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(5, 18, 7);
         break;
      case 110:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(5, 21, 7);
         break;
      case 116:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(5, 17, 7);
         else if ((active0 & 0x100000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 56, 7);
         else if ((active0 & 0x200000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 57, 7);
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 68:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000000000000L);
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x2000000000000000L);
      case 111:
         return jjMoveStringLiteralDfa7_0(active0, 0x400000000000000L);
      case 116:
         if ((active0 & 0x10000000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 52, 7);
         break;
      case 121:
         return jjMoveStringLiteralDfa7_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 69:
         return jjMoveStringLiteralDfa8_0(active0, 0x800000000000000L);
      case 97:
         return jjMoveStringLiteralDfa8_0(active0, 0x40000000000000L);
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x2000000000000000L);
      case 110:
         return jjMoveStringLiteralDfa8_0(active0, 0x400000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 107:
         if ((active0 & 0x2000000000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 61, 7);
         break;
      case 110:
         return jjMoveStringLiteralDfa9_0(active0, 0xc00000000000000L);
      case 116:
         return jjMoveStringLiteralDfa9_0(active0, 0x40000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa10_0(active0, 0x40000000000000L);
      case 101:
         return jjMoveStringLiteralDfa10_0(active0, 0x400000000000000L);
      case 116:
         return jjMoveStringLiteralDfa10_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
private int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa11_0(active0, 0x40000000000000L);
      case 99:
         return jjMoveStringLiteralDfa11_0(active0, 0x400000000000000L);
      case 105:
         return jjMoveStringLiteralDfa11_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
private int jjMoveStringLiteralDfa11_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa12_0(active0, 0x40000000000000L);
      case 114:
         return jjMoveStringLiteralDfa12_0(active0, 0x800000000000000L);
      case 116:
         return jjMoveStringLiteralDfa12_0(active0, 0x400000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
private int jjMoveStringLiteralDfa12_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(10, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0);
      return 12;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa13_0(active0, 0x800000000000000L);
      case 105:
         return jjMoveStringLiteralDfa13_0(active0, 0x400000000000000L);
      case 115:
         return jjMoveStringLiteralDfa13_0(active0, 0x40000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(11, active0);
}
private int jjMoveStringLiteralDfa13_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(11, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(12, active0);
      return 13;
   }
   switch(curChar)
   {
      case 68:
         return jjMoveStringLiteralDfa14_0(active0, 0x800000000000000L);
      case 101:
         if ((active0 & 0x40000000000000L) != 0L)
            return jjStartNfaWithStates_0(13, 54, 7);
         break;
      case 111:
         return jjMoveStringLiteralDfa14_0(active0, 0x400000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(12, active0);
}
private int jjMoveStringLiteralDfa14_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(12, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(13, active0);
      return 14;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa15_0(active0, 0x800000000000000L);
      case 110:
         if ((active0 & 0x400000000000000L) != 0L)
            return jjStartNfaWithStates_0(14, 58, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(13, active0);
}
private int jjMoveStringLiteralDfa15_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(13, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(14, active0);
      return 15;
   }
   switch(curChar)
   {
      case 116:
         return jjMoveStringLiteralDfa16_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(14, active0);
}
private int jjMoveStringLiteralDfa16_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(14, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(15, active0);
      return 16;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa17_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(15, active0);
}
private int jjMoveStringLiteralDfa17_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(15, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(16, active0);
      return 17;
   }
   switch(curChar)
   {
      case 98:
         return jjMoveStringLiteralDfa18_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(16, active0);
}
private int jjMoveStringLiteralDfa18_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(16, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(17, active0);
      return 18;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa19_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(17, active0);
}
private int jjMoveStringLiteralDfa19_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(17, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(18, active0);
      return 19;
   }
   switch(curChar)
   {
      case 115:
         return jjMoveStringLiteralDfa20_0(active0, 0x800000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(18, active0);
}
private int jjMoveStringLiteralDfa20_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(18, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(19, active0);
      return 20;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x800000000000000L) != 0L)
            return jjStartNfaWithStates_0(20, 59, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(19, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 16;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 63)
                        kind = 63;
                     jjCheckNAddStates(0, 2);
                  }
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(9, 10);
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 0;
                  else if (curChar == 63)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 0:
                  if (curChar == 47)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 1:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 4:
                  if (curChar == 63)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 64)
                     kind = 64;
                  jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 8:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(9, 10);
                  break;
               case 9:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(9, 10);
                  break;
               case 10:
                  if (curChar == 34 && kind > 66)
                     kind = 66;
                  break;
               case 11:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 63)
                     kind = 63;
                  jjCheckNAddStates(0, 2);
                  break;
               case 12:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 63)
                     kind = 63;
                  jjCheckNAdd(12);
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(13, 14);
                  break;
               case 14:
                  if (curChar != 46)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(15);
                  break;
               case 15:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(15);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
               case 7:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 64)
                     kind = 64;
                  jjCheckNAdd(7);
                  break;
               case 1:
                  jjAddStates(3, 4);
                  break;
               case 5:
                  if ((0x7fffffe00000000L & l) == 0L)
                     break;
                  if (kind > 62)
                     kind = 62;
                  jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 9:
                  jjAddStates(5, 6);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(3, 4);
                  break;
               case 9:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(5, 6);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 16 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   12, 13, 14, 1, 2, 9, 10, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, "\151\146", "\151\146\146", 
"\145\154\163\145", "\167\150\151\154\145", "\146\157\162", "\151\156\164", 
"\146\154\157\141\164", "\142\157\157\154", "\166\157\151\144", "\154\151\163\164", 
"\164\165\160\154\145", "\157\142\152\145\143\164", "\163\164\162\151\156\147", "\164\162\165\145", 
"\146\141\154\163\145", "\162\145\164\165\162\156", "\46\46", "\174\174", "\50", "\51", "\133", 
"\135", "\173", "\175", "\73", "\75\75", "\41\75", "\74\75", "\76\75", "\74\55", 
"\174", "\75", "\74", "\76", "\55", "\53", "\52", "\57", "\45", "\41", "\54", "\47", 
"\56", "\136", "\50\134", "\55\76", "\103\157\156\156\145\143\164", 
"\103\154\141\163\163", "\103\162\145\141\164\145\104\141\164\141\142\141\163\145", 
"\143\162\145\141\164\145", "\111\156\163\145\162\164", "\123\145\154\145\143\164", 
"\103\154\157\163\145\103\157\156\156\145\143\164\151\157\156", 
"\104\151\163\160\154\141\171\105\156\164\151\162\145\104\141\164\141\142\141\163\145", "\43", "\163\150\157\167\163\164\141\143\153", null, null, null, null, null, 
null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0xffffffffffffffc1L, 0xfL, 
};
static final long[] jjtoSkip = {
   0x3eL, 0x0L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[16];
private final int[] jjstateSet = new int[32];
protected char curChar;
/** Constructor. */
public ParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public ParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 16; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedPos == 0 && jjmatchedKind > 67)
   {
      jjmatchedKind = 67;
   }
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
