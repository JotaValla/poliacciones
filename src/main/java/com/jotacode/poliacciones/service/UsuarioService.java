package com.jotacode.poliacciones.service;

import com.jotacode.poliacciones.model.Usuario;
import com.jotacode.poliacciones.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }


}
