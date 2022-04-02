package com.prueba.prueba.controllers;

import com.prueba.prueba.DAO.UsuarioDao;
import com.prueba.prueba.models.Usuario;
import com.prueba.prueba.utils.JWTutil;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {
    @Autowired
    private UsuarioDao usuarioDao;  //Inyección de dependencias.

    @Autowired  //Se le coloca notación de componente en el Utils para
    // poder compartirlo en todos los lugares y nos permite utilizar
    // las de value para poder cargar información de las properties

    private JWTutil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario) {


        Usuario usuarioLogueado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);


        if (usuarioLogueado != null) {

            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }

}
