/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ana
 */
public class Testing {

    List<ArrayList<String>> data;
    double[][] data_training1;
    double[][] data_training2;
    double[][] data_training3;
    double[][] data_testing;
    double bias1;
    double bias2;
    double bias3;
    double hasilfx1, hasilfx2, hasilfx3;
    double[] alfa_1;
    double[] alfa_2;
    double[] alfa_3;
    double akurasi;
    double hasilUji[][];

    public Testing(double bias1, double bias2, double bias3, double[] alfa_1, double[] alfa_2, double[] alfa_3) {
        data_training1 = new double[104][18];
        data_training2 = new double[80][18];
        data_training3 = new double[51][18];
        data_testing = new double[18][18];
        baca_testing("E:\\Uji2(80_20).xls");
        baca_training("E:\\Latih2(80_20).xls", "E:\\Latih2(80_20)level2.xls", "E:\\Latih2(80_20)level3.xls");
        this.bias1 = bias1;
        this.bias2 = bias2;
        this.bias3 = bias3;
        this.alfa_1 = alfa_1;
        this.alfa_2 = alfa_2;
        this.alfa_3 = alfa_3;
    }

    public void baca_testing(String file) {
        File fIn = new File(file);
        data = new file_excel().baca(fIn);
        System.out.println(" banyak data testing " + data.size());
        int a = 0;
        for (ArrayList<String> arrayList : data) {
            for (int i = 0; i < arrayList.size(); i++) {
                data_testing[a][i] = Double.valueOf(arrayList.get(i));
            }
            a++;

        }
        System.out.println("TESTING......");
        for (int i = 0; i < data_testing.length; i++) {//
            for (int j = 0; j < data_testing[0].length; j++) {
                System.out.print(data_testing[i][j] + "|");
            }
            System.out.println("");
        }
    }

    public void baca_training(String file1, String file2, String file3) {
        File fIn = new File(file1);
        data = new file_excel().baca(fIn);
        int a = 0;
        for (ArrayList<String> arrayList : data) {
            // System.out.println("data "+arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                data_training1[a][i] = Double.valueOf(arrayList.get(i));
            }
            a++;

        }
        System.out.println("TRAINING 1....");
        for (int i = 0; i < data_training1.length; i++) {//
            for (int j = 0; j < data_training1[0].length; j++) {
                System.out.print(data_training1[i][j] + "|");
            }
            System.out.println("");
        }
        fIn = new File(file2);
        data = new file_excel().baca(fIn);
        a = 0;
        for (ArrayList<String> arrayList : data) {

            for (int i = 0; i < arrayList.size(); i++) {
                data_training2[a][i] = Double.valueOf(arrayList.get(i));
            }
            a++;

        }
        System.out.println("TRAINING 2....");
        for (int i = 0; i < data_training2.length; i++) {//
            for (int j = 0; j < data_training2[0].length; j++) {
                System.out.print(data_training2[i][j] + "|");
            }
            System.out.println("");
        }
        fIn = new File(file3);
        data = new file_excel().baca(fIn);
        a = 0;
        for (ArrayList<String> arrayList : data) {

            for (int i = 0; i < arrayList.size(); i++) {
                data_training3[a][i] = Double.valueOf(arrayList.get(i));
            }
            a++;

        }
        System.out.println("TRAINING 3....");
        for (int i = 0; i < data_training3.length; i++) {//
            for (int j = 0; j < data_training3[0].length; j++) {
                System.out.print(data_training3[i][j] + "|");
            }
            System.out.println("");
        }
    }

    public double[] Kxix(int idxTraining, int idxTesting) {
        double[][] data_training;
        double[] kernel;
        double C = 1.0;
        if (idxTraining == 1) {
            data_training = data_training1;
        } else if (idxTraining == 2) {
            data_training = data_training2;
        } else {
            data_training = data_training3;
        }
        kernel = new double[data_training.length];

        System.out.println("Kernel RBF");
        for (int i = 0; i < data_training.length; i++) {
            kernel[i] = Math.exp(-1 * ((Math.pow(data_testing[idxTesting][0] - data_training[i][0], 2) + Math.pow(data_testing[idxTesting][1] - data_training[i][1], 2) + Math.pow(data_testing[idxTesting][2] - data_training[i][2], 2) + Math.pow(data_testing[idxTesting][3] - data_training[i][3], 2)
                    + Math.pow(data_testing[idxTesting][4] - data_training[i][4], 2) + Math.pow(data_testing[idxTesting][5] - data_training[i][5], 2) + Math.pow(data_testing[idxTesting][6] - data_training[i][6], 2)
                    + Math.pow(data_testing[idxTesting][7] - data_training[i][7], 2) + Math.pow(data_testing[idxTesting][8] - data_training[i][8], 2)
                    + Math.pow(data_testing[idxTesting][9] - data_training[i][9], 2) + Math.pow(data_testing[idxTesting][10] - data_training[i][10], 2)
                    + Math.pow(data_testing[idxTesting][11] - data_training[i][11], 2) + Math.pow(data_testing[idxTesting][12] - data_training[i][12], 2) + Math.pow(data_testing[idxTesting][13] - data_training[i][13], 2)
                    + Math.pow(data_testing[idxTesting][14] - data_training[i][14], 2) + Math.pow(data_testing[idxTesting][15] - data_training[i][15], 2)) / 2));
            //    System.out.print(kernel[i]+" \\");
        }
        System.out.println("Kxix - " + idxTesting);
        for (int i = 0; i < kernel.length; i++) {//
            System.out.print(kernel[i] + "|");
            System.out.println("");
        }

//         System.out.println("cekkkkkk "+data_testing[idxTesting][0]+" | "+data_testing[idxTesting][1]+" | "+data_testing[idxTesting][2]
//         +" | "+data_testing[idxTesting][3]+" | "+data_testing[idxTesting][4]+" | "+data_testing[idxTesting][5]+" | "+data_testing[idxTesting][6]
//         +" | "+data_testing[idxTesting][7]+" | "+data_testing[idxTesting][8]+" | "+data_testing[idxTesting][9]+" | "+data_testing[idxTesting][10]
//         +" | "+data_testing[idxTesting][11]+" | "+data_testing[idxTesting][12]+" | "+data_testing[idxTesting][13]+" | "+data_testing[idxTesting][14]
//         +" | "+data_testing[idxTesting][15]);
//         
//         for (int i = 0; i < data_training.length; i++) {
//         System.out.println("TEST "+data_training[i][0]+" | "+data_training[i][1]+" | "+data_training[i][2]
//         +" | "+data_training[i][3]+" | "+data_training[i][4]+" | "+data_training[i][5]+" | "+data_training[i][6]
//         +" | "+data_training[i][7]+" | "+data_training[i][8]+" | "+data_training[i][9]+" | "+data_training[i][10]
//         +" | "+data_training[i][11]+" | "+data_training[i][12]+" | "+data_training[i][13]+" | "+data_training[i][14]
//         +" | "+data_training[i][15]);
//        }
        return kernel;
    }

    public double level1(int idxTesting) {
        double[] K = Kxix(1, idxTesting);
        double[] aiyiK = new double[K.length];
        double total = 0;
        double fx = 0;
        System.out.println("a LEVEL 1");
        for (int i = 0; i < aiyiK.length; i++) {
            aiyiK[i] = alfa_1[i] * data_training1[i][16] * K[i];
            total += aiyiK[i];
            System.out.println("Nilai aiyiK = " + aiyiK[i]);
        }

        fx = bias1 + total;
        System.out.println("Nilai F(x) LEVEL 1 = " + fx);
        double hasil = 0;
        hasil = Math.signum(fx);
//        System.out.println("Hasil : " + hasil);
        hasilfx1 = hasil;
        System.out.println("Hasil LEVEL 1 = " + hasilfx1);
        return hasilfx1;
    }

    public double level2(int idxTesting) {
        double[] K = Kxix(2, idxTesting);
        double[] aiyiK = new double[K.length];
        double total = 0;
        double fx = 0;
        System.out.println("a LEVEL 2");
        for (int i = 0; i < aiyiK.length; i++) {
            aiyiK[i] = alfa_1[i] * data_training1[i][16] * K[i];
            total += aiyiK[i];
            System.out.println("Nilai aiyiK = " + aiyiK[i]);
        }
        fx = bias1 + total;
        System.out.println("Nilai F(x) LEVEL 2 = " + fx);
        double hasil = 0;
        hasil = Math.signum(fx);
        hasilfx2 = hasil;
        System.out.println("Hasil LEVEL 2 = " + hasilfx2);
        return hasilfx2;
    }

    public double level3(int idxTesting) {
        double[] K = Kxix(3, idxTesting);
        double[] aiyiK = new double[K.length];
        double total = 0;
        double fx = 0;
        System.out.println("a LEVEL 3");
        for (int i = 0; i < aiyiK.length; i++) {
            aiyiK[i] = alfa_1[i] * data_training1[i][16] * K[i];
            total += aiyiK[i];
            System.out.println("Nilai aiyiK = " + aiyiK[i]);
        }
        fx = bias1 + total;
        System.out.println("Nilai F(x) LEVEL 3 = " + fx);
        double hasil = 0;
        hasil = Math.signum(fx);
        hasilfx3 = hasil;
        System.out.println("Hasil LEVEL 3 = " + hasilfx3);
        return hasilfx3;
    }
