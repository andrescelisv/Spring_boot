package com.prueba.prueba.DAO;

import com.prueba.prueba.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //Conexión con la base de datos, repositorio a la base de datos
@Transactional //permite armar las consultas sql a la base de datos, consultas de sql, como las arma y como las va a ejecutar
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext   //Es una notación de un contexto en la base de datos a utilizar.
    EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query= "FROM Usuario";   // En la consulta lleva el nombre  de la clase porqué se esta trabajando con los objetos de la clase usuario.
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query= "FROM Usuario WHERE email = :email";
        // En la consulta lleva el nombre  de la clase porqué se esta trabajando con los objetos de la clase usuario.
        List<Usuario> lista= entityManager.createQuery(query)
                .setParameter( "email",usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        }
        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        System.out.println(argon2.verify(passwordHashed,usuario.getPassword()));
        if(argon2.verify(passwordHashed,usuario.getPassword())){
            return lista.get(0);
        }
        return null;
    }

}
