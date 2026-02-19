package in.iasst.projectGEMC.controllers;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.repositories.ContractRepository;
import in.iasst.projectGEMC.services.ExcelExportService;
import in.iasst.projectGEMC.services.UserServices;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/export")
public class ContractExportController {

    @Autowired
    private ContractRepository repo;

    @Autowired
    private ExcelExportService excelService;
    
	@Autowired
	UserServices user_services;
    
    @GetMapping("/")
	public String showExportPage(HttpSession session, RedirectAttributes ra) {
    	String username = (String)session.getAttribute("userName");
    	if(username != null && user_services.isAdmin(username)) {
    		
    		return "admin/export-page";
    	}
    	ra.addFlashAttribute("message", "Unauthorised!");
    	return "redirect:/logout";
	}

    
    @GetMapping("/excel")
    public ResponseEntity<?> downloadExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpSession session) throws Exception {
    	
    	String username = (String)session.getAttribute("userName");
    	
    	if(username != null && user_services.isAdmin(username)) {
    		
    		List<Contract> data;

            // Provision logic
            if (startDate != null && endDate != null) {
                data = repo.findBycontractDateBetween(startDate, endDate);
            } else {
                data = repo.findAll();
            }

            ByteArrayInputStream in = excelService.exportToExcel(data);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=GEMC_Report.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(in));
        }
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: You are not authorised to export data.");
    }
        
}