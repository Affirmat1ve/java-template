package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixTest
{
  /**
   * ожидается 4 таких теста
   */
  @Test
  public void mulDD() {
    System.out.println("Test 1");
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
    System.out.println("Test 2");
    Matrix m3 = new DenseMatrix("m3.txt");
    Matrix m4 = new DenseMatrix("m4.txt");
    Matrix expected2 = new DenseMatrix("result2.txt");
    assertEquals(expected2, m3.mul(m4));
    /*
    System.out.println("Test 3");
    Matrix m5 = new DenseMatrix("m5.txt");
    Matrix m6 = new DenseMatrix("m6.txt");
    Matrix expected3 = new DenseMatrix("result3.txt");
    assertEquals(expected3, m5.mul(m6));
    */
  }
}
