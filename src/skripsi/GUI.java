/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ana
 */
public class GUI extends javax.swing.JFrame {

    List<ArrayList<String>> data;
    double[][] data_training;
    double[][] data_testing;
    //double[][] data_training = new double[12][17];
    double[][] kernel;
    double[][] matrixhessian;
    double[] ai;
    double[] Ei;
    double[] alfa_i;
    double[] delta_i;
    double gamma = 0.01;
    double lamda = 0.5;
    double C = 1;
    int max = 20; // banyaknya iterasi
    double epsilon = 0.0000000010;
    double D = 2;
    double urutan;
    double urutanNegatif;
    double sum;
    double sum1;
    double bias;
    int p, n;

    public double getgamma() {
        return gamma;
    }

    public void setgamma(double gamma) {
        this.gamma = gamma;
    }

    public double getlamda() {
        return lamda;
    }

    public void setlamda(double lamda) {
        this.lamda = lamda;
    }

    public double getC() {
        return C;
    }

    public void setC(double C) {
        this.C = C;
    }

    public int getmax() {
        return max;
    }

    public void setmax(int max) {
        this.max = max;
    }

    public double getepsilon() {
        return epsilon;
    }

    public void setepsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double getD() {
        return D;
    }

    public void setD(double D) {
        this.D = D;
    }

    public GUI(int x, int y) {
        data_training = new double[x][y];
        kernel = new double[data_training.length][data_training.length];
        matrixhessian = new double[data_training.length][data_training.length];
        ai = new double[data_training.length];
        Ei = new double[data_training.length];
        delta_i = new double[data_training.length];
        alfa_i = new double[data_training.length];
        data_testing = new double[x][y];

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
            System.out.println("Baris Ke - " + (i + 1) + " ");
            for (int j = 0; j < data_training.length; j++) {
                kernel[i][j] = ((Math.pow(((data_training[i][0] * data_training[j][0]) + (data_training[i][1] * data_training[j][1])
                        + (data_training[i][2] * data_training[j][2]) + (data_training[i][3] * data_training[j][3]) + (data_training[i][4] * data_training[j][4]) + (data_training[i][5] * data_training[j][5])
                        + (data_training[i][6] * data_training[j][6]) + (data_training[i][7] * data_training[j][7]) + (data_training[i][8] * data_training[j][8]) + (data_training[i][9] * data_training[j][9])
                        + (data_training[i][10] * data_training[j][10]) + (data_training[i][11] * data_training[j][11]) + (data_training[i][12] * data_training[j][12])
                        + (data_training[i][13] * data_training[j][13]) + (data_training[i][14] * data_training[j][14]) + (data_training[i][15] * data_training[j][15])) + C, 2)));
                System.out.print(kernel[i][j] + " | ");
            }
            System.out.println();
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

    public void Matrixhessian(String nama) { //nama adalah string kernel 
        if (nama.equalsIgnoreCase("rbf")) {
            KernelRBF();
        } else if (nama.equalsIgnoreCase("polinomial")) {
            KernelPolynomialDegree();
        } else if (nama.equalsIgnoreCase("degree2")) {
            KernelPolynomialDegree2();
        } else if (nama.equalsIgnoreCase("linier")) {
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

    public void Ei(double[] data, String nama) {
        Matrixhessian(nama);
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

    public void delta_i(double[] data, String nama) {
        Ei(data, nama);
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
    double alfabefore[];

    public double[] alfa_i(double[] data, String nama) {//memanggil proses terakhir saja, karena diatasnya sudah emmanggil proses sebelumnya dan seterusnya
        alfabefore = new double[data_training.length];
        delta_i(data, nama);
        System.out.println("sebelum update");
        for (int i = 0; i < data.length; i++) {
//            alfabefore[i] = data[i];
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
        bias = 0;
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
        urutan = kelaspositif[0][0];
        for (int i = 0; i < kelaspositif.length; i++) {
            if (maxPositif < kelaspositif[i][1]) {
                maxPositif = kelaspositif[i][1];
                urutan = kelaspositif[i][0];
            }

        }
        double maxNegatif = kelasnegatif[0][1];
        urutanNegatif = kelasnegatif[0][0];
        for (int i = 0; i < kelasnegatif.length; i++) {
            if (maxNegatif < kelasnegatif[i][1]) {
                maxNegatif = kelasnegatif[i][1];
                urutanNegatif = kelasnegatif[i][0];
            }
        }
        //kernel X+
        sum = 0.0;
        for (int i = 0; i < data_training.length; i++) {
            double hasil = (alfa_i[i] * data_training[i][16] * matrixhessian[i][(int) urutan]);
            sum += hasil;
        }

        //kernel X+
        sum1 = 0.0;
        for (int i = 0; i < data_training.length; i++) {
            double hasil = (alfa_i[i] * data_training[i][16] * matrixhessian[i][(int) urutanNegatif]);
            sum1 += hasil;
        }
        System.out.println("Nilai Kx+ = " + urutan);
        System.out.println("Nilai Kx- = " + urutanNegatif);
        System.out.println("Kernel(xi,x+)   = " + sum);
        System.out.println("Kernel(xi,x-)  = " + sum1);
        //menghitung b
        bias = -0.5 * (sum + sum1);

        System.out.println("bias = " + bias);
        return bias;
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

    public GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTable24 = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        Kernel = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jlamda = new javax.swing.JTextField();
        jgamma = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jC = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jepsilon = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jIterasi = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jD = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable15 = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTable18 = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTable25 = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable19 = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTable20 = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTable21 = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTable22 = new javax.swing.JTable();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTable23 = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable26 = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel1.setText("KLASIFIKASI PENYAKIT GIGI DAN MULLUT MENGGUNAKAN METODE SUPPORT VECTOR MACHINE");

        jButton1.setText("TAMPILKAN DATA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11", "G12", "G13", "G14", "G15", "G16", "Kelas", "Actual Class"
            }
        ));
        jScrollPane11.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("DATA TRAINING", jPanel6);

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11", "G12", "G13", "G14", "G15", "G16", "Kelas", "Actual Class"
            }
        ));
        jScrollPane12.setViewportView(jTable11);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("DATA TESTING", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2))
        );

