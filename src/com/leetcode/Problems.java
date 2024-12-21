package com.leetcode;

import java.util.*;

public class Problems {

    public int shortestPathLength(int[][] graph) { return 1; }

    // System.out.println(minCostConnectPoints(new int[][]{{-8,14},{16,-18},{-19,-13},{-18,19},{20,20},{13,-20},{-15,9},{-4,-8}}));
    public static int minCostConnectPoints(int[][] points) {
        int numberOfNodes = points.length;
        Integer [][] graph = new Integer[(numberOfNodes*(numberOfNodes-1))/2][3];

        int k = 0;
        for(int i=0; i<points.length; i++) {
            for(int j=i+1; j<points.length; j++) {
                graph[k][0] = i;
                graph[k][1] = j;
                graph[k][2] = Math.abs(points[i][0]-points[j][0]) + Math.abs(points[i][1]-points[j][1]);
                ++k;
            }
        }
        Arrays.sort(graph, (node1, node2) -> Integer.compare(node1[2], node2[2]));

        Integer[][] minimumSpanningGraph = new Integer[numberOfNodes-1][3];

        int numberOfEdges = -1, cost = 0;
        for(int i=0; i<graph.length; i++) {
            if(numberOfEdges == numberOfNodes-2) break;
            minimumSpanningGraph[++numberOfEdges] = graph[i];
            cost+=graph[i][2];
            if(isGraphCircular(minimumSpanningGraph)) {
                minimumSpanningGraph[numberOfEdges] = null;
                --numberOfEdges;
                cost-=graph[i][2];
            }
        }

        for (Integer[] ints : minimumSpanningGraph) {
            System.out.println(Arrays.toString(ints));
        }
        return cost;
    }

    public static boolean isGraphCircular(Integer[][] graph) {
        HashMap<Integer, ArrayList<Integer>> nodeOfGraph = new HashMap<>();
        for (int i = 0; i < graph.length; i++) {
            if(graph[i][0]==null)  continue;
            if (nodeOfGraph.containsKey(graph[i][0])) {
                nodeOfGraph.get(graph[i][0]).add(graph[i][1]);
            } else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(graph[i][1]);
                nodeOfGraph.put(graph[i][0], temp);
            }
        }

        Integer[] nodes = nodeOfGraph.keySet().toArray(new Integer[0]);

