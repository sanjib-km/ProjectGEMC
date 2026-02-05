package in.iasst.projectGEMC.exception;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/generic-error"; 
    }

    // 2. Handle Data Integrity / Validation Errors (400)
    // Useful for: Duplicate emails, missing required procurement fields, etc.
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorTitle", "Invalid Request");
        model.addAttribute("errorMessage", "The data provided was invalid: " + ex.getMessage());
        return "error/generic-error";
    }

    // 3. Handle Generic System Errors (500)
    // The "Catch-All" for unexpected crashes
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGlobalError(Exception ex, Model model) {
        model.addAttribute("errorTitle", "System Error");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please contact the administrator.");
        // Log the actual error for debugging
        System.err.println("Unexpected error: " + ex.getMessage());
        return "error/generic-error";
    }
}
