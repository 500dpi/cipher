package edu.grinnell.csc207;

import edu.grinnell.csc207.util.CipherUtils;
import edu.grinnell.csc207.main.AllCaesar;
import edu.grinnell.csc207.main.Cipher;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests of the various classes for MP01.
 */
public class TestMP01 {
  // +--------------+------------------------------------------------
  // | Design notes |
  // +--------------+

  /*
   Since some of the project goals are classes with main methods,
   we have a variety of utility classes that allow us to capture
   System.in and System.out. We then call the main method and check
   the results. 
   */

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * Blank output for AllCaesar
   */
  private static final String ALL_CAESAR_EMPTY = ""
      .concat("n = a: \n")
      .concat("n = b: \n")
      .concat("n = c: \n")
      .concat("n = d: \n")
      .concat("n = e: \n")
      .concat("n = f: \n")
      .concat("n = g: \n")
      .concat("n = h: \n")
      .concat("n = i: \n")
      .concat("n = j: \n")
      .concat("n = k: \n")
      .concat("n = l: \n")
      .concat("n = m: \n")
      .concat("n = n: \n")
      .concat("n = o: \n")
      .concat("n = p: \n")
      .concat("n = q: \n")
      .concat("n = r: \n")
      .concat("n = s: \n")
      .concat("n = t: \n")
      .concat("n = u: \n")
      .concat("n = v: \n")
      .concat("n = w: \n")
      .concat("n = x: \n")
      .concat("n = y: \n")
      .concat("n = z: \n");

  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /** The original value of System.err. */
  static PrintStream stderr;

  /** The original value of System.out. */
  static PrintStream stdout;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** A place to put System.err output. */
  ByteArrayOutputStream errbytes;

  /** A place to put System.out output. */
  ByteArrayOutputStream outbytes;

  /** The replacement System.err. */
  PrintStream newerr = null;

  /** The replacement System.out. */
  PrintStream newout = null;

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Redirect System.err and System.out.
   */
  public void redirectStandardFDs() {
    errbytes = new ByteArrayOutputStream();
    outbytes = new ByteArrayOutputStream();
    newerr = new PrintStream(errbytes);
    newout = new PrintStream(outbytes);
    System.setErr(newerr);
    System.setOut(newout);
  } // redirectStandardFDs

  /**
   * Return to the normal stderr and stdout.
   */
  public void restoreStandardFDs() {
    if (newerr != null) {
      newerr.close();
      newerr = null;
    } // if (newerr != null)
    if (newout != null) {
      newout.close();
      newout = null;
    } // if (! outclosed)
    System.setErr(stderr);
    System.setOut(stdout);
  } // restoreStandardFDs

  // +--------------------+------------------------------------------
  // | Setup and takedown |
  // +--------------------+

  /**
   * Since we shuffle System.err and System.out, begin by saving them.
   */
  @BeforeAll
  public static void saveStandardFDs() {
    stderr = System.err;
    stdout = System.out;
  } // saveStdFDs

  // +-------------------+-------------------------------------------
  // | Main method tests |
  // +-------------------+

  /**
   * Followup on a test of a main method.
   *
   * @param expectedOut
   *   The expected output. Set to null if we expect it to fail.
   * @param msg
   *   The messsage to display.
   */
  public void checkMain(String expectedOut, String msg) {
    String outString = outbytes.toString();
    String errString = errbytes.toString();
    // Test for null
    if (expectedOut == null) {
      assertEquals("", outString, msg);
      assertEquals("Error:", errString.substring(0, 6), msg);
    } else {
      if ((expectedOut.length() > 0)
          && (expectedOut.charAt(expectedOut.length() - 1) != '\n')
          && (outString.length() > 0)
          && (outString.charAt(outString.length() - 1) == '\n')) {
        outString = outString.substring(0, outString.length() - 1);
      } // if
      assertEquals(expectedOut, outString, msg);
      assertEquals("", errString, msg);
    } // if/else
  } // checkMain(String, String)

