package com.parshva.docker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
//@RequestMapping("/demo")
public class Controller {

	public static HashMap<String, ArrayList<PurchaseOrder>> supOrdersMap;
	public static ArrayList<PurchaseOrder> purOrders;
	
	@Autowired
	private DocketRepo docketRepo;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/")
	public String home(){
		getDocketList();
	        return "Hello World!";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/getPo")  
	public String[] getPo(@RequestBody String supplier){
//		HashMap<String, ArrayList<String>> pos = new HashMap<String, ArrayList<String>>();
		ArrayList<String> poNums = new ArrayList<String>();
		ArrayList<PurchaseOrder> purordes = new ArrayList<PurchaseOrder>();
		for (PurchaseOrder iterable_element : Controller.supOrdersMap.get(supplier)) {
			poNums.add(iterable_element.getpONum());
//			poNums.add(iterable_element.getpODesc());
			purordes.add(iterable_element);
		}
		Controller.purOrders = purordes;
//		pos.put(supplier, poNums);
		return poNums.toArray(new String[poNums.size()]);
	}


	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/getPoDesc")  
	public String getPoDesc(@RequestBody String poNumber){
//		HashMap<String, ArrayList<String>> pos = new HashMap<String, ArrayList<String>>();
//		ArrayList<String> poNums = new ArrayList<String>();
		String poDesc ="";
		for (PurchaseOrder iterable_element : Controller.purOrders) {
			if(iterable_element.getpONum().equalsIgnoreCase(poNumber)) {
				poDesc=  iterable_element.getpODesc();
//				break;
			}
		}
		return poDesc; 
	}

	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getSupplierDetails")
	public String[] getSupplierDetails() throws IOException {
//		ArrayList<String> supplierArr = new ArrayList<>();
//		XSSFWorkbook workBook = new XSSFWorkbook("C:\\Users\\prash\\Downloads\\export29913.xlsx"); 
//		XSSFSheet sheet = workBook.getSheetAt(0); 
//		int count =0;
//		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//			  XSSFRow row = sheet.getRow(rowIndex);
//			  if (row != null) {
//			    int colIndex = 11;
//				Cell cell = row.getCell(colIndex);
//			    if (cell != null) {
//			      // Found column and there is value in the cell.
//			      String cellValueMaybeNull = cell.getStringCellValue();
//			      if(cellValueMaybeNull.length()>0) {
//			    	  supplierArr.add(cellValueMaybeNull);
//			      }
//			 
//			    }
//			  }
//			}
//		String supplierArray[] = supplierArr.toArray(new String[supplierArr.size()]);
//		return supplierArray;
		
		

		
		HashMap<String, ArrayList<PurchaseOrder>> supOrdersMap = new HashMap<String, ArrayList<PurchaseOrder>>();
		
		ArrayList<String> supplierArr = new ArrayList<>();
		XSSFWorkbook workBook = new XSSFWorkbook("C:\\Users\\prash\\Downloads\\export29913.xlsx"); 
		XSSFSheet sheet = workBook.getSheetAt(0); 
		int count =0;
		String tempSupplierName = "";
		ArrayList<PurchaseOrder> po = new ArrayList<PurchaseOrder>();
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			  XSSFRow row = sheet.getRow(rowIndex);
			  if (row != null) {
			    int colIndex = 11;
				Cell cell = row.getCell(colIndex);
			    if (cell != null) {
			      // Found column and there is value in the cell.
			      String cellValueMaybeNull = cell.getStringCellValue();
			      
			      String poNumber = row.getCell(1).getStringCellValue();
			      String poDesc = row.getCell(15).getStringCellValue();
			    
			      if(cellValueMaybeNull.length()>0) {
			    	  // herer supplier is changeing 
			    	  if(po.size()>0) {
			    		  supOrdersMap.put(tempSupplierName, po);
			    		  po = new ArrayList<PurchaseOrder>();
			    	  }
			    	  tempSupplierName = cellValueMaybeNull;
			    	  supplierArr.add(cellValueMaybeNull);
			    	  PurchaseOrder order = new PurchaseOrder();
			    	  order.setpONum(poNumber);
			    	  order.setpODesc(poDesc);
			    	  po.add(order);
//			    	  System.out.println("key "+tempSupplierName +" :  po "+ order.toString() );
			    	  
			      }else {
			    	  // same suppler from temp
			    	  PurchaseOrder order = new PurchaseOrder();
			    	  order.setpONum(poNumber);
			    	  order.setpODesc(poDesc);
			    	  po.add(order);
//			    	  System.out.println("key "+tempSupplierName +" :  po "+ order.toString() );
			      }
			      			      
			    }
			  }
		}
		Controller.supOrdersMap = supOrdersMap;
		
		String supplierArray[] = supplierArr.toArray(new String[supplierArr.size()]);
		return supplierArray;		
	
	}	

	
	
