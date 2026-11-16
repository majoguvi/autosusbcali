
package co.edu.usbcali.autosusbcali.controller;


import co.edu.usbcali.autosusbcali.dto.request.CrearModificarMarcaRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerMarcaResponse;
import co.edu.usbcali.autosusbcali.service.IMarcaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/marcas")
@AllArgsConstructor
public class MarcaController {
    private final IMarcaService marcaService;

    @GetMapping
    public List<ObtenerMarcaResponse> obtenerTodos() {
        return marcaService.obtenerTodos();
    }

    @GetMapping("/activos")
    public List<ObtenerMarcaResponse> obtenerTodosActivos(){
        return marcaService.buscarMarcaPorEstado(true);
    }

    @GetMapping("/inactivos")
    public List<ObtenerMarcaResponse> obtenerTodosInactivo(){
        return marcaService.buscarMarcaPorEstado(false);
    }

    @GetMapping("/por-nombre/{nombre}")
    public ResponseEntity<ObtenerMarcaResponse> obtenerPorNombre(@PathVariable String nombre){
        ObtenerMarcaResponse marcaResponse = marcaService.obtenerMarcaPorNombre(nombre);
        if (marcaResponse != null){
            return new ResponseEntity<>(marcaResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/por-id/{id}")
    public ResponseEntity<ObtenerMarcaResponse> obtenerPorId(@PathVariable Long id){
        ObtenerMarcaResponse marcaResponse = marcaService.obtenerMarcaPorId(id);
        if (marcaResponse != null){
            return new ResponseEntity<>(marcaResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ObtenerMarcaResponse> crearMarca(
            @RequestBody CrearModificarMarcaRequest crearMarcaRequest) throws Exception{
        return new ResponseEntity<>(
                marcaService.crearMarca(crearMarcaRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ObtenerMarcaResponse> modificarMarca(
            @PathVariable Long id,
            @RequestBody CrearModificarMarcaRequest modificarMarcaRequest) throws Exception {

        return new ResponseEntity<>(
                marcaService.modificarMarca(id, modificarMarcaRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarMarca(
            @PathVariable Long id) throws Exception {

        marcaService.eliminarMarca(id);

        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/eliminar-fisicamente/{id}")
    public ResponseEntity<Void> eliminarMarcaFisicamente(
            @PathVariable Long id) throws Exception {

        marcaService.eliminarMarcaFisicamente(id);

        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT);
    }


}
