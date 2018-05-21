package com.tszh.exception;

import com.tszh.cons.Code;
import com.tszh.vo.ResponseTemplate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuw on 16/11/8.
 *
 * 全局异常处理
 */

@ControllerAdvice
@ResponseBody
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * 操作数据库出现异常:名称重复，外键关联
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.DATA_INTEGRITY_VIOLATION_ERROR.getCode(),ex.getLocalizedMessage());
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 自定义异常处理
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleCustomRequest(final CustomException ex, final WebRequest request) {
        ResponseTemplate responseTemplate = new ResponseTemplate(ex.getCode(), ex.getLocalizedMessage());
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 参数校验错误处理
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        List<String> errors=new ArrayList<>();
        for(ConstraintViolation<?> cv: ex.getConstraintViolations())
        {
            errors.add(cv.getMessage());
        }
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.CONSTRAINT_VIOlATION_ERROR.getCode(),errors);
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 参数校验失败处理
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        ServletWebRequest servletWebRequest=(ServletWebRequest)request;
        try {
            servletWebRequest.getRequest().setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BindingResult result = ex.getBindingResult();
        List<String> errors=new ArrayList<>();
        for (ObjectError error : result.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.METHOD_ARGUMENT_ERROR.getCode(),errors);
        return handleExceptionInternal(ex, responseTemplate, headers, HttpStatus.BAD_REQUEST, request);
    }


    /**
     * 数据绑定异常处理
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors=new ArrayList<>();
        for (ObjectError error : ex.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.DATA_BIND_ERROR.getCode(),errors);
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * 请求参数缺少异常处理
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseTemplate responseTemplate = new ResponseTemplate(Code.MISS_SERVLET_REQUEST_PARAMETER_ERROR.getCode(), ex.getParameterName() + " is required");
        return handleExceptionInternal(ex, responseTemplate, headers, HttpStatus.BAD_REQUEST, request);
    }

  /*  @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(this.getClass() + ex.getMessage());
        System.err.println(ex);
        ErrorResponse bodyOfResponse = new ErrorResponse(124, ex.getRequestPartName() + " is missed");
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        bodyOfResponse.setPath(servletWebRequest.getRequest().getRequestURI());
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);    }

    *//**
     * 文件太大 异常处理
     * @param ex
     * @param request
     * @return
     *//*
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleFileUploadRequest(final MaxUploadSizeExceededException ex, final WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse(124, "上传文件不能超过20M");
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        bodyOfResponse.setPath(servletWebRequest.getRequest().getRequestURI());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
*/

    /**
     * 拒绝访问
     * @param ex
     * @param request
     * @return
     */
    // 403
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final Exception ex, final WebRequest request) {
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.ACCESS_DENIED_ERROR.getCode(),ex.getLocalizedMessage());
        return new ResponseEntity<Object>(responseTemplate, HttpStatus.FORBIDDEN);
    }

    // 404

    /**
     * 没有可访问资源
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.NOT_FOUND_ERROR.getCode(),ex.getLocalizedMessage());
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    /**
     * 访问方法不支持错误处理
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.HTTP_REQUESTMETHOD_NOT_SUPPORTED_ERROR.getCode(),
                ex.getLocalizedMessage());
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

   /* // 409
    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getLocalizedMessage());
        ErrorResponse bodyOfResponse = new ErrorResponse(150, "Data Access Exception");
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        bodyOfResponse.setPath(servletWebRequest.getRequest().getRequestURI());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }*/

    /**
     * MIME类型不支持错误处理
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    //419
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getCode(),ex.getLocalizedMessage());
        return handleExceptionInternal(ex, responseTemplate, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    /**
     * 运行时通用错误处理
     * @param ex
     * @param request
     * @return
     */
    // 412
    // 500
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        ResponseTemplate responseTemplate=new ResponseTemplate(Code.RUNTIME_ERROR.getCode(),ex.getLocalizedMessage());
        return handleExceptionInternal(ex, responseTemplate, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
