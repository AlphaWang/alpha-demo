package com.alphawang.algorithm.graph;

import java.util.LinkedList;

public class Graph {
    
    // 顶点个数
    private int v;
    // 邻接表表示法
    private LinkedList<Integer>[] adj;
    
    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }
    
    // 无向图 加一条边
    public void addEdge(int s, int t) {
        adj[s].add(t);
        adj[t].add(s);
    }
    
    public LinkedList<Integer>[] getData() {
        return this.adj;
    }
    

}