        jTabbedPane1.addTab("DATA", jPanel1);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("KERNEL", jPanel11);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("MATRIX HESSIAN", jPanel12);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        jLabel28.setText("Delta Alpha");

        jLabel29.setText("Alpha Setelah Update");

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane17.setViewportView(jTable16);

        jLabel30.setText("Alpha Sebelum Update");

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane18.setViewportView(jTable17);

        jLabel31.setText("Nilai Wx+ = ");

        jLabel32.setText("Nilai Kx- KE = ");

        jLabel33.setText("Nilai Kx + KE = ");

        jLabel34.setText("Nilai Wx- = ");

        jLabel11.setText("NILAI BIAS LEVEL 1 = ");

        jTable24.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane25.setViewportView(jTable24);

        jLabel53.setText("Nilai Ei");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)))
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jTextField4)))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField3))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(155, 155, 155))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel29)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("SEQUENTIAL TRAINING", jPanel13);

        Kernel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KERNEL RBF", "KERNEL POLYNOMIAL DEGREE", "KERNEL POLYNOMIAL DEGREE 2", "KERNEL LINEAR" }));
        Kernel.setToolTipText("");
        Kernel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KernelActionPerformed(evt);
            }
        });

        jLabel3.setText("LAMDA");

        jLabel17.setText("GAMMA");

        jlamda.setText("0.5");
        jlamda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlamdaActionPerformed(evt);
            }
        });

        jgamma.setText("0.008");

        jLabel25.setText("C");

        jC.setText("1");

        jLabel26.setText("EPSILON");

        jepsilon.setText("0.001");

        jLabel27.setText("ITERASI");

        jIterasi.setText("2");
        jIterasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIterasiActionPerformed(evt);
            }
        });

        jButton3.setText("PROSES");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel52.setText("D");

        jD.setText("2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(Kernel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jgamma, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(jlamda))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jC, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jepsilon, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jD, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(jIterasi))
                        .addGap(46, 46, 46)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1305, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Kernel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlamda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27)
                                    .addComponent(jIterasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jepsilon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jgamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel52)))
                            .addComponent(jD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)))
                .addComponent(jTabbedPane4))
        );

        jTabbedPane1.addTab("LEVEL 1", jPanel4);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            }
        ));
        jScrollPane7.setViewportView(jTable7);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("KERNEL", jPanel14);

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            }
        ));
        jScrollPane8.setViewportView(jTable8);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("MATRIX HESSIAN", jPanel15);

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane9.setViewportView(jTable9);

        jLabel13.setText("NILAI BIAS LEVEL 2 = ");

        jLabel35.setText("Alpha Setelah Update");

        jLabel36.setText("Delta Alpha");

        jTable15.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane16.setViewportView(jTable15);

        jLabel37.setText("Alpha Sebelum Update");

        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane19.setViewportView(jTable18);

        jLabel38.setText("Nilai Kx- KE = ");

        jLabel39.setText("Nilai Wx+ = ");

        jLabel40.setText("Nilai Wx- = ");

        jLabel41.setText("Nilai Kx+ KE = ");

        jTable25.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane26.setViewportView(jTable25);

        jLabel54.setText("Nilai Ei");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField10))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField9))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 156, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("SEQUENTIAL TRAINING", jPanel16);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5))
        );

        jTabbedPane1.addTab("LEVEL 2", jPanel5);

        jTable19.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            }
        ));
        jScrollPane20.setViewportView(jTable19);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("KERNEL", jPanel18);

        jTable20.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            }
        ));
        jScrollPane21.setViewportView(jTable20);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 1310, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("MATRIX HESSIAN", jPanel19);

        jTable21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane22.setViewportView(jTable21);

        jLabel44.setText("NILAI BIAS LEVEL 3 = ");

        jLabel45.setText("Alpha Setelah Update");

        jLabel46.setText("Nilai Ei");

        jTable22.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane23.setViewportView(jTable22);

        jLabel47.setText("Alpha Sebelum Update");

        jTable23.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane24.setViewportView(jTable23);

        jLabel48.setText("Nilai Kx- KE = ");

        jLabel49.setText("Nilai Wx+ = ");

        jLabel50.setText("Nilai Wx- = ");

        jLabel51.setText("Nilai Kx+ KE = ");

        jTable26.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ));
        jScrollPane27.setViewportView(jTable26);

        jLabel55.setText("Delta Alpha");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel20Layout.createSequentialGroup()
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addComponent(jTextField15, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(16, 16, 16))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131))))
        );

        jTabbedPane6.addTab("SEQUENTIAL TRAINING", jPanel20);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane6)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6))
        );

        jTabbedPane1.addTab("LEVEL 3", jPanel2);

        jLabel2.setText("AKURASI = ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "Sign f(x) Level 1", "Sign f(x) Level 2", "Sign f(x) Level 3", "Hasil Klasifikasi", "Actual Class"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel42.setText("HASIL KLASIFIKASI");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(541, 541, 541)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(569, 569, 569)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(365, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("TESTING SVM", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1048, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        GUI x = new GUI(104, 18);
        GUI y = new GUI(18, 18);
        x.gamma = Double.parseDouble(jgamma.getText());
        System.out.println(" Gamma " + jgamma);
        x.lamda = Double.parseDouble(jlamda.getText());
        x.C = Double.parseDouble(jC.getText());
        x.max = Integer.parseInt(jIterasi.getText());
        x.epsilon = Double.parseDouble(jepsilon.getText());
        x.D = Double.parseDouble(jD.getText());
//        t.KernelRBF();
//        t.Matrixhessian();
        x.baca("E:\\Latih2(80_20).xls");
        y.baca_testing("E:\\Uji2(80_20).xls");
//        System.out.println("data training");
//        System.out.println(x.data_training.length);
//        for (int i = 0; i < x.data_training.length; i++) {
//            for (int j = 0; j < x.data_training[0].length; j++) {
//                System.out.println(x.data_training[i][j]);
//            }
//        }
        DefaultTableModel tbl_training = (DefaultTableModel) (jTable2.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_training.addRow(
                    new Object[]{
                        x.data_training[i][0],
                        x.data_training[i][1],
                        x.data_training[i][2],
                        x.data_training[i][3],
                        x.data_training[i][4],
                        x.data_training[i][5],
                        x.data_training[i][6],
                        x.data_training[i][7],
                        x.data_training[i][8],
                        x.data_training[i][9],
                        x.data_training[i][10],
                        x.data_training[i][11],
                        x.data_training[i][12],
                        x.data_training[i][13],
                        x.data_training[i][14],
                        x.data_training[i][15],
                        x.data_training[i][16],
                        x.data_training[i][17]
                    });
        }
        DefaultTableModel tbl_testing = (DefaultTableModel) (jTable11.getModel());
        for (int i = 0; i < y.data_testing.length; i++) {
            tbl_testing.addRow(
                    new Object[]{
                        y.data_testing[i][0],
                        y.data_testing[i][1],
                        y.data_testing[i][2],
                        y.data_testing[i][3],
                        y.data_testing[i][4],
                        y.data_testing[i][5],
                        y.data_testing[i][6],
                        y.data_testing[i][7],
                        y.data_testing[i][8],
                        y.data_testing[i][9],
                        y.data_testing[i][10],
                        y.data_testing[i][11],
                        y.data_testing[i][12],
                        y.data_testing[i][13],
                        y.data_testing[i][14],
                        y.data_testing[i][15],
                        y.data_testing[i][16],
                        y.data_testing[i][17]
                    });
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void KernelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KernelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KernelActionPerformed

    private void jlamdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jlamdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jlamdaActionPerformed

    private void jIterasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIterasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jIterasiActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        main x = new main(104, 18);
//        GUI a = new GUI(72, 18);
        GUI b = new GUI(18, 18);
        x.gamma = Double.parseDouble(jgamma.getText());
        System.out.println(" Gamma " + jgamma);
        x.lamda = Double.parseDouble(jlamda.getText());
        x.C = Double.parseDouble(jC.getText());
        x.max = Integer.parseInt(jIterasi.getText());
        x.epsilon = Double.parseDouble(jepsilon.getText());
        x.D = Double.parseDouble(jD.getText());
        String nm_kernel = null;
        //menentukan kernel yg dipilih
        if (Kernel.getSelectedItem()== "KERNEL RBF"){
            nm_kernel = "rbf";
        } else if (Kernel.getSelectedItem() == "KERNEL POLYNOMIAL DEGREE"){
            nm_kernel = "polinomial"; 
        } else if (Kernel.getSelectedItem() == "KERNEL POLYNOMIAL DEGREE 2"){
            nm_kernel = "degree2";
        } else if (Kernel.getSelectedItem() == "KERNEL LINEAR"){
            nm_kernel = "linier";
        }
        
        System.out.println("LEVEL 1");
        

        int n = 104; //banyak data
        double alfa_i[] = new double[n];
        // double alpha[] = new double[n];
        //   boolean cek = false;
        double aai[] = new double[n];//nilai alfa awal (0)
        int banyak = 0;//untuk menampilkan iterasi ke berapa, dan biar tau iterasi berapa aja yg sudah dilalui

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
            x.baca("E:\\Latih2(80_20).xls");
            alfa_i = x.alfa_i(alfa_i, nm_kernel);
            banyak++;
            if (banyak == x.max) {
                bias = x.Kernelnegatifpositif(alfa_i, 24, 80);
            }
        } while (banyak != x.max);

        System.out.println("");

//        a.KernelRBF();
        System.out.println("Kernel RBF");

        DefaultTableModel tbl_rbf = (DefaultTableModel) (jTable4.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_rbf.addRow(
                    new Object[]{
                        x.kernel[i][0],
                        x.kernel[i][1],
                        x.kernel[i][2],
                        x.kernel[i][3],
                        x.kernel[i][4],
                        x.kernel[i][5],
                        x.kernel[i][6],
                        x.kernel[i][7],
                        x.kernel[i][8],
                        x.kernel[i][9],
                        x.kernel[i][10],
                        x.kernel[i][11],
                        x.kernel[i][12],
                        x.kernel[i][13],
                        x.kernel[i][14],
                        x.kernel[i][15],
                        x.kernel[i][16],
                        x.kernel[i][17],
                        x.kernel[i][18],
                        x.kernel[i][19]
                    });
        }
//        a.Matrixhessian();
        DefaultTableModel tbl_mh = (DefaultTableModel) (jTable5.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_mh.addRow(
                    new Object[]{
                        x.matrixhessian[i][0],
                        x.matrixhessian[i][1],
                        x.matrixhessian[i][2],
                        x.matrixhessian[i][3],
                        x.matrixhessian[i][4],
                        x.matrixhessian[i][5],
                        x.matrixhessian[i][6],
                        x.matrixhessian[i][7],
                        x.matrixhessian[i][8],
                        x.matrixhessian[i][9],
                        x.matrixhessian[i][10],
                        x.matrixhessian[i][11],
                        x.matrixhessian[i][12],
                        x.matrixhessian[i][13],
                        x.matrixhessian[i][14],
                        x.matrixhessian[i][15],
                        x.matrixhessian[i][16],
                        x.matrixhessian[i][17],
                        x.matrixhessian[i][18],
                        x.matrixhessian[i][19]
                    });
        }

//        a.alfa_i(a.alfa_i);
//        a.delta_i(a.alfa_i);
//        a.Ei(a.alfa_i);
        DefaultTableModel tbl_ei = (DefaultTableModel) (jTable16.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_ei.addRow(
                    new Object[]{
                        x.Ei[i]
                    });
        }
        DefaultTableModel tbl_deltai = (DefaultTableModel) (jTable24.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_deltai.addRow(
                    new Object[]{
                        x.delta_i[i]
                    });
        }
        DefaultTableModel tbl_alphabefore = (DefaultTableModel) (jTable17.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_alphabefore.addRow(
                    new Object[]{
                        x.alfa_i[i]
                    });
        }
        DefaultTableModel tbl_alphaupdate = (DefaultTableModel) (jTable6.getModel());
        for (int i = 0; i < x.data_training.length; i++) {
            tbl_alphaupdate.addRow(
                    new Object[]{
                        x.alfa_i[i]
                    });
        }
//        a.Kernelnegatifpositif(alfa_i, 18, 54);
        jTextField3.setText(Double.toString(x.urutan));
        jTextField2.setText(Double.toString(x.urutanNegatif));
        jTextField4.setText(Double.toString(x.sum));
        jTextField5.setText(Double.toString(x.sum1));
        jTextField6.setText(Double.toString(x.bias));

        //  GUI c = new GUI(54, 18);
        main x2 = new main(80, 18);
//        GUI a = new GUI(72, 18);
        // GUI b = new GUI(50, 18);
        x2.gamma = Double.parseDouble(jgamma.getText());
        System.out.println(" Gamma " + jgamma);
        x2.lamda = Double.parseDouble(jlamda.getText());
        x2.C = Double.parseDouble(jC.getText());
        x2.max = Integer.parseInt(jIterasi.getText());
        x2.epsilon = Double.parseDouble(jepsilon.getText());
        x2.D = Double.parseDouble(jD.getText());

        System.out.println("LEVEL 2");
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
            x2.baca("E:\\Latih2(80_20)level2.xls");
            alfa_2 = x2.alfa_i(alfa_2, nm_kernel);
            banyak++;
            if (banyak == x.max) {
                bias2 = x2.Kernelnegatifpositif(alfa_2, 29, 51);
            }
        } while (banyak != x.max);

        System.out.println("");

