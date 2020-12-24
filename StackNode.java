// Author: Roman Smith
// Class/Assignment: CSE 205/
// Due Date: July 7, 2020
// Description: a templated class for a node in a stack

public class StackNode<Type> {
    // variables
    private Type data;
    private StackNode<Type> next;

    // default constructor
    public StackNode() {
        this.data = null;
        this.next = null;
    } // StackNode()

    // constructor with data but not next
    public StackNode(Type data) {
        this.data = data;
        this.next = null;
    } // StackNode()

    // constructor with both data and next
    public StackNode(Type data, StackNode<Type> next) {
        this.data = data;
        this.next = next;
    } // StackNode()

    // getters and setters
    public Type getData() { return data; }
    public void setData(Type data) { this.data = data; }
    public StackNode<Type> getNext() { return next; }
    public void setNext(StackNode<Type> next) { this.next = next; }
} // StackNode class
