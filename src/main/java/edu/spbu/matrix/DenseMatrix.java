package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix {
    private double[][] DMatr;
    private int nrows;
    private int ncols;

    /**
     * загружает матрицу из файла
     *
     * @param fileName - path to the text file with matrix
     */
    public DenseMatrix(String fileName) {
        try {
            FileReader rdr = new FileReader(fileName);
            BufferedReader bufR = new BufferedReader(rdr);
            String[] dividedcurrln;
            String strrepcurrln = bufR.readLine();
            double[] currln;
            ArrayList<double[]> L = new ArrayList<>();
            int height = 0, length = 0;
            while (strrepcurrln != null) {
                dividedcurrln = strrepcurrln.split(" ");                          //?????????
                length = dividedcurrln.length;
                currln = new double[length];
                for (int j = 0; j < length; ++j) {
                    currln[j] = Double.parseDouble(dividedcurrln[j]);
                }
                L.add(currln);
                height++;
                strrepcurrln = bufR.readLine();
            }
            rdr.close();
            bufR.close();
            double[][] res = new double[height][length];
            for (int i = 0; i < height; i++) {
                for (int k = 0; k < length; ++k) {
                    res[i][k] = L.get(i)[k];
                }
            }
            DMatr = res;
            ncols = length;
            nrows = height;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * однопоточное умнджение матриц
     * должно поддерживаться для всех 4-х вариантов
     *
     * @param o - another matrix to multiply by
     * @return resulting matrix of the multiplication
     */
    @Override
    public Matrix mul(Matrix o) {
        DenseMatrix DMtx = (DenseMatrix) o;
        if (ncols == DMtx.nrows && DMatr != null && DMtx.DMatr != null) {
            double[][] res = new double[nrows][DMtx.ncols];
            DenseMatrix tDMtx = DMtx.Transpose();
            for (int i = 0; i < nrows; ++i) {
                for (int j = 0; j < tDMtx.nrows; ++j) {
                    for (int k = 0; k < ncols; ++k) {
                        res[i][j] += DMatr[i][k] * tDMtx.DMatr[j][k];
                    }
                }
            }
            return new DenseMatrix(res);
        } else {
            System.out.println("mul err");
            return null;
        }
    }


    //транспозиция
    private DenseMatrix Transpose() {
        double[][] transposedDMtx = new double[ncols][nrows];
        for (int i = 0; i < ncols; ++i) {
            for (int j = 0; j < nrows; ++j) {
                transposedDMtx[i][j] = DMatr[j][i];
            }
        }
        return new DenseMatrix(transposedDMtx);
    }


//умножение

    @Override
    public Matrix mul(Matrix o) {
        DenseMatrix DMtx = (DenseMatrix) o;
        if (ncols == DMtx.nrows && DMatr != null && DMtx.DMatr != null) {

            double[][] res = new double[nrows][DMtx.ncols];
            DenseMatrix tDMtx = DMtx.Transpose();
            for (int i = 0; i < nrows; ++i) {
                for (int j = 0; j < tDMtx.nrows; ++j) {
                    for (int k = 0; k < ncols; ++k) {
                        res[i][j] += DMatr[i][k] * tDMtx.DMatr[j][k];
                    }
                }
            }
            return new DenseMatrix(res);
        } else {
            System.out.println("mul err");
            return null;
        }
    }

    /**
     * многопоточное умножение матриц
     *
     * @param o - another matrix to multiply by
     * @return resulting matrix of the multiplication
     */
    @Override
    public Matrix dmul(Matrix o) {
        return null;//skip
    }

    //to string


    @Override
    public String toString() {
        StringBuilder resBuilder = new StringBuilder();
        resBuilder.append('\n');
        for (int i = 0; i < nrows; ++i) {
            resBuilder.append('[');
            for (int j = 0; j < ncols; ++j) {
                resBuilder.append(DMatr[i][j]);
                if (j < ncols - 1)
                    resBuilder.append(" ");
            }
            resBuilder.append("]\n");

        }
        return resBuilder.toString();
    }


    //hash


    @Override
    public int hashCode() {
        int result = Objects.hash(nrows, ncols);
        result = 31 * result + Arrays.deepHashCode(DMatr);
        return result;
    }


    /**
     * спавнивает с обоими вариантами
     *
     * @param o - an object to compare against
     * @return true if equals
     */
    @Override
    public boolean equals(Object o) {

        DenseMatrix DMtx = (DenseMatrix) o;
        if (DMatr == null || DMtx.DMatr == null) return false;
        if (DMtx.DMatr == DMatr) return true;
        System.out.println("expected: " + this.toString());
        System.out.println("actual: " + DMtx.toString());
        if (this.hashCode() == DMtx.hashCode())
            if (nrows == DMtx.nrows && ncols == DMtx.ncols) {
                for (int i = 0; i < nrows; ++i) {
                    for (int j = 0; j < ncols; ++j) {
                        if (DMatr[i][j] != DMtx.DMatr[i][j]) {
                            return false;
                        }
                    }
                }
                return true;
            }
        return false;
    }

}
