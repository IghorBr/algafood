package com.ibn.algafood.core.validation.validator;

import com.ibn.algafood.core.validation.FileSize;
import com.ibn.algafood.core.validation.Multiplo;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Objects;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize dataSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.dataSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || value.getSize() <= this.dataSize.toBytes();
    }
}