//        c.KernelRBF();
        System.out.println("Kernel RBF");
        for (int i = 0; i < x2.data_training.length; i++) {
            for (int j = 0; j < x2.data_training.length; j++) {
                System.out.println(x2.kernel[i][j]);
            }
        }
        DefaultTableModel tbl_rbf2 = (DefaultTableModel) (jTable7.getModel());
        for (int i = 0; i < x2.data_training.length; i++) {
            tbl_rbf2.addRow(
                    new Object[]{
                        x2.kernel[i][0],
                        x2.kernel[i][1],
                        x2.kernel[i][2],
                        x2.kernel[i][3],
                        x2.kernel[i][4],
                        x2.kernel[i][5],
                        x2.kernel[i][6],
                        x2.kernel[i][7],
                        x2.kernel[i][8],
                        x2.kernel[i][9],
                        x2.kernel[i][10],
                        x2.kernel[i][11],
                        x2.kernel[i][12],
                        x2.kernel[i][13],
                        x2.kernel[i][14],
                        x2.kernel[i][15],
                        x2.kernel[i][16],
                        x2.kernel[i][17],
                        x2.kernel[i][18],
                        x2.kernel[i][19]
                    });
        }
//        c.Matrixhessian();
        DefaultTableModel tbl_mh2 = (DefaultTableModel) (jTable8.getModel());
        for (int i = 0; i < x2.data_training.length; i++) {
            tbl_mh2.addRow(
                    new Object[]{
                        x2.matrixhessian[i][0],
                        x2.matrixhessian[i][1],
                        x2.matrixhessian[i][2],
                        x2.matrixhessian[i][3],
                        x2.matrixhessian[i][4],
                        x2.matrixhessian[i][5],
                        x2.matrixhessian[i][6],
                        x2.matrixhessian[i][7],
                        x2.matrixhessian[i][8],
                        x2.matrixhessian[i][9],
                        x2.matrixhessian[i][10],
                        x2.matrixhessian[i][11],
                        x2.matrixhessian[i][12],
                        x2.matrixhessian[i][13],
                        x2.matrixhessian[i][14],
                        x2.matrixhessian[i][15],
                        x2.matrixhessian[i][16],
                        x2.matrixhessian[i][17],
                        x2.matrixhessian[i][18],
                        x2.matrixhessian[i][19]
                    });
        }
