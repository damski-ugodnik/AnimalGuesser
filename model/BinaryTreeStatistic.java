package model;

import utils.Constants;
import utils.LanguageRules;

import java.util.ArrayList;
import java.util.List;


public class BinaryTreeStatistic {
    private final String rootNodeValue;
    private int numberOfNodes;
    private int numberOfLeaves;
    private int numberOfStatements;
    private final int height;
    private final int minimumLeafDepth;
    private final float averageLeaveDepth;
    private final List<Integer> leaveDepths;

    public static BinaryTreeStatistic of(Node root) {
        return new BinaryTreeStatistic(root);
    }

    private BinaryTreeStatistic(Node rootNode) {
        this.leaveDepths = new ArrayList<>();
        this.rootNodeValue = rootNode.getValue();
        this.numberOfLeaves = 0;
        this.numberOfNodes = 0;
        this.numberOfStatements = 0;
        calculateStats(rootNode);
        this.minimumLeafDepth = minimumDepth(rootNode) - 1;
        this.height = maxDepth(rootNode) - 1;
        calculateDepths(rootNode, 0);
        var sum = leaveDepths.stream().mapToInt(Integer::intValue).sum();
        this.averageLeaveDepth = (float) sum / leaveDepths.size();
    }

    private void calculateStats(Node root) {
        numberOfNodes++;
        if (root.isLeaf()) {
            numberOfLeaves++;
            return;
        }
        numberOfStatements++;
        calculateStats(root.getNegativeResultNode());
        calculateStats(root.getPositiveResultNode());
    }

    private void calculateDepths(Node root, int depth) {
        if (root.isLeaf()) {
            leaveDepths.add(depth);
            return;
        }
        depth++;
        calculateDepths(root.getNegativeResultNode(), depth);
        calculateDepths(root.getPositiveResultNode(), depth);
    }

    private int minimumDepth(Node root) {
        if (root == null) {
            return 0;
        }
        if (root.isLeaf()) {
            return 1;
        }
        if (root.getNegativeResultNode() == null) {
            return minimumDepth(root.getPositiveResultNode()) + 1;
        }
        if (root.getPositiveResultNode() == null) {
            return minimumDepth(root.getNegativeResultNode()) + 1;
        }
        return Math.min(minimumDepth(root.getPositiveResultNode()),
                minimumDepth(root.getNegativeResultNode())) + 1;
    }

    private int maxDepth(Node node) {
        if (node == null) {
            return 0;
        } else {
            int leftDepth = maxDepth(node.getPositiveResultNode());
            int rightDepth = maxDepth(node.getNegativeResultNode());

            if (leftDepth > rightDepth) {
                return (leftDepth + 1);
            } else {
                return (rightDepth + 1);
            }
        }
    }

    @Override
    public String toString() {
        return LanguageRules.getMessage("tree.stats.title") + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.root", rootNodeValue) + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.nodes", numberOfNodes) + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.animals", numberOfLeaves) + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.statements", numberOfStatements) + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.height", height) + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.minimum", minimumLeafDepth) + Constants.LINE_SEPARATOR +
                LanguageRules.getMessage("tree.stats.average", averageLeaveDepth) + Constants.LINE_SEPARATOR;
    }
}