package com.ibn.algafood.api.mapper;

import com.ibn.algafood.api.model.in.UsuarioInputDTO;
import com.ibn.algafood.api.model.out.UsuarioOutDTO;
import com.ibn.algafood.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final ModelMapper mapper;

    public UsuarioOutDTO domainToDto(Usuario usuario) {
        return mapper.map(usuario, UsuarioOutDTO.class);
    }

    public List<UsuarioOutDTO> listDomainToDto(List<Usuario> usuarios) {
        return usuarios.stream().map(u -> this.domainToDto(u)).toList();
    }

    public Usuario inputDtoToDomain(UsuarioInputDTO inputDTO) {
        return mapper.map(inputDTO, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInputDTO dto, Usuario usuario) {
        mapper.map(dto, usuario);
    }
}
