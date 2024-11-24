package com.inventory.unit.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import com.inventory.utils.FileHandler;
import com.inventory.models.Product;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    @TempDir
    Path tempDir;
    private FileHandler fileHandler;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_inventory.txt").toString();
        fileHandler = new FileHandler(testFilePath);
    }

    @Test
    void testPartialFileCorruption() throws IOException {
        try (PrintWriter writer = new PrintWriter(testFilePath)) {
            writer.println("1,Test1,10.0,5,Test"); // Valid line
            writer.println("corrupted"); // Invalid line
            writer.println("2,Test2,20.0,10,Test"); // Valid line
        }

        List<Product> loaded = fileHandler.loadInventory();
        assertEquals(2, loaded.size(), "Should skip corrupted lines");
        assertEquals("Test1", loaded.get(0).getName(), "First product should be loaded");
        assertEquals("Test2", loaded.get(1).getName(), "Second product should be loaded");
    }

    @Test
    void testBasicSaveAndLoad() {
        Product product = new Product("1", "Test", 10.0, 5, "Test");
        List<Product> products = new ArrayList<>();
        products.add(product);
        
        fileHandler.saveInventory(products);
        List<Product> loaded = fileHandler.loadInventory();
        
        assertEquals(1, loaded.size(), "Should load one product");
        assertEquals("Test", loaded.get(0).getName(), "Should load correct product data");
    }
}