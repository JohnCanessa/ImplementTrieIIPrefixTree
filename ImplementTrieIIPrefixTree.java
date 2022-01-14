import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * LeetCode 1804. Implement Trie II (Prefix Tree)
 * https://leetcode.com/problems/implement-trie-ii-prefix-tree/
 * 
 * Runtime: 124 ms, faster than 34.10% of Java online submissions.
 * Memory Usage: 117.7 MB, less than 32.57% of Java online submissions.
 * 
 * 78 / 78 test cases passed.
 * Status: Accepted
 * Runtime: 124 ms
 * Memory Usage: 117.7 MB
 */
public class ImplementTrieIIPrefixTree {


    /**
     * Trie class
     */
    static class Trie {


        // **** class members ****
        public Trie[]   children;
        public int      prefixCnt;
        public int      wordCnt;

        // ???? experimenting ????
        public char     ch;
		public String   str;
        public boolean  end;
        public Trie     prev;


        /**
         * Constructor.
         */
        public Trie() {
            this.children   = new Trie[26];
            this.prefixCnt  = 0;
            this.wordCnt    = 0;

            // ???? experimenting ????
            this.ch         = '*';
        }


        /**
         * Constructor.
         */
        public Trie(char ch) {
            this.children   = new Trie[26];
            this.prefixCnt  = 0;
            this.wordCnt    = 0;

            // ???? experimenting ????
            this.ch         = ch;
        }


        /**
         * Constructor.
         */
        public Trie(char ch, String str) {
            this.children   = new Trie[26];
            this.prefixCnt  = 0;
            this.wordCnt    = 0;

            // ???? experimenting ????
            this.ch         = ch;
            this.str        = str;
        }


        /**
         * Generate string representation of Trie.
         * Some experimental members are displayed.
         * 
         * !!!! NOT PART OF SOLUTION !!!!
         */
        @Override
        public String toString() {

            // **** initialization ****
            StringBuilder sb = new StringBuilder("(");

            // **** ****
            sb.append("ch: " + this.ch);
            sb.append(" prefixCnt: " + this.prefixCnt);
            sb.append(" wordCnt: " + this.wordCnt);
            sb.append(" str: " + this.str);

            // **** ****
            sb.append(")");

            // **** ****
            return sb.toString(); 
        }
        

        /**
         * Inserts the string word into the trie.
         */
        public void insert0(String word) {

            // **** initialization ****
            Trie p = this;

            // **** traverse word inserting characters ****
            for (int i = 0; i < word.length(); i++) {

                // **** for ease of use ****
                char ch = word.charAt(i);

                // **** ****
                Trie prev = p;
    
                // **** create new child  ****
                if (p.children[ch - 'a'] == null)
                    p.children[ch - 'a'] = new Trie(ch, word.substring(0, i + 1));

                // **** point to next child ****
                p = p.children[ch - 'a'];

                // **** ****
                p.prev = prev;
            }

            // **** increment prefixCnt ****
            p.prefixCnt++;

            // **** end of word ****
            p.end = true;
        }


        /**
         * Inserts the string word into the trie.
         */
        public void insert(String word) {

            // ???? ????
            System.out.println("insert <<< word ==>" + word + "<==");

            // **** initialization ****
            Trie p = this;

            // **** traverse the word one character at a time ****
            for (int i = 0; i < word.length(); i++) {

                // **** for ease of use ****
                char ch = word.charAt(i);
                int ndx = ch - 'a';

                // **** create and append new Trie node ****
                if (p.children[ndx] == null)
                    p.children[ndx] = new Trie(ch, word.substring(0, i + 1));

                // **** move to next child ****
                p = p.children[ndx];

                // **** increment prefix count *****
                p.prefixCnt++;

                // ???? ????
                System.out.println("insert <<< p: " + p);
            }

            // **** save word in this node ****
            p.str = word;

            // **** increment word count ****
            p.wordCnt++;

            // ???? ????
            System.out.println("insert <<< p: " + p);
        }


