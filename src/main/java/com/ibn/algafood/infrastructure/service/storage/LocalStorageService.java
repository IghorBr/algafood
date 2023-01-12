package com.ibn.algafood.infrastructure.service.storage;

import com.ibn.algafood.domain.exception.StorageException;
import com.ibn.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalStorageService implements FotoStorageService {

    @Value("${algafood.storage.local}")
    private Path path;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            return Files.newInputStream(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path path = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path path = getArquivoPath(nomeArquivo);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Nao foi possível excluir o arquivo", e);
        }
    }

    private Path getArquivoPath(String nome) {
        return path.resolve(Path.of(nome));
    }
}
