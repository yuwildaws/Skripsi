/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import skripsi.file_excel;

public class main2 {

    List<ArrayList<String>> data;
    double[][] data_training;
    //double[][] data_training = new double[12][17];
    double[][] kernel;
    double[][] matrixhessian;
    double lamda = 0.5;
    double[] ai;
    double[] Ei;
    double[] delta_i;
    double gamma = 0.01;
    double C = 1;
    double[] alfa_i;
    static int pilihKernel;

//    public static void main(String[] args) {
//        new main().menu();
//    }
    public main2(int x, int y) {
        data_training = new double[x][y];
        kernel = new double[data_training.length][data_training.length];
        matrixhessian = new double[data_training.length][data_training.length];
        ai = new double[data_training.length];
        Ei = new double[data_training.length];
        delta_i = new double[data_training.length];
        alfa_i = new double[data_training.length];

    }

    public main2() {

    }

    public void baca(String file) {
        File fIn = new File(file);
        data = new file_excel().baca(fIn);
        System.out.println("-------------------------------------------------------------------------------------");
        int a = 0;
        int akhir = data.size() - 1;
        System.out.println("akhir " + akhir);
        ArrayList<String> x = data.get(akhir);
        //buat enghilangkan data yan tidak ada nilainya
        if (x.get(0).equals("")) {
            data.remove(data.size() - 1);
        }
        // end
        for (ArrayList<String> arrayList : data) {
            for (int i = 0; i < arrayList.size(); i++) {
                data_training[a][i] = Double.valueOf(arrayList.get(i));
                // System.out.println("baris " + a + " kolom : " + i + " ini nilai arraylist :" + arrayList.get(i) +"");
            }
            a++;

        }
        System.out.println("--------DATA TRAINING--------");
        for (int i = 0; i < data_training.length; i++) {//
            for (int j = 0; j < data_training[0].length; j++) {
                System.out.print(data_training[i][j] + "|");
            }
            System.out.println("");
        }
    }

    public void KernelRBF() {
        System.out.println("Kernel RBF");
        for (int i = 0; i < data_training.length; i++) {
            for (int j = 0; j < data_training.length; j++) {
                kernel[i][j] = Math.exp(-1 * ((Math.pow(data_training[i][0] - data_training[j][0], 2) + Math.pow(data_training[i][1] - data_training[j][1], 2) + Math.pow(data_training[i][2] - data_training[j][2], 2) + Math.pow(data_training[i][3] - data_training[j][3], 2)
                        + Math.pow(data_training[i][4] - data_training[j][4], 2) + Math.pow(data_training[i][5] - data_training[j][5], 2) + Math.pow(data_training[i][6] - data_training[j][6], 2)
                        + Math.pow(data_training[i][7] - data_training[j][7], 2) + Math.pow(data_training[i][8] - data_training[j][8], 2)
                        + Math.pow(data_training[i][9] - data_training[j][9], 2) + Math.pow(data_training[i][10] - data_training[j][10], 2)
                        + Math.pow(data_training[i][11] - data_training[j][11], 2) + Math.pow(data_training[i][12] - data_training[j][12], 2) + Math.pow(data_training[i][13] - data_training[j][13], 2)
                        + Math.pow(data_training[i][14] - data_training[j][14], 2) + Math.pow(data_training[i][15] - data_training[j][15], 2)) / 2));
//                System.out.println(kernel[i][j]);
            }
        }
        for (int i = 0; i < kernel.length; i++) {//
            for (int j = 0; j < kernel[0].length; j++) {
                System.out.print(kernel[i][j] + "|");
            }
            System.out.println("");
        }
    }

    public void KernelPolynomialDegree2() {
        System.out.println("Kernel Polynomial Degree 2");
        for (int i = 0; i < data_training.length; i++) {
            for (int j = 0; j < data_training.length; j++) {
                kernel[i][j] = ((Math.pow(((data_training[i][0] * data_training[j][0]) + (data_training[i][1] * data_training[j][1])
                        + (data_training[i][2] * data_training[j][2]) + (data_training[i][3] * data_training[j][3]) + (data_training[i][4] * data_training[j][4]) + (data_training[i][5] * data_training[j][5])
                        + (data_training[i][6] * data_training[j][6]) + (data_training[i][7] * data_training[j][7]) + (data_training[i][8] * data_training[j][8]) + (data_training[i][9] * data_training[j][9])
                        + (data_training[i][10] * data_training[j][10]) + (data_training[i][11] * data_training[j][11]) + (data_training[i][12] * data_training[j][12])
                        + (data_training[i][13] * data_training[j][13]) + (data_training[i][14] * data_training[j][14]) + (data_training[i][15] * data_training[j][15])) + C, 2)));
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                System.out.print(kernel[i][j] + "|");
            }
        System.out.println("");
        }
    }

