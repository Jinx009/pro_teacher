package nbBase.service.common;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import nbBase.helper.common.CommonHelper;
import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;



@Service("officeService")
public class OfficeServiceImpl implements OfficeService {

	@Override
	public HSSFWorkbook loadWorkbook(String fileName) {
		try {
			return this.loadWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public HSSFWorkbook loadWorkbook(FileInputStream fis) {
		HSSFWorkbook book = null;
		try {
			book = new HSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public HSSFSheet loadSheet(HSSFWorkbook workbook, int index) {
		if( workbook == null )
			return null;
		
		return workbook.getSheetAt(index);
	}

	@Override
	public HSSFSheet loadSheet(HSSFWorkbook workbook, String sheetName) {
		if( workbook == null )
			return null;
		
		return workbook.getSheet(sheetName);
	}

	@Override
	public List<List<String>> getAllLines(HSSFSheet sheet) {
		if( sheet == null)
			return null;
		List<List<String>> matrix = new ArrayList<List<String>>();
		
		int startCellIndex = getStartCellIndex(sheet);
		
		Iterator<?> rowIterator = sheet.rowIterator();
		while( rowIterator.hasNext() ){
			HSSFRow row = (HSSFRow)rowIterator.next();
			List<String> erow = new ArrayList<String>();
			int rowEndIndex = row.getLastCellNum();
			for( int i = startCellIndex ; i <= rowEndIndex ; i++){
				HSSFCell cell = row.getCell(i, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
	            erow.add(this.getCellValue(cell));
	        }
	        matrix.add(erow);
		}
		print(matrix);
		return matrix;
	}
	
	private int getStartCellIndex(HSSFSheet sheet) {
		
		int startIndex = 999999;
		Iterator<?> rowIterator = sheet.rowIterator();
		while( rowIterator.hasNext() ){
			HSSFRow row = (HSSFRow)rowIterator.next();
			if( row.getFirstCellNum() < startIndex)
				startIndex = row.getFirstCellNum();
		}
		
		return 0;
	}

	private void print(List<List<String>> matrixData){
		for( List<String> row : matrixData){
			for( String data : row){
				System.out.print("\t"+data);
			}
			System.out.println("\r\n");
		}
	}


	
	private String getCellValue(HSSFCell cell){
        String value = null;
        //简单的查检列类型
        switch(cell.getCellType())
        {
            case HSSFCell.CELL_TYPE_STRING://字符串
                value = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC://数字
                long dd = (long)cell.getNumericCellValue();
                value = dd+"";
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = null;
                break;    
            case HSSFCell.CELL_TYPE_FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return value;
    }

	@Override
	public nbReturn writeDataToFile(List<List<String>> dataMatrix, String filePath) {
		nbReturn nbRet = new nbReturn();
		
		CommonHelper.filePathCheckAndCreate(filePath);
		
		HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet();
        
        int rowIndex = 0 ;
        for(List<String> dataRow : dataMatrix){
        	HSSFRow exRow = sheet.createRow(rowIndex++);
        	
        	int cellIndex = 0;
        	for( String data : dataRow){
        		
        		HSSFCell cell = exRow.createCell(cellIndex++);
        		cell.setCellValue(data);
       
        	}
        	
        }
        
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			book.write(out);
	        out.close();
	        nbRet.setObject(filePath);
	        
		} catch (IOException e) {
			nbRet.setError(ReturnCode.PARAMETER_PHARSE_ERROR);
			e.printStackTrace();
		}
		
		return nbRet;
        
	}
	

}
