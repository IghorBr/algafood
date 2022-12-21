package com.ibn.algafood.core.validation.validator;

import com.ibn.algafood.core.validation.FileContentType;
import com.ibn.algafood.core.validation.FileSize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedValues;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedValues = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (this.allowedValues.isEmpty())
            return true;

        for (String allow : allowedValues) {
            if (value.getContentType().equals(allow))
                return true;
        }

        return false;
    }
}
