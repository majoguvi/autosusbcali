package co.edu.usbcali.autosusbcali.service;

import co.edu.usbcali.autosusbcali.dto.request.CrearModificarMarcaRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerMarcaResponse;

import java.util.List;

public interface IMarcaService {
    // Busca todas las marcas sin importar su estado
    List<ObtenerMarcaResponse> obtenerTodos();

    //Buscamos las marcas por estado (ya sea activo o inactivo)
    List<ObtenerMarcaResponse> buscarMarcaPorEstado(Boolean activo);

    ObtenerMarcaResponse obtenerMarcaPorNombre(String nombre);

    ObtenerMarcaResponse obtenerMarcaPorId(Long id);

    //Metodo para crear una marca
    ObtenerMarcaResponse crearMarca(CrearModificarMarcaRequest crearModificarMarcaRequest) throws Exception;

    //Metodo para modificar una marca
    ObtenerMarcaResponse modificarMarca(Long id, CrearModificarMarcaRequest modificarMarcaRequest) throws Exception;

    // Metodo para eliminar una marca
    void eliminarMarca(Long id) throws Exception;

    // Metodo para eliminar fisicamente una marca
    void eliminarMarcaFisicamente(Long id) throws Exception;
}
