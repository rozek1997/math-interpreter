package zad1;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {

    private String input;
    private Stack<Node> stack;
    private Memory memory;
    private String[] buildNodes;
    private Pattern variablePattern;
    private Pattern numericalPattern;
    private Pattern operatorPattern;

    public Parser(Memory memory) {
        this.memory = memory;
        createPatterns();

    }


    public void parseInput(String in) throws ParsingException, NotFoundException {

        this.stack = new Stack<>();
        divideInputString(in);

        if (input.contains("=")) {
            parseAssign();
        } else {
            int temp = parseExpression(0);
            System.out.println(temp);
        }


    }

    public void parseAssign() throws ParsingException, NotFoundException {

        if (isVariable(buildNodes[0])) {
            if (buildNodes[1].equals("=")) {
                int temp = parseExpression(2);
                memory.putValue(buildNodes[0], temp);
                return;
            }
        }

        throw new ParsingException("Error");

    }


    public int parseExpression(int startPoint) throws ParsingException, NotFoundException {

        buildExpressionTree(startPoint);
        if (stack.size() != 1) throw new ParsingException("Error");
        return calculateValue(stack.pop());

    }


    private void buildExpressionTree(int startPoint) throws ParsingException, NotFoundException {

        for (int i = startPoint; i < buildNodes.length; i++) {
            if (isOperator(buildNodes[i])) {
                if (stack.size() < 2) throw new ParsingException("Error");
                Node rightOperand = stack.pop();
                Node leftOperand = stack.pop();
                stack.push(new Node(buildNodes[i], leftOperand, rightOperand));
            } else if (isNumericalValue(buildNodes[i]) || isVariable(buildNodes[i])) {
                stack.push(new Node(buildNodes[i]));
            } else {
                throw new ParsingException("Error");
            }

        }
    }


    private int calculateValue(Node node) throws ParsingException, NotFoundException {

        Node temp = node;
        int leftValue = 0;
        int rightValue = 0;

        if (temp.getLeftNode() != null && temp.getRightNode() != null) {
            leftValue = calculateValue(temp.getLeftNode());
            rightValue = calculateValue(temp.getRightNode());

        } else if (isVariable(temp.getValue())) {
            if (memory.containsKey(temp.getValue()))
                return memory.getValue(temp.getValue());
            else throw new NotFoundException("???");
        } else if (isNumericalValue(temp.getValue()))
            try {
                Integer integer = Integer.parseInt(temp.getValue());
                return integer;
            } catch (Exception e) {
                throw new ParsingException("Error");
            }

        return makeOperation(leftValue, rightValue, temp.getValue());

    }


    private int makeOperation(int leftValue, int rightValue, String operator) throws ParsingException {
        switch (operator) {
            case "*":
                return leftValue * rightValue;
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "/":
                if (rightValue != 0)
                    return leftValue / rightValue;
                else throw new ParsingException("Dividing by zero");

        }

        return 0;
    }

    private void divideInputString(String input) {
        String temp = input;
        this.input = temp;
        temp = StringUtils.normalizeSpace(input);
        temp = temp.replaceAll("\\t", "");
        int index = temp.indexOf("#");
        if (index != -1) {
            temp = temp.substring(0, index);
        }
        this.input = temp;
        this.buildNodes = temp.split(" ");
    }


    private void createPatterns() {
        String patternOperatorString = "^[\\/\\+\\-\\*]{1}$";
        String patternVariableString = "^[a-zA-Z][a-zA-Z_$0-9]*$";
        String patternNumericalString = "^[\\-\\+]?(0|[1-9][0-9]*)$";
        this.operatorPattern = Pattern.compile(patternOperatorString);
        this.variablePattern = Pattern.compile(patternVariableString);
        this.numericalPattern = Pattern.compile(patternNumericalString);
    }


    private boolean isOperator(String check) {

        Matcher matcher = operatorPattern.matcher(check);
        return matcher.matches();
    }

    private boolean isVariable(String check) {

        Matcher matcher = variablePattern.matcher(check);
        return matcher.matches();
    }

    private boolean isNumericalValue(String check) {

        Matcher matcher = numericalPattern.matcher(check);
        return matcher.matches();

    }


}
