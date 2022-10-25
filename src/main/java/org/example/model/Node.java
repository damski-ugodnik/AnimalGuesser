package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.utils.LanguageRules;

import java.text.MessageFormat;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
    private String value;
    private Node positiveResultNode;
    private Node negativeResultNode;

    /**
     * used by Jackson Databind
     */
    public Node() {

    }

    public Node(String value) {
        this.value = value;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return positiveResultNode == null && negativeResultNode == null;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getIndefiniteStatement(boolean correct) {
        return correct
                ? value
                : LanguageRules.applyRules(value, "negative.indefinite");
    }

    public String getStatement(boolean correct) {
        Node node = correct ? positiveResultNode : negativeResultNode;
        return getStatementWithSpecifiedName(correct, node.getValue());
    }

    public String getStatementWithSpecifiedName(boolean correct, String name) {
        String format = correct
                ? LanguageRules.applyRules(value, "positive")
                : LanguageRules.applyRules(value, "negative");
        return MessageFormat.format(format, LanguageRules.applyRules(name, "animalName"));
    }

    public Node getPositiveResultNode() {
        return positiveResultNode;
    }

    public void setPositiveResultNode(Node positiveResultNode) {
        this.positiveResultNode = positiveResultNode;
    }

    public Node getNegativeResultNode() {
        return negativeResultNode;
    }

    public void setNegativeResultNode(Node negativeResultNode) {
        this.negativeResultNode = negativeResultNode;
    }

    @JsonIgnore
    public String getQuestion() {
        return isLeaf()
                ? LanguageRules.applyRules(value, "guessAnimal")
                : LanguageRules.applyRules(value, "question");
    }

    public static Node of(String name) {
        return new Node(name);
    }

    public static Node addNewNode(String currentNodeName, String statement, String newValue, boolean isNewCorrect) {
        Node newNode = Node.of(statement);
        Node newLeaf = Node.of(newValue);
        Node oldLeaf = Node.of(currentNodeName);
        newNode.setPositiveResultNode(isNewCorrect ? newLeaf : oldLeaf);
        newNode.setNegativeResultNode(isNewCorrect ? oldLeaf : newLeaf);
        return newNode;
    }
}
