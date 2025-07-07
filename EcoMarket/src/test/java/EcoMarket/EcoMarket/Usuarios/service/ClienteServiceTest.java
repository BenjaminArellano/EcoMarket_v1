package EcoMarket.EcoMarket.Usuarios.service;

import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import EcoMarket.EcoMarket.Usuarios.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testObtenerTodos() {
        Cliente cliente = crearCliente();
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteService.obtenerTodos();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals(cliente.getId(), clientes.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Cliente cliente = crearCliente();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente found = clienteService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.ObtenerPorId(id);
        });
        assertEquals("Cliente no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Cliente cliente = crearCliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente saved = clienteService.guardar(cliente);
        assertNotNull(saved);
        assertEquals("Juan Pérez", saved.getNombre());
        assertEquals("juan@email.com", saved.getCorreo());
        assertEquals("Calle 123", saved.getDireccionEnvio());
        assertEquals("123456789", saved.getTelefono());
    }

    @Test
    public void testGuardarConNombreVacio() {
        Cliente cliente = crearCliente();
        cliente.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.guardar(cliente);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConNombreNulo() {
        Cliente cliente = crearCliente();
        cliente.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.guardar(cliente);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConCorreoVacio() {
        Cliente cliente = crearCliente();
        cliente.setCorreo("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.guardar(cliente);
        });
        assertEquals("El Correo no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConDireccionVacia() {
        Cliente cliente = crearCliente();
        cliente.setDireccionEnvio("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.guardar(cliente);
        });
        assertEquals("La Direccion de envio no puede estar vacía.", exception.getMessage());
    }

    @Test
    public void testGuardarConTelefonoVacio() {
        Cliente cliente = crearCliente();
        cliente.setTelefono("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.guardar(cliente);
        });
        assertEquals("El Telefono no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(clienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(id);

        clienteService.eliminar(id);
        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(clienteRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Cliente con ID " + id + " no existe.", exception.getMessage());
    }

    private Cliente crearCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Pérez");
        cliente.setCorreo("juan@email.com");
        cliente.setDireccionEnvio("Calle 123");
        cliente.setTelefono("123456789");

        return cliente;
    }
}
