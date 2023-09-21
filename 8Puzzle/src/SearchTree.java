import java.util.*;

public class SearchTree {
    private Node root;
    private String goalState;

    public SearchTree(Node root, String goalState) {
        this.root = root;
        this.goalState = goalState;
    }

    public void breadthFirstSearch() {
        Set<String> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        int time = 0;

        queue.offer(root);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            visited.add(currentNode.getState());

            if (currentNode.getState().equals(goalState)) {
                NodeUtil.printSolution(currentNode, visited, root, time);
                return;
            }

            List<String> successors = NodeUtil.getSuccessors(currentNode.getState());
            for (String successorState : successors) {
                if (!visited.contains(successorState)) {
                    Node child = new Node(successorState);
                    currentNode.addChild(child);
                    child.setParent(currentNode);
                    queue.offer(child);
                }
            }
            time++;
        }
    }

    public void depthFirstSearch() {
        Set<String> visited = new HashSet<>();
        MyQueue<Node> stack = new MyQueue<>(); 
        int time = 0;

        stack.enqueue(root); 

        while (!stack.isEmpty()) {
            Node currentNode = stack.dequeue(); 
            visited.add(currentNode.getState());

            if (currentNode.getState().equals(goalState)) {
                NodeUtil.printSolution(currentNode, visited, root, time);
                return;
            }

            List<String> successors = NodeUtil.getSuccessors(currentNode.getState());
            for (String successorState : successors) {
                if (!visited.contains(successorState)) {
                    Node child = new Node(successorState);
                    currentNode.addChild(child);
                    child.setParent(currentNode);
                    stack.enqueue(child); // Cambio de push a enqueue
                }
            }
            time++;
        }
    }

    public void uniformCostSearch() {
        Set<String> visited = new HashSet<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodePriorityComparator());
        int time = 0;

        root.setCost(0);
        priorityQueue.offer(root);

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            visited.add(currentNode.getState());

            if (currentNode.getState().equals(goalState)) {
                NodeUtil.printSolution(currentNode, visited, root, time);
                return;
            }

            List<String> successors = NodeUtil.getSuccessors(currentNode.getState());
            for (String successorState : successors) {
                if (!visited.contains(successorState)) {
                    Node child = new Node(successorState);
                    currentNode.addChild(child);
                    child.setParent(currentNode);
                    child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), 0);
                    priorityQueue.offer(child);
                }
            }
            time++;
        }
    }
}
