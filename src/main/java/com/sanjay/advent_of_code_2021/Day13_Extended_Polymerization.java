package com.sanjay.advent_of_code_2021;

import java.util.HashMap;
import java.util.Map;

public class Day13_Extended_Polymerization {

    private static Map<Integer, Map<Integer, Long>> cache;

    public static void main(String[] args) {
//        String polymerTemplate = samplePolymerTemplateInput();
//        String[] pairInsertions = samplePairInsertionInput();
        String polymerTemplate = myPolymerTemplateInput();
        String[] pairInsertions = myPairInsertionInput();
        Map<Integer, Integer> pairInsertionMap = parseInput(pairInsertions);
        System.out.println("Part1 : " + processAndFind(polymerTemplate, pairInsertionMap, 10));
        System.out.println("Part2 : " + processAndFind(polymerTemplate, pairInsertionMap, 40));
    }

    private static long processAndFind(String polymerTemplate, Map<Integer, Integer>  pairInsertionMap, int steps) {
        cache = new HashMap<>();
        Map<Integer, Long> freqMap = processInsertions(polymerTemplate, pairInsertionMap, steps);
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;
        for(int tmpl : freqMap.keySet()) {
            max = Math.max(max, freqMap.get(tmpl));
            min = Math.min(min, freqMap.get(tmpl));
        }
        return (max - min);
    }

    private static Map<Integer, Long> processInsertions(String originalTmpl, Map<Integer, Integer> pairInsertionMap, int steps) {
        Map<Integer, Long> originalTmplFreqMap = new HashMap<>();
        originalTmplFreqMap.put(key(originalTmpl.charAt(0)), 1l);
        for(int index = 1; index < originalTmpl.length(); index++) {
            Integer tmpl = key(originalTmpl.charAt(index-1)) * 100 + key(originalTmpl.charAt(index));
            Map<Integer, Long> tmplFreqMap = recursiveInsertion(tmpl, pairInsertionMap, 1, steps);
//            System.out.println(index + " : " + tmplFreqMap);
            tmplFreqMap.forEach((k, v) -> originalTmplFreqMap.merge(k, v, (a, b) -> a+b));
        }
        return originalTmplFreqMap;
    }

    private static Map<Integer, Long> recursiveInsertion(Integer tmpl, Map<Integer, Integer> insertionMap, int depth, int N) {
        if(depth > N) {
            Map<Integer, Long> freqMap = new HashMap<>();
            freqMap.merge(tmpl % 100, 1l, (a, b) -> a+b);
            return freqMap;
        }
        int cacheKey = tmpl * 100 + depth;
        if(cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }
        Integer middle = insertionMap.get(tmpl);
        Integer leftTmpl = tmpl - (tmpl % 100) + middle;
        Map<Integer, Long> left = recursiveInsertion(leftTmpl, insertionMap, depth + 1, N);
        Integer rightTmpl = middle * 100 + (tmpl % 100);
        Map<Integer, Long> right = recursiveInsertion(rightTmpl, insertionMap, depth + 1, N);
        Map<Integer, Long> merged = new HashMap<>();
        left.forEach((k, v) -> merged.merge(k, v, (a, b) -> a+b));
        right.forEach((k, v) -> merged.merge(k, v, (a, b) -> a+b));
        cache.put(cacheKey, merged);
        return merged;
    }

    private static Map<Integer, Integer> parseInput(String[] pairInsertions) {
        Map<Integer, Integer> map = new HashMap<>();
        for(String pair : pairInsertions) {
            String[] splits = pair.replaceAll(" ", "").split("->");
            Integer key = key(splits[0].charAt(0)) * 100 + key(splits[0].charAt(1));
            Integer val = key(splits[1].charAt(0));
//            System.out.println(pair + " -> " + key + ":" + val);
            map.put(key, val);
        }
        return map;
    }

    private static Integer key(char ch) {
        return ch - 'A' + 1;
    }

    private static String samplePolymerTemplateInput() {
        return "NNCB";
    }
    private static String[] samplePairInsertionInput() {
        return new String[] {
                "CH -> B", "HH -> N", "CB -> H", "NH -> C", "HB -> C", "HC -> B", "HN -> C", "NN -> C",
                "BH -> H", "NC -> B", "NB -> B", "BN -> B", "BB -> N", "BC -> B", "CC -> N", "CN -> C"
        };
    }

    private static String myPolymerTemplateInput() {
        return "KFFNFNNBCNOBCNPFVKCP";
    }
    private static String[] myPairInsertionInput() {
        return new String[] {
                "PB -> F", "KC -> F", "OB -> H", "HV -> N", "FS -> S", "CK -> K", "CC -> V", "HF -> K", "VP -> C", "CP -> S", "HO -> N", "OS -> N", "HS -> O", "HB -> F", "OH -> V", "PP -> B", "BS -> N", "VS -> F", "CN -> B", "KB -> O", "KH -> B", "SS -> K", "NS -> B", "BP -> V", "FB -> S", "PV -> O", "NB -> S", "FC -> F", "VB -> P", "PC -> O", "VF -> K", "BV -> K", "OO -> B", "PN -> N", "NH -> H", "SP -> B", "KF -> O", "BN -> F", "OF -> C", "VV -> H", "BB -> P", "KN -> H", "PO -> C", "BH -> O", "HC -> B", "VO -> O", "FV -> B", "PK -> V", "KO -> H", "BK -> V", "SC -> S", "KV -> B", "OV -> S", "HK -> F", "NP -> V", "VH -> P", "OK -> S", "SO -> C", "PF -> C", "SH -> N", "FP -> V", "CS -> C", "HH -> O", "KK -> P", "BF -> S", "NN -> O", "OC -> C", "CB -> O", "BO -> V", "ON -> F", "BC -> P", "NO -> N", "KS -> H", "FF -> V", "FN -> V", "HP -> N", "VC -> F", "OP -> K", "VN -> S", "NV -> F", "SV -> F", "FO -> V", "PS -> H", "VK -> O", "PH -> P", "NF -> N", "KP -> S", "CF -> S", "FK -> P", "FH -> F", "CO -> H", "SN -> B", "NC -> H", "SK -> P", "CV -> P", "CH -> H", "HN -> N", "SB -> H", "NK -> B", "SF -> H"
        };
    }

}