	public List<DocketTable> getDocketList(){
		System.err.println("entered getDocketList");
		List<DocketTable> sd = (List<DocketTable>) docketRepo.findAll();
		for (DocketTable docketTable : sd) {
			System.out.println(docketTable.getName());
		}
		System.out.println("exisitng getDocketList");
		return (List<DocketTable>)
				docketRepo.findAll();
	}
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/saveDoc")  
	public DocketTable saveDetails(DocketTable docketTable) {
		
		DocketTable savedDocket = docketRepo.save(docketTable);
		String name = savedDocket.getName();

		return savedDocket;
		
	}
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		HashMap<String, ArrayList<PurchaseOrder>> supOrdersMap = new HashMap<String, ArrayList<PurchaseOrder>>();
		
		ArrayList<String> supplierArr = new ArrayList<>();
		XSSFWorkbook workBook = new XSSFWorkbook("C:\\Users\\prash\\Downloads\\export29913.xlsx"); 
		XSSFSheet sheet = workBook.getSheetAt(0); 
		int count =0;
		String tempSupplierName = "";
		ArrayList<PurchaseOrder> po = new ArrayList<PurchaseOrder>();
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			  XSSFRow row = sheet.getRow(rowIndex);
			  if (row != null) {
			    int colIndex = 11;
				Cell cell = row.getCell(colIndex);
			    if (cell != null) {
			      // Found column and there is value in the cell.
			      String cellValueMaybeNull = cell.getStringCellValue();
			      
			      String poNumber = row.getCell(1).getStringCellValue();
			      String poDesc = row.getCell(15).getStringCellValue();
			    
			      if(cellValueMaybeNull.length()>0) {
			    	  // herer supplier is changeing 
			    	  if(po.size()>0) {
			    		  supOrdersMap.put(tempSupplierName, po);
			    		  po = new ArrayList<PurchaseOrder>();
			    	  }
			    	  tempSupplierName = cellValueMaybeNull;
			    	  supplierArr.add(cellValueMaybeNull);
			    	  PurchaseOrder order = new PurchaseOrder();
			    	  order.setpONum(poNumber);
			    	  order.setpODesc(poDesc);
			    	  po.add(order);
			    	  System.out.println("key "+tempSupplierName +" :  po "+ order.toString() );
			    	  
			      }else {
			    	  // same suppler from temp
			    	  PurchaseOrder order = new PurchaseOrder();
			    	  order.setpONum(poNumber);
			    	  order.setpODesc(poDesc);
			    	  po.add(order);
			    	  System.out.println("key "+tempSupplierName +" :  po "+ order.toString() );
			      }
			      			      
			    }
			  }
		}
		
//		for (PurchaseOrder purchaseOrder : supOrdersMap.get("Bunnings - QLD")) {
//			System.out.println(purchaseOrder.toString());
//		}
		String supplierArray[] = supplierArr.toArray(new String[supplierArr.size()]);
	//	return supplierArray;
	
		
	}
}
