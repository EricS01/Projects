/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import edu.ncsu.csc216.product_backlog.model.product.Product;

/**
 * writes the list of products to a file
 * @author Eric Samuel
 */
public class ProductsWriter {
	

	/**
	 * writes the list of products to filename
	 * @param file filename of the place list of products will be saved to
	 * @param products list that contains the products
	 * @throws IllegalArgumentException if there are any errors when trying to save file
	 */
	public static void writeProductsToFile(String file, List<Product> products) {
		try (PrintStream fileWriter = new PrintStream(new FileOutputStream(file))) {
			for(Product p: products) {
				fileWriter.println("# " + p.getProductName());
				for(int i = 0; i < p.getTasks().size(); i++) {
					fileWriter.print(p.getTasks().get(i).toString());
				}
			}
			
			fileWriter.close();
		} catch(IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
	
	
}
