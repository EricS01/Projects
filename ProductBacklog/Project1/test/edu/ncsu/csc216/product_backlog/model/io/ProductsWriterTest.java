/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;





import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.Assert.fail;


import java.util.ArrayList;


import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.product.Product;





/**
 * Tests the ProductsWriter class and its methods
 * @author Eric Samuel
 */

class ProductsWriterTest {




	/**
	 * Tests the product writer class and its ability to write the products to a file
	 */
	@Test
	public void testWriteActivityRecords() {

	
		ArrayList<Product> products = new ArrayList<Product>();
		Product product1 = new Product("product name");
		Product product2 = new Product("Hello");
		Product product3 = new Product("Tesing");
		
		
		products.add(product1);
		products.add(product2);
		products.add(product3);
		ProductsWriter.writeProductsToFile("test-files/tasksNew.txt", products);
		
		
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> ProductsWriter.writeProductsToFile("", products));
		assertEquals("Unable to save file.", e1.getMessage());
		
	
		

	}
	


}
