package com.example.demo.entites;

import com.example.demo.entities.Inventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Inventory.class)
public class InventoryTest {
    @Test
    public void testToStringFunction(){
        Inventory inventory = new Inventory("01789fec-c94d-4cb2-8e45-212131312312", "01789fec-c94d-4cb2-8e45-9fe9fd1ba88a", 1);
        assertEquals(inventory.toString(), "Inventory(uuid=01789fec-c94d-4cb2-8e45-212131312312, bookUuid=01789fec-c94d-4cb2-8e45-9fe9fd1ba88a, quantity=1)");
    }
}