  /**
   * A test of AllCaesar.
   *
   * @param args
   *   The command-line parameters.
   * @param expectedOut
   *   The expected output. Set to null if we expect it to fail.
   * @param msg
   *   The messsage to display.
   */
  public void allCaesarTest(String args[], String expectedOut, String msg) {
    redirectStandardFDs();
    AllCaesar.main(args);
    restoreStandardFDs();
    checkMain(expectedOut, msg);
  } // allCaesarTest

  /**
   * A standard cipher test.
   *
   * @param level
   *   The level of the test ("R", "M", or "E")
   * @param args
   *   Simulated command-line arguments.
   * @param expectedOutput
   *   The expected output. Set to null if we expect it to fail.
   */
  public void cipherTest(String level, String[] args, String expectedOut) {
    String msg = level + ": " + "Cipher";
    for (String arg : args) {
      msg += " " + "\"" + arg + "\"";
    } // for
    if (expectedOut == null) {
      msg = msg + " [should report an error]";
    } // if
    redirectStandardFDs();
    Cipher.main(args);
    restoreStandardFDs();
    checkMain(expectedOut, msg);
  } // cipherTest(String[], String)

  // +-------------------------+-------------------------------------
  // | R tests for CipherUtils |
  // +-------------------------+

  /**
   * Ensure that caesarEncrypt returns a non-empty string given non-empty input.
   */
  @Test
  public void testCaesarEncryptReturnsNonEmptyString() {
    String encode = CipherUtils.caesarEncrypt("abcde", 'a');
    assertTrue(encode != null, "R: caesarEncrypt doesn't return null");
    assertTrue(!encode.equals(""), "R: caesarEncrypt returns non-empty string");
  } // testCaesarEncryptReturnsString()

  /**
   * Ensure that caesarDecrypt returns a non-empty string given non-empty input.
   */
  @Test
  public void testCaesarDecryptReturnsNonEmptyString() {
    String decode = CipherUtils.caesarDecrypt("abcde", 'a');
    assertTrue(decode != null, "R: caesarDecrypt doesn't return null");
    assertTrue(!decode.equals(""), "R: caesarDecrypt returns non-empty string");
  } // testCaesarEncryptReturnsString()

  /**
   * Ensure that vigenereEncrypt returns a non-empty string given
   * non-empty input.
   */
  @Test
  public void testVigenereEncryptReturnsNonEmptyString() {
    String encode = CipherUtils.vigenereEncrypt("abcde", "pqrst");
    assertTrue(encode != null, "R: vigenereEncrypt doesn't return null");
    assertTrue(!encode.equals(""), "R: vigenereEncrypt returns non-empty string");
  } // testVigenereEncryptReturnsNonEmptyString()

  /**
   * Ensure that vigenereEncrypt returns a non-empty string given
   * non-empty input.
   */
  @Test
  public void testVigenereDecryptReturnsNonEmptyString() {
    String decode = CipherUtils.vigenereDecrypt("abcde", "pqrst");
    assertTrue(decode != null, "R: vigenereDecrypt doesn't return null");
    assertTrue(!decode.equals(""), "R: vigenereDecrypt returns non-empty string");
  } // testVigenereDecryptReturnsNonEmptyString()

  // +-------------------------+-------------------------------------
  // | M tests for CipherUtils |
  // +-------------------------+