//        c.Ei(alfa_i);
//        c.delta_i(alfa_i);
//        c.alfa_i(alfa_i);

        DefaultTableModel tbl_ei2 = (DefaultTableModel) (jTable15.getModel());
        for (int i = 0; i < x2.data_training.length; i++) {
            tbl_ei2.addRow(
                    new Object[]{
                        x2.Ei[i]
                    });
        }
        DefaultTableModel tbl_deltai2 = (DefaultTableModel) (jTable9.getModel());
        for (int i = 0; i < x2.data_training.length; i++) {
            tbl_deltai2.addRow(
                    new Object[]{
                        x2.delta_i[i]
                    });
        }
        DefaultTableModel tbl_alphabefore2 = (DefaultTableModel) (jTable18.getModel());
        for (int i = 0; i < x2.data_training.length; i++) {
            tbl_alphabefore2.addRow(
                    new Object[]{
                        x2.alfa_i[i]
                    });
        }
        DefaultTableModel tbl_alphaupdate2 = (DefaultTableModel) (jTable25.getModel());
        for (int i = 0; i < x2.data_training.length; i++) {
            tbl_alphaupdate2.addRow(
                    new Object[]{
                        x2.alfa_i[i]
                    });
        }
//        c.Kernelnegatifpositif(alfa_i, 17, 37);
        jTextField9.setText(Double.toString(x2.urutan));
        jTextField8.setText(Double.toString(x2.urutanNegatif));
        jTextField10.setText(Double.toString(x2.sum));
        jTextField11.setText(Double.toString(x2.sum1));
        jTextField12.setText(Double.toString(x2.bias));

        GUI d = new GUI(51, 18);
        main x3 = new main(51, 18);
        x3.gamma = Double.parseDouble(jgamma.getText());
        System.out.println(" Gamma " + jgamma);
        x3.lamda = Double.parseDouble(jlamda.getText());
        x3.C = Double.parseDouble(jC.getText());
        x3.max = Integer.parseInt(jIterasi.getText());
        x3.epsilon = Double.parseDouble(jepsilon.getText());
        x3.D = Double.parseDouble(jD.getText());

        System.out.println("LEVEL 3");
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
            x3.baca("E:\\Latih2(80_20)level3.xls");
            alfa_3 = x3.alfa_i(alfa_3, nm_kernel);
            banyak++;
            if (banyak == x.max) {
                bias3 = x3.Kernelnegatifpositif(alfa_3, 21, 30);
            }
        } while (banyak != x.max);

        System.out.println("");

