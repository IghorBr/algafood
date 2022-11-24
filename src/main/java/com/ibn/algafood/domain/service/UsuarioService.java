package com.ibn.algafood.domain.service;

import com.ibn.algafood.domain.exception.AlgafoodException;
import com.ibn.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.ibn.algafood.domain.model.Usuario;
import com.ibn.algafood.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void changePassword(String senhaAtual, String novaSenha, Long usuarioId) {
        Usuario usuario = this.findById(usuarioId);

        if (!usuario.validaSenha(senhaAtual))
            throw new AlgafoodException("Senha atual informada não coincide com a senha do usuário");

        usuario.setSenha(novaSenha);
    }
}