        /**
         * Returns the number of instances of the string word in the trie.
         */
        public int countWordsEqualTo0(String word) {

            // **** initialization ****
            Trie p = this;

            // **** traverse word ****
            for (char ch : word.toCharArray()) {

                // **** character not found ****
                if (p.children[ch - 'a'] == null) return 0;

                // **** continue search ****
                p = p.children[ch - 'a'];
            }

            // **** check if this is not a word ****
            if (p.end == false) return 0;

            // **** return prefixCnt of words equal to word ****
            return p.prefixCnt;
        }


        /**
         * Returns the number of instances of the string word in the trie.
         */
        public int countWordsEqualTo(String word) {

            // **** initialization ****
            Trie p = this;

            // **** traverse the word one character at a time ****
            for (char ch : word.toCharArray()) {

                // **** for ease of use ****
                int i = ch - 'a';

                // **** word not found in trie ****
                if (p.children[i] == null) return 0;

                // **** move to next child ****
                p = p.children[i];
            }

            // **** ****
            return p.wordCnt;
        }


        /**
         * Returns the number of strings in the trie that have 
         * the string prefix as a prefix.
         */
        public int countWordsStartingWith0(String prefix) {

            // **** initialization ****
            Trie p = this;

            // **** traverse the prefix one charracter at a time ****
            for (char ch : prefix.toCharArray()) {

                // **** prexid not found ****
                if (p.children[ch - 'a'] == null) return 0;

                // **** move to next child ****
                p = p.children[ch - 'a'];
            }

            // **** ****
            return countEnds0(p);
        }


        /**
         * Returns the number of strings in the trie that have 
         * the string prefix as a prefix.
         */
        public int countWordsStartingWith(String prefix) {

            // **** initialization ****
            Trie p = this;

            // **** traverse prefix one character at a time ****
            for (char ch : prefix.toCharArray()) {

                // **** for ease of use ****
                int i = ch - 'a';

                // **** prefix not cound ****
                if (p.children[i] == null) return 0;

                // **** move to next child ****
                p = p.children[i];
            }

            // **** ****
            return p.prefixCnt;
        }


        /**
         * Return count of words that start here.
         * Recursive call.
         */
        private int countEnds0(Trie p) {

            // **** base case ****
            if (p == null) return 0;

            // **** initialization ****
            int prefixCnt = 0;

            // **** recurse ****
            for (int i = 0; i < 26; i++)
                prefixCnt += countEnds0(p.children[i]);

            // **** ****
            if (p.end == true) prefixCnt += p.prefixCnt;

            // **** ****
            return prefixCnt;
        }
        

        /**
         * Erases the string word from the trie.
         * It is guaranteed that for any function call to erase, 
         * the string word will exist in the trie.
         */
        public void erase0(String word) {
            
            // **** initialization ****
            Trie p = this;

            // **** traverse the word one character at a time ****
            for (char ch : word.toCharArray()) {

                // **** point to next node ****
                p = p.children[ch - 'a'];

                // **** update current node ****
                if (p.str.equals(word)) {
                    
                    // **** decrement counter ****
                    p.prefixCnt--;

                    // **** flag that word is not in the Trie ****
                    if (p.prefixCnt == 0) p.end = false;
                }
            }
        }


        /**
         * Erases the string word from the trie.
         * It is guaranteed that for any function call to erase, 
         * the string word will exist in the trie.
         */
        public void erase(String word) {
            
            // ???? ????
            System.out.println("erase <<< word ==>" + word + "<==");

            // **** initialization ****
            Trie p          = this;
            boolean delete  = false;

            // **** traverse the word one character at a time ****
            for (char ch : word.toCharArray()) {

                // ???? ????
                System.out.println("erase <<<      p: " + p);

                // **** for ease of use ****
                int i = ch - 'a';

                // **** next child ****
                Trie next = p.children[i];  

                // ???? ????
                System.out.println("erase <<<   next: " + next);

                // **** decrement counter ****
                next.prefixCnt--;

                // **** ****
                if (next.prefixCnt == 0) delete = true;

                // ????? ????
                System.out.println("erase <<< delete: " + delete);

                // **** delete reference in children ****
                if (delete) p.children[i] = null;

                // **** point to next node ****
                p = next;
            }

            // **** decrement counter ****
            p.wordCnt--;

            // ???? ????
            System.out.println("erase <<< p: " + p);
        }


