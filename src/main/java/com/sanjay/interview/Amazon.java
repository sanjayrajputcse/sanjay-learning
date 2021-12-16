package com.sanjay.interview;

import java.util.*;

public class Amazon {

    private class ProductSale {
        public String productName;
        public Integer sales;
        public ProductSale next;
    }

    private Map<String, List<String>> rankBestSellingProducts(Map<String, List<ProductSale>> categoryTosubCategorySales) {
        Map<String, List<String>> out = new HashMap<>();
        if(categoryTosubCategorySales == null || categoryTosubCategorySales.size() == 0) {
            return out;
        }

        for(String category : categoryTosubCategorySales.keySet()) {
            PriorityQueue<ProductSale> maxHeap = new PriorityQueue<>((ps1, ps2) -> ps2.sales - ps1.sales);
            List<ProductSale> subCats = categoryTosubCategorySales.get(category);
            for(int i = 0; i < subCats.size(); i++) {
                ProductSale head = subCats.get(i);
                if(head != null) {
                    maxHeap.add(head);
                }
            }
            List<String> bestSellingForCategory = new ArrayList<>();
            while(!maxHeap.isEmpty()) {
                ProductSale product = maxHeap.poll();
                if(product.next != null) {
                    maxHeap.add(product.next);
                }
                bestSellingForCategory.add(product.productName);
            }
            out.put(category, bestSellingForCategory);
        }
        return out;
    }
}