//        d.KernelRBF();
        System.out.println("Kernel RBF");

        DefaultTableModel tbl_rbf3 = (DefaultTableModel) (jTable19.getModel());
        for (int i = 0; i < x3.data_training.length; i++) {
            tbl_rbf3.addRow(
                    new Object[]{
                        x3.kernel[i][0],
                        x3.kernel[i][1],
                        x3.kernel[i][2],
                        x3.kernel[i][3],
                        x3.kernel[i][4],
                        x3.kernel[i][5],
                        x3.kernel[i][6],
                        x3.kernel[i][7],
                        x3.kernel[i][8],
                        x3.kernel[i][9],
                        x3.kernel[i][10],
                        x3.kernel[i][11],
                        x3.kernel[i][12],
                        x3.kernel[i][13],
                        x3.kernel[i][14],
                        x3.kernel[i][15],
                        x3.kernel[i][16],
                        x3.kernel[i][17],
                        x3.kernel[i][18],
                        x3.kernel[i][19]
                    });
        }
//        d.Matrixhessian();
        DefaultTableModel tbl_mh3 = (DefaultTableModel) (jTable20.getModel());
        for (int i = 0; i < x3.data_training.length; i++) {
            tbl_mh3.addRow(
                    new Object[]{
                        x3.matrixhessian[i][0],
                        x3.matrixhessian[i][1],
                        x3.matrixhessian[i][2],
                        x3.matrixhessian[i][3],
                        x3.matrixhessian[i][4],
                        x3.matrixhessian[i][5],
                        x3.matrixhessian[i][6],
                        x3.matrixhessian[i][7],
                        x3.matrixhessian[i][8],
                        x3.matrixhessian[i][9],
                        x3.matrixhessian[i][10],
                        x3.matrixhessian[i][11],
                        x3.matrixhessian[i][12],
                        x3.matrixhessian[i][13],
                        x3.matrixhessian[i][14],
                        x3.matrixhessian[i][15],
                        x3.matrixhessian[i][16],
                        x3.matrixhessian[i][17],
                        x3.matrixhessian[i][18],
                        x3.matrixhessian[i][19]
                    });
        }