  /**
   * Ensure that caesarEncrypt returns the right string for a variety
   * of keys.
   */
   @Test
   public void testCaesarEncrypt() {
     assertEquals("abcdefghijklmnopqrstuvwxyz",
         CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'a'),
         "M: caesarEncrypt with 'a'");
     assertEquals("bcdefghijklmnopqrstuvwxyza",
         CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'b'),
         "M: caesarEncrypt with 'b'");
     assertEquals("cdefghijklmnopqrstuvwxyzab",
         CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'c'),
         "M: caesarEncrypt with 'c'");
     assertEquals("defghijklmnopqrstuvwxyzabc",
         CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'd'),
         "M: caesarEncrypt with 'd'");
     assertEquals("uryybjbeyq",
         CipherUtils.caesarEncrypt("helloworld", 'n'),
         "M: caesarEncrypt helloworld with 'n'");
  } // testCaesarEncrypt()

  /**
   * Ensure that caesarDecrypt returns the right string for a variety
   * of keys.
   */
   @Test
   public void testCaesarDecrypt() {
     assertEquals("abcdefghijklmnopqrstuvwxyz",
         CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'a'),
         "M: caesarDecrypt with 'a'");
     assertEquals("zabcdefghijklmnopqrstuvwxy",
         CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'b'),
         "M: caesarDecrypt with 'b'");
     assertEquals("yzabcdefghijklmnopqrstuvwx",
         CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'c'),
         "M: caesarDecrypt with 'c'");
     assertEquals("xyzabcdefghijklmnopqrstuvw",
         CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'd'),
         "M: caesarDecrypt with 'd'");
  } // testCaesarDecrypt()

  /**
   * Ensure the vigenereEncrypt returns the right result for a variety
   * of strings (mostly ones I can figure out easily).
   */
  @Test
  public void testVigenereEncrypt() {
    assertEquals("abcdefg",
      CipherUtils.vigenereEncrypt("abcdefg", "aaa"),
      "M: vigenereEncrypt with abcdefg and aaa");
    assertEquals("acedfhg",
      CipherUtils.vigenereEncrypt("abcdefg", "abc"),
      "M: vigenereEncrypt with abcdefg and abc");
    assertEquals("ace",
      CipherUtils.vigenereEncrypt("abc", "abcdefg"),
      "M: vigenereEncrypt with abc and abcdefg");
    assertEquals("klmnopq",
      CipherUtils.vigenereEncrypt("lmnopqr", "zzz"),
      "M: vigenereEncrypt with lmnopqr and zzz");
    assertEquals("ikmlnpo",
      CipherUtils.vigenereEncrypt("lmnopqr", "xyz"),
      "M: vigenereEncrypt with lmnopqr and xyz");
    assertEquals("acegikmo",
      CipherUtils.vigenereEncrypt("abcdefgh", "abcdefgh"),
      "M: vigenereEncrypt with abcdefgh and abcdefgh");
    assertEquals("hhhhhhhh",
      CipherUtils.vigenereEncrypt("abcdefgh", "hgfedcba"),
      "M: vigenereEncrypt with abcdefgh and hgfedcba");
  } // testVigenereEncrypt()

  /**
   * Ensure the vigenereDecrypt returns the right result for a variety
   * of strings (mostly ones I can figure out easily).
   */
  @Test
  public void testVigenereDecrypt() {
    assertEquals("abcdefg",
      CipherUtils.vigenereDecrypt("abcdefg", "aaa"),
      "M: vigenereDecrypt with abcdefg and aaa");
    assertEquals("abcdefg",
      CipherUtils.vigenereDecrypt("acedfhg", "abc"),
      "M: vigenereDecrypt with acedfhg and abc");
    assertEquals("abc",
      CipherUtils.vigenereDecrypt("ace", "abcdefg"),
      "M: vigenereDecrypt with ace and abcdefg");
    assertEquals("lmnopqr",
      CipherUtils.vigenereDecrypt("klmnopq", "zzz"),
      "M: vigenereDecrypt with klmnopq and zzz");
    assertEquals("lmnopqr",
      CipherUtils.vigenereDecrypt("ikmlnpo", "xyz"),
      "M: vigenereDecrypt with ikmlnpo and xyz");
    assertEquals("abcdefgh",
      CipherUtils.vigenereDecrypt("acegikmo", "abcdefgh"),
      "M: vigenereDecrypt with acegikmo and abcdefgh");
    assertEquals("abcdefgh",
      CipherUtils.vigenereDecrypt("hhhhhhhh", "hgfedcba"),
      "M: vigenereDecrypt with hhhhhhhh and hgfedcba");
  } // testVigenereDecrypt()

  // +-------------------------+-------------------------------------
  // | E tests for CipherUtils |
  // +-------------------------+

  /**
   * Ensure that we can successfully encrypt the empty string.
   */
  @Test
  public void caesarEncryptEmpty() {
     assertEquals("",
         CipherUtils.caesarEncrypt("", 'c'),
         "E: caesarEncrypt of the empty string");
  } // caesarEncryptEmpty()

  /**
   * Ensure that we can successfully decrypt the empty string.
   */
  @Test
  public void caesarDecryptEmpty() {
     assertEquals("",
         CipherUtils.caesarDecrypt("", 'q'),
         "E: caesarEncrypt of the empty string");
  } // caesarEncryptEmpty()

  /**
   * Ensure that we can successfully encrypt the empty string.
   */
  @Test
  public void vigenereEncryptEmpty() {
     assertEquals("",
         CipherUtils.vigenereEncrypt("", "c"),
         "E: vigenereEncrypt of the empty string");
  } // vigenereEncryptEmpty()

  /**
   * Ensure that we can successfully decrypt the empty string.
   */
  @Test
  public void vigenereDecryptEmpty() {
     assertEquals("",
         CipherUtils.vigenereDecrypt("", "qrst"),
         "E: vigenereEncrypt of the empty string");
  } // vigenereEncryptEmpty()

  // +-----------------------+---------------------------------------
  // | R tests for AllCaesar |
  // +-----------------------+

  /**
   * Does AllCaesar run when given appropriate parameters?
   */
  @Test
  public void allCaesarRuns() {
    redirectStandardFDs();
    AllCaesar.main(new String[] { "encode", "abcde" });
    restoreStandardFDs();
    assertTrue(true, "R: The AllCaesar program runs with correct inputs.");
  } // allCaesarRuns()

  // +-----------------------+---------------------------------------
  // | M tests for AllCaesar |
  // +-----------------------+

  /**
   * Does AllCaesar give the correct output for encrypting helloworld?
   */
  @Test
  public void allCaesarEncryptHelloWorld() {
    allCaesarTest(new String[] { "encode", "helloworld" },
        """
        n = a: helloworld
        n = b: ifmmpxpsme
        n = c: jgnnqyqtnf
        n = d: khoorzruog
        n = e: lippsasvph
        n = f: mjqqtbtwqi
        n = g: nkrrucuxrj
        n = h: olssvdvysk
        n = i: pmttwewztl
        n = j: qnuuxfxaum
        n = k: rovvygybvn
        n = l: spwwzhzcwo
        n = m: tqxxaiadxp
        n = n: uryybjbeyq
        n = o: vszzckcfzr
        n = p: wtaadldgas
        n = q: xubbemehbt
        n = r: yvccfnficu
        n = s: zwddgogjdv
        n = t: axeehphkew
        n = u: byffiqilfx
        n = v: czggjrjmgy
        n = w: dahhksknhz
        n = x: ebiiltloia
        n = y: fcjjmumpjb
        n = z: gdkknvnqkc
        """,
        "M: AllCaesar encode helloworld");
  } // allCaesarEncryptHelloWorld()

  /**
   * Does AllCaesar give the correct output for encrypting the alphabet?
   */
  @Test
  public void allCaesarEncryptAlphabet() {
    allCaesarTest(new String[] { "encode", "abcdefghijklmnopqrstuvwxyz" },
        """
        n = a: abcdefghijklmnopqrstuvwxyz
        n = b: bcdefghijklmnopqrstuvwxyza
        n = c: cdefghijklmnopqrstuvwxyzab
        n = d: defghijklmnopqrstuvwxyzabc
        n = e: efghijklmnopqrstuvwxyzabcd
        n = f: fghijklmnopqrstuvwxyzabcde
        n = g: ghijklmnopqrstuvwxyzabcdef
        n = h: hijklmnopqrstuvwxyzabcdefg
        n = i: ijklmnopqrstuvwxyzabcdefgh
        n = j: jklmnopqrstuvwxyzabcdefghi
        n = k: klmnopqrstuvwxyzabcdefghij
        n = l: lmnopqrstuvwxyzabcdefghijk
        n = m: mnopqrstuvwxyzabcdefghijkl
        n = n: nopqrstuvwxyzabcdefghijklm
        n = o: opqrstuvwxyzabcdefghijklmn
        n = p: pqrstuvwxyzabcdefghijklmno
        n = q: qrstuvwxyzabcdefghijklmnop
        n = r: rstuvwxyzabcdefghijklmnopq
        n = s: stuvwxyzabcdefghijklmnopqr
        n = t: tuvwxyzabcdefghijklmnopqrs
        n = u: uvwxyzabcdefghijklmnopqrst
        n = v: vwxyzabcdefghijklmnopqrstu
        n = w: wxyzabcdefghijklmnopqrstuv
        n = x: xyzabcdefghijklmnopqrstuvw
        n = y: yzabcdefghijklmnopqrstuvwx
        n = z: zabcdefghijklmnopqrstuvwxy
        """,
        "M: AllCaesar encode abcdefghijklmnopqrstuvwxyz");
  } // allCaesarEncryptAlphabet()

  /**
   * Does AllCaesar give the correct output for decrypting dahhksknhz?
   */
  @Test
  public void allCaesarDecryptDahhksknhz() {
    allCaesarTest(new String[] { "decode", "dahhksknhz" },
        """
        n = a: dahhksknhz
        n = b: czggjrjmgy
        n = c: byffiqilfx
        n = d: axeehphkew
        n = e: zwddgogjdv
        n = f: yvccfnficu
        n = g: xubbemehbt
        n = h: wtaadldgas
        n = i: vszzckcfzr
        n = j: uryybjbeyq
        n = k: tqxxaiadxp
        n = l: spwwzhzcwo
        n = m: rovvygybvn
        n = n: qnuuxfxaum
        n = o: pmttwewztl
        n = p: olssvdvysk
        n = q: nkrrucuxrj
        n = r: mjqqtbtwqi
        n = s: lippsasvph
        n = t: khoorzruog
        n = u: jgnnqyqtnf
        n = v: ifmmpxpsme
        n = w: helloworld
        n = x: gdkknvnqkc
        n = y: fcjjmumpjb
        n = z: ebiiltloia
        """,
        "M: AllCaesar decode dahhksknhz");
  } // allCaesarDecryptDahhksknhz()

  /**
   * Does AllCaesar give the correct output for decrypting the alphabet?
   */
  @Test
  public void allCaesarDecryptAlphabet() {
    allCaesarTest(new String[] { "decode", "abcdefghijklmnopqrstuvwxyz" },
        """
        n = a: abcdefghijklmnopqrstuvwxyz
        n = b: zabcdefghijklmnopqrstuvwxy
        n = c: yzabcdefghijklmnopqrstuvwx
        n = d: xyzabcdefghijklmnopqrstuvw
        n = e: wxyzabcdefghijklmnopqrstuv
        n = f: vwxyzabcdefghijklmnopqrstu
        n = g: uvwxyzabcdefghijklmnopqrst
        n = h: tuvwxyzabcdefghijklmnopqrs
        n = i: stuvwxyzabcdefghijklmnopqr
        n = j: rstuvwxyzabcdefghijklmnopq
        n = k: qrstuvwxyzabcdefghijklmnop
        n = l: pqrstuvwxyzabcdefghijklmno
        n = m: opqrstuvwxyzabcdefghijklmn
        n = n: nopqrstuvwxyzabcdefghijklm
        n = o: mnopqrstuvwxyzabcdefghijkl
        n = p: lmnopqrstuvwxyzabcdefghijk
        n = q: klmnopqrstuvwxyzabcdefghij
        n = r: jklmnopqrstuvwxyzabcdefghi
        n = s: ijklmnopqrstuvwxyzabcdefgh
        n = t: hijklmnopqrstuvwxyzabcdefg
        n = u: ghijklmnopqrstuvwxyzabcdef
        n = v: fghijklmnopqrstuvwxyzabcde
        n = w: efghijklmnopqrstuvwxyzabcd
        n = x: defghijklmnopqrstuvwxyzabc
        n = y: cdefghijklmnopqrstuvwxyzab
        n = z: bcdefghijklmnopqrstuvwxyza
        """,
        "M: AllCaesar decode abcdefghijklmnopqrstuvwxyz");
  } // allCaesarDecryptAlphabet()

  /**
   * Does AllCaesar issue an error with zero arguments?
   */
  @Test
  public void allCaesarErrorZeroArguments() {
    allCaesarTest(new String[] { }, null, "M: AllCaesar with zero arguments");
  } // allCaesarErrorZeroArguments()

  /**
   * Does AllCaesar issue an error with one argument?
   */
  @Test
  public void allCaesarErrorOneArgument() {
    allCaesarTest(new String[] { "encode" }, 
        null, 
        "M: AllCaesar with one argument");
    allCaesarTest(new String[] { "helloworld" }, 
        null, 
        "M: AllCaesar with one argument");
  } // allCaesarErrorOneArgument()

  /**
   * Does AllCaesar issue an error with three arguments?
   */
  @Test
  public void allCaesarErrorThreeArguments() {
    allCaesarTest(new String[] { "encode", "helloworld", "q" }, 
        null, 
        "M: AllCaesar with three arguments");
  } // allCaesarErrorThreeArguments()

  /**
   * Does AllCaesar issue an error with an invalid action?
   */
  @Test
  public void allCaesarErrorInvalidAction() {
    allCaesarTest(new String[] { "deecode", "helloworld" },
      null,
      "M: AllCaesar with invalid action");
    allCaesarTest(new String[] { "emcode", "helloworld" },
      null,
      "M: AllCaesar with invalid action");
    allCaesarTest(new String[] { "codeen", "helloworld" },
      null,
      "M: AllCaesar with invalid action");
    allCaesarTest(new String[] { "", "helloworld" },
      null,
      "M: AllCaesar with invalid action");
  } // allCaesarErrorInvalidAction

  /**
   * Does AllCaesar issue an error with an invalid input string?
   */
  public void allCaesarErrorInvalidString() {
    allCaesarTest(new String[] { "encode", "Helloworld" },
      null,
      "M: AllCaesar with invalid string (\"HelloWorld\")");
    allCaesarTest(new String[] { "encode", " " },
      null,
      "M: AllCaesar with invalid string (\" \")");
    allCaesarTest(new String[] { "encode", "hello world" },
      null,
      "M: AllCaesar with invalid string (\"hello world\")");
    allCaesarTest(new String[] { "encode", "h3lloworld" },
      null,
      "M: AllCaesar with invalid string (\"h3lloworld\")");
    allCaesarTest(new String[] { "decode", "Helloworld" },
      null,
      "M: AllCaesar with invalid string (\"HelloWorld\")");
    allCaesarTest(new String[] { "decode", " " },
      null,
      "M: AllCaesar with invalid string (\" \")");
    allCaesarTest(new String[] { "decode", "hello world" },
      null,
      "M: AllCaesar with invalid string (\"hello world\")");
    allCaesarTest(new String[] { "decode", "h3lloworld" },
      null,
      "M: AllCaesar with invalid string (\"h3lloworld\")");
  } // allCaesarErrorInvalidString()

  // +-----------------------+---------------------------------------
  // | E tests for AllCaesar |
  // +-----------------------+

  /**
   * Does AllCaesar give the correct output for encrypting the empty string?
   */
  @Test
  public void allCaesarEncryptEmptyString() {
    allCaesarTest(new String[] { "encode", "" },
        ALL_CAESAR_EMPTY,
        "E: AllCaesar encode \"\"");
  } // allCaesarEncryptEmptyString()

  /**
   * Does AllCaesar give the correct output for decrypting the empty string?
   */
  @Test
  public void allCaesarDecryptEmptyString() {
    allCaesarTest(new String[] { "decode", "" },
        ALL_CAESAR_EMPTY,
        "E: AllCaesar decode \"\"");
  } // allCaesarDecryptEmptyString()

  // +--------------------+------------------------------------------
  // | R tests for Cipher |
  // +--------------------+

  /**
   * Does Cipher run when given appropriate parameters?
   */
  @Test
  public void cipherRuns() {
    redirectStandardFDs();
    Cipher.main(new String[] { "-caesar", "-encode", "abcde", "abcde" });
    restoreStandardFDs();
    assertTrue(true, "R: The Cipher program runs with correct inputs.");
  } // cipherRuns()

  // +--------------------+------------------------------------------
  // | M tests for Cipher |
  // +--------------------+

  /**
   * Does Cipher appropriately encode with the Caesar cipher using a 
   * variety of parameter orders?
   */
  @Test
  public void cipherCaesarEncrypt() {
    cipherTest("M", new String[] { "-caesar", "-encode", "abcde", "a" }, 
        "abcde");
    cipherTest("M", new String[] { "-encode", "-caesar", "abcdef", "b" }, 
        "bcdefg");
    cipherTest("M", new String[] { "pqrs", "c", "-encode", "-caesar" },
        "rstu");
    cipherTest("M", new String[] { "-encode", "pqrs", "d", "-caesar" },
        "stuv");
  } // cipherCaesarEncrypt()

  /**
   * Does Cipher appropriately decode with the Caesar cipher?
   */
  @Test
  public void cipherCaesarDecode() {
    cipherTest("M", new String[] { "-caesar", "-decode", "abcde", "a" }, 
        "abcde");
    cipherTest("M", new String[] { "-decode", "-caesar", "abcdef", "b" }, 
        "zabcde");
    cipherTest("M", new String[] { "srqp", "c", "-decode", "-caesar" },
        "qpon");
  } // cipherCaesarDecode()

  /**
   * Does Cipher appropriately encode with the Vigenere cipher?
   */
  @Test
  public void cipherVigenereEncrypt() {
    cipherTest("M", new String[] { "-vigenere", "-encode", "abcdefg", "aaa" },
        "abcdefg");
    cipherTest("M", new String[] { "abcdefg", "-encode", "abc", "-vigenere" },
        "acedfhg");
    cipherTest("M", new String[] { "-encode", "-vigenere", "abc", "abcdefg" },
        "ace");
  } // cipherVigenereEncrypt()

  /**
   * Does Cipher appropriately decode with the Vigenere cipher?
   */
  @Test
  public void cipherVigenereDecode() {
    cipherTest("M", 
        new String[] { "-vigenere", "-decode", "pqozblxpdhprr", "hello" },
        "imdonetesting");
  } // cipherVigenereDecode()

  /**
   * Does Cipher issue an error with the wrong number of parameters?
   */
  @Test 
  public void cipherWrongNumberOfParams() {
    cipherTest("M", new String[] { }, null);
    cipherTest("M", new String[] { "-caesar" }, null);
    cipherTest("M", new String[] { "-caesar", "-encode" }, null);
    cipherTest("M", new String[] { "-caesar", "-encode", "hello" }, null);
    cipherTest("M", new String[] { "-caesar", "-encode", "hello" }, null);
    cipherTest("M", new String[] { "-caesar", "-encode", "a", "b", "c" }, 
        null);
  } // cipherWrongNumberOfParams()

  /**
   * Does Cipher issue an error with an invalid string to encode?
   */
  @Test
  public void cipherInvalidString() {
    cipherTest("M", new String[] { "-encode", "-vigenere", "hi world", "a" },
        null);
  } // cipherInvalidString()

  /**
   * Does Cipher issue an error with an invalid key?
   */
  @Test
  public void cipherInvalidKey() {
    cipherTest("M", new String[] { "-vigenere", "-encode", "hi", "W0R1D" },
        null);
  } // cipherInvalidKey()

  // +--------------------+------------------------------------------
  // | E tests for Cipher |
  // +--------------------+

  /**
   * Does Cipher issue an error with an invalid letter for a Caesar key?
   */
  @Test
  public void cipherBadCaesarKey() {
    cipherTest("E", new String[] { "-caesar", "-decode", "h", "Q" }, null);
  } // cipherBadCaesarKey()

  /**
   * Does Cipher issue an error with the a too-long Caesar key?
   */
  @Test
  public void cipherLongCaesarKey() {
    cipherTest("E", new String[] { "-caesar", "-encode", "h", "bo" }, null);
  } // cipherLongCaesarKey()

  /**
   * Does Cipher issue an error with an empty key?
   */
  @Test
  public void cipherEmptyKey() {
    cipherTest("E", new String[] { "-vigenere", "-decode", "h", "" }, null);
  } // cipherEmptyKey()

} // TestMP01

