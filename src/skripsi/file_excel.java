/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi;

import com.sun.rowset.internal.Row;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class file_excel {

    public List<ArrayList<String>> baca(File inpFile) {
        List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            Workbook w1 = Workbook.getWorkbook(inpFile);
            Sheet s1 = w1.getSheet("Sheet1");
            ArrayList<String> dt = null;
            for (int i = 0; i < s1.getRows(); i++) {
                dt = new ArrayList<String>();
                for (int j = 0; j < s1.getColumns(); j++) {
                    dt.add(j, s1.getCell(j, i).getContents());
                }
                data.add(dt);
            }
        } catch (Exception e) {
        }
        return data;
    }
    
//    public void baca(){
//        Cell ex[] = new Cell[18];
//        File baca = new File ("F:\\Data.xls");
//        Workbook workbook = null;
//        try{
//            workbook = Workbook.getWorkbook(baca);
//            Sheet svm_latih = workbook.getSheet(0);
//            System.out.println("DATA SVM LEVEL 1");
//            for (int i = 0; i < 12; i++) {
//                System.out.println("");
//                for (int j = 0; j < 12; j++) {
//                    ex[j] = svm.
                    
}
                
            
            
        
    

    

