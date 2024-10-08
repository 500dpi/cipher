/*
 * CipherUtils.java
 * 
 * Author: Sara Jaljaa
 * Course: CSC-207-01
 * 
 */

package edu.grinnell.csc207.util;

/**
 * Helper functions to encrypt and decrypt with Caesar or
 * Vigenere ciphers.
 * 
 * @author Sara Jaljaa
 *
 */
public class CipherUtils {

  /**
   * The ASCII value of lowercase a.
   */
  private static int alphStart = 97;

  /*
   * The length of the alphabet.
   */
  private static int numStart = 26;

  /**
   * Convert from ASCII to numerical alphabetical order.
   *
   * @param letter The letter in ASCII form.
   * @return The letter calibrated to its alphabetical position,
   *         inclusive (0-25).
   */
  private static int letter2int(char letter) {
    int charToI = ((int) letter - alphStart);
    return charToI;
  } // letter2int(char)

  /**
   * Convert from numerical alphabetical order to ASCII value.
   *
   * @param i The integer i in alphabetical numerical form.
   * @return The integer i in ASCII form.
   */
  private static char int2letter(int i) {
    return (char) (i + alphStart);
  } // int2letter(int)

  /**
   * Check if all the characters in a string are lowercase.
   *
   * @param arg The string key for the Vigenere cipher.
   * @return A boolean that indicates if all the characters are lowercase.
   */
  public static boolean allLower(String arg) {
    boolean bool = false;
    for (int i = 0; i < arg.length(); i++) {
      if (arg.charAt(i) < 'a' || arg.charAt(i) > 'z') {
        bool = true;
        break;
      } else {
        bool = false;
      } // if the character is not lowercase.
    } return bool;
  } // allLower(String)

  /**
   * Encrypt a string via Caesar cipher.
   *
   * @param str The string to encrypt with the Caesar cipher.
   * @param letter The letter in ASCII form character.
   * @return A new string that has been encrypted with the letter.
   */
  public static String caesarEncrypt(String str, char letter) {
    char[] encString = str.toCharArray();
    int newVal = 0;
    for (int i = 0; i < str.length(); i++) {
      newVal = (letter2int(encString[i]) + letter2int(letter));
      if (newVal >= numStart) {
        newVal = newVal % numStart;
      } else if (newVal < 0) {
        newVal = numStart + (newVal % numStart);
      } // if the letter is greater than 26, wrap around.
      encString[i] = int2letter(newVal);
    } return new String(encString);
  } // caesarEncrypt(str,letter)

  /**
   * Decrypt a string via Caesar cipher.
   *
   * @param str The string to decrypt with the Caesar cipher.
   * @param letter The letter in ASCII form character.
   * @return A new string that has been decrypted with the letter.
   */
  public static String caesarDecrypt(String str, char letter) {
    char[] decString = str.toCharArray();
    int newVal = 0;
    for (int i = 0; i < str.length(); i++) {
      newVal = (letter2int(decString[i]) - letter2int(letter));
      if (newVal >= numStart) {
        newVal = newVal % numStart;
      } else if (newVal < 0) {
        newVal = numStart + (newVal % numStart);
      } // if value is greater than end of alphabet (26), wrap around.
      decString[i] = int2letter(newVal);
    } return new String(decString);
  } // caesarDecrypt(String,char)

  /**
   * Check if the key is shorter than the string for a Vigenere
   * cipher; if true, expand the length by repeating it.
   *
   * @param str The string to compare against.
   * @param key The key string to check.
   * @return A new string that has either been extended
   * or remained the same.
   */
  public static String equalString(String str, String key) {
    char[] max = new char[str.length()];
    char[] keyArr = key.toCharArray();

    if (key.length() < str.length()) {
      for (int i = 0, j = 0; i < str.length(); i++, j++) {
        if (j < key.length()) {
          max[i] = keyArr[j];
        } else if (j >= key.length()) {
          j = 0;
          max[i] = keyArr[j];
        } // if key length < str length, adjust key values if shorter than string str.
      } // end for loop to repeat key.
    } else {
      for (int i = 0; i < str.length(); i++) {
        max[i] = keyArr[i];
      } // for the entir str length, repeat the key so that it is the same size array.
    } // return else branch if key is not less than str.
    return new String(max);
  } // equalString(String,String)
  
  /**
   * Encrypt a string using the Vigenere cipher and a
   * key.
   *
   * @param str The string to be encrypted.
   * @param key The key string to encrypt with.
   * @return A new Vigenere-encrypted string.
   */
  public static String vigenereEncrypt(String str, String key) {
    char[] encString = str.toCharArray();
    int newVal = 0;

    if (str.length() > key.length()) {
      key = equalString(str, key);
    } // end check is key is smaller.
    char[] encKey = key.toCharArray();
    for (int i = 0; i < str.length(); i++) {
      newVal = (letter2int(encString[i]) + letter2int(encKey[i]));
      if (newVal >= numStart) {
        newVal = newVal % numStart;
      } else if (newVal < 0) {
        newVal = numStart + (newVal % numStart);
      } // if newly computed char is larger than 26, wrap around. 
      encString[i] = int2letter(newVal);
    } // end for loop to encrypt string str.
    return new String(encString);
  } // vigenereEncrypt(String,String)

  /**
   * Decrypt a string using the Vigenere cipher and a
   * key.
   *
   * @param str The string to be decrypted.
   * @param key The key string to decrypt with.
   * @return A new Vigenere-decrypted string.
   */
  public static String vigenereDecrypt(String str, String key) {
    char[] decString = str.toCharArray();
    int newVal = 0;

    if (str.length() > key.length()) {
      key = equalString(str, key);
    } // if str is larger, use equalString to loop key to a larger array.
    char[] decKey = key.toCharArray();

    for (int i = 0; i < str.length(); i++) {
      newVal = (letter2int(decString[i]) - letter2int(decKey[i]));
      if (newVal >= numStart) {
        newVal = newVal % numStart;
      } else if (newVal < 0) {
        newVal = numStart + (newVal % numStart);
      } // if newly computed char is larger than 26, wrap around.
      decString[i] = int2letter(newVal);
    } return new String(decString);
  } // vigenereDecrypt(String,String)
} // class CipherUtils
