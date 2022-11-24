package com.ibn.algafood.domain.repository;

import com.ibn.algafood.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
