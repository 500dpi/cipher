package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CipherUtils;
import java.io.PrintWriter;

/**
 * Prints an encrypted or decrypted Vigenere/Caesar cipher with
 * the specified input values.
 *
 * @author Sara Jaljaa
 */
public class Cipher {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * Required parameters to create a cipher.
   */
  private static final int PARAMS = 4;

  // +----------------+----------------------------------------------
  // | Helper Methods |
  // +----------------+

  /**
   * Checks if either strings match the desired value.
   *
   * @param match
   *    The string to compare to.
   * @param str1
   *    The first string to compare.
   * @param str2
   *    The second string to compare.
   *
   * @return
   *    True if one of the strings are the same as the string to match.
   */
  public static boolean matchCheck(String match, String str1, String str2) {
    if (emptyCheck(match)) {
      return false;
    } // if
    return (match.equals(str1) || match.equals(str2));
  } // matchCheck(String, String, String, String)

  /**
   * Checks if a string is empty.
   *
   * @param arg
   *    The string to check.
   *
   * @return
   *    True or false if the string is empty.
   */
  public static boolean emptyCheck(String arg) {
    return ((arg == null) || arg.equals(""));
  } // emptyCheck(String)

  // +-------------+-------------------------------------------------
  // | Main Method |
  // +-------------+

  /**
   * Creates a cipher based on command line arguments.
   *
   * @param args
   *    Command line arguments that indicate the type, action,
   *    message, and key (in any order) to create a cipher.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);

    // Empty parameters
    String key = null;
    String type = null;
    String action = null;
    String message = null;

    if (args.length != PARAMS) {
      System.err.println("Error: Expected 4 parameters, received: " + args.length);
      return;
    } // if

    for (int i = 0; i < args.length; i++) {
      // Check for the cipher type
      if (matchCheck(args[i], "-vigenere", "-caesar") && type == null) {
        type = args[i];
      // Check for the cipher action
      } else if (matchCheck(args[i], "-encode", "-decode") && action == null) {
        action = args[i];
      // Check for the plaintext
      } else if (message == null) {
        if (emptyCheck(args[i])) {
          return;
        } else if (CipherUtils.lowercaseCheck(args[i])) {
          message = args[i];
        } else {
          System.err.println("Error: String must be only lowercase letters.");
          return;
        } // elif
      // Check for the cipher key
      } else if (key == null) {
        if (emptyCheck(args[i])) {
          System.err.println("Error: Empty keys are not permitted.");
          return;
        } else if (CipherUtils.lowercaseCheck(args[i])) {
          key = args[i];
        } else {
          System.err.println("Error: Key must be only lowercase letters.");
          return;
        } // elif
      } // elif
    } // for

    // Check that the cipher action and type have valid values
    if (action.equals("")) {
      System.err.println("Error: no valid action specified."
                        + "Legal values are \"-encode\" and \"-decode\"");
      return;
    } else if (type.equals("")) {
      System.err.println("Error: no valid cipher specified."
                        + "Legal values are \"-caesar\" and \"-vigenere\"");
      return;
    } // elif

    // If the type is a Caesar cipher, check that the key is the proper length
    if ((type.equals("-caesar")) && (key.length() != 1)) {
      System.err.println("Error: Caesar ciphers require a one-character key.");
      return;
    } // if

    // Print the cipher
    switch (type) {
      case "-vigenere":
        if (action.equals("-decode")) {
          pen.println(CipherUtils.vigenereDecrypt(message, key));
        } else if (action.equals("-encode")) {
          pen.println(CipherUtils.vigenereEncrypt(message, key));
        } // elif
        break;
      case "-caesar":
        if (action.equals("-decode")) {
          pen.println(CipherUtils.caesarDecrypt(message, key.charAt(0)));
        } else if (action.equals("-encode")) {
          pen.println(CipherUtils.caesarEncrypt(message, key.charAt(0)));
        } // elif
        break;
      default:
        break;
    } // switch
  } // main(String[])
} // class Cipher
