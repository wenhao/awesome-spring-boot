package com.github.wenhao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasedErrorController extends AbstractErrorController {

    @Autowired
    public BasedErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public ResponseEntity error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, false);
        List<FieldError> errors = (List) errorAttributes.get("errors");
        List<String> errorMessages = errors.stream()
                .map(error -> error.getDefaultMessage())
                .collect(toList());
        Map<String, List<String>> result = new HashMap<String, List<String>>() {{
            put("errors", errorMessages);
        }};
        return ResponseEntity.status(getStatus(request)).body(result);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
