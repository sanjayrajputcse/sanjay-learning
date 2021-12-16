package com.sanjay.javaSyntax;

import java.util.List;
import java.util.PriorityQueue;

public class PriorityQ {

    PriorityQueue<List<Integer>> pq = new PriorityQueue<>((p1, p2) -> p1.get(4) - p2.get(4));
}
