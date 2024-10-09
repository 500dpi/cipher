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
 * Contains general and helper methods to encrypt and decrypt
 * with CipherUtils methods.
 *
 * @author Sara Jaljaa
 *
 */
public class Cipher {

  /**
   * Required argument length.
   */
  private static final int ARG = 4;

  /**
   * Determines if string is encoded or decoded.
   *
   * @param str Takes a string to encipher.
   * @param method Changes to 1, 2, or 0 based on if it is
   * meant to encrypt, decrypt, or no action.
   * @param counter Counts the instances of the variable.
   * @return The integer method of enciphering/deciphering.
   */
  public static int encOrDec(String str, int method, int counter) {
    if (str.equals("-encode")) {
      method = 1;
      counter++;
    } else if (str.equals("-decode")) {
      method = 2;
      counter++;
    } // if string matches flags, set method to a number.
    return method;
  } // encOrDec(String,int,int)

  /**
   * Determines the cipher type (Vigenere or Caesar).
   *
   * @param str Takes a string to encipher.
   * @param type Changes to 1, 2, or 0 based on if it is
   * meant to caesar, vigenere, or no type.
   * @param counter Counts the instances of the variable.
   * @return The integer type of enciphering/deciphering.
   */
  public static int caeOrVig(String str, int type, int counter) {
    if (str.equals("-caesar")) {
      type = 1;
      counter++;
    } else if (str.equals("-vigenere")) {
      type = 2;
      counter++;
    } // if string matches flags, set type to a number.
    return type;
  } // caeOrVig(String,int,int)

  /**
   * Determines the arguments to the cipher methods entered in
   * the command line.
   *
   * @param arg String array of arguments from the command line.
   * @param counter Counts the instances of the variable.
   * @return A new string array with just the string and key to encipher/
   * decipher with.
   */
  public static String[] getArgs(String[] arg, int counter) {
    String[] newArg = new String[2];
    for (int i = 0, j = 0; i < arg.length && j < 2; i++) {
      if (arg[i].charAt(0) != '-') {
        newArg[j] = arg[i];
        j++;
        counter++;
      } // if arg doesn't have a flag, add it to the array.
    } // for the length of the string array find all non-flags.
    return newArg;
  } // getArgs(String[],int)

 /**
   * Creates a cipher based on command line arguments.
   *
   * @param args Command line arguments to encode/decode,
   * the type of cipher (vigenere/caesar), the input string, and key.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    int type = 0;
    int method = 0;
    int counter = 0;
    String[] arguments = new String[2];
    String output;
    for (int i = 0; i < args.length; i++) {
      method = caeOrVig(args[i], method, counter);
      type = encOrDec(args[i], type, counter);
      arguments = getArgs(args, counter);
    } // end finding the arguments and assigning them values.
    if (args.length < ARG) {
      System.err.println("Error: Expected 4 parameters, received " + args.length);
    } else {
      switch (method) {
        case 1:
          if (arguments[1] != null) {
            char ch = arguments[1].charAt(0);
            String str = arguments[0];
            if (arguments[1].length() > 1) {
              System.err.println("Error: Caesar ciphers require a one-character key.");
            } // print error if argument has no key.
            if (type == 1) {
              output = CipherUtils.caesarEncrypt(str, ch);
              pen.printf("%s\n", output);
            } else if (type == 2) {
              output = CipherUtils.caesarDecrypt(str, ch);
              pen.printf("%s\n", output);
            } else {
              System.err.println("Error: No valid action specified. Legal values are"
                                + " \"-encode\" and \"-decode\"");
            } // if type is 1 or 2 call method.
          } else if (arguments[1] == null) {
            System.err.println("Error: Caesar ciphers require a one-character key.");
          } // else/if
          break;
        case 2:
          if (arguments[1] != null) {
            if (type == 1) {
              output = CipherUtils.vigenereEncrypt(arguments[0], arguments[1]);
              pen.printf("%s\n", output);
            } else if (type == 2) {
              output = CipherUtils.vigenereDecrypt(arguments[0], arguments[1]);
              pen.printf("%s\n", output);
            } else {
              System.err.println("Error: No valid action specified. Legal values are"
                                + " \"-encode\" and \"-decode\"");
            } // if type is 1 or 2 call method.
          } else if (arguments[1] == null) {
            System.err.println("Error: Empty keys are not permitted");
          } else {
            System.err.println("Error: strings must be lowercase letters");
          } // if arguments are null, print error.
          break;
        default:
          System.err.println("Error: Expected 4 parameters, received " + args.length);
      } // end switch statement to create & print ciphers.
    } // end number of arguments check.
  } // main(String[])
} // class Cipher

