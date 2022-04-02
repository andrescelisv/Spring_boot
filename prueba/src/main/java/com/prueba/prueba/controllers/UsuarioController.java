package com.prueba.prueba.controllers;

//Estas clases permiten controlar las url donde el usuario accede.

import com.prueba.prueba.DAO.UsuarioDao;
import com.prueba.prueba.models.Usuario;
import com.prueba.prueba.utils.JWTutil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;  //Inyección de dependencias.
    @Autowired
    private JWTutil jwtutil;

    @RequestMapping(value="api/usuarios/{id}",method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id) {

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("rodriguez");
        usuario.setEmail("jassa@gmail.com");
        usuario.setTelefono("7877778");
        usuario.setPassword("nada12");
        return usuario;
    }

    @RequestMapping(value="api/usuarios",method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
      System.out.println("usuario"+token);
        if(!validarToken(token)){

            return null;

        }


        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId = jwtutil.getKey(token);
        System.out.println("usuario_id"+usuarioId);
        return usuarioId!=null;
    }

    @RequestMapping(value="api/usuarios",method = RequestMethod.POST)
    public void registrarusuario(@RequestBody Usuario usuario) {

        Argon2 argon2  = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024, 1,usuario.getPassword());
        //i= iteraciones i1= cantidad de memoría i2= paralelismo
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }




    @RequestMapping(value="editar")
    public Usuario geteditar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("rodriguez");
        usuario.setEmail("jassa@gmail.com");
        usuario.setTelefono("7877778");
        usuario.setPassword("nada12");
        return usuario;
    }

    @RequestMapping(value="api/usuarios/{id}",method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token,@PathVariable Long id) {
        if(validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value="buscar")
    public Usuario buscar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("rodriguez");
        usuario.setEmail("jassa@gmail.com");
        usuario.setTelefono("7877778");
        usuario.setPassword("nada12");
        return usuario;
    }




}
