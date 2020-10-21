package trickytasks.IMatrtixWorker;


public class IMatrixWorkerImp implements IMatrixWorker {


    @Override
    public void print(double[][] m) {
        for (double[] doubles : m) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
    }

    @Override
    public boolean haveSameDimension(double[][] m1, double[][] m2) {
        return (m1.length == m2.length) && (m1[0].length == m2[0].length);
    }

    @Override
    public double[][] add(double[][] m1, double[][] m2) {
        double[][] tmpMatrix = new double[m1.length][m1[0].length];
        if (haveSameDimension(m1, m2)) {
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[0].length; j++) {
                    tmpMatrix[i][j] = m1[i][j] + m2[i][j];
                }
            }
            return tmpMatrix;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public double[][] subtract(double[][] m1, double[][] m2) {
        double[][] tmpMatrix = new double[m1.length][m1[0].length];
        if (haveSameDimension(m1, m2)) {
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m1[0].length; j++) {
                    tmpMatrix[i][j] = m1[i][j] - m2[i][j];
                }
            }
            return tmpMatrix;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public double[][] multiply(double[][] m1, double[][] m2) {
        double[][] tmpMatrix = new double[m1.length][m2[0].length];
        if (m1[0].length == m2.length) {
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m2[0].length; j++) {
                    tmpMatrix[i][j] = 0;
                    for (int k = 0; k < m1[0].length; k++) {
                        tmpMatrix[i][j] += m1[i][k] + m2[k][j];
                    }
                }
            }
            return tmpMatrix;
        } else {
            throw new IllegalStateException();
        }
    }

    /*Дополнительные задачи*/

    private double[][] getMinor(double[][] m, int n) {
        double[][] tmpMatrix = new double[m.length - 1][m.length - 1];
        for (int i = 1; i < m.length; i++) {
            for (int j = 0; j < n; j++)
                tmpMatrix[i - 1][j] = m[i][j];
            for (int j = n + 1; j < m.length; j++)
                tmpMatrix[i - 1][j - 1] = m[i][j];
        }
        return tmpMatrix;

    }

    public double determinant(double[][] m) {
        if(m.length==m[0].length) {
            double result = 0;
            if (m.length == 1) {
                return m[0][0];
            }
            for (int i = 0; i < m[0].length; i++) {
                double[][] minor = getMinor(m, i);
                result += Math.pow(-1, i) * m[0][i] * determinant(minor);
            }
            return result;
        }
        else
            throw new IllegalStateException();
    }


}
