package com.alphawang.algorithm.design.lru.interview;

public class Solution_0916_LiuHaoZhi {

    private static class LRUNode {
        public int key;
        public int value;
        public LRUNode previous;
        public LRUNode next;
        public LRUNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.previous = null;
        }
    }
    
    private static class LRUCache {
        private int MAX_CAP = 2;
        private int NOT_FOUND = -1;

        private LRUNode head = null;
        private LRUNode tail = null;
        private int count = 0;
        
        synchronized public int put(int key, int value) {
            LRUNode current = this.head;
            boolean found = false;
            while (current != null)
            {
                if (current.key == key)
                {
                    found = true;
                    current.value = value;
                    break;
                }
                current = current.next;
            }
            if (found) {
                return value;
            }
                

            if (count >= MAX_CAP && this.tail != null)
            {
                this.tail.previous.next = null;
                count --;
            }

            LRUNode tmp = new LRUNode(key, value);
            if (this.head == null)
            {
                this.head = tmp;
                this.tail = tmp;
                count = 1;
                return value;
            } else {
                tmp.next = this.head;
                this.head.previous = tmp;
                
                this.head = tmp;
            }

            
            
            count ++;
            return value;
        }

        synchronized public int get(int key) {
            LRUNode current = this.head;
            while (current != null)
            {
                if (current.key == key)
                {
                    if (current.previous != null) {
                        // move current node to head.
                        current.previous.next = current.next;
                    }
                    if (current.next != null) {
                        current.next.previous = current.previous;
                    }
                        
                    current.previous = null;
                    current.next = this.head;
                    this.head.previous = current;
                    this.head = current;

                    return current.value;
                }
                current = current.next;
            }

            return NOT_FOUND;
        }

        public LRUCache(int cap)
        {
            this.MAX_CAP = cap;
        }
    }
    
    
    public static void main(String args[] ) throws Exception {
        LRUCache cache = new LRUCache( 2 /* capacity */ );
        
        int result;

        cache.put(1, 1);
        cache.put(2, 2);
        result = cache.get(1);       // returns 1
        System.out.println(result);
        
        cache.put(3, 3);    // evicts key 2


        result = cache.get(2);       // returns -1 (not found)
        System.out.println(result);
        
        cache.put(4, 4);    // evicts key 1

        result = cache.get(1);       // returns -1 (not found)
        System.out.println(result);

        result = cache.get(3);       // returns 3
        System.out.println(result);

        result = cache.get(4);       // returns 4
        System.out.println(result);
    }

}
