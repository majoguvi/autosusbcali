package co.edu.usbcali.autosusbcali.repository;

import co.edu.usbcali.autosusbcali.domain.RolPermiso;
import co.edu.usbcali.autosusbcali.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
}
