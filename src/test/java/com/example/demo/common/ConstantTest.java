package com.example.demo.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Constants.class)
public class ConstantTest {
    @Test
    public void testConstants()
    {
        assertEquals(Constants.QUANTITY_INDEX, 2);
        assertEquals(Constants.DELIMITER, ",");
        assertEquals(Constants.UUID_INDEX, 0);
        assertEquals(Constants.BOOK_UUID_INDEX, 1);
        assertEquals(Constants.QUANTITY_INDEX, 2);
        assertEquals(Constants.S3_ELEMENT, 0);
        assertEquals(Constants.TOTAL_ELEMENT_IN_LINE, 3);
        assertEquals(Constants.TABLE_NAME, "Inventory");
        assertEquals(Constants.GET_QUANTITY_BY_BOOK, "/books/{id}/quantity");
    }
}
