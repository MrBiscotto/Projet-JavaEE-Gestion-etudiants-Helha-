package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import entities.Etudiant;

public abstract class ExcelReader {
    
    public static List<Etudiant> getEtudiants(File file) throws IOException, InvalidFormatException, EncryptedDocumentException, org.apache.poi.openxml4j.exceptions.InvalidFormatException 
    {
        List<Etudiant> etudiants = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file);
        
        Sheet sheet = workbook.getSheetAt(0);
        
        sheet.forEach(row -> {
            if(row.getRowNum() > 0)
            {
            	etudiants.add(new Etudiant().loadFromRow(row));
            }
        });

        workbook.close();
        
        return etudiants;
    }
}