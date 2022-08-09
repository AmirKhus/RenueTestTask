package com.example.renuetesttask;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Tree {
    static class TreeNode {
        TreeMap<Character, TreeNode> children = new TreeMap<>();
        List<String[]> leaf = new ArrayList<String[]>();
    }

    TreeNode root = new TreeNode();

    public void add(String[] line, int n) {
        TreeNode node = root;
        var s = line[n];
        for (char ch : s.toLowerCase().toCharArray()) {
            if (!node.children.containsKey(ch)) {
                node.children.put(ch, new TreeNode());
            }
            node = node.children.get(ch);
        }
        node.leaf.add(line);
    }

    public List<String[]> getAllLeaf(String s) {
        TreeNode node = root;
        for (char ch : s.toLowerCase().toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return new ArrayList<>();
            } else {
                node = node.children.get(ch);
            }
        }
        var result = new ArrayList<TreeNode>();
        getAllChildren(node, result);
        return result.stream().flatMap(x -> x.leaf.stream()).collect(Collectors.toList());
    }

    private void getAllChildren(TreeNode currentRoot, ArrayList<TreeNode> result) {
        if (currentRoot.leaf != null) {
            result.add(currentRoot);
        }
        for (var child : currentRoot.children.keySet()) {
            getAllChildren(currentRoot.children.get(child), result);
        }
    }
}
