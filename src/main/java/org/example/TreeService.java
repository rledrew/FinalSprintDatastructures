package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeService {

    private final TreeRepository treeRepository;

    @Autowired
    public TreeService(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    public Tree saveTree(List<Integer> numbers) {
        BinarySearchTree bst = new BinarySearchTree();
        for (Integer number : numbers) {
            bst.insert(number);
        }
        Tree tree = new Tree();
        tree.setInputNumbers(numbers.toString());
        try {
            tree.setTreeStructure(bst.toJson());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON: " + e.getMessage());
        }
        return treeRepository.save(tree);
    }

    public List<Tree> getAllTreesSortedByNewest() {
        return treeRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    class BinarySearchTree {
        Node root;

        void insert(int value) {
            root = insertRec(root, value);
        }

        Node insertRec(Node root, int value) {
            if (root == null) {
                root = new Node(value);
                return root;
            }
            if (value < root.value)
                root.left = insertRec(root.left, value);
            else if (value > root.value)
                root.right = insertRec(root.right, value);
            return root;
        }

        String toJson() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(toMap(root));
        }

        Map<String, Object> toMap(Node node) {
            Map<String, Object> map = new HashMap<>();
            if (node == null) {
                return map;
            }
            map.put("value", node.value);
            map.put("left", toMap(node.left));
            map.put("right", toMap(node.right));
            return map;
        }
    }
}