        /**
         * Returns a list of words if there are one or more 
         * previously inserted string words that have the prefix prefix.
         * If no words are found returns an empty list.
         * 
         * !!!! NOT PART OF SOLUTION !!!!
         */
        public List<String> anyWords(String prefix) {
        
            // **** initialization ****
            List<String> words = new ArrayList<>();
        
            // **** sanity checks ****
            if (prefix.length() == 0 || prefix.equals(" ")) return words;
        
            // **** to traverse trie ****
            Trie p = this;
        
            // **** get to the end of the prefix ****
            for (int i = 0; prefix.charAt(0) != '*' && i < prefix.length(); i++) {
        
                // **** get current character ****
                char ch = prefix.charAt(i);
        
                // **** ****
                if (p.children[ch - 'a'] == null) return words;
        
                // **** continue search ****
                p = p.children[ch - 'a'];
            }
        
            // **** search for full words starting here ****
            findWords(p, words);
        
            // **** return list of possible words ****
            return words;
        }


        /**
         * Find full words in the trie starting at p
         * and add them to the list.
         * Recursive call.
         * 
         * !!!! NOT PART OF SOLUTION !!!!
         */
        public void findWords(Trie p, List<String>words) {
        
            // **** end condition ****
            if (p == null) return;
        
            // **** traverse trie ****
            for (Trie child : p.children)
                findWords(child, words);

            // **** add full world to list ****
            if (p.wordCnt != 0) words.add(p.str + " (" + p.wordCnt + ")");
        }
    }
    

    /**
     * Test scaffold
     * !!!! NOT PART OF SOLUTION !!!!
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read commands (method calls) ****
        String[] cmdArr = Arrays.stream(br.readLine().trim().split(", "))
                                .map(s -> s.trim())
                                .toArray(size -> new String[size]);
        
        // **** get array size ****
        int n = cmdArr.length;
    
        // **** read arguments to commands (method calls) ****
        String[] argArr = Arrays.stream(br.readLine().trim().split(", "))
                                .map(s -> s.trim())
                                .toArray(size -> new String[size]);

        // **** get array size ****
        int m = argArr.length;

        // **** close buffered reader ****
        br.close();

        // **** compare array lengths ****
        if (n != m) {
            System.err.println("main <<< n: " + n + " != m: " + m);
            System.exit(-1);
        }

        // ???? ????
        System.out.println("main <<<      n: " + n);
        System.out.println("main <<< cmdArr: " + Arrays.toString(cmdArr));
        System.out.println("main <<< argArr: " + Arrays.toString(argArr));

        // **** ****
        Trie trie           = null;
        Integer[] output    = new Integer[n];

        // **** process commands in array ****
        for (int i = 0 ; i < n; i++) {

            // **** process this command ****
            switch (cmdArr[i]) {
                case "Trie":
                    trie = new Trie();
                    output[i] = null;
                break;

                case "insert":
                    trie.insert(argArr[i]);
                    output[i] = null;
                break;

                case "countWordsEqualTo":
                    output[i] = trie.countWordsEqualTo(argArr[i]);
                break;

                case "countWordsStartingWith":
                    output[i] = trie.countWordsStartingWith(argArr[i]);
                break;

                case "erase":
                    trie.erase(argArr[i]);
                    output[i] = null;
                break;

                case "anyWords":
                    System.out.println("main <<< trie: " + trie.anyWords(argArr[i]));
                break;

                default:
                    System.err.println("main <<< unexpected cmdArr[" + i + "]: " + cmdArr[i]);
                    System.exit(-1);
                break;
            }

            // ???? ????
            // List<String> words = new ArrayList<>();
            // trie.findWords(trie, words);
            // System.out.println("main <<< anyWords: " + words.toString());
        }

        // **** display output ****
        System.out.println("main <<< output: " + Arrays.toString(output));
    }
}