    public void KernelPolynomialDegree() {
        System.out.println("Kernel Polynomial Degree");
        for (int i = 0; i < data_training.length; i++) {
            for (int j = 0; j < data_training.length; j++) {
                kernel[i][j] = ((data_training[i][0] * data_training[j][0]) + (data_training[i][1] * data_training[j][1])
                        + (data_training[i][2] * data_training[j][2]) + (data_training[i][3] * data_training[j][3]) + (data_training[i][4] * data_training[j][4]) + (data_training[i][5] * data_training[j][5])
                        + (data_training[i][6] * data_training[j][6]) + (data_training[i][7] * data_training[j][7]) + (data_training[i][8] * data_training[j][8]) + (data_training[i][9] * data_training[j][9])
                        + (data_training[i][10] * data_training[j][10]) + (data_training[i][11] * data_training[j][11]) + (data_training[i][12] * data_training[j][12])
                        + (data_training[i][13] * data_training[j][13]) + (data_training[i][14] * data_training[j][14]) + (data_training[i][15] * data_training[j][15]));
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                System.out.print(kernel[i][j] + "|");
            }
        System.out.println("");
        }
    }

    public void KernelLinear() {
        System.out.println("Kernel Linear");
        for (int i = 0; i < data_training.length; i++) {
            for (int j = 0; j < data_training.length; j++) {
                kernel[i][j] = (((data_training[i][0] * data_training[j][0]) + (data_training[i][1] * data_training[j][1])
                        + (data_training[i][2] * data_training[j][2]) + (data_training[i][3] * data_training[j][3]) + (data_training[i][4] * data_training[j][4]) + (data_training[i][5] * data_training[j][5])
                        + (data_training[i][6] * data_training[j][6]) + (data_training[i][7] * data_training[j][7]) + (data_training[i][8] * data_training[j][8]) + (data_training[i][9] * data_training[j][9])
                        + (data_training[i][10] * data_training[j][10]) + (data_training[i][11] * data_training[j][11]) + (data_training[i][12] * data_training[j][12])
                        + (data_training[i][13] * data_training[j][13]) + (data_training[i][14] * data_training[j][14]) + (data_training[i][15] * data_training[j][15])));
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                System.out.print(kernel[i][j] + "|");
            }
            System.out.println("");
        }
    }

    public int pilihKernel() {
        return pilihKernel;
    }

    public void Matrixhessian() {
        if (pilihKernel == 1) {
            KernelRBF();
        } else if (pilihKernel == 2) {
            KernelPolynomialDegree2();
        } else if (pilihKernel == 3) {
            KernelPolynomialDegree();
        } else if (pilihKernel == 4) {
            KernelLinear();
        }
        System.out.println("");
        for (int i = 0; i < data_training.length; i++) {
            for (int j = 0; j < data_training.length; j++) {
                matrixhessian[i][j] = data_training[i][16] * data_training[j][16] * (kernel[i][j] + Math.pow(lamda, 2));

            }
        }
        System.out.println("Matrix Hessian");
        for (int i = 0; i < matrixhessian.length; i++) {//
            for (int j = 0; j < matrixhessian[0].length; j++) {
                System.out.print(matrixhessian[i][j] + "|");
            }
            System.out.println("");
        }
    }

