import client.Client;
import client.NetworkConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NetworkConnectionTest {

    NetworkConnection NC;
    @BeforeEach
    public void clientConstructorTest() {
        final String data = " ";
        NC = new Client("192.168.0.111", 8080, d->{});
        assertNotNull(NC, "client constructor failed");
    }

    @Test
    public void getConnectedTest() {
        NC.setConnected(true);
        boolean c = NC.getConnected();
        assertEquals(c, true, "getConnectedTest() failed");
    }

    @Test
    public void setConnectedTest() {
        NC.setConnected(true);
        assertEquals(NC.getConnected(), true, "setConnectedTest() failed");
    }

}
