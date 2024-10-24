package ventaEntradasTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.entradas.sociosFutbol.ventaEntradas.Entrada;
import com.entradas.sociosFutbol.ventaEntradas.ventaEntradasDao;
import com.entradas.sociosFutbol.ventaEntradas.ventaEntradasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class VentaEntradasServiceTest {

    @Mock
    private ventaEntradasDao ventaEntradasDao;

    @InjectMocks
    private ventaEntradasService ventaEntradasService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEntradas() throws Exception {
        // Arrange
        List<Entrada> mockEntradas = new ArrayList<>();
        mockEntradas.add(new Entrada()); // Agrega instancias de Entrada según sea necesario
        when(ventaEntradasDao.getAllEntradas()).thenReturn(mockEntradas);

        // Act
        List<Entrada> result = ventaEntradasService.getAllEntradas();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ventaEntradasDao, times(1)).getAllEntradas();
    }


    @Test
    public void testUpdateEntrada() throws Exception {
        // Arrange
        int id=1;
        Entrada entrada = new Entrada();
        entrada.setTipoEntrada("popular"); // Establece un nombre
        entrada.setPrecio("100.0"); // Establece un precio
        when(ventaEntradasDao.updateEntrada(eq(id), any(Entrada.class))).thenReturn(entrada);

        // Act
        Entrada result = ventaEntradasService.updateEntrada(id, entrada);

        // Assert
        assertNotNull(result);
        assertEquals("popular", result.getTipoEntrada());
        assertEquals(100.0, Double.parseDouble(result.getPrecio()), 0.01); // Verifica el precio
        verify(ventaEntradasDao, times(1)).updateEntrada(eq(id), any(Entrada.class));
    }
}