        for (int i = 0; i < nodes.length; i++) {
            if (isGraphCircularUtil(nodes[i], nodeOfGraph, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGraphCircularUtil(Integer parent, HashMap<Integer, ArrayList<Integer>> graph, HashSet<Integer> visitedSet) {
        if(parent == null) return false;
        if(visitedSet.contains(parent)) {
            return true;
        } else {
            visitedSet.add(parent);
            if(graph.containsKey(parent)) {
                Integer[] childs = graph.get(parent).toArray(new Integer[0]);
                for(int i=0; i<childs.length; i++) {
                    if(isGraphCircularUtil(childs[i], graph, visitedSet)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public static int findDuplicate(int[] nums) {
        int len = nums.length;
        for (int num : nums) {
            int idx = Math.abs(num);
            if (nums[idx] < 0) {
                return idx;
            }
            nums[idx] = -nums[idx];
        }
        return len;
    }

    public static int[] kWeakestRows(int[][] mat, int k) {

        PriorityQueue<int[]> weakestRows = new PriorityQueue<>(k,(node1, node2) -> {
            if(node1[0] == node2[0]) {
                return Integer.compare(node1[1], node2[1]);
            } else {
                return Integer.compare(node1[0], node2[0]);
            }
        });

        for(int i=0; i<mat.length; i++) {
            int[] temp = new int[2];
            temp[0] = binarySearchSoliderEnd(mat[i], 0, mat[i].length-1)+1;
            temp[1] = i;
            weakestRows.add(temp);
        }

        int[] result = new int[k];
        for(int i=0; i<k; i++) {
            result[i] = weakestRows.poll()[1];
        }
        return result;
    }

    public static int binarySearchSoliderEnd(int[] array, int startPosition, int endPosition) {
        if(endPosition < startPosition) return -1;
        int mid = startPosition + (int)Math.floor((endPosition-startPosition)/2);
        int current = array[mid];
        if(current == 1) {
            if((mid+1 < array.length && array[mid+1] == 0) || (mid == array.length-1)) return mid;
            else return binarySearchSoliderEnd(array, mid+1, endPosition);
        } else {
            if(mid-1 > -1 && array[mid-1] == 1) return mid-1;
            else return binarySearchSoliderEnd(array, startPosition, mid-1);
        }
    }

    static ArrayList<String> generateParanthesis(int n) {
        ArrayList<String> result = new ArrayList<>();
        if(n==0) return result;
        addParanthesis("", 0, 0, n, result);
        return result;
    }

    static void addParanthesis(String brackets, int noOfOB, int noOfCB, int n, ArrayList<String> result) {
        if(brackets.length() == n*2) {
            result.add(brackets);
            return;
        }
        if(noOfOB < n)
            addParanthesis(brackets+"(", noOfOB+1, noOfCB, n, result);
        if(noOfCB < noOfOB)
            addParanthesis(brackets+")", noOfOB, noOfCB+1, n, result);
    }

    public static int countVowelStrings(int n) {
        ArrayList<String> vowelStrings = new ArrayList<>();
        generateVowelStrings("", new String[]{"a", "e", "i", "o", "u"}, 0, n, vowelStrings);
        for(int i=0; i<vowelStrings.size(); i++) {
            System.out.println(vowelStrings.get(i));
        }
        return vowelStrings.size();

    }

    public static void generateVowelStrings(String vowelString, String[] vowels, int startIndex, int n, ArrayList<String> result) {
        if(vowelString.length() == n || startIndex == vowels.length-1) {
            result.add(vowelString);
            return;
        }

        for(int currentIndex=startIndex; currentIndex<vowels.length; currentIndex++) {
            generateVowelStrings(vowelString+vowels[currentIndex], vowels, currentIndex, n, result);
        }
    }

    public static List<List<Integer>> groupThePeople(int[] groupSizes) {
        HashMap<Integer, List<Integer>> memberPositionMap = new HashMap<>();
        for(int i=0; i<groupSizes.length; i++) {
            List<Integer> tempPositions = new ArrayList<>();
            if(memberPositionMap.containsKey(groupSizes[i])) {
                tempPositions = memberPositionMap.get(groupSizes[i]);
                tempPositions.add(i);
                memberPositionMap.put(groupSizes[i], tempPositions);
            } else {
                tempPositions.add(i);
                memberPositionMap.put(groupSizes[i], tempPositions);
            }
        }

        Iterator keyIterator = memberPositionMap.keySet().iterator();
        List<List<Integer>> result = new ArrayList<>();

        while(keyIterator.hasNext()) {
            int key = (int)keyIterator.next();
            List<Integer> indexList = memberPositionMap.get(key);
            Integer[] tempArray = new Integer[key];
            for(int i=0; i<indexList.size(); i++) {
                if(i != 0 && i%key == 0) {
                    result.add(Arrays.asList(tempArray));
                    tempArray = new Integer[key];
                    tempArray[0] = indexList.get(i);
                } else {
                    tempArray[i%key] = indexList.get(i);
                }
            }
            result.add(Arrays.asList(tempArray));
        }
        return result;
    }

    public static int minDeletions(String s) {
        HashMap<Character, Integer> characterCounts = new HashMap();
        for(int i=0; i<s.length(); i++) {
            if(characterCounts.containsKey(s.charAt(i))) {
                characterCounts.put(s.charAt(i), (characterCounts.get(s.charAt(i)))+1);
            } else {
                characterCounts.put(s.charAt(i),1);
            }
        }
        Integer[] counts = characterCounts.values().toArray(new Integer[0]);
        Arrays.sort(counts);
        int totalNumberOfDeletion = 0;
        for(int i=counts.length-2; i>=0; i--) {
            if(counts[i] >= counts[i+1]) {
                int temp = counts[i];
                counts[i] = counts[i+1]-1;
                totalNumberOfDeletion+=(temp-counts[i]);
            }
        }
        return totalNumberOfDeletion;
    }

    // find cycle in directed graph
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> courseAndJoints = new ArrayList<>(numCourses);

        for(int i=0; i<numCourses; i++) {
            courseAndJoints.add(new ArrayList<>());
        }

        for(int i=0; i<prerequisites.length; i++) {
            courseAndJoints.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }

        boolean[] visitedCourses = new boolean[numCourses];
        for(int i=0; i<numCourses; i++) {
            if(!visitedCourses[i]) {
                if(!trackCourse(courseAndJoints, visitedCourses, new boolean[numCourses], i)) return false;
            }
        }
        return true;
    }

    public static boolean trackCourse( ArrayList<ArrayList<Integer>> courseAndJoints, boolean[] visitedCourses, boolean[] dfsVisitedCourses, Integer parentCourse) {
        if(dfsVisitedCourses[parentCourse]) {
            return false;
        } else {
            visitedCourses[parentCourse] = true;
            dfsVisitedCourses[parentCourse] = true;
            ArrayList<Integer> requiredCourses = courseAndJoints.get(parentCourse);
            if(requiredCourses.size() == 0) {
                dfsVisitedCourses[parentCourse] = false;
                return true;
            }
            for(int i=0; i<requiredCourses.size(); i++) {
                if(!trackCourse(courseAndJoints, visitedCourses, dfsVisitedCourses, requiredCourses.get(i))) return false;
            }
            dfsVisitedCourses[parentCourse] = false;
            return true;
        }
    }

    public static void main(String[] args) {
//        System.out.println(reverseString("Aditya"));
//        System.out.println(5/2);
//        findCountOfCharacter("aaabbc");


        Integer[] array = new Integer[]{1,2,3,4,5,6};

//        Arrays.stream(array).map(item -> 2*item).forEach(System.out::println);
//        Arrays.stream(array).filter(item -> item%2==0).forEach(System.out::println);
//        System.out.println(Arrays.stream(array).reduce(0, (result, item) -> result+item));
//        int output = Arrays.stream(array)
//                .map(item -> 2*item)
//                .filter(item -> item%2==0)
//                .reduce(0, (result, item) -> result+item);
//        System.out.println(output);
//        System.out.println(Arrays.stream(array).min().getAsInt());
//        System.out.println(Arrays.stream(array).max().getAsInt());
//        Arrays.stream(array).sorted().forEach(System.out::println);
//        Arrays.stream(array).skip(1).limit(1).forEach(System.out::println);
//        System.out.println(Arrays.stream(array).min().getAsInt());
//        System.out.println(Arrays.stream(array).max().getAsInt());
//        Arrays.stream(array).distinct().forEach(System.out::println);
//        System.out.println(Arrays.stream(array).allMatch(item -> item%1==0));
//        System.out.println(Arrays.stream(array).anyMatch(item -> item%5==0));
//        System.out.println(Arrays.stream(array).noneMatch(item -> item%5==0));
//        Arrays.stream(array).map(item -> 2*item).collect(Collectors.toList()).forEach(System.out::println);

//        Arrays.stream(array).forEach(System.out::println);






//        System.out.println(minOperations(new int[]{
//                5207,5594,477,6938,8010,7606,2356,6349,3970,751,5997,6114,9903,3859,6900,7722,2378,1996,8902,228,4461,
//                90,7321,7893,4879,9987,1146,8177,1073,7254,5088,402,4266,6443,3084,1403,5357,2565,3470,3639,9468,8932,
//                3119,5839,8008,2712,2735,825,4236,3703,2711,530,9630,1521,2174,5027,4833,3483,445,8300,3194,8784,279,
//                3097,1491,9864,4992,6164,2043,5364,9192,9649,9944,7230,7224,585,3722,5628,4833,8379,3967,5649,2554,5828,
//                4331,3547,7847,5433,3394,4968,9983,3540,9224,6216,9665,8070,31,3555,4198,2626,9553,9724,4503,1951,9980,
//                3975,6025,8928,2952,911,3674,6620,3745,6548,4985,5206,5777,1908,6029,2322,2626,2188,5639
//        },565610));

    }

    public static String reverseString(String inputString) {
        StringBuilder inputStringBuilder = new StringBuilder(inputString);
        for(int i=0; i<(inputStringBuilder.length())/2; i++) {
            Character temp = inputStringBuilder.charAt(i);
            inputStringBuilder.setCharAt(i, inputStringBuilder.charAt(inputStringBuilder.length()-1-i));
            inputStringBuilder.setCharAt(inputStringBuilder.length()-1-i, temp);
        }
        return inputStringBuilder.toString();
    }

    public static void findCountOfCharacter(String inputString) {
        HashMap<Character, Integer> countMap = new HashMap<>();
        for(int i=0; i<inputString.length(); i++) {
            if(countMap.containsKey(inputString.charAt(i))) {
                countMap.put(inputString.charAt(i), countMap.get(inputString.charAt(i))+1);
            } else {
                countMap.put(inputString.charAt(i), 1);
            }
        }

        Iterator it = countMap.entrySet().iterator();
        while(it.hasNext()) {
            Object entrySet  = it.next();
            System.out.println(entrySet.toString());
        }
    }

    public static int minOperations(int[] nums, int x) {
        int sum = Arrays.stream(nums).sum(), sumSoFar=0;
        int startPointer=0, endPointer=0, target = sum-x, maxLength = -1;
        if(x > sum) return -1;
        while(true) {
            if(endPointer > startPointer && sumSoFar >= target) {
                sumSoFar-=nums[startPointer++];
            } else {
                if(endPointer > nums.length-1) break;
                sumSoFar+=nums[endPointer++];
            }

            if(sumSoFar == target) {
                int currentLength = endPointer-startPointer;
                maxLength  = currentLength > maxLength ? currentLength : maxLength;
            }
        }
        return maxLength == -1 ? -1 : nums.length - maxLength;
    }

    public static boolean isSubsequence(String s, String t) {
        if(s.length()==0) return true;
        int ps=0, pt=-1;
        while (ps < s.length() && pt < t.length()-1) {
            while (pt < t.length()-1) {
                ++pt;
                if(s.charAt(ps) == t.charAt(pt)) {
                    ++ps;
                    break;
                }
            }
            if(ps == s.length()) return true;
        }
        return false;
    }

//    public int numIdenticalPairs(int[] nums) {
//        HashMap<Integer, ArrayList<Integer>> numberMap = new HashMap<>();
//        for(int i=0; i<nums.length; i++) {
//            ArrayList<Integer> temp = null;
//            if(numberMap.containsKey(nums[i])) {
//                temp = numberMap.get(nums[i]);
//                temp.add(i);
//                numberMap.put(nums[i], temp);
//            } else {
//                temp = new ArrayList<Integer>();
//                temp.add(i);
//                numberMap.put(nums[i], i);
//            }
//        }
//
//        int result = 0;
//        Iterator it = numberMap.values().iterator();
//        while(it.hasNext()) {
//            ArrayList<Integer> temp = (ArrayList<Integer>) it.next();
//            result+=(temp.size())*((temp.size())-1)/2;
//        }
//
//        return result;
//    }

//    public static int minOperations(int[] nums, int x) {
//        int sum = Arrays.stream(nums).sum(), sumSoFar=0;
//        int startPointer=0, endPointer=-1, target = sum-x;
//        for(int i=0; i<nums.length; i++) {
//            if(sumSoFar == target) break;
//            sumSoFar+=nums[i];
//            ++endPointer;
//            while(sumSoFar > target) {
//                sumSoFar-=nums[startPointer];
//                ++startPointer;
//            }
//        }
//        System.out.format("%d %d\n",startPointer, endPointer);
//        return nums.length-(endPointer-startPointer);
//    }

//    class Cards  extends Comparable{
//
//        Suits suit;
//        Ranks rank;
//        int priority;
//
//        Cards(Suits suit, Ranks ranks) {
//            this.suit = suit;
//            this.ranks = rank;
//            this.priority = suit.valueOf()
//        }
//
//        Cards(suit, Ranks ranks, priority) {
//
//        }
//
//        compareTo(this, target) {
//            if(this.rank == target.rank) {
//                Suits.compareTo(this.Suit, target.suit)
//            } else {
//                Ranks.compareTo(this.rank, target.rank)
//            }
//        }
//
//    }
//
//
//
//
//    enum Ranks {
//    "ace", "king", "queen", "jack", "10", "9", "8", "7", "6", "5", "4", "3", "2"
//    }
//
//    enum Suits {
//    "heart", "spade", , "club", "diamond"
//    }
//
//
//    class Deck {
//
//        enum ranks =
//
//        // Deck() {
//
//        //     this.cards = this.iterate.suits --> List<Types> --> ranks --> List<Type>
//        // }
//
//
//    }

//    public static int minCostConnectPoints(int[][] points) {
//        int numberOfNodes = points.length;
//        Integer[][] graph = new Integer[(numberOfNodes*(numberOfNodes-1))/2][3];
//
//        int k = 0;
//        for(int i=0; i<points.length; i++) {
//            for(int j=i+1; j<points.length; j++) {
//                graph[k][0] = i;
//                graph[k][1] = j;
//                graph[k][2] = Math.abs(points[i][0]-points[j][0]) + Math.abs(points[i][1]-points[j][1]);
//                ++k;
//            }
//        }
//        Arrays.sort(graph, (node1, node2) -> Integer.compare(node1[2], node2[2]));
//
//        Integer[][] minimumSpanningGraph = new Integer[numberOfNodes-1][3];
//
//        for (Integer[] ints : graph) {
//            System.out.println(Arrays.toString(ints));
//        }
//
//        int numberOfEdges = -1, cost = 0;
//        for(int i=0; i<graph.length; i++) {
//            if(numberOfEdges == numberOfNodes-2) break;
//            minimumSpanningGraph[++numberOfEdges] = graph[i];
//            cost+=graph[i][2];
//            System.out.println(Arrays.toString(graph[i]));
//            if(isGraphCircular(minimumSpanningGraph)) {
//                minimumSpanningGraph[numberOfEdges] = null;
//                --numberOfEdges;
//                cost-=graph[i][2];
//            }
//        }
//
//        for (Integer[] ints : minimumSpanningGraph) {
//            System.out.println(Arrays.toString(ints));
//        }
//
//        return cost;
//    }
//
//    public static boolean isGraphCircular(Integer[][] graph) {
//        HashMap<Integer, ArrayList<Integer>> nodeOfGraph = new HashMap<>();
//        for (int i = 0; i < graph.length; i++) {
//            if(graph[i][0]==null)  continue;
//            if (nodeOfGraph.containsKey(graph[i][0])) {
//                nodeOfGraph.get(graph[i][0]).add(graph[i][1]);
//            } else {
//                ArrayList<Integer> temp = new ArrayList<>();
//                temp.add(graph[i][1]);
//                nodeOfGraph.put(graph[i][0], temp);
//            }
//        }
//
//        Integer[] nodes = nodeOfGraph.keySet().toArray(new Integer[0]);
//
//        for (int i = 0; i < nodes.length; i++) {
//            if (isGraphCircularUtil(nodes[i], nodeOfGraph, new HashSet<>())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static boolean isGraphCircularUtil(Integer parent, HashMap<Integer, ArrayList<Integer>> graph, HashSet<Integer> visitedSet) {
//        if(parent == null) return false;
//        if(visitedSet.contains(parent)) {
//            return true;
//        } else {
//            visitedSet.add(parent);
//            if(graph.containsKey(parent)) {
//                Integer[] childs = graph.get(parent).toArray(new Integer[0]);
//                for(int i=0; i<childs.length; i++) {
//                    if(isGraphCircularUtil(childs[i], graph, visitedSet)) {
//                        return true;
//                    }
//                }
//            }
//
//        }
//        return false;
//    }

    public static int binarySearch(int[] nums, int start, int end, int target) {
        System.out.format("%d-%d\n", start, end);
        if(end < start) return -1;
        int mid = start + (end - start)/2;
        if(nums[mid] == target) return mid;
        if(target < nums[mid]) {
           int index = binarySearch(nums, start,mid-1, target);
           if(index == -1) {
               if(mid-1 < 0) return 0;
               if(nums[mid-1] <= target) return mid;
           }
           return index;
        } else {
            int index = binarySearch(nums, mid + 1, end, target);
            if(index == -1) {
                if(mid + 1 > nums.length-1) return nums.length;
                if(nums[mid + 1] >= target) return mid + 1;
            }
            return index;
        }
    }

    class MyHashMap {

        int bucketSize  = 10000;
        LinkedList<Node>[] bucket;

        public MyHashMap() {
            bucket = new LinkedList[this.bucketSize];
        }

        public void put(int key, int value) {
            int hash = Integer.hashCode(key);
            int index = hash%bucketSize;
            LinkedList<Node> list = bucket[index];
            if(list==null) {
                Node tempNode = new Node(hash, key, value);
                LinkedList<Node> tempList = new LinkedList<>();
                tempList.add(tempNode);
                 bucket[index] = tempList;
            } else {
                Iterator it = list.iterator();
                boolean isAvailable = false;
                while(it.hasNext()) {
                    Node temp = (Node) it.next();
                    if(temp.hash == hash) {
                        temp.setValue(value);
                        isAvailable = true;
                        break;
                    }
                }

                if(!isAvailable) {
                    list.add(new Node(hash, key, value));
                }
            }
        }

        public int get(int key) {
            int hash = Integer.hashCode(key);
            int index = hash%bucketSize;
            LinkedList<Node> list = bucket[index];
            if(list != null || list.size()==0) return -1;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Node temp = (Node) it.next();
                if(temp.hash == hash) {
                    return  temp.value;
                }
            }
            return -1;
        }

        public void remove(int key) {
            int hash = Integer.hashCode(key);
            int index = hash%bucketSize;
            LinkedList<Node> list = bucket[index];
            if(list != null || list.size()==0) return;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Node temp = (Node) it.next();
                if(temp.hash == hash) {
                    list.remove(temp);
                    return;
                }
            }
            return;
        }
    }

    class Node {
        Integer hash, key, value;

        public Integer getHash() {
            return hash;
        }

        public void setHash(Integer hash) {
            this.hash = hash;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Node(Integer hash, Integer key, Integer value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    public boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()) return false;

        HashMap<Character, ArrayList<Integer>> alphabetMap = new HashMap();
        
        for(int i=0; i<s.length(); i++) {
            ArrayList<Integer> indexes;
            if(alphabetMap.containsKey(s.charAt(i))) {
                indexes = alphabetMap.get(s.charAt(i));
                indexes.add(i);
            } else {
                indexes = new ArrayList<>();
                indexes.add(i);
                alphabetMap.put(s.charAt(i), indexes);
            }
        }

        Iterator it = alphabetMap.values().iterator();
        HashSet<Character> uniqueCharacters = new HashSet<>();
        while(it.hasNext()) {
            ArrayList<Integer> indexes = (ArrayList<Integer>) it.next();
            Character toMatchChar = t.charAt(indexes.get(0));
            if(uniqueCharacters.contains(toMatchChar)) return false;
            uniqueCharacters.add(toMatchChar);
            for(int i=0; i<indexes.size(); i++) {
                    if(!toMatchChar.equals(t.charAt(indexes.get(i)))) return false;
            }
        }

        return true;

    }

    public static int findMin(int[] nums) {
        return nums[searchForMinimum(nums, 0, nums.length-1)];
    }

    public static int searchForMinimum(int[] num, int left, int right) {
        int mid = left + (right - left)/2;

        if(mid > 0 && mid < num.length-1) {
            if(num[mid] < num[mid-1] && num[mid] < num[mid+1]) return mid;
        }  else if(left == right) {
            return mid;
        } else if(mid == 0) {
            if(num[mid] < num[num.length-1] && num[mid] < num[mid+1]) return mid;
        } else if(mid == num.length-1) {
            if(num[mid] < num[0] && num[mid] < num[mid-1]) return mid;
        }

        if(num[left] <= num[mid]) {
            if(num[right] < num[mid]) {
                return searchForMinimum(num, mid+1, right);
            }
        }
        return searchForMinimum(num, left, mid-1);
    }

    public static int findPeakElement(int[] nums) {
        int startIdx = 0;
        int endIdx = nums.length-1;
        while (true) {
            int mid = startIdx + (endIdx-startIdx)/2;
            if(mid == 0) {
                if(nums[mid+1] > nums[mid]) {
                    startIdx = mid+1;
                    continue;
                }
                return mid;
            }

            if(mid == nums.length-1) {
                if(nums[mid-1] > nums[mid]) {
                    endIdx = mid-1;
                    continue;
                }
                return mid;
            }
            if(nums[mid] > nums[mid-1] && nums[mid] > nums[mid+1]) return mid;
            if(nums[mid] < nums[mid-1] && nums[mid] > nums[mid+1]) {
                endIdx = mid-1;
                continue;
            }
            if(nums[mid] > nums[mid-1] && nums[mid] < nums[mid+1]) {
                startIdx = mid+1;
                continue;
            }
            endIdx = mid-1;
        }

    }

    public static String addBinary(String a, String b) {
        int i = a.length()-1, j = b.length()-1, carry = 0;
        StringBuilder result = new StringBuilder();

        while(i>-1 && j>-1) {
            int a1 = Integer.parseInt(""+a.charAt(i));
            int a2 = Integer.parseInt(""+b.charAt(j));
            int sum = a1^a2^carry;
            carry = a1&a2 | (a1^a2)&carry;
            result.append((Integer.toString(sum)).charAt(0));
            --i;
            --j;
        }

        while(i>-1) {
            int a1 = Integer.parseInt(""+a.charAt(i));
            int sum = a1^carry;
            carry = a1&carry;
            result.append((Integer.toString(sum)).charAt(0));
            --i;
        }

        while(j>-1) {
            int a1 = Integer.parseInt(""+b.charAt(j));
            int sum = a1^carry;
            carry = a1&carry;
            result.append((Integer.toString(sum)).charAt(0));
            --j;
        }

        if(carry == 1) result.append('1');

        return result.reverse().toString();
    }

    public static int reverseBits(int n) {
        StringBuilder reverseBits = new StringBuilder();
        while (reverseBits.length() < 32) {
            int mod = n%2;
            n = n/2;
            reverseBits.append(mod == 1 ? '1' : '0');
        }
        System.out.println(reverseBits);
        return Integer.parseInt(reverseBits.toString(), 2);
    }

    public static List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        if(nums.length == 0) return result;
        int start = 0;
        int end = 0;
        for(int i=1; i<nums.length; i++) {
            if(nums[i]-nums[i-1] == 1){
                ++end;
            } else {
                if(start == end) {
                    result.add(Integer.toString(nums[start]));
                } else {
                    result.add(String.format("%d->%d", start, end));
                }
                start = i;
                end = i;
            }
        }
        if(start == end) {
            result.add(Integer.toString(nums[start]));
        } else {
            result.add(String.format("%d->%d", start, end));
        }
        return result;
    }

    public static int[][] merge(int[][] intervals) {
        /**
         * time complexity nlog(n) --> since sorting
         * space complexity o(n)
         */
        Arrays.sort(intervals, (t1, t2) -> Integer.compare(t1[0], t2[0]));
        List<int[]> result = new ArrayList<>();
        int startPoint = 0, endPoint = 0;
        for(int i=0; i<intervals.length; i++) {
            if(i==0) {
                startPoint = intervals[i][0];
                endPoint = intervals[i][1];
            } else {
                if(intervals[i][0] < endPoint) {
                    endPoint = Math.max(intervals[i][1],endPoint);
                } else {
                    result.add(new int[]{startPoint, endPoint});
                    startPoint = intervals[i][0];
                    endPoint = intervals[i][1];
                }
            }
        }
        result.add(new int[]{startPoint, endPoint});
        return result.toArray(new int[0][0]);
    }

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> result = new ArrayList<>();
        for(int i=0; i<intervals.length; i++) {
            if(newInterval[0] > intervals[i][1]) {
                result.add(intervals[i]);
            }  else if(newInterval[1] < intervals[i][0]) {
                result.add(newInterval);
                newInterval = new int[]{intervals[i][0], intervals[i][1]};
            } else {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            }
        }
        result.add(newInterval);
        return result.toArray(new int[0][0]);
    }

    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (int[] e1, int[] e2) -> {
            if(e1[0] == e2[0]) {
                return Integer.compare(e1[1], e2[1]);
            }
            return Integer.compare(e1[0], e2[0]);
        });
        int throwsRequired = 0;
        int[] baseBalloon = null;
        int maxBound = -1;
        for(int i=0; i<points.length; i++) {
            if(baseBalloon == null) {
                baseBalloon = points[i];
                maxBound = points[i][1];
                ++throwsRequired;
                continue;
            }

            if(points[i][0] <= baseBalloon[1] && points[i][0] <= maxBound) {
                maxBound = Math.min(maxBound, points[i][1]);
                points[i] = null;
            } else {
                baseBalloon = points[i];
                maxBound = points[i][1];
                ++throwsRequired;
            }
        }
        return throwsRequired;
    }

    public static List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> result = new ArrayList<>();

        int left = 0, right = matrix[0].length;
        int top = 0, bottom = matrix.length;

        while(left < right && top < bottom) {

            for(int i=left; i<right; i++) {
               result.add(matrix[top][i]);
            }
            ++top;

            for(int i=top; i<bottom; i++) {
                result.add(matrix[i][right-1]);
            }
            --right;

            if(top < bottom) {
                for(int i=right-1; i>=left; i--) {
                    result.add(matrix[bottom-1][i]);
                }
            }
            --bottom;

            if(left < right) {
                for(int i=bottom-1; i>=top; i--) {
                    result.add(matrix[i][left]);
                }
            }
            ++left;
        }

        return result;

    }

    public static void setZeroes(int[][] matrix) {

        boolean firstRowContainsZero = false;
        boolean firstColumnContainsZero = false;

        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if(matrix[i][j] == 0) {
                    if(i == 0 && j == 0) {
                        firstRowContainsZero = true;
                        firstColumnContainsZero = true;
                    }

                    if(i == 0) {
                        firstRowContainsZero = true;
                    }

                    if(j == 0) {
                        firstColumnContainsZero = true;
                    }
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for(int i=matrix[0].length-1; i>0; i--) {
            if(matrix[0][i] == 0) {
                for(int j=matrix.length-1; j>0; j--) {
                    matrix[j][i] = 0;
                }
            }
        }

        for(int i=matrix.length-1; i>0; i--) {
            if(matrix[i][0] == 0) {
                for(int j=matrix[0].length-1; j>0; j--) {
                    matrix[i][j] = 0;
                }
            }
        }

        if(firstRowContainsZero) {
            for(int i=1; i<matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }

        if(firstColumnContainsZero) {
            for(int i=1; i<matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

    }

    public static void gameOfLife(int[][] board) {
        int[][] boardStore = new int[board.length+2][board[0].length+2];

        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                boardStore[i+1][j+1] = board[i][j];
            }
        }

        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                int count = countNumberOfOne(boardStore, i+1, j+1);
                if(boardStore[i+1][j+1] == 1) {
                    if(count < 2 || count > 3) board[i][j] = 0;
                }
                if(boardStore[i+1][j+1] == 0 && count == 3) board[i][j] = 1;
            }
        }

    }

    public static int countNumberOfOne(int[][] matrix, int i, int j) {
        int count = 0;

        if(matrix[i][j-1] == 1) ++count;
        if(matrix[i+1][j-1] == 1) ++count;
        if(matrix[i-1][j-1] == 1) ++count;

        if(matrix[i+1][j] == 1) ++count;
        if(matrix[i-1][j] == 1) ++count;

        if(matrix[i][j+1] == 1) ++count;
        if(matrix[i+1][j+1] == 1) ++count;
        if(matrix[i-1][j+1] == 1) ++count;

        return count;
    }

    public static void rotate(int[][] matrix){
        for(int i=0; i<matrix.length; i++) {
            for(int j=i; j<matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        int m = 0, n = matrix.length-1;
        while(m < n) {
            for(int i=0; i<matrix.length; i++) {
                int temp = matrix[i][m];
                matrix[i][m] = matrix[i][n];
                matrix[i][n] = temp;
            }
            ++m;
            --n;
        }

    }

    public static boolean isValidSudoku(char[][] board) {

        int[] trackingArray;

//        check column
        for(int i=0; i<board.length; i++) {
            trackingArray = new int[9];
            for (int j = 0; j < board[0].length; j++) {
                if(!validateElement(board[j][i], trackingArray)){
                    return false;
                }
            }
        }

        // check row
        for(int i=0; i<board.length; i++) {
            trackingArray = new int[9];
            for (int j = 0; j < board[0].length; j++) {
                if(!validateElement(board[i][j], trackingArray)){
                    return false;
                }
            }
        }

        // check blocks
        for(int m=1; m<4; m++) {
            for(int n=1; n<4; n++) {
                trackingArray = new int[9];
                for(int i=1; i<4; i++) {
                    for(int j=1; j<4; j++) {
                        if(!validateElement(board[3*(m-1)+(i-1)][3*(n-1)+(j-1)], trackingArray)){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    static boolean validateElement(char currentCharacter, int[] trackingArray) {
        if (currentCharacter == '.') {
            return true;
        }
        int currentNumber = currentCharacter - '0';
        if (trackingArray[currentNumber - 1] == 1) {
            return false;
        } else {
            trackingArray[currentNumber - 1] = 1;
        }
        return true;
    }

    public static int maxDepth(TreeNode root) {
        if(root == null) return 0;
        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * time complexity - o(n)
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * time complexity - o(n)
     */
    public static TreeNode invertTree(TreeNode root) {
        if(root == null) return root;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        TreeNode currentNode;
        while(!queue.isEmpty()) {
            currentNode = queue.poll();
            if(currentNode.left != null) queue.add(currentNode.left);
            if(currentNode.right != null) queue.add(currentNode.right);
            TreeNode temp = currentNode.left;
            currentNode.left = currentNode.right;
            currentNode.right = temp;;
        }
        return root;
    }

    public static boolean isSymmetric(TreeNode root) {
        ArrayList<TreeNode> firstList = new ArrayList<>();
        ArrayList<TreeNode> secondList = new ArrayList<>();
        firstList.add(root);
        TreeNode currentNode;
        ArrayList<Integer> tempList = new ArrayList<>();
        while (!(firstList.isEmpty() && secondList.isEmpty())) {

            if(!firstList.isEmpty()) {
                Iterator<TreeNode> it = firstList.iterator();
                while (it.hasNext()) {
                    currentNode = it.next();
                    if(currentNode != null) {
                        tempList.add(currentNode.val);
                        secondList.add(currentNode.left);
                        secondList.add(currentNode.right);
                    } else {
                        tempList.add(-1);
                    }
                }
                firstList = new ArrayList<>();
            } else if(!secondList.isEmpty()) {
                Iterator<TreeNode> it = secondList.iterator();
                while (it.hasNext()) {
                    currentNode = it.next();
                    if(currentNode != null) {
                        tempList.add(currentNode.val);
                        firstList.add(currentNode.left);
                        firstList.add(currentNode.right);
                    } else {
                        tempList.add(-1);
                    }
                }
                secondList = new ArrayList<>();
            }

            int leftPtr = 0, rightPtr = tempList.size()-1;
            while (leftPtr < rightPtr) {
                if(tempList.get(leftPtr) != tempList.get(rightPtr)) return false;
                ++leftPtr;
                --rightPtr;
            }
            tempList = new ArrayList<>();

        }
        return true;
    }

    public static int longestSequence(Integer[] numbers) {
        Set<Integer> numberSet = new HashSet<>(List.of(numbers));
        int maxSoFar = 0;
        for(int i=0; i<numbers.length; i++) {
            int currentLength = 0;
            int currentNumber = numbers[i];
            while (numberSet.contains(currentNumber)) {
                numberSet.remove(currentNumber);
                ++currentNumber;
                ++currentLength;
            }
            maxSoFar = Math.max(maxSoFar, currentLength);
        }
        return  maxSoFar;
    }

    static Map<Integer, Integer> numbers = new HashMap<>();
    static int postorderIndex = -1;
    static int[] inorderArray = new int[]{};
    static int[] postorderArray = new int[]{};

    public static TreeNode buildTree(int[] postorder, int[] inorder) {
        for(int i=0; i<inorder.length; i++) numbers.put(inorder[i], i);
        inorderArray = inorder;
        postorderArray = postorder;
        postorderIndex = postorder.length;
        return buildTree(0, inorder.length);
    }

    public static TreeNode buildTree(int startIndex, int endIndex) {

        if(endIndex - startIndex <= 0) return null;

        int rootNodeIndex = numbers.get(postorderArray[--postorderIndex]);

        TreeNode currentNode = new TreeNode(inorderArray[rootNodeIndex]);
        currentNode.right = buildTree(rootNodeIndex+1, endIndex);
        currentNode.left = buildTree(startIndex, rootNodeIndex);
        return  currentNode;
    }

    public static TreeNode connect(TreeNode root) {
        if(root == null) return root;
        Queue<TreeNode> queue1 = new ArrayDeque<>();
        queue1.add(root);
        Queue<TreeNode> queue2 = new ArrayDeque<>();
        while(!queue1.isEmpty() || !queue2.isEmpty()) {
            TreeNode previousNode = null;
            TreeNode currentNode;
            if(!queue1.isEmpty()) {
                while (!queue1.isEmpty()) {
                    currentNode = queue1.poll();
                    if(previousNode != null) {
                        previousNode.next = currentNode;
                    }
                    previousNode = currentNode;
                    if(currentNode.left != null) queue2.add(currentNode.left);
                    if(currentNode.right != null) queue2.add(currentNode.right);
                }
            } else {
                while (!queue2.isEmpty()) {
                    currentNode = queue2.poll();
                    if(previousNode != null) {
                        previousNode.next = currentNode;
                    }
                    previousNode = currentNode;
                    if(currentNode.left != null) queue1.add(currentNode.left);
                    if(currentNode.right != null) queue1.add(currentNode.right);
                }
            }
        }
        return root;
    }

    public static void flatten(TreeNode root) {
        flattenNode(root);
    }

    public static void flattenNode(TreeNode root) {
        if(root.left == null && root.right == null) return;
        if(root.left != null) flattenNode(root.left);
        if(root.right != null) flattenNode(root.right);
        if(root.left != null) {
            TreeNode rightPtr = root.right;
            TreeNode rightEndOfLeftBranch = root.left;
            while(rightEndOfLeftBranch.right != null) {
                rightEndOfLeftBranch = rightEndOfLeftBranch.right;
            }
            root.right = root.left;
            root.left = null;
            rightEndOfLeftBranch.right = rightPtr;
        }
    }

    // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7

    /**
     *
     *      1
     *   2    3
     * 4  5  6  7
     *
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        return hasPathSum(root, 0, targetSum);
    }

    public static boolean hasPathSum(TreeNode currentNode, Integer currentSum, Integer targetSum) {
        currentSum += currentNode.val;
        if(currentNode.right == null && currentNode.left == null && currentSum == targetSum) return true;
        if(currentNode.left != null) if(hasPathSum(currentNode.left, currentSum, targetSum)) return true;
        if(currentNode.right != null) if(hasPathSum(currentNode.right, currentSum, targetSum)) return true;
        return false;
    }

    public static int sumNumbers(TreeNode root) {
        List<Integer> numbers = new ArrayList<>();
        sumNumbers(root, 0, numbers);
        int result = 0;
        for(int i=0; i<numbers.size(); i++) {
            result+=numbers.get(i);
        }
        return result;
    }

    public static void sumNumbers(TreeNode currentNode, int numberSoFar, List<Integer> numbers) {
        numberSoFar = numberSoFar*10 + currentNode.val;
        if(currentNode.right == null && currentNode.left == null) {
            numbers.add(numberSoFar);
        }
        if(currentNode.left != null) sumNumbers(currentNode.left, numberSoFar, numbers);
        if(currentNode.right != null) sumNumbers(currentNode.right, numberSoFar, numbers);
    }


}
