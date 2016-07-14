package nbBase.service.common;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import nbBase.helper.common.nbReturn;


public interface OfficeService {
	
	public HSSFWorkbook loadWorkbook(String fileName);
	public HSSFWorkbook  loadWorkbook(FileInputStream fis);
	
	public HSSFSheet loadSheet(HSSFWorkbook workbook, int index);
	public HSSFSheet loadSheet(HSSFWorkbook workbook, String sheetName);
	
	public List<List<String>> getAllLines(HSSFSheet sheet);
	public nbReturn writeDataToFile(List<List<String>> dataMatrix, String filePath);

}
