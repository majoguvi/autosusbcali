package co.edu.usbcali.autosusbcali.mapper;

import co.edu.usbcali.autosusbcali.domain.Marca;
import co.edu.usbcali.autosusbcali.dto.request.CrearModificarMarcaRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerMarcaResponse;

import java.util.List;

public class MarcaMapper {

    public static ObtenerMarcaResponse marcaAObtenerMarcaResponse(Marca marca) {
        ObtenerMarcaResponse obtenerMarcaResponse =
                new ObtenerMarcaResponse(marca.getId(), marca.getNombre());
        return obtenerMarcaResponse;
    }

    public static List<ObtenerMarcaResponse> listaMarcasAListaObtenerMarcaResponse(List<Marca> marcas) {
        return marcas.stream().map(MarcaMapper::marcaAObtenerMarcaResponse).toList();
    }

    // Metodo que convierte un Request de Crear Marca a una Marca (Entidad de Dominio)
    public static Marca CrearModificarMarcaRequestAMarca(CrearModificarMarcaRequest crearModificarMarcaRequest) {
        // Forma 1 usando el constructor
        return new Marca(null,
                crearModificarMarcaRequest.nombre(),
                true);

        // Forma 2 usando el constructor vacio
        /* Marca marca = new Marca();
        marca.setNombre(crearModificarMarcaRequest.nombre());
        marca.setActivo(true);
        return marca; */
    }
}