//
//        public double[][] kernelTestingLinear(double[][] data_training, double[][] data_testing) {
//        double result[][] = new double[data_training.length][data_testing.length];
//        double HasilKernel[][] = new double[data_training.length][data_testing.length];
//        double hasil;
//        //  System.out.println("K(xi,x):");
//        System.out.println();
//        for (int x = 0; x < data_training.length; x++) { //jumlah baris
//            for (int y = 0; y < data_testing.length; y++) { //jumlah kolom atau parameter
//                hasil = 0;
//                for (int z = 0; z < data_training[x].length; z++) {
//                    //rumus untuk kernel
//                    hasil += (data_training[x][z] * data_testing[y][z]);
//                    HasilKernel[x][y] = hasil;
//                    result[x][y] = HasilKernel[x][y];
//                }
//            }
//        }
//            System.out.println("Kernel Linear = " + result);
//        return result;
//    }

    public double Test() {
        hasilUji = new double[data_testing.length][2];//kolom pertama buat urutan, kolom kedua untuk hasil tiap levelnya
        for (int i = 0; i < data_testing.length; i++) {
            System.out.println("TESTING KE - " + i);
            hasilUji[i][1] = level1(i);
            hasilUji[i][0] = 1;
            if (hasilUji[i][1] != 1) {
                hasilUji[i][1] = level2(i);
                hasilUji[i][0] = 2;
                if (hasilUji[i][1] != 1) {
                    hasilUji[i][1] = level3(i);
                    hasilUji[i][0] = 3;
                    if (hasilUji[i][1] != 1) {
                        hasilUji[i][1] = -1;
                        hasilUji[i][0] = 4;
                    }
                }
            }
        }
        akurasi = 0;
        for (int i = 0; i < data_testing.length; i++) {
            if (hasilUji[i][0] == data_testing[i][17]) {
                akurasi++;
            }
        }
        akurasi = (akurasi * 100) / data_testing.length;
        System.out.println("Akurasi = " + akurasi);
        return akurasi;
    }
}
