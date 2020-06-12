package com.alphawang.algorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

public class GraphBFS {

    /**
     * 图的广度优先遍历
     */
    public void bfs(Graph graph, int s, int t) {
        LinkedList<Integer>[] adj = graph.getData();
        
        
    }
    
    private void bfs(LinkedList<Integer>[] adj, int v, int start, int target) {
        if (start == target) {
            return;
        }
        
        // 记录访问过的节点
        boolean[] visited = new boolean[v];
        visited[start] = true;

        // 记录已访问、但是相连顶点还没有访问的节点
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        
        // 记录搜索路径
        int[] prev = new int[v];
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        
        while (queue.size() > 0) {
            int node = queue.poll();
            
            // 遍历node的链表
            for (int i = 0; i < adj[node].size(); i++) {
                int nodeItem = adj[node].get(i);
                if (!visited[nodeItem]) {
                    prev[nodeItem] = node;
                    
                    if (nodeItem == target) {
                        //found!
                        return;
                    }
                    
                    visited[nodeItem] = true;
                    queue.add(nodeItem);
                }
            }
        }
    }

}
