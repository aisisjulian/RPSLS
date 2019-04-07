import client.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientTest {

    Client c;
    @BeforeEach
    public void clientConstructorTest() {
        final String data = " ";
        c = new Client("192.168.0.111", 8080, "TestName", d->{});
        assertNotNull(c, "client constructor failed");
    }

    @Test
    public void getClientIPTest() {
        String ip = c.getIP();
        assertEquals(ip, "192.168.0.111", "getIP() failed");
    }

    @Test
    public void getClientPortTest() {
        int port = c.getPort();
        assertEquals(port, 8080, "getPort() failed");
    }

}
