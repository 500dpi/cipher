package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CipherUtils;
import java.io.PrintWriter;

/**
 * Prints all the possible combinations (a-z) of an encrypted or
 * decrypted Caesar cipher.
 *
 * @author Sara Jaljaa
 * @course CSC-207-01
 */
public class AllCaesar {

  // +----------------+----------------------------------------------
  // | Helper Methods |
  // +----------------+

  /**
   * Print all possible Caesar ciphers for a message.
   *
   * @param str
   *    The string to encipher.
   * @param pen
   *    The object to print from.
   * @param choice
   *    A letter (e or d) to indicate the cipher action.
   */
  public static void printCipher(String str, PrintWriter pen, char choice) {
    for (char key = 'a'; key < (char) (('z') + 1); key++) {
      switch (choice) {
        case 'e':
          pen.printf("n = %c: %s\n", key, CipherUtils.caesarEncrypt(str, key));
          break;
        case 'd':
          pen.printf("n = %c: %s\n", key, CipherUtils.caesarDecrypt(str, key));
          break;
        default:
          System.err.println("Error: Invalid cipher action.");
          return;
      } // switch
    } // for
  } // printCipher(String, PrintWriter, char)

  // +-------------+-------------------------------------------------
  // | Main Method |
  // +-------------+

  /**
   * Prints a Caesar cipher from command line arguments.
   *
   * @param args
   *    Command line arguments, where args[0] is cipher action and
   *    args[1] is the message.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);

    if (args.length != 2) {
      System.err.printf("Error: Incorrect number of parameters (%d).\n", args.length);
      return;
    } // if

    if (!(CipherUtils.lowercaseCheck(args[0]))) {
      System.err.println("Error: Strings must be only lowercase letters.");
      return;
    } // if

    if (args[0].equals("encode")) {
      printCipher(args[1], pen, 'e');
    } else if (args[0].equals("decode")) {
      printCipher(args[1], pen, 'd');
    } else {
      System.err.printf("Error: Invalid option: %s. Valid options are"
                        + " \"encode\" or \"decode\".\n", args[0]);
    } // elif
  } // main(String[])
} // class main

