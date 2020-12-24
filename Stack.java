// Author: Roman Smith
// Class/Assignment: CSE 205/
// Due Date: July 7, 2020
// Description: a templated class for a stack

public class Stack<Type extends Comparable<Type>> {
    private StackNode<Type> head;
    private int size;

    // constructor, always default, empty stack
    public Stack() {
        this.head = null;
        this.size = 0;
    } // Stack()

    // push a node onto the stack
    public void push(StackNode<Type> nodeToPush) {
        // if stack is empty, first node is the head
        if (this.head == null) {
            this.head = nodeToPush;
        } else { // if it is not empty, the new node will reference the current head and then the head with move
            nodeToPush.setNext(this.head);
            this.head = nodeToPush;
        }
        this.size++; // increase the size by one
    } // push()

    // pop the top node off the stack
    public void pop() throws NullPointerException {
        // if the stack is empty, throw an exception
        if (this.head == null) {
            throw new NullPointerException("Error in pop method in Stack class: stack empty");
        } else { // else delete the head and adjust the size
            this.head = this.head.getNext();
            this.size--; // decrease size by one
        }
    } // pop()

    // return the data in the top node but do not delete it
    public Type peek() throws NullPointerException {
        // if the stack is empty, throw an exception
        if (this.head == null) {
            throw new NullPointerException("Error in peek method in Stack class: stack empty");
        } else { // else return the data on the top element
            return this.head.getData();
        }
    } // peek()

    // return the current count/size of the stack
    public int count() {
        return this.size;
    } // count()
} // Stack class
