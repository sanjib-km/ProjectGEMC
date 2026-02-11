package in.iasst.projectGEMC.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.PaymentDetails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    public ByteArrayInputStream exportToExcel(List<Contract> data) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Project GEMC Data");

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"GEMC No", "Contract Date", "Buyer Name", "Seller Name", "Account Details", "Amount", "Quantity", "Item Details",
            		"Indenter", "Source of Fund", "Project Name", "eFile No", "Pur. Request Putup By", "Order Status", "Consignee Name",
            		"Delivery Date", "Good Acceptance Status", "CRAC Gen. Date", "Reciept Qty.", "Accepted Reciept(s)", "Rejected Reciept(s)", "Invoice No.",
            		"Invoice Date", "Bill Amount", "Payment Date", "Payment Info"};
            
			for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                // Bold headers
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Fill Data Rows
            int rowIdx = 1;
            for (Contract c : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(c.getGemNo());
                row.createCell(1).setCellValue(c.getContractDate().toString());
                row.createCell(2).setCellValue(c.getBuyerName().getName());
                row.createCell(3).setCellValue(c.getSellerName());
                row.createCell(4).setCellValue(c.getAccountDetails());
                row.createCell(5).setCellValue(c.getAmount());
                row.createCell(6).setCellValue(c.getQuantity());
                row.createCell(7).setCellValue(c.getItemDetails());
                row.createCell(8).setCellValue(c.getIndenter());
                row.createCell(9).setCellValue(c.getSourceOfFund());
                row.createCell(10).setCellValue(c.getProjectName());
                row.createCell(11).setCellValue(c.geteFileNo());
                row.createCell(12).setCellValue(c.getPur_req_putupBy());
                row.createCell(13).setCellValue(c.getOrderStatus());
                row.createCell(14).setCellValue(c.getConsigneeName().getName());
                row.createCell(15).setCellValue(c.getConsigneeDetails().getDeliveryDate().toString());
                row.createCell(16).setCellValue(c.getConsigneeDetails().isGoodAcceptanceStatus());
                row.createCell(17).setCellValue(c.getConsigneeDetails().getCracGenDate().toString());
                row.createCell(18).setCellValue(c.getConsigneeDetails().getRecieptQuantity());
                row.createCell(19).setCellValue(c.getConsigneeDetails().getAceptedQuantity());
                row.createCell(20).setCellValue(c.getConsigneeDetails().getRejectedQuantity());
                row.createCell(21).setCellValue(c.getConsigneeDetails().getInvoiceNo());
                row.createCell(22).setCellValue(c.getConsigneeDetails().getInvoiceDate().toString());
                row.createCell(23).setCellValue(c.getConsigneeDetails().getBillAmount());
                int cellIdx=24;
                for(PaymentDetails p: c.getPaymentDetails()) {
                	row.createCell(cellIdx++).setCellValue(p.getPaymentDate().toString());
                	row.createCell(cellIdx++).setCellValue(p.getPaymentInfo());
                }
                
                
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}