//        d.Ei(alfa_i);
//        d.delta_i(alfa_i);
//        d.alfa_i(alfa_i);

        DefaultTableModel tbl_ei3 = (DefaultTableModel) (jTable26.getModel());
        for (int i = 0; i < x3.data_training.length; i++) {
            tbl_ei3.addRow(
                    new Object[]{
                        x3.Ei[i]
                    });
        }
        DefaultTableModel tbl_deltai3 = (DefaultTableModel) (jTable21.getModel());
        for (int i = 0; i < x3.data_training.length; i++) {
            tbl_deltai3.addRow(
                    new Object[]{
                        x3.delta_i[i]
                    });
        }
        DefaultTableModel tbl_alphabefore3 = (DefaultTableModel) (jTable23.getModel());
        for (int i = 0; i < x3.data_training.length; i++) {
            tbl_alphabefore3.addRow(
                    new Object[]{
                        x3.alfa_i[i]
                    });
        }
        DefaultTableModel tbl_alphaupdate3 = (DefaultTableModel) (jTable22.getModel());
        for (int i = 0; i < x3.data_training.length; i++) {
            tbl_alphaupdate3.addRow(
                    new Object[]{
                        x3.alfa_i[i]
                    });
        }
//        d.Kernelnegatifpositif(alfa_i, 18, 19);
        jTextField14.setText(Double.toString(x3.urutan));
        jTextField15.setText(Double.toString(x3.urutanNegatif));
        jTextField16.setText(Double.toString(x3.sum));
        jTextField17.setText(Double.toString(x3.sum1));
        jTextField18.setText(Double.toString(x3.bias));
        
        System.out.println("TESTING");
        Testing hasil = new Testing(bias, bias2, bias3, alfa_i, alfa_2, alfa_3);
        hasil.Test();
        System.out.println(Math.signum(0.9999));
        
        DefaultTableModel tbl_testing = (DefaultTableModel) (jTable1.getModel());
        for (int i = 0; i < hasil.data_testing.length; i++) {
            tbl_testing.addRow(
                    new Object[]{
                        i + 1,
                        hasil.hasilfx1,
                        hasil.hasilfx2,
                        hasil.hasilfx3,
                        hasil.hasilUji[i][0],
                        hasil.data_testing[i][17]
                    });
        }
        jTextField19.setText(Double.toString(hasil.akurasi));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