    public void Ei(double[] data) {
        Matrixhessian();
        System.out.println("");
        for (int i = 0; i < data_training.length; i++) {
            double sum = 0;//untuk menyimpan hasil penjumlahan ai*matrixhessian
            for (int j = 0; j < data_training.length; j++) {
                sum += (data[i] * matrixhessian[i][j]);
            }
            Ei[i] = sum;
        }
//        System.out.println("data ---");
//        for (int i = 0; i < Ei.length; i++) {
//            System.out.print(data[i] + "|");
//        }
        // System.out.println("");

        // System.out.println("");
        System.out.println("nilai EI");
        for (int i = 0; i < Ei.length; i++) {
            System.out.print(Ei[i] + "|");
        }
        System.out.println("");
    }
//    public void Ei(double[] data) {
//        Matrixhessian();
//        double[][] xx = matrixhessian;// inisialisasi untuk mencetak nilai Ei per baris
//        System.out.println("");
//        for (int i = 0; i < data_training.length; i++) {
//            double sum = 0;
//            for (int j = 0; j < data_training.length; j++) {
//                xx[i][j] = (data[i] * matrixhessian[i][j]);
//            }
//        }
//        
//        for (int i = 0; i < xx.length; i++) {            
//            Ei[i] = xx[i][0]+xx[i][1]+xx[i][2]+xx[i][3]+xx[i][4]+xx[i][5]+xx[i][6]+xx[i][7]+xx[i][8]+xx[i][9]+
//                    xx[i][10]+xx[i][11];
//        }
//        System.out.println("ei");
//        for (int i = 0; i < xx.length; i++) {
//            for (int j = 0; j < xx[0].length; j++) {
//                System.out.print(xx[i][j] +" ");
//                
//            }
//            System.out.println("");
//            
//        }
//        System.out.println("");
//        System.out.println("Nilai Ei");
//        for (int i = 0; i < Ei.length; i++) {
//            System.out.println(Ei[i] + "|");
//        }
//    }

    public void delta_i(double[] data) {
        Ei(data);
        System.out.println("");
        for (int i = 0; i < data_training.length; i++) {
            delta_i[i] = Math.min(Math.max(gamma * (1 - Ei[i]), (-1 * data[i])), C - data[i]);
        }
        System.out.println("nilai delta");
        for (int i = 0; i < delta_i.length; i++) {
            System.out.print(delta_i[i] + "|");
        }
        System.out.println("");
    }

    public double[] alfa_i(double[] data) {//memanggil proses terakhir saja, karena diatasnya sudah emmanggil proses sebelumnya dan seterusnya
        delta_i(data);
        System.out.println("sebelum update");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
        }

        System.out.println("");
        for (int i = 0; i < data_training.length; i++) {
            alfa_i[i] = data[i] + delta_i[i];
        }
        System.out.println("nilai alpha");
        for (int i = 0; i < alfa_i.length; i++) {
            System.out.print(alfa_i[i] + "|");
        }
        System.out.println("");
        ai = alfa_i;
        System.out.println("setelah update");
        for (int i = 0; i < ai.length; i++) {
            System.out.println(ai[i]);
        }
        return ai;
    }

    public double Kernelnegatifpositif(double[] alfa_i, int p, int n) {
        double bias = 0;
        // baca();
        double kelaspositif[][] = new double[p][2];
        double kelasnegatif[][] = new double[n][2];
        int c = 0;
        int d = 0;

        for (int i = 0; i < data_training.length; i++) {
            if (data_training[i][16] == 1.0) {
                kelaspositif[c][0] = i;
                kelaspositif[c][1] = alfa_i[i];
                c++;
            } else if (data_training[i][16] == -1) {
                kelasnegatif[d][0] = i;
                kelasnegatif[d][1] = alfa_i[i];
                d++;
            }
        }
        //mencari nilai maksimal
        double maxPositif = kelaspositif[0][1];
        double urutan = kelaspositif[0][0];
        for (int i = 0; i < kelaspositif.length; i++) {
            if (maxPositif < kelaspositif[i][1]) {
                maxPositif = kelaspositif[i][1];
                urutan = kelaspositif[i][0];
            }

        }
        double maxNegatif = kelasnegatif[0][1];
        double urutanNegatif = kelasnegatif[0][0];
        for (int i = 0; i < kelasnegatif.length; i++) {
            if (maxNegatif < kelasnegatif[i][1]) {
                maxNegatif = kelasnegatif[i][1];
                urutanNegatif = kelasnegatif[i][0];
            }
        }
        //kerne X+ untuk Wx+
        double sum = 0.0;
        for (int i = 0; i < data_training.length; i++) {
            double hasil = (alfa_i[i] * data_training[i][16] * matrixhessian[i][(int) urutan]);
            sum += hasil;
        }

        //kernel X- untuk Wx-
        double sum1 = 0.0;
        for (int i = 0; i < data_training.length; i++) {
            double hasil = (alfa_i[i] * data_training[i][16] * matrixhessian[i][(int) urutanNegatif]);
            sum1 += hasil;
        }
        System.out.println("Nilai Kx+ = " + urutan);
        System.out.println("Nilai Kx- = " + urutanNegatif);
        System.out.println("Nilai Wx+   = " + sum);
        System.out.println("Nilai Wx-  = " + sum1);

        //menghitung b
        bias = -0.5 * (sum + sum1);

        System.out.println("bias = " + bias);
        return bias;
    }

    public static void levelIterasi() {
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih Kernel : ");
        System.out.println("1. Kernel RBF");
        System.out.println("2. Kernel Polynomial");
        System.out.println("3. Kernel Polynomial Degree");
        System.out.println("4. Kernel Linear");
        System.out.print("Masukkan Pilihan Kernel : ");
        pilihKernel = input.nextInt();
        System.out.println("LEVEL 1");
        main x = new main(92, 18);

        double epsilon = 0.001;
        int n = 92; //banyak data
        double alfa_i[] = new double[n];
        // double alpha[] = new double[n];
        //   boolean cek = false;
        double aai[] = new double[n];//nilai alfa awal (0)
        int banyak = 0;//untuk menampilkan iterasi ke berapa, dan biar tau iterasi berapa aja yg sudah dilalui
        int max = 2; // banyaknya iterasi
        double bias = 0;
        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
            System.out.println("");
            System.out.println("iterasi ke-" + (banyak + 1));
//            new main().baca();// buat baca file
            System.out.println("------------");
            /*aai = new main().alfa_i(aai);//
             System.out.println("TEST AAI MAIN:");
             for (int i = 0; i < aai.length; i++) {
             System.out.println("nilai aai -"+i+"- : "+aai[i]);
             }*/
            x.baca("E:\\datatraining.xls");
            alfa_i = x.alfa_i(alfa_i,"rbf");
            banyak++;
            if (banyak == max) {
                bias = x.Kernelnegatifpositif(alfa_i, 24, 68);
            }
        } while (banyak != max);
