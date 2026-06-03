package cl.paris.administrador.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.paris.administrador.model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {
    Optional<Administrador> findByUsername(String username);
}