package processor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (true) {
            System.out.println("1. Add matrices\n" +
                    "2. Multiply matrix to a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit");
            System.out.print("Your choice: ");
            choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    addMatrixes(scanner);
                    break;
                case 2:
                    multiplyMatrixByAConstant(scanner);
                    break;
                case 3:
                    multiplyMatrixByMatrix(scanner);
                    break;
                case 4:
                    transposeMatrix(scanner);
                    break;
                case 5:
                    calculateDetermant(scanner);
                    break;
                case 6:
                    inverseMatrix(scanner);
                    break;
                case 0:
                    System.exit(1);
            }
        }
    }

    private static void inverseMatrix(Scanner scanner) {
        double[][] matrix = getInputMatrix(scanner);
//        System.out.println("Input "+Arrays.deepToString(matrix));
        double det = calculateDet(matrix, 0, matrix.length);
        double[][] Ct = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                double[][] smM = new double[matrix.length - 1][matrix.length - 1];
                double[] els = new double[matrix.length * (matrix.length - 2) + 1];

                int elscount = 0;
                for (int k = 0; k < matrix.length; k++) {
                    for (int h = 0; h < matrix[0].length; h++) {
                        if (k != i && h != j) {
                            els[elscount] = matrix[h][k];
                            elscount += 1;
                        }
                    }
                }
//                System.out.println(Arrays.toString(els));
                int i_s = 0;
                for (int q = 0; q < smM.length; q++) {
                    for (int z = 0; z < smM.length; z++) {
                        smM[q][z] = els[i_s];
                        i_s += 1;
                    }
                }
//                System.out.println(Arrays.deepToString(smM));

                Ct[i][j] = Math.pow(-1, i+1 +j +1) *  calculateDet(smM, 0, smM.length);
            }
        }
        for (int i = 0; i < Ct.length; i++) {
            for (int j = 0; j < Ct[0].length; j++) {
                Ct[i][j] *= 1/det;
            }
        }

//        System.out.print(Arrays.deepToString(Ct));
        System.out.println("The result is: ");
        printMatrix(Ct);
        System.out.println();
        scanner.nextLine();
    }

    static double[][] getInputMatrix(Scanner scan) {
        System.out.print("Enter matrix size: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter matrix: ");
        double[][] a = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                a[i][j] = scan.nextDouble();
            }
        }
        return a;
    }

    private static void calculateDetermant(Scanner scan) {
        double[][] a = getInputMatrix(scan);
        double res = calculateDet(a, 0, a.length);
        System.out.println("The result is: ");
        System.out.println(res);
        scan.nextLine();
    }

    private static double calculateDet(double[][] a, int k, int maxLength) {

        if (a.length == 2) {
            return a[0][0] * a[1][1] - a[0][1] * a[1][0];
        }

        int count_0 = 0;
        int row = 0;// the row with the most 0s
        for (int i = 0; i < a.length; i++) {
            if (checkZero(a[i]) > count_0) {
                row = i;
            }
        }

        double res = 0;

        for (int m = 0; m < maxLength; m++) {
            double[][] s = new double[a.length - 1][a.length - 1];
            int ll_index = 0;
            double[] ll = new double[a.length * a.length - 2 * a.length + 1];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    if (i == row) {
//                        System.out.print("_ ");
                    } else {
                        if (j != m) {
//                            System.out.print(a[i][j] + " ");
                            ll[ll_index] = a[i][j];
                            ll_index += 1;
                        }
//                        else
//                           System.out.print("_ ");
                    }

                }
//                System.out.println();
            }

            int i_s = 0;
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    s[i][j] = ll[i_s];
                    i_s += 1;
                }
            }
            double w = a[row][m] * Math.pow(-1, row + m) * calculateDet(s, k, s.length);
            res += w;
        }

        return res;

    }

    private static int checkZero(double[] m) {
        int c = 0;
        for (double value : m) if (value == 0) c += 1;
        return c;
    }

    private static void transposeMatrix(Scanner scanner) {
        System.out.println("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line\n");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        System.out.print("Enter matrix size: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        scanner.nextLine();
        double[][] a1 = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                a1[i][j] = scanner.nextDouble();
            }
        }
