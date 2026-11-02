package co.edu.usbcali.autosusbcali.service;

import co.edu.usbcali.autosusbcali.domain.Rol;
import co.edu.usbcali.autosusbcali.dto.request.CrearModificarRolRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerRolResponse;
import co.edu.usbcali.autosusbcali.mapper.RolMapper;
import co.edu.usbcali.autosusbcali.repository.RolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class RolService implements IRolService {
    private  final RolRepository rolRepository;

    @Override
    public List<ObtenerRolResponse> obtenerTodos() {
        return RolMapper.listaRolesAlistaObtenerRolResponse(rolRepository.findAll());
    }

    @Override
    public List<ObtenerRolResponse> buscarRolPorEstado(Boolean activo) {
        List<Rol> rolesPorEstado = rolRepository.findByActivo(activo);
        return RolMapper.listaRolesAlistaObtenerRolResponse(rolesPorEstado);
    }

    @Override
    public ObtenerRolResponse obtenerRolPorNombre(String nombre) {
        Rol rolPorNombre = rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el rol por nombre"+nombre));
        return RolMapper.rolAObtenerRolResponse(rolPorNombre);
    }

    @Override
    public ObtenerRolResponse obtenerRolPorId(Long id) {
        Rol rolPorId = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el rol por id " +id));
        return RolMapper.rolAObtenerRolResponse(rolPorId);
    }

    @Override
    public ObtenerRolResponse crearRol(CrearModificarRolRequest crearRolRequest) throws Exception {
        //Validaciones del objeto #CrearModificarRolRequest
        if (crearRolRequest == null) {
            throw new Exception("El objeto CrearModificarRolRequest no puede ser nulo");
        }
        if (crearRolRequest.nombre() == null
                || crearRolRequest.nombre().isBlank()) {
            throw new Exception("El nombre no puede ser nulo ni estar vacio.");
        }
        if (crearRolRequest.descripcion()== null
        || crearRolRequest.descripcion().isBlank()){
            throw new Exception("La descripcion no puede ser nulo ni estar vacia.");
        }

        //Buscar si existe un rol con el nombre que se quiere agregar Opcion 1 traer el objeto
        /*Optional<Rol> rolPorNombre = rolRepository.findByNombre(crearRolRequest.nombre());
        if (rolPorNombre.isPresent()){
            throw new Exception("Ya existe un rol con el nombre" + crearRolRequest.nombre());
        }*/

        //Buscar si existe un rol con el nombre que se quiere agregar Opcion 2 buscar boolean
        if (rolRepository.existsByNombre(crearRolRequest.nombre())){
            throw new Exception("Ya existe un rol con el nombre" + crearRolRequest.nombre());
        }
        //Usar mapper para convertir DTO a objeto Domain
        Rol rol = RolMapper.CrearModificarRolRequestARol(crearRolRequest);

        //persistir el Rol en base de datos
        rol = rolRepository.save(rol);

        //Crear DTO response obtenerRolResponse
        ObtenerRolResponse rolResponse = RolMapper.rolAObtenerRolResponse(rol);

        //Retornar rol
        return rolResponse;
    }

    @Override
    public ObtenerRolResponse modificarRol(Long id, CrearModificarRolRequest modificarRolRequest) throws Exception {

        // Validaciones del id
        if (id == null) {
            throw new Exception("El id no puede ser nulo.");
        }

        // Validaciones del objeto CrearModificarRolRequest
        if (modificarRolRequest == null) {
            throw new Exception("El objeto CrearModificarRolRequest no puede ser nulo.");
        }

        if (modificarRolRequest.nombre() == null
                || modificarRolRequest.nombre().isBlank()) {
            throw new Exception("El nombre no puede ser nulo ni estar vacio.");
        }

        if (modificarRolRequest.descripcion() == null
                || modificarRolRequest.descripcion().isBlank()) {
            throw new Exception("La descripcion no puede ser nula ni estar vacia.");
        }

        // Buscar el rol que se quiere modificar
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un rol con el id " + id));

        // Verificar que el nombre no esté registrado en otro rol
        if (!rol.getNombre().equals(modificarRolRequest.nombre())
                && rolRepository.existsByNombre(modificarRolRequest.nombre())) {
            throw new Exception("Ya existe otro rol con ese nombre.");
        }

        // Actualizar la informacion del rol
        rol.setNombre(modificarRolRequest.nombre());
        rol.setDescripcion(modificarRolRequest.descripcion());

        // Guardar los cambios en la base de datos
        rol = rolRepository.save(rol);

        // Convertir el rol actualizado a response
        ObtenerRolResponse rolResponse = RolMapper.rolAObtenerRolResponse(rol);

        // Retornar el resultado
        return rolResponse;
    }
    @Override
    public void eliminarRol(Long id) throws Exception {

        // Validar que se haya recibido el id
        if (id == null) {
            throw new Exception("El id no puede ser nulo.");
        }

        // Consultar el rol que se desea eliminar
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un rol con el id " + id));

        // Comprobar que el rol se encuentre activo
        if (Boolean.FALSE.equals(rol.getActivo())) {
            throw new Exception("El rol ya se encuentra inactivo.");
        }

        // Cambiar el estado del rol a inactivo
        rol.setActivo(false);

        // Guardar el cambio en la base de datos
        rolRepository.save(rol);
    }

}
