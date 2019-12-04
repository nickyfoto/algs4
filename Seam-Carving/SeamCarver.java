import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.Arrays;


public class SeamCarver {

    private Picture p;
    // Unless you are optimizing your program by updating only the energy values
    // that change after removing a seam, you should not need to maintain the
    // energy values in an instance variable.
    private double[][] energyMatrix;
    // private Color[][] colorMatrix;
    private int[][] intColorMatrix;
    private boolean transposed;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture==null) throw new IllegalArgumentException("picture cannot be null ");
        p = picture;
        constructMatrix();
    }

    // current picture
    public Picture picture() {
        // Picture newP = new Picture(colorMatrix[0].length, colorMatrix.length);
        Picture newP = new Picture(intColorMatrix[0].length, intColorMatrix.length);
        for (int row=0; row<newP.height(); row++) {
            for (int col=0; col<newP.width(); col++) {
                // newP.set(col, row, colorMatrix[row][col]);
                newP.setRGB(col, row, intColorMatrix[row][col]);
            }
        }
        return newP;
    }
    // width of current picture
    public int width() {
        return p.width();
    }

    // height of current picture
    public int height() {
        return p.height();
    }

    // testing purpose
    // private when turn in
    // private Color rgb(int x, int y) {
    //     return p.get(x, y);
    // }

    // private int getRGB(int x, int y) {
        // return p.getRGB(x, y);
    // }

    private double xGradient(int x, int y) {
        // Color left = p.get(x-1, y);
        int left = p.getRGB(x-1, y);
        // Color right = p.get(x+1, y);
        int right = p.getRGB(x+1, y);
        // int Rx = right.getRed() - left.getRed();
        int Rx = (right >>16 & 0xFF) - (left >>16 & 0xFF);
        // int Gx = right.getGreen() - left.getGreen();
        int Gx = (right >>8 & 0xFF) - (left >>8 & 0xFF);
        // int Bx = right.getBlue() - left.getBlue();
        int Bx = (right >>0 & 0xFF) - (left >>0 & 0xFF);
        int deltaX = Rx*Rx + Gx*Gx + Bx*Bx;
        // StdOut.println(deltaX);
        return (double) deltaX;
    }

    private double yGradient(int x, int y) {
        // Color up = p.get(x, y-1);
        int up = p.getRGB(x, y-1);
        // Color down = p.get(x, y+1);
        int down = p.getRGB(x, y+1);
        // int Ry = down.getRed() - up.getRed();
        // int Gy = down.getGreen() - up.getGreen();
        // int By = down.getBlue() - up.getBlue();
        int Ry = (down >>16 & 0xFF) - (up >>16 & 0xFF);
        // int Gx = down.getGreen() - up.getGreen();
        int Gy = (down >>8 & 0xFF) - (up >>8 & 0xFF);
        // int Bx = down.getBlue() - up.getBlue();
        int By = (down >>0 & 0xFF) - (up >>0 & 0xFF);
        int deltaY = Ry*Ry + Gy*Gy + By*By;
        // StdOut.println(deltaY);
        return (double) deltaY;
    }

    private boolean atBorder(int x, int y) {
        if (x == 0 || y == 0) return true;
        if (x == p.width() - 1 || y == p.height() - 1) return true;
        return false;
    }

    private double getEnergy(int x, int y) {
        if (atBorder(x, y)) return 1000.;
        return Math.sqrt(xGradient(x, y) + yGradient(x, y));
    }

    private void constructMatrix() {
        energyMatrix = new double[p.height()][p.width()];
        // colorMatrix = new Color[p.height()][p.width()];
        intColorMatrix = new int[p.height()][p.width()];
        for (int i=0; i<p.height(); i++) {
            for (int j=0; j<p.width(); j++) {
                energyMatrix[i][j] = getEnergy(j, i);
                // colorMatrix[i][j] = rgb(j, i);
                intColorMatrix[i][j] = p.getRGB(j, i);
            }
        }
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= height())
            throw new IllegalArgumentException("row index must be between 0 and " + (height() - 1) + ": " + row);
    }

    private void validateColumnIndex(int col) {
        if (col < 0 || col >= width())
            throw new IllegalArgumentException("column index must be between 0 and " + (width() - 1) + ": " + col);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // StdOut.println("row: "+y+" col: " + x);
        validateColumnIndex(x);
        validateRowIndex(y);
        return energyMatrix[y][x];
    }

    private int getMinSumEnergyIdx(double[] sumArray) {
        int minIdx = 0;
        for (int i=0;i<p.width();i++ ) {
            if (sumArray[i] < sumArray[minIdx]) minIdx = i;
        }
        return minIdx;
    }

    private int getLeastIdx(int row, int col, double[][] matrix) {
        // int left, mid, right;
        // StdOut.println("getLeastIdx row:" + row + " col: "+col);
        if (matrix[0].length == 1) return 0;
        if (col == 0) {
            if (matrix[row-1][1] < matrix[row-1][0]) return 1;
            return 0;
        }
        if (col == matrix[0].length-1) {
            if (matrix[row-1][col-1] > matrix[row-1][col]) return col-1;
            return col-1;
        }
        int left = col - 1;
        int mid = col;
        int right = col+1;

        if (matrix[row-1][mid] < matrix[row-1][left] &&
            matrix[row-1][mid] <= matrix[row-1][right]) return mid;
        if (matrix[row-1][right] < matrix[row-1][mid] &&
            matrix[row-1][right] < matrix[row-1][left]) return right;
        return left;
    }

    private int getFinalIdx(double[][] matrix) {
        int minIdx = 0;
        double[] lastRow = matrix[matrix.length-1];
        for (int i=0; i<matrix[0].length; i++) {
            if (lastRow[i] < lastRow[minIdx]) minIdx = i;
        }
        return minIdx;
    }

    private void transposeEnergyMatrix() {
        int numOfRow = energyMatrix.length;
        int numOfCol = energyMatrix[0].length;
        double[][] energyMatrixT = new double[numOfCol][numOfRow];
        for (int i=0; i<energyMatrixT.length; i++) {
            for (int j=0; j<energyMatrixT[0].length; j++) {
                energyMatrixT[i][j] = energyMatrix[j][i];
            }
        }
        energyMatrix = energyMatrixT;
    }

    private void transposeIntColorMatrix() {
        int numOfRow = intColorMatrix.length;
        int numOfCol = intColorMatrix[0].length;
        int[][] intColorMatrixT = new int[numOfCol][numOfRow];
        for (int i=0; i<intColorMatrixT.length; i++) {
            for (int j=0; j<intColorMatrixT[0].length; j++) {
                intColorMatrixT[i][j] = intColorMatrix[j][i];
            }
        }
        intColorMatrix = intColorMatrixT;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!transposed) {
            transposeEnergyMatrix();
            transposed = true;
        }
        return findVerticalSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int numOfRow = energyMatrix.length;
        int numOfCol = energyMatrix[0].length;
        // Least energy index pointing to the nodes
        int[][] idxMatrix = new int[numOfRow][numOfCol];
        // current accumulated energy amount
        double[][] leastEnergyMatrix = new double[numOfRow][numOfCol];

        for (int i=0; i<numOfCol; i++) {
            idxMatrix[0][i] = i;
            leastEnergyMatrix[0][i] = 1000.0;
        }
        for (int i=1; i<numOfRow; i++ ) {
            for (int j=0; j<numOfCol; j++ ) {
                int leastIdx = getLeastIdx(i, j, leastEnergyMatrix);
                idxMatrix[i][j] = leastIdx;
                leastEnergyMatrix[i][j] = energyMatrix[i][j] + leastEnergyMatrix[i-1][leastIdx];
            }
        }
        int FinalIdxOfLeastEnergy = getFinalIdx(leastEnergyMatrix);
        int[] seam = new int[numOfRow];
        seam[numOfRow-1] = FinalIdxOfLeastEnergy;
        for (int i=numOfRow-2; i>=0; i--) {
            seam[i] = idxMatrix[i+1][seam[i+1]];
        }
        if (transposed) transposeEnergyMatrix();
        transposed = false;
        return seam;
    }

    private void printMatrix(int[][] matrix) {
        StdOut.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                StdOut.print(matrix[i][j] + " ");
            }
            StdOut.println();
        }
    }

    private void printMatrix(double[][] matrix) {
        StdOut.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                StdOut.print(matrix[i][j] + " ");
            }
            StdOut.println();
        }
    }
    private void printMatrix(Color[][] matrix) {
        StdOut.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                StdOut.print(matrix[i][j] + " ");
            }
            StdOut.println();
        }
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam==null) throw new IllegalArgumentException("seam cannot be null ");
        if (!transposed) {
            transposeEnergyMatrix();
            transposeIntColorMatrix();
            transposed = true;
        };
        removeVerticalSeam(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam==null) throw new IllegalArgumentException("seam cannot be null ");
        for (int i=0; i<seam.length; i++) {
            if (seam[i] > energyMatrix[0].length-1) {
                throw new IllegalArgumentException("seam[i] > energyMatrix[0].length-1");
            }
        }
        if (seam.length != energyMatrix.length) throw new IllegalArgumentException("seam wrong length");
        for (int i=0; i<seam.length; i++) {
            if (seam[i] < 0) {
                throw new IllegalArgumentException(
                "seam element < 0");
            }
        }
        for (int i=0; i<seam.length-1;i++ ) {
            if (seam[i+1]- seam[i]  > 1 || seam[i+1]- seam[i] < -1) {
                throw new IllegalArgumentException("seam is not within range");
            }
        }
        int[][] newIntColorMatrix = new int[energyMatrix.length][energyMatrix[0].length-1];
        for (int row=0; row<energyMatrix.length; row++) {
            for (int col=0; col<energyMatrix[0].length; col++) {
                if (col < seam[row]) newIntColorMatrix[row][col] = intColorMatrix[row][col];
                if (col > seam[row]) newIntColorMatrix[row][col-1] = intColorMatrix[row][col];
            }
        }

        intColorMatrix = newIntColorMatrix;
        if (transposed) {
            transposeIntColorMatrix();
            transposed = false;
        }
        p = picture();
        energyMatrix = new double[p.height()][p.width()];
        for (int i=0; i<p.height(); i++) {
            for (int j=0; j<p.width(); j++) {
                energyMatrix[i][j] = getEnergy(j, i);
            }
        }
    }
}
