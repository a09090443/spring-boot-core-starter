package com.zipe.exception;

import com.zipe.common.model.ErrorLog;
import com.zipe.common.payload.ValidationErrorResponse;
import com.zipe.common.service.ErrorLogService;
import com.zipe.util.StringConstant;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorLogService errorLogServiceImpl;

    private final Environment env;

    @Autowired
    public GlobalExceptionHandler(ErrorLogService errorLogServiceImpl, Environment env){
        this.errorLogServiceImpl = errorLogServiceImpl;
        this.env = env;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorLog> handleException(HttpServletRequest request, Exception e){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(this.handleErrorMessage(request.getServerName(), e));
    }

    @ExceptionHandler(SchedulerException.class)
    @ResponseBody
    public ResponseEntity<ErrorLog> handleSchedulerExceptionException(HttpServletRequest request, SchedulerException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(this.handleErrorMessage(request.getServerName(), e));
    }

    @ExceptionHandler(SQLServerException.class)
    @ResponseBody
    public ResponseEntity<ErrorLog> handleClassNotFoundException(HttpServletRequest request, SQLServerException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(this.handleErrorMessage(request.getServerName(), e));
    }

    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorLog> handleClassNotFoundException(HttpServletRequest request, ClassNotFoundException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(this.handleErrorMessage(request.getServerName(), e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public List<ValidationErrorResponse> handleFiledException(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse> validationErrorResponseList = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrorResponseList
                    .add(new ValidationErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return validationErrorResponseList;
    }

    private ErrorLog handleErrorMessage(String serverName, Exception e) {
        log.error(e.toString(),e);
        ErrorLog errorLog = new ErrorLog();
        errorLog.setServerIp(serverName);
        if(null != e.getCause()){
            errorLog.setMessage(e.getCause().getMessage() + "," + e.getMessage());
        }else{
            errorLog.setMessage(e.getMessage());
        }
        errorLog.setTime(new Date());
        // 當紀錄 Error log 打開時才會紀錄到 db 中
        if(StringConstant.TRUE.equalsIgnoreCase(env.getProperty("error.log.record.to.db"))){
            errorLogServiceImpl.saveOrUpdate(errorLog);
        }
        return errorLog;
    }
}
