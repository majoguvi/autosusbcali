
package co.edu.usbcali.autosusbcali.service;

import co.edu.usbcali.autosusbcali.domain.Marca;
import co.edu.usbcali.autosusbcali.dto.request.CrearModificarMarcaRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerMarcaResponse;
import co.edu.usbcali.autosusbcali.mapper.MarcaMapper;
import co.edu.usbcali.autosusbcali.repository.MarcaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class MarcaService implements IMarcaService {
    private final MarcaRepository marcaRepository;

    @Override
    public List<ObtenerMarcaResponse> obtenerTodos() {
        return MarcaMapper.listaMarcasAListaObtenerMarcaResponse(marcaRepository.findAll());
    }

    @Override
    public List<ObtenerMarcaResponse> buscarMarcaPorEstado(Boolean activo) {
        List<Marca> marcasPorEstado = marcaRepository.findByActivo(activo);
        return MarcaMapper.listaMarcasAListaObtenerMarcaResponse(marcasPorEstado);
    }

    @Override
    public ObtenerMarcaResponse obtenerMarcaPorNombre(String nombre) {
        Marca marcaPorNombre = marcaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la marca por nombre " + nombre));
        return MarcaMapper.marcaAObtenerMarcaResponse(marcaPorNombre);
    }

    @Override
    public ObtenerMarcaResponse obtenerMarcaPorId(Long id) {
        Marca marcaPorId = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la marca por id " + id));
        return MarcaMapper.marcaAObtenerMarcaResponse(marcaPorId);
    }

    @Override
    public ObtenerMarcaResponse crearMarca(CrearModificarMarcaRequest crearMarcaRequest) throws Exception {
        //Validaciones del objeto CrearModificarMarcaRequest
        if (crearMarcaRequest == null) {
            throw new Exception("El objeto CrearModificarMarcaRequest no puede ser nulo");
        }

        if (crearMarcaRequest.nombre() == null
                || crearMarcaRequest.nombre().isBlank()) {
            throw new Exception("El nombre no puede ser nulo ni estar vacio.");
        }

        //Buscar si existe una marca con el nombre que se quiere agregar
        if (marcaRepository.existsByNombre(crearMarcaRequest.nombre())) {
            throw new Exception("Ya existe una marca con el nombre " + crearMarcaRequest.nombre());
        }

        //Usar mapper para convertir DTO a objeto Domain
        Marca marca = MarcaMapper.CrearModificarMarcaRequestAMarca(crearMarcaRequest);

        //persistir la Marca en base de datos
        marca = marcaRepository.save(marca);

        //Crear DTO response ObtenerMarcaResponse
        ObtenerMarcaResponse marcaResponse = MarcaMapper.marcaAObtenerMarcaResponse(marca);

        //Retornar marca
        return marcaResponse;
    }

    @Override
    public ObtenerMarcaResponse modificarMarca(Long id, CrearModificarMarcaRequest modificarMarcaRequest) throws Exception {

        // Validaciones del id
        if (id == null) {
            throw new Exception("El id no puede ser nulo.");
        }

        // Validaciones del objeto CrearModificarMarcaRequest
        if (modificarMarcaRequest == null) {
            throw new Exception("El objeto CrearModificarMarcaRequest no puede ser nulo.");
        }

        if (modificarMarcaRequest.nombre() == null
                || modificarMarcaRequest.nombre().isBlank()) {
            throw new Exception("El nombre no puede ser nulo ni estar vacio.");
        }

        // Buscar la marca que se quiere modificar
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe una marca con el id " + id));

        // Verificar que el nombre no esté registrado en otra marca
        if (!marca.getNombre().equals(modificarMarcaRequest.nombre())
                && marcaRepository.existsByNombre(modificarMarcaRequest.nombre())) {
            throw new Exception("Ya existe otra marca con ese nombre.");
        }

        // Actualizar la informacion de la marca
        marca.setNombre(modificarMarcaRequest.nombre());

        // Guardar los cambios en la base de datos
        marca = marcaRepository.save(marca);

        // Convertir la marca actualizada a response
        ObtenerMarcaResponse marcaResponse = MarcaMapper.marcaAObtenerMarcaResponse(marca);

        // Retornar el resultado
        return marcaResponse;
    }

    @Override
    public void eliminarMarca(Long id) throws Exception {

        // Validar que se haya recibido el id
        if (id == null) {
            throw new Exception("El id no puede ser nulo.");
        }

        if (id == 0) {
            throw new Exception("El id no puede ser 0.");
        }

        // Consultar la marca que se desea eliminar
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe una marca con el id " + id));

        // Comprobar que la marca se encuentre activa
        if (Boolean.FALSE.equals(marca.getActivo())) {
            throw new Exception("La marca ya se encuentra inactiva.");
        }

        // Cambiar el estado de la marca a inactivo
        marca.setActivo(false);

        // Guardar el cambio en la base de datos
        marcaRepository.save(marca);
    }

    @Override
    public void eliminarMarcaFisicamente(Long id) throws Exception {

        // Validar que se haya recibido el id
        if (id == null) {
            throw new Exception("El id no puede ser nulo.");
        }

        if (id == 0) {
            throw new Exception("El id no puede ser 0.");
        }

        // Consultar la marca que se desea eliminar
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe una marca con el id " + id));

        // Eliminar fisicamente la marca de la base de datos
        marcaRepository.delete(marca);
    }

}
