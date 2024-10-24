package ventaEntradasTest;

import com.entradas.sociosFutbol.ventaEntradas.Entrada;
import com.entradas.sociosFutbol.ventaEntradas.ventaEntradasController;
import com.entradas.sociosFutbol.ventaEntradas.ventaEntradasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VentaEntradasControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ventaEntradasService ventaEntradasService; // Mockeamos el servicio

    @InjectMocks
    private ventaEntradasController ventaEntradasController; // Inyectamos el mock en el controlador

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
        mockMvc = MockMvcBuilders.standaloneSetup(ventaEntradasController).build(); // Construimos el mock de MVC
    }

    @Test
    public void testGetAllEntradas() throws Exception {

        List<Entrada> mockEntradas = new ArrayList<>();
        // Datos de prueba
        Entrada entrada1 = new Entrada(); // Utilizando el constructor vacío
        entrada1.setId(1);
        entrada1.setPrecio("100.0");
        entrada1.setTipoEntrada("Concierto A");
        entrada1.setIsSocio(true);

        mockEntradas.add(entrada1);

// Crear la segunda entrada y setear los datos
        Entrada entrada2 = new Entrada(); // Utilizando el constructor vacío
        entrada2.setId(2);
        entrada2.setPrecio("150.0");
        entrada2.setTipoEntrada("Concierto B");
        entrada2.setIsSocio(false);
        mockEntradas.add(entrada2);

        // Definimos el comportamiento del mock del service
        when(ventaEntradasService.getAllEntradas()).thenReturn(mockEntradas);

        // Hacemos una solicitud al endpoint /entradas
        mockMvc.perform(get("/getAllEntradas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verificamos que la respuesta sea 200 OK
                .andExpect(content().json("[{\"id\":1,\"precio\":\"100.0\",\"tipoEntrada\":\"Concierto A\",\"isSocio\":true}," +
                        "{\"id\":2,\"precio\":\"150.0\",\"tipoEntrada\":\"Concierto B\",\"isSocio\":false}]"));

    }
}
