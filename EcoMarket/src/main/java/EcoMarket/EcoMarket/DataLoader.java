package EcoMarket.EcoMarket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.model.Envio;
import EcoMarket.EcoMarket.Pedidos.model.Factura;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.CuponRepository;
import EcoMarket.EcoMarket.Pedidos.repository.DetallePedidoRepository;
import EcoMarket.EcoMarket.Pedidos.repository.EnvioRepository;
import EcoMarket.EcoMarket.Pedidos.repository.FacturaRepository;
import EcoMarket.EcoMarket.Pedidos.repository.PedidoRepository;
import EcoMarket.EcoMarket.Productos.model.Inventario;
import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.model.Resenia;
import EcoMarket.EcoMarket.Productos.repository.InventarioRepository;
import EcoMarket.EcoMarket.Productos.repository.ProductoRepository;
import EcoMarket.EcoMarket.Productos.repository.ProveedorRepository;
import EcoMarket.EcoMarket.Productos.repository.ReseniaRepository;
import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import EcoMarket.EcoMarket.Usuarios.model.Empleado;
import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.model.Usuario;
import EcoMarket.EcoMarket.Usuarios.repository.ClienteRepository;
import EcoMarket.EcoMarket.Usuarios.repository.EmpleadoRepository;
import EcoMarket.EcoMarket.Usuarios.repository.PermisoRepository;
import EcoMarket.EcoMarket.Usuarios.repository.RolRepository;
import EcoMarket.EcoMarket.Usuarios.repository.UsuarioRepository;
import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PermisoRepository permisoRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private ReseniaRepository reseniaRepository;
    
    @Autowired
    private CuponRepository cuponRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    
    @Autowired
    private FacturaRepository facturaRepository;
    
    @Autowired
    private EnvioRepository envioRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay datos en la base de datos
        if (permisoRepository.count() > 0) {
            System.out.println("La base de datos ya contiene datos. Saltando la carga inicial.");
            return;
        }

        System.out.println("Iniciando carga de datos ficticios...");

        // Cargar datos en orden de dependencias
        loadPermisos();
        loadRoles();
        loadUsuarios();
        loadClientes();
        loadEmpleados();
        loadProveedores();
        loadProductos();
        loadInventarios();
        loadResenias();
        loadCupones();
        loadPedidos();
        loadDetallesPedidos();
        loadFacturas();
        loadEnvios();

        System.out.println("Carga de datos ficticios completada exitosamente!");
    }

    private void loadPermisos() {
        List<Permiso> permisos = new ArrayList<>();
        
        String[] nombresPermisos = {
            "CREAR_PRODUCTO", "EDITAR_PRODUCTO", "ELIMINAR_PRODUCTO", "VER_PRODUCTO",
            "CREAR_USUARIO", "EDITAR_USUARIO", "ELIMINAR_USUARIO", "VER_USUARIO",
            "CREAR_PEDIDO", "EDITAR_PEDIDO", "ELIMINAR_PEDIDO", "VER_PEDIDO",
            "GESTIONAR_INVENTARIO", "VER_REPORTES", "ADMINISTRAR_SISTEMA"
        };
        
        String[] descripciones = {
            "Permite crear nuevos productos", "Permite editar productos existentes", 
            "Permite eliminar productos", "Permite ver productos",
            "Permite crear nuevos usuarios", "Permite editar usuarios existentes",
            "Permite eliminar usuarios", "Permite ver usuarios",
            "Permite crear nuevos pedidos", "Permite editar pedidos existentes",
            "Permite eliminar pedidos", "Permite ver pedidos",
            "Permite gestionar el inventario", "Permite ver reportes del sistema",
            "Permite administrar el sistema completo"
        };

        for (int i = 0; i < nombresPermisos.length; i++) {
            Permiso permiso = new Permiso();
            permiso.setNombre(nombresPermisos[i]);
            permiso.setDescripcion(descripciones[i]);
            permisos.add(permiso);
        }

        permisoRepository.saveAll(permisos);
        System.out.println("Cargados " + permisos.size() + " permisos");
    }

    private void loadRoles() {
        List<Permiso> todosPermisos = permisoRepository.findAll();
        List<Rol> roles = new ArrayList<>();

        // Rol Administrador - todos los permisos
        Rol admin = new Rol();
        admin.setNombre("ADMINISTRADOR");
        admin.setPermisos(new ArrayList<>(todosPermisos));
        roles.add(admin);

        // Rol Empleado - permisos limitados
        Rol empleado = new Rol();
        empleado.setNombre("EMPLEADO");
        List<Permiso> permisosEmpleado = todosPermisos.stream()
            .filter(p -> p.getNombre().contains("VER_") || 
                        p.getNombre().contains("CREAR_PRODUCTO") ||
                        p.getNombre().contains("EDITAR_PRODUCTO") ||
                        p.getNombre().contains("GESTIONAR_INVENTARIO"))
            .toList();
        empleado.setPermisos(new ArrayList<>(permisosEmpleado));
        roles.add(empleado);

        // Rol Cliente - permisos muy limitados
        Rol cliente = new Rol();
        cliente.setNombre("CLIENTE");
        List<Permiso> permisosCliente = todosPermisos.stream()
            .filter(p -> p.getNombre().equals("VER_PRODUCTO") || 
                        p.getNombre().equals("CREAR_PEDIDO"))
            .toList();
        cliente.setPermisos(new ArrayList<>(permisosCliente));
        roles.add(cliente);

        rolRepository.saveAll(roles);
        System.out.println("Cargados " + roles.size() + " roles");
    }

    private void loadUsuarios() {
        List<Rol> roles = rolRepository.findAll();
        List<Usuario> usuarios = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password());
            usuario.setRol(roles.get(random.nextInt(roles.size())));
            usuarios.add(usuario);
        }

        usuarioRepository.saveAll(usuarios);
        System.out.println("Cargados " + usuarios.size() + " usuarios");
    }

    private void loadClientes() {
        List<Cliente> clientes = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().fullName());
            cliente.setCorreo(faker.internet().emailAddress());
            cliente.setDireccionEnvio(faker.address().fullAddress());
            cliente.setTelefono(faker.phoneNumber().phoneNumber());
            clientes.add(cliente);
        }

        clienteRepository.saveAll(clientes);
        System.out.println("Cargados " + clientes.size() + " clientes");
    }

    private void loadEmpleados() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Empleado> empleados = new ArrayList<>();

        String[] cargos = {"Gerente", "Vendedor", "Almacenista", "Contador", "Supervisor"};

        for (int i = 0; i < Math.min(10, usuarios.size()); i++) {
            Empleado empleado = new Empleado();
            empleado.setUsuario(usuarios.get(i));
            empleado.setNombre(usuarios.get(i).getNombre() + " " + usuarios.get(i).getApellido());
            empleado.setCargo(cargos[random.nextInt(cargos.length)]);
            empleado.setCorreo(usuarios.get(i).getEmail());
            empleados.add(empleado);
        }

        empleadoRepository.saveAll(empleados);
        System.out.println("Cargados " + empleados.size() + " empleados");
    }

    private void loadProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(faker.company().name());
            proveedor.setContacto(faker.name().fullName());
            proveedor.setTelefono(faker.phoneNumber().phoneNumber());
            proveedores.add(proveedor);
        }

        proveedorRepository.saveAll(proveedores);
        System.out.println("Cargados " + proveedores.size() + " proveedores");
    }

    private void loadProductos() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        List<Producto> productos = new ArrayList<>();

        String[] categorias = {"Electrónicos", "Ropa", "Hogar", "Deportes", "Libros", "Juguetes", "Alimentación"};

        for (int i = 0; i < 100; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence(10));
            producto.setCategoria(categorias[random.nextInt(categorias.length)]);
            producto.setPrecio(random.nextInt(500) + 10); // Precio entre 10 y 510
            producto.setProveedor(proveedores.get(random.nextInt(proveedores.size())));
            productos.add(producto);
        }

        productoRepository.saveAll(productos);
        System.out.println("Cargados " + productos.size() + " productos");
    }

    private void loadInventarios() {
        List<Producto> productos = productoRepository.findAll();
        List<Inventario> inventarios = new ArrayList<>();

        for (Producto producto : productos) {
            Inventario inventario = new Inventario();
            inventario.setProducto(producto);
            inventario.setStock(random.nextInt(100) + 1); // Stock entre 1 y 100
            inventarios.add(inventario);
        }

        inventarioRepository.saveAll(inventarios);
        System.out.println("Cargados " + inventarios.size() + " inventarios");
    }

    private void loadResenias() {
        List<Producto> productos = productoRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<Resenia> resenias = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Resenia resenia = new Resenia();
            resenia.setCalificacion(random.nextInt(5) + 1); // Calificación entre 1 y 5
            resenia.setComentario(faker.lorem().sentence(15));
            resenia.setProducto(productos.get(random.nextInt(productos.size())));
            resenia.setCliente(clientes.get(random.nextInt(clientes.size())));
            resenias.add(resenia);
        }

        reseniaRepository.saveAll(resenias);
        System.out.println("Cargadas " + resenias.size() + " reseñas");
    }

    private void loadCupones() {
        List<Cupon> cupones = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Cupon cupon = new Cupon();
            cupon.setCodigo("CUPON" + faker.number().digits(6));
            cupon.setDescuento(random.nextInt(50) + 5); // Descuento entre 5% y 55%
            cupones.add(cupon);
        }

        cuponRepository.saveAll(cupones);
        System.out.println("Cargados " + cupones.size() + " cupones");
    }

    private void loadPedidos() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Cupon> cupones = cuponRepository.findAll();
        List<Pedido> pedidos = new ArrayList<>();

        String[] estados = {"PENDIENTE", "PROCESANDO", "ENVIADO", "ENTREGADO", "CANCELADO"};

        for (int i = 0; i < 80; i++) {
            Pedido pedido = new Pedido();
            pedido.setFecha(LocalDateTime.now().minusDays(random.nextInt(30)));
            pedido.setEstado(estados[random.nextInt(estados.length)]);
            pedido.setTotal(random.nextInt(1000) + 50); // Total entre 50 y 1050
            pedido.setCliente(clientes.get(random.nextInt(clientes.size())));
            
            // Algunos pedidos tendrán cupón, otros no
            if (random.nextBoolean()) {
                pedido.setCupon(cupones.get(random.nextInt(cupones.size())));
            }
            
            pedidos.add(pedido);
        }

        pedidoRepository.saveAll(pedidos);
        System.out.println("Cargados " + pedidos.size() + " pedidos");
    }

    private void loadDetallesPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Producto> productos = productoRepository.findAll();
        List<DetallePedido> detalles = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            // Cada pedido tendrá entre 1 y 5 productos
            int numProductos = random.nextInt(5) + 1;
            
            for (int i = 0; i < numProductos; i++) {
                DetallePedido detalle = new DetallePedido();
                detalle.setPedido(pedido);
                detalle.setProducto(productos.get(random.nextInt(productos.size())));
                detalle.setCantidad(random.nextInt(5) + 1); // Cantidad entre 1 y 5
                detalle.setPrecio(detalle.getProducto().getPrecio());
                detalles.add(detalle);
            }
        }

        detallePedidoRepository.saveAll(detalles);
        System.out.println("Cargados " + detalles.size() + " detalles de pedidos");
    }

    private void loadFacturas() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Factura> facturas = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            // Solo crear facturas para pedidos que no estén cancelados
            if (!"CANCELADO".equals(pedido.getEstado())) {
                Factura factura = new Factura();
                factura.setPedido(pedido);
                factura.setFecha(pedido.getFecha().plusHours(1)); // Factura 1 hora después del pedido
                factura.setTotal(pedido.getTotal());
                facturas.add(factura);
            }
        }

        facturaRepository.saveAll(facturas);
        System.out.println("Cargadas " + facturas.size() + " facturas");
    }

    private void loadEnvios() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Envio> envios = new ArrayList<>();

        String[] estadosEnvio = {"PREPARANDO", "EN_TRANSITO", "ENTREGADO", "DEVUELTO"};

        for (Pedido pedido : pedidos) {
            // Solo crear envíos para pedidos que no estén cancelados
            if (!"CANCELADO".equals(pedido.getEstado())) {
                Envio envio = new Envio();
                envio.setPedido(pedido);
                envio.setDireccion(pedido.getCliente().getDireccionEnvio());
                envio.setEstado(estadosEnvio[random.nextInt(estadosEnvio.length)]);
                envios.add(envio);
            }
        }

        envioRepository.saveAll(envios);
        System.out.println("Cargados " + envios.size() + " envíos");
    }
}
