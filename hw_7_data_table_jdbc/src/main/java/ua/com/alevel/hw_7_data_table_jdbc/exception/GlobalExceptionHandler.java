package ua.com.alevel.hw_7_data_table_jdbc.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ModelAndView entityNotFoundErrorHandler(EntityNotFoundException exception) {
        return generateModelAndView(exception.getMessage());
    }

    @ExceptionHandler(value = {EntityExistException.class})
    public ModelAndView entityExistErrorHandler(EntityExistException exception) {
        return generateModelAndView(exception.getMessage());
    }

    /*@ExceptionHandler(value = {ConstraintViolationException.class})
    public ModelAndView constraintViolationErrorHandler(ConstraintViolationException exception) {
        return generateModelAndView(exception.getMessage());
    }
*/
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ModelAndView methodArgumentNotValidErrorHandler(MethodArgumentNotValidException exception) {
        return generateModelAndView(exception.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ModelAndView methodArgumentNotValidErrorHandler(MethodArgumentTypeMismatchException exception) {
        return generateModelAndView("incorrect value");
    }

    @ExceptionHandler(Exception.class)
    public String serverError(final Exception e, final Model model) {
        System.out.println("GlobalExceptionHandler.serverError");
        String errorMessage = (e != null ? e.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("showMessage", true);
        return "error";
    }

    private ModelAndView generateModelAndView(String msg) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("showMessage", true);
        mav.addObject("errorMessage", msg);
        mav.setViewName("error");
        return mav;
    }
}
