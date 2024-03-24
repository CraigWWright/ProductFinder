package com.example.productfinder;

import java.util.ArrayList;
import java.util.List;

public class ProductPermutations {
    public static ArrayList<ArrayList<ProductClass>> generatePermutations(ArrayList<ProductClass> productList) {
        ArrayList<ArrayList<ProductClass>> result = new ArrayList<>();
        permute(productList, 0, result);
        return result;
    }

    private static void permute(ArrayList<ProductClass> productList, int start, ArrayList<ArrayList<ProductClass>> result) {
        if (start >= productList.size()) {
            result.add(new ArrayList<>(productList));
            return;
        }

        for (int i = start; i < productList.size(); i++) {
            // Swap elements
            swap(productList, start, i);
            // Recursively generate permutations
            permute(productList, start + 1, result);
            // Backtrack: Undo the swap
            swap(productList, start, i);
        }
    }

    private static void swap(ArrayList<ProductClass> productList, int i, int j) {
        ProductClass temp = productList.get(i);
        productList.set(i, productList.get(j));
        productList.set(j, temp);
    }
}