//        System.out.println("LEVEL 1");
//        main x = new main(72, 18);
//        double epsilon = 0.001;
//        int n = 72; //banyak data
//        int max = 2; // banyaknya iterasi
//        double alfa_i[] = new double[n];
//        // double alpha[] = new double[n];
//        //   boolean cek = false;
//        double aai[] = new double[n];//nilai alfa awal (0)
//        int banyak = 0;//untuk menampilkan iterasi ke berapa, dan biar tau iterasi berapa aja yg sudah dilalui
//
//        double bias = 0;
//        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
//            System.out.println("");
//            System.out.println("iterasi ke-" + (banyak + 1));
////            new main().baca();// buat baca file
//            System.out.println("------------");
//            /*aai = new main().alfa_i(aai);//
//             System.out.println("TEST AAI MAIN:");
//             for (int i = 0; i < aai.length; i++) {
//             System.out.println("nilai aai -"+i+"- : "+aai[i]);
//             }*/
//            x.baca("F:\\datatraining.xls");
//            alfa_i = x.alfa_i(alfa_i);
//            banyak++;
//            if (banyak == max) {
//                bias = x.Kernelnegatifpositif(alfa_i, 18, 54);
//            }
//        } while (banyak != max);
////        
////        // proses Level 2
////        SVM_Level2 level2 = new SVM_Level2();
////        double alfa_2[] = new double[n];
////        banyak =0;
////        double aai_2[] = new double[n];//nilai alfa awal (0)
////        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
////            System.out.println("");
////            System.out.println("iterasi ke-" + (banyak + 1));
//////            new main().baca();// buat baca file
////            System.out.println("------------");
////            aai_2 = level2.alfa_i(aai_2);//
////            alfa_i = level2.alfa_i(alfa_i);
////            banyak++;
////            if (banyak == max){
////                bias = level2.Kernelnegatifpositif(alfa_i);
////            }
////        } while (banyak != max);
////    }
//        System.out.println("");
//        //level 2
//
//        System.out.println("LEVEL 2");
//        main x2 = new main(54, 18);
//        double alfa_2[] = new double[n];
//        double bias2 = 0;
//        banyak = 0;
//        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
//            System.out.println("");
//            System.out.println("iterasi ke-" + (banyak + 1));
////            new main().baca();// buat baca file
//            System.out.println("------------");
//            /*aai = new main().alfa_i(aai);//
//             System.out.println("TEST AAI MAIN:");
//             for (int i = 0; i < aai.length; i++) {
//             System.out.println("nilai aai -"+i+"- : "+aai[i]);
//             }*/
//            x2.baca("F:\\datatraininglevel2.xls");
//            alfa_2 = x2.alfa_i(alfa_2);
//            banyak++;
//            if (banyak == max) {
//                bias2 = x2.Kernelnegatifpositif(alfa_2, 17, 37);
//            }
//        } while (banyak != max);
//
//        System.out.println("");
//        //level 2
//
//        System.out.println("LEVEL 3");
//        main x3 = new main(37, 18);
//        double alfa_3[] = new double[n];
//        double bias3 = 0;
//        banyak = 0;
//        do {//perulangan dijalankan dulu baru dicek apakah sudah sebanyak iter max
//            System.out.println("");
//            System.out.println("iterasi ke-" + (banyak + 1));
////            new main().baca();// buat baca file
//            System.out.println("------------");
//            /*aai = new main().alfa_i(aai);//
//             System.out.println("TEST AAI MAIN:");
//             for (int i = 0; i < aai.length; i++) {
//             System.out.println("nilai aai -"+i+"- : "+aai[i]);
//             }*/
//            x3.baca("F:\\datatraininglevel3.xls");
//            alfa_3 = x3.alfa_i(alfa_3);
//            banyak++;
//            if (banyak == max) {
//                bias3 = x3.Kernelnegatifpositif(alfa_3, 18, 19);
//            }
//        } while (banyak != max);
//        System.out.println("TESTING");
//        Testing hasil = new Testing(bias, bias2, bias3, alfa_i, alfa_2, alfa_3);
//        hasil.Test();
//        System.out.println(Math.signum(0.9999));

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Kernel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JTextField jC;
    private javax.swing.JTextField jD;
    private javax.swing.JTextField jIterasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable11;
    private javax.swing.JTable jTable15;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable19;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable20;
    private javax.swing.JTable jTable21;
    private javax.swing.JTable jTable22;
    private javax.swing.JTable jTable23;
    private javax.swing.JTable jTable24;
    private javax.swing.JTable jTable25;
    private javax.swing.JTable jTable26;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jepsilon;
    private javax.swing.JTextField jgamma;
    private javax.swing.JTextField jlamda;
    // End of variables declaration//GEN-END:variables
}
