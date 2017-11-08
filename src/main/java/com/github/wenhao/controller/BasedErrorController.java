package com.github.wenhao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasedErrorController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    public BasedErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    public ResponseEntity error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        List<FieldError> errors = (List) errorAttributes.get("errors");
        List<String> errorMessages = errors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(toList());
        int status = (int) errorAttributes.get("status");
        Map<String, List<String>> result = new HashMap<String, List<String>>() {{
            put("errors", errorMessages);
        }};
        return ResponseEntity.status(status).body(result);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
