package com.ibn.algafood.api.model.in;

import com.ibn.algafood.core.validation.FileContentType;
import com.ibn.algafood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class FotoProdutoInputDTO {

    @NotNull
    @FileSize(max = "2MB")
    @FileContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
