package edu.ncsu.csc216.product_backlog.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * Allows for the reading of files to be turned into an arraylist of products
 * @author Eric Samuel
 *
 */
public class ProductsReader {
    /**
     * Reads a file from given name
     * @param fileName name of file to be read
     * @return an ArrayList of products that are read from file
     */
    public static ArrayList<Product> readProductsFile(String fileName) {
        ArrayList<Product> p = new ArrayList<Product>();

        try {
            Scanner in = new Scanner(new FileInputStream(fileName));
            String text = "";
            while (in.hasNext()) {
                text += "\n" + in.nextLine();
            }
            String[] hashtagDelim = text.split("\n# ");
            for (int i = 1; i < hashtagDelim.length; i++) {
                String[] astDelim = hashtagDelim[i].split("\n\\* ");
                Product product = processProduct(astDelim[0]);
                for (int j = 1; j < astDelim.length; j++) {
                    Task task = processTask(astDelim[j]);
                    product.addTask(task);
                }
                p.add(product);
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to load file.");
        }
        return p;

    }
    /**
     * readProductsFile method is a helper method that creates a product object
     * @param productName that is a product
     * @return Product from given line
     */
    private static Product processProduct(String productName) {
        return new Product(productName);

    }
    /**
     * processtask method processes a recieved line to be turned into a task object
     * @param line task information as string
     * @return task which is the constructed task with the required parameters.
     */
    private static Task processTask(String line) {
        String[] hyphenDelim = line.split("\n- ");
        ArrayList<String> notes = new ArrayList<String>();
        String[] taskParams = hyphenDelim[0].split(",");

        for (int i = 1; i < hyphenDelim.length; i++) {
            notes.add(hyphenDelim[i]);
        }

        return new Task(Integer.parseInt(taskParams[0]), taskParams[1], taskParams[2], taskParams[3], taskParams[4], taskParams[5], taskParams[6], notes);
    }

}