//        
//        // proses Level 2
//        SVM_Level2 level2 = new SVM_Level2();
//        double alfa_2[] = new double[n];
//        banyak =0;
//        double aai_2[] = new double[n];//nilai alfa awal (0)
//        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
//            System.out.println("");
//            System.out.println("iterasi ke-" + (banyak + 1));
////            new main().baca();// buat baca file
//            System.out.println("------------");
//            aai_2 = level2.alfa_i(aai_2);//
//            alfa_i = level2.alfa_i(alfa_i);
//            banyak++;
//            if (banyak == max){
//                bias = level2.Kernelnegatifpositif(alfa_i);
//            }
//        } while (banyak != max);
//    }
        System.out.println("");
        //level 2

        System.out.println("LEVEL 2");
        main x2 = new main(68, 18);
        double alfa_2[] = new double[n];
        double bias2 = 0;
        banyak = 0;
        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
            System.out.println("");
            System.out.println("iterasi ke-" + (banyak + 1));
//            new main().baca();// buat baca file
            System.out.println("------------");
            /*aai = new main().alfa_i(aai);//
             System.out.println("TEST AAI MAIN:");
             for (int i = 0; i < aai.length; i++) {
             System.out.println("nilai aai -"+i+"- : "+aai[i]);
             }*/
            x2.baca("E:\\datatraininglevel 2.xls");
            alfa_2 = x2.alfa_i(alfa_2, "rbf");
            banyak++;
            if (banyak == max) {
                bias2 = x2.Kernelnegatifpositif(alfa_2, 21, 47);
            }
        } while (banyak != max);

        System.out.println("");
        //level 2

        System.out.println("LEVEL 3");
        main x3 = new main(47, 18);
        double alfa_3[] = new double[n];
        double bias3 = 0;
        banyak = 0;
        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
            System.out.println("");
            System.out.println("iterasi ke-" + (banyak + 1));
//            new main().baca();// buat baca file
            System.out.println("------------");
            /*aai = new main().alfa_i(aai);//
             System.out.println("TEST AAI MAIN:");
             for (int i = 0; i < aai.length; i++) {
             System.out.println("nilai aai -"+i+"- : "+aai[i]);
             }*/
            x3.baca("E:\\datatraininglevel 3.xls");
            alfa_3 = x3.alfa_i(alfa_3, "rbf");
            banyak++;
            if (banyak == max) {
                bias3 = x3.Kernelnegatifpositif(alfa_3, 23, 24);
            }
        } while (banyak != max);
        System.out.println("TESTING");
        Testing2 hasil = new Testing2(bias, bias2, bias3, alfa_i, alfa_2, alfa_3);
        hasil.Test();
    }

    public static void main(String[] args) {
        levelIterasi();
    }
}
