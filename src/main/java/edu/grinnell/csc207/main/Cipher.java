/*
 * Cipher.java
 *
 * Author: Sara Jaljaa
 * Course: CSC-207-01
 *
 */

package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CipherUtils;
import java.io.PrintWriter;

/**
 * Prints encrypted or decrypted Vigenere/Caesar ciphers.
 *
 * @author Sara Jaljaa
 */
public class Cipher {

  /**
   * Compares a val to two strings + returns a boolean depending on
   * if the string matches one of the values and has an empty
   * String to later store the matching value.
   *
   * @param compareVal The string to compare to others.
   * @param str1 The first string to compare with.
   * @param str2 The second string to compare with.
   * @param store An empty String variable.
   * @return True if the strings are all the same and store is empty (null), else
   *         false.
   */
  public static boolean compareArg(String compareVal, String str1, String str2, String store) {
    return ((compareVal.equals(str1) || compareVal.equals(str2)) && store == null);
  } // compareArg(String, String, String, String)

  /**
   * Required parameters to create a cipher.
   */
  private static final int PARAMS = 4;

  /**
   * Creates a cipher based on command line arguments.
   *
   * @param args Command line arguments to encode/decode,
   * the type of cipher (vigenere/caesar), the input string, and key.
   */
  @SuppressWarnings("unused")
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);

    // Variables to store parameters to cipher
    String type = null;
    String action = null;
    String target = null;
    String key = null;

    if (args.length != PARAMS) {
      System.err.println("Error: Expected 4 parameters, received: " + args.length);
      return;
    } // if

    for (int i = 0; i < args.length; i++) {
      if (compareArg(args[i], "-vigenere", "-caesar", type)) {
        type = args[i];
      } else if (compareArg(args[i], "-encode", "-decode", action)) {
        action = args[i];
      } else if (target == null) {
        if (CipherUtils.allLower(args[i])) {
          target = args[i];
        } else {
          System.err.println("Error: Strings must be only lowercase letters.");
          break;
        } // if/else
      } else if (key == null) {
        if (CipherUtils.allLower(args[i])) {
          key = args[i];
        } else {
          System.err.println("Error: Keys must be only lowercase letters.");
          break;
        } // if/else
      } // if
    } // for

    // Check for presence of cipher type and action specified in arguments.
    if (action == null) {
      System.err.println("Error: no valid action specified."
                        + "Legal values are \'-encode\' and \'-decode\'");
      return;
    } else if (type == null) {
      System.err.println("Error: no valid cipher specified."
                        + "Legal values are \'-caesar\' and \'-vigenere\'");
      return;
    } else if (key == null) {
      System.err.println("Error: Empty keys are not permitted.");
      return;
    } // if

    // Check the caesar cipher has a one-character key.
    if ((type.equals("-caesar")) && (key.length() > 1)) {
      System.err.println("Error: Caesar ciphers require a one-character key.");
      return;
    } // if

    // Print the cipher depending on its type & action.
    switch (type) {
      case "-vigenere":
        if (action.equals("-decode")) {
          pen.println(CipherUtils.vigenereDecrypt(target, key));
        } else if (action.equals("-encode")) {
          pen.println(CipherUtils.vigenereEncrypt(target, key));
        } // if
        break;
      case "-caesar":
        if (action.equals("-decode")) {
          pen.println(CipherUtils.caesarDecrypt(target, key.charAt(0)));
        } else if (action.equals("-encode")) {
          pen.println(CipherUtils.caesarEncrypt(target, key.charAt(0)));
        } // if
        break;
      default:
        break;
    } // switch
    pen.close();
  } // main(String[])
} // class Cipher
