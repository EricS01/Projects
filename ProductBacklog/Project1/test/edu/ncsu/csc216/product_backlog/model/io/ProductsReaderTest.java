	package edu.ncsu.csc216.product_backlog.model.io;


	
	import static org.junit.jupiter.api.Assertions.assertEquals;

	
	import java.util.ArrayList;
	
	import org.junit.jupiter.api.Test;
	
	import edu.ncsu.csc216.product_backlog.model.product.Product;






	/**
	 * Tests the ProductsReader class and its methods
	 * @author Eric Samuel
	 */

	class ProductsReaderTest {



		
		@Test
		void testProductReader() {
		
		ArrayList<Product> products = ProductsReader.readProductsFile("test-files/tasks1.txt");
	
		assertEquals(2, products.size());

		}


	}




