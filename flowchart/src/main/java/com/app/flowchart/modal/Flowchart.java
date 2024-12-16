package com.app.flowchart.modal;


import com.app.flowchart.exception.InvalidEdgeException;

import lombok.Data;
import java.util.*;

@Data
public class Flowchart {


    public static long count = 0;
    private final Long id = ++count;


    private Map<Long, Node> nodes = new HashMap<>();


    private List<Edge> edges = new ArrayList<>();


    private Map<Long, List<Long>> adj = new HashMap<>();


    public void addNewEdge(Edge edge) {
        long u = edge.getStartNode().getId();
        long v = edge.getEndNode().getId();
        edges.add(edge);
        adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        adj.computeIfAbsent(v, k -> new ArrayList<>());
        boolean isValid = !isCyclic();
        if (!isValid) {
            removeEdge(edge);
            throw new InvalidEdgeException("Edge is invalid");
        }
        nodes.put(u,edge.getStartNode());
        nodes.put(v,edge.getEndNode());

    }

    public void removeEdge(Edge edge) {
        long u = edge.getStartNode().getId();
        long v = edge.getEndNode().getId();
        edges.remove(edge);
        adj.get(u).remove(v);
        boolean secondNodeExistsInAnyEdge = adj.values().stream().anyMatch(x -> x.contains(v));
        if (!secondNodeExistsInAnyEdge)
            nodes.remove(v);
    }

    public List<Node> getAllDirectNodes(long nodeId) {
        return adj.get(nodeId).stream().map(nextNodeId -> nodes.get(nextNodeId)).toList();
    }

    public List<Node> getAllNodes(long nodeId) {
        return dfs(nodeId).stream().map(nextNodeId -> nodes.get(nextNodeId)).toList();
    }

    public List<Long> dfs(Long u) {
        List<Long> allNodes = new ArrayList<>();
        if (adj.containsKey(u)) {
            for(Long x : adj.get(u)) {
                allNodes.addAll(dfs(x));
            }
        }

        allNodes.add(u);
        return allNodes;
    }


    public boolean isCyclic() {
        Set<Long> visited = new HashSet<>();
        Set<Long> recursionStack = new HashSet<>();

        List<Long> nodeIds = adj.keySet().stream().toList();


        for (Long node : nodeIds) {
            if (dfsCycleCheck(node, visited, recursionStack)) {
                return true; // Cycle detected
            }
        }
        return false; // No cycle detected
    }


    private boolean dfsCycleCheck(Long node, Set<Long> visited, Set<Long> recursionStack) {
        if (recursionStack.contains(node)) {
            return true; // If the node is already in the recursion stack, cycle detected
        }

        if (visited.contains(node)) {
            return false; // If already visited, no need to check further
        }

        visited.add(node);
        recursionStack.add(node);

        // Check all adjacent nodes (outgoing edges)
        if (adj.containsKey(node)) {
            for (Long neighbor : adj.get(node)) {
                if (dfsCycleCheck(neighbor, visited, recursionStack)) {
                    return true;
                }
            }
        }

        recursionStack.remove(node);
        return false;
    }
}
