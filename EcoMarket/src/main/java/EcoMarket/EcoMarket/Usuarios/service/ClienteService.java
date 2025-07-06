package EcoMarket.EcoMarket.Usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import EcoMarket.EcoMarket.Usuarios.repository.ClienteRepository;



@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente ObtenerPorId(int id) {
        return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
    }

    public Cliente guardar(Cliente cliente) {
        
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacío.");
        }

        if (cliente.getCorreo() == null || cliente.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El Correo no puede estar vacío.");
        }

        if (cliente.getDireccionEnvio() == null || cliente.getDireccionEnvio().trim().isEmpty()) {
            throw new IllegalArgumentException("La Direccion de envio no puede estar vacía.");
        }

        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El Telefono no puede estar vacío.");
        }

        return clienteRepository.save(cliente);
        
    }

    public void eliminar(int id) {

        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Cliente con ID " + id + " no existe.");
        }

        clienteRepository.deleteById(id);
    }

}