//        System.out.println(choice);
//        System.out.println(Arrays.deepToString(a1));
        double[][] r = new double[x][y];

        if (choice == 1) {

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    r[i][j] = a1[j][i];
                }
            }
        } else if (choice == 2) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    r[i][j] = a1[y - j - 1][x - i - 1];
                }
            }
        } else if (choice == 4) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    r[i][j] = a1[x - i - 1][j];
                }
            }
        } else if (choice == 3) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    r[i][j] = a1[i][Math.abs(y - j - 1)];
                }
            }
        }

        System.out.println("The result is: ");
        printMatrix(r);
        scanner.nextLine();

    }


    private static void multiplyMatrixByMatrix(Scanner scn) {
        ArrayList<int[]> pairs = new ArrayList<>();
        ArrayList<double[][]> m = new ArrayList<>();
        for (int k = 0; k < 2; k++) {
            if (k == 1)
                System.out.print("Enter size of second matrix: ");
            else System.out.print("Enter size of first matrix: ");
            int x1 = scn.nextInt();
            int y1 = scn.nextInt();
            int[] o = new int[2];
            o[0] = x1;
            o[1] = y1;
            pairs.add(o);

            scn.nextLine();
            double[][] a1 = new double[x1][y1];
            for (int i = 0; i < x1; i++) {
                for (int j = 0; j < y1; j++) {
                    a1[i][j] = scn.nextDouble();
                }
            }
            m.add(a1);
            scn.nextLine();
//            System.out.println(Arrays.deepToString(a1));
        }

        if (pairs.get(0)[1] != pairs.get(1)[0]) {
            System.out.println("ERROR");
            return;
        }
        double[][] re = multiplyMatrices(m.get(0), m.get(1), pairs.get(0)[0], pairs.get(0)[1], pairs.get(1)[1]);

        System.out.println("The multiplication result is:");
        printMatrix(re);
        System.out.println();

    }

    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix, int r1, int c1, int c2) {
        double[][] product = new double[r1][c2];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }


    private static void multiplyMatrixByAConstant(Scanner scan) {
        System.out.print("Enter size of the matrix: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        scan.nextLine();
        int[][] a1 = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                a1[i][j] = scan.nextInt();
            }
        }
        System.out.print("Enter the constant: ");
        int m = scan.nextInt();
        System.out.println("The multiplication result is:");
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a1[0].length; j++) {
                a1[i][j] *= m;
                System.out.print(a1[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        scan.nextLine();
    }

    private static void addMatrixes(Scanner scn) {
        ArrayList<int[]> pairs = new ArrayList<>();
        ArrayList<double[][]> m = new ArrayList<>();
        for (int k = 0; k < 2; k++) {
            if (k == 1)
                System.out.print("Enter size of second matrix: ");
            else System.out.print("Enter size of first matrix: ");
            int x1 = scn.nextInt();
            int y1 = scn.nextInt();
            int[] o = new int[2];
            o[0] = x1;
            o[1] = y1;
            pairs.add(o);
            scn.nextLine();
            double[][] a1 = new double[x1][y1];
            for (int i = 0; i < x1; i++) {
                for (int j = 0; j < y1; j++) {
                    a1[i][j] = scn.nextDouble();
                }
            }
            m.add(a1);
            scn.nextLine();
//            System.out.println(Arrays.deepToString(a1));
        }
        if (pairs.get(0)[1] != pairs.get(1)[1] &&
                pairs.get(0)[0] != pairs.get(1)[0]) {
            System.out.println("ERROR");
            return;
        }
        double[][] x1 = m.get(0);
        double[][] x2 = m.get(1);
        double[][] p = new double[x1.length][x1[0].length];
        for (int i = 0; i < x1.length; i++) {
            for (int j = 0; j < x1[0].length; j++) {
                p[i][j] = x1[i][j] + x2[i][j];
            }
            System.out.println();
        }
        System.out.println("The addition result is:");
        printMatrix(p);
        System.out.println();
    }

    static void printMatrix(double[][] p) {
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[0].length; j++) {
                String s = new DecimalFormat("##.##").format(p[i][j] == 0 ? 0 : p[i][j]);
//                System.out.printf("%.2f ",p[i][j]);
                System.out.printf("%s ",s);
            }
            System.out.println();
        }
    }

}

