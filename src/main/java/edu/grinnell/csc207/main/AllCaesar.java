/*
 * AllCaesar.java
 * 
 * Author: Sara Jaljaa
 * Course: CSC-207-01
 * 
 */

package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CipherUtils;
import java.io.PrintWriter;

/**
 * Prints and encrypted or decrypted string from a-z using the
 * Caesar cipher.
 * 
 * @author Sara Jaljaa
 *
 */
public class AllCaesar {
/**
   * Prints a caesar cipher.
   *
   * @param str Takes a string to encipher.
   * @param pen Takes an object to print from.
   * @param choice Takes a letter, 'e' or 'd' indicating the action.
   */
  public static void printCipher(String str, PrintWriter pen, char choice) {
    for (char ch = 'a'; ch < (char) (('z') + 1); ch++) {
      if (choice == 'e') {
        pen.printf("n = %c: %s\n", ch, CipherUtils.caesarEncrypt(str, ch));
      } else if (choice == 'd') {
        pen.printf("n = %c: %s\n", ch, CipherUtils.caesarDecrypt(str, ch));
      } else {
        break;
      } // end if the choice is encode or decode.
    } // end for loop that repeats caesar cipher from a-z.
  } // printCipher(String,PrintWriter,char)
/**
   * Prints a caesar cipher from command line arguments.
   *
   * @param args Command line arguments, where args[0] is the string
   * and args[1] is the key.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    String encode = "encode";
    String decode = "decode";
    if (args.length == 2) {
      if (CipherUtils.allLower(args[1])) {
        System.err.println("Error: String contains characters other than lowercase letters.");
      } else if (encode.equals(args[0])) {
        printCipher(args[1], pen, 'e');
      } else if (decode.equals(args[0])) {
        printCipher(args[1], pen, 'd');
      } else {
        pen.close();
        System.err.printf("Error: Invalid option: %s. Valid options are"
                          + " encode\" or \"decode\".\n", args[0]);
      } // if print statement.
    } else {
      System.err.println("Error: Incorrect number of parameters.");
    } // if args length is not 2.
  } // main(String[])
} // class main

