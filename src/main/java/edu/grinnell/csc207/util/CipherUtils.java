package edu.grinnell.csc207.util;

import java.util.Arrays;

/**
 * Encrypts or decrypts Caesar and Vigenere ciphers.
 *
 * @author Sara Jaljaa
 * @course CSC-207-01
 */
public class CipherUtils {

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The length of the alphabet.
   */
  private static final int ALPH_SIZE = 26;

  // +----------------+----------------------------------------------
  // | Helper Methods |
  // +----------------+

  /**
   * Convert from ASCII value to numerical alphabetical order.
   *
   * @param letter
   *    The letter in ASCII form.
   *
   * @return
   *    The letter calibrated to its alphabetical position from
   *    0-26 (exclusive).
   */
  private static int letter2int(char letter) {
    return (int) (letter - 'a');
  } // letter2int(char)

  /**
   * Convert from numerical alphabetical order to ASCII value.
   *
   * @param i
   *    An integer representing the letter's index in the alphabet.
   * @return
   *    An integer in ASCII form.
   */
  private static char int2letter(int i) {
    return (char) (i + (int) 'a');
  } // int2letter(int)

  /**
   * Check if all the characters in a string are lowercase.
   *
   * @param arg
   *    The key for a Vigenere cipher.
   * @return
   *    True or false if all the characters in the string are lowercase.
   */
  public static boolean lowercaseCheck(String arg) {
    boolean bool = false;
    for (int i = 0; i < arg.length(); i++) {
      if ((arg.charAt(i) >= 'a') && (arg.charAt(i) <= 'z')) {
        bool = true;
      } else {
        return false;
      } // elif
    } // for
    return bool;
  } // lowercaseCheck(String)

  /**
   * Wraps the index if it is out of the alphabetical
   * index bounds 0-26 (exclusive).
   *
   * @param index
   *    The index of the character in the alphabet.
   * @return
   *    The (modified) index.
   */
  public static int wrap(int index) {
    if (index >= ALPH_SIZE) {
      index = index % ALPH_SIZE;
    } else if (index < 0) {
      index = ALPH_SIZE + (index % ALPH_SIZE);
    } // elif
    return index;
  } // wrap(int)

  /**
   * Expands a key string if it is shorter than the message to
   * (en)/(de)cipher.
   *
   * @param key
   *    The key string to extend.
   * @param str
   *    The plaintext message.
   * @return
   *    A key string that is >= the message length.
   */
  public static String extend(String key, String str) {
    // If the key is longer than the message, return the key
    if (!(key.length() < str.length())) {
      return key;
    } // if

    // Iterate over the length of the string with the key's
    // values, repeating the key when the end is reached
    char[] extended = str.toCharArray();
    for (int i = 0, j = 0; i < str.length(); i++, j++) {
      if (j >= key.length()) {
        j = 0;
      } // if
      extended[i] = key.charAt(j);
    } // for
    return new String(extended);
  } // extend(String, String)

  /**
   * Generalized encryption/decryption with strings.
   *
   * @param str
   *    The plaintext message.
   * @param key
   *    The key to en/decode with.
   * @param action
   *    The action of the cipher (encode/decode).
   *
   * @return
   *    The ciphertext message.
   */
  public static String cipher(String str, String key, char action) {
    char[] cipher = new char[str.length()];
    int index = 0;

    for (int i = 0; i < str.length(); i++) {
      if (action == 'e') {
        index = letter2int(str.charAt(i)) + letter2int(key.charAt(i));
      } else if (action == 'd') {
        index = letter2int(str.charAt(i)) - letter2int(key.charAt(i));
      } // elif
      cipher[i] = int2letter(wrap(index));
    } // for
    return new String(cipher);
  } // cipher(String, String, char)

  // +----------------+----------------------------------------------
  // | Caesar Methods |
  // +----------------+

  /**
   * Encrypt a Caesar cipher.
   *
   * @param str
   *    The string to encrypt.
   * @param letter
   *    The letter to encrypt with.
   * @return
   *    An encrypted Caesar cipher.
   */
  public static String caesarEncrypt(String str, char letter) {
    char[] letters = new char[str.length()];
    Arrays.fill(letters, letter);
    return cipher(str, new String(letters), 'e');
  } // caesarEncrypt(String, char)

  /**
   * Decrypt a Caesar cipher.
   *
   * @param str
   *    The string to decrypt.
   * @param letter
   *    The letter to decrypt with.
   * @return
   *    A decrypted Caesar cipher.
   */
  public static String caesarDecrypt(String str, char letter) {
    char[] letters = new char[str.length()];
    Arrays.fill(letters, letter);
    return cipher(str, new String(letters), 'd');
  } // caesarDecrypt(String, char)

  // +------------------+--------------------------------------------
  // | Vigenere Methods |
  // +------------------+

  /**
   * Encrypt a Vigenere cipher.
   *
   * @param str
   *    The string to encrypt.
   * @param key
   *    The string to encrypt with.
   * @return
   *    An encrypted Vigenere cipher.
   */
  public static String vigenereEncrypt(String str, String key) {
    key = extend(key, str);
    return cipher(str, key, 'e');
  } // vigenereEncrypt(String, String)

  /**
   * Decrypt a Vigenere cipher.
   *
   * @param str
   *    The string to decrypt.
   * @param key
   *    The string to decrypt with.
   * @return
   *    A decrypted Vigenere cipher.
   */
  public static String vigenereDecrypt(String str, String key) {
    key = extend(key, str);
    return cipher(str, key, 'd');
  } // vigenereDecrypt(String, String)
} // class CipherUtils
