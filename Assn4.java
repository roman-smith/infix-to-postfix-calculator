// Author: Roman Smith
// Class/Assignment: CSE 205/
// Due Date: July 7, 2020
// Description: a calculator that translates infix to postfix expressions and solves them

import java.util.Scanner;

public class Assn4 {
    public static void main(String[] args) {
        // variables
        String userInput; // for holding the infix equation the user enters
        String postFix = ""; // for holding the postfix expression that we will make from userInput
        Scanner in = new Scanner(System.in);
        Stack<Character> characterStack = new Stack<>(); // stack for parentheses check and converting to postfix
        Stack<Double> doubleStack = new Stack<>(); // stack for evaluating the postfix
        double result = 0.0; // for storing the current result of the expression
        boolean goodForm = true; // for storing whether or not the user's input is in a usable form

        // collect user input
        System.out.println("Welcome to the CSE205 Calculator!\n");
        System.out.println("Please input an expression to resolve.");
        System.out.println("::> ");

        userInput = in.nextLine();


        /*
        TASK 1: PARENTHESES MATCHING
         */

        System.out.print("Running parentheses check... ... ... ");

        // go through each character of the input string one at a time
        for (int index = 0; index < userInput.length(); index++) {

            if (goodForm) { // if the user's input is still in good form, as far as we know

                if (userInput.charAt(index) == '(') { // if it is a '(', add it to the stack
                    characterStack.push(new StackNode<Character>('('));

                } else if (userInput.charAt(index) == ')') { // if it is a ')',

                    if (characterStack.count() > 0) { // if there are still parentheses left on the stack, pop one
                        characterStack.pop();

                    } else { // if there are no parentheses left on the stack, the user's input was malformed
                        goodForm = false;
                        System.out.println("Your statement is malformed. Parentheses do not align.");
                    }
                }
            }
        }

        // we have now gone through the string and must check to see if there were too many open parentheses
        if (goodForm) { // if it passed the closed parentheses check

            if (characterStack.count() != 0) { // if there are still open parentheses left on the stack, there were too many
                goodForm = false;
                System.out.println("Your statement is malformed. Parentheses do not align.");
            }
        }

        // print result of test
        if (goodForm) { // if we are still in good form after both checks
            System.out.println("OK!");
        }


        /*
        TASK 2: INFIX TO POSTFIX
         */

        if (goodForm) { // if the form was bad to begin with, we can't do this
            System.out.println("Converting to postfix... ... ...");

            // reset characterStack
            characterStack = new Stack<>();

            // create array for tokenized input
            String[] tokenizedInput = userInput.split("((?<=[-+*/()])|(?=[-+*/()]))");

            // walk through index of the tokenized input
            for (int index = 0; index < tokenizedInput.length; index++) {

                // if the character is an open parentheses, always put it on the stack
                if (tokenizedInput[index].equals("(")) {
                    characterStack.push(new StackNode<Character>(tokenizedInput[index].charAt(0)));

                    // if it is mult. or division, check if mult. or division is on the head, if so pop and append, push on stack
                } else if (tokenizedInput[index].equals("*") || tokenizedInput[index].equals("/")) {

                    if (characterStack.count() != 0) { // if the stack isn't empty
                        if (characterStack.peek().compareTo('*') == 0 || characterStack.peek().compareTo('/') == 0) {
                            postFix = postFix + characterStack.peek() + " "; // adds the current head to the postfix string with a space
                            characterStack.pop();
                        }
                    }

                    characterStack.push(new StackNode<Character>(tokenizedInput[index].charAt(0)));

                    // if it is add. or sub., check if there is an operator at the head, if so pop and append, push on stack
                } else if (tokenizedInput[index].equals("+") || tokenizedInput[index].equals("-")){

                    if (characterStack.count() != 0) { // if the stack isn't empty
                        if (characterStack.peek().compareTo('+') == 0 || characterStack.peek().compareTo('-') == 0 || characterStack.peek().compareTo('*') == 0 || characterStack.peek().compareTo('/') == 0) {
                            postFix = postFix + characterStack.peek() + " "; // adds the current head to the postfix string with a space
                            characterStack.pop();
                        }
                    }

                    characterStack.push(new StackNode<Character>(tokenizedInput[index].charAt(0)));

                    // if it is a closed parentheses, peek/pop and append the head until the corresponding ( has been popped
                } else if (tokenizedInput[index].equals(")")) {

                    while (characterStack.peek().compareTo('(') != 0) {
                        postFix = postFix + characterStack.peek() + " "; // adds the current head to the postfix string with a space
                        characterStack.pop();
                    }

                    // get rid of the (
                    characterStack.pop();

                    // else it must be a number which is appended straight to the postfix string
                } else {
                    postFix = postFix + tokenizedInput[index] + " "; // adds the current index of tokenized input to the postfix string with a space
                }
            }

            // empty whatever is left in the stack
            while (characterStack.count() != 0) {
                postFix = postFix + characterStack.peek() + " "; // adds the current head to the postfix string with a space
                characterStack.pop();
            }

            System.out.println("Equation::>" + postFix);
        }


        /*
        TASK 3: POSTFIX EVALUATION
         */

        if (goodForm) { // if the form was bad to begin with, we can't do this
            System.out.println("Evaluating... ... ...");

            // create an array for tokenized postfix
            String[] tokenizedPostfix = postFix.trim().split(" ");

            // walk through each index in the tokenized postfix
            for (int index = 0; index < tokenizedPostfix.length; index++) {

                // only do something if the statement is in good form as far as we know
                if (goodForm) {

                    // if the token is an operator, peek/pop the two operands, perform operation, and push
                    // if there are not two operands to peek/pop, the statement was malformed
                    if (tokenizedPostfix[index].equals("+") || tokenizedPostfix[index].equals("-") || tokenizedPostfix[index].equals("*") || tokenizedPostfix[index].equals("/")) {
                        // local temp variables for storing operands and result of operation
                        double op1 = 0.0;
                        double op2 = 0.0;

                        // verify stack is not empty, if it is the statement is malformed
                        if (doubleStack.count() > 0) {
                            op1 = doubleStack.peek();
                            doubleStack.pop();
                        } else {
                            goodForm = false;
                            System.out.println("Your statement is malformed. Error evaluating the postfix.");
                        }

                        if (doubleStack.count() > 0) {
                            op2 = doubleStack.peek();
                            doubleStack.pop();
                        } else {
                            goodForm = false;
                            System.out.println("Your statement is malformed. Error evaluating the postfix.");
                        }

                        // now we can operate
                        if (tokenizedPostfix[index].equals("+")) {
                            result = op2 + op1;
                        } else if (tokenizedPostfix[index].equals("-")) {
                            result = op2 - op1; // always subtract the most recent number from the older number
                        } else if (tokenizedPostfix[index].equals("*")) {
                            result = op2 * op1;
                        } else if (tokenizedPostfix[index].equals("/")) {
                            result = op2 / op1;
                        }

                        // push the result back on the stack
                        doubleStack.push(new StackNode<>(result));

                        // if it is an operand, we just push it onto the stack
                    } else {
                        doubleStack.push(new StackNode<>(Double.parseDouble(tokenizedPostfix[index])));
                    }
                }
            }

            // after we have gone through each token from the postfix expression, we have our final value or a malformed notice
            if (goodForm) {
                System.out.println("Result = " + result);
            }
        }
    } // psvm()
} // Assn4 class
