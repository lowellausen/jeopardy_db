package CPD.Models;

/**
 * Created by Octavio on 16/06/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Trie implements java.io.Serializable{

    private static final NodoTrie[] EMPTYNODES = new NodoTrie[0];

    private static final class NodoTrie implements Comparable<NodoTrie> , Serializable{

        private final char character;
        private boolean isWord = false;
        private  Map<Character, NodoTrie> filho  = null;
        private List<Integer> bucket;

        public NodoTrie(char ch) {
            character = ch;
            bucket = new ArrayList<>();
        }


        public NodoTrie AchaOuCria(char ch) {
            //Se não tiver filho, cria um
            if (filho == null) {
                filho = new HashMap<>();
            }
            NodoTrie nodofilho = filho.get(ch); //Pega o char do filho
            if (nodofilho == null) {
                nodofilho = new NodoTrie(ch);
                filho.put(ch, nodofilho);
            }
            return nodofilho;
        }

        public NodoTrie get(char ch) {
            return filho != null ? filho.get(ch) : null;
        }

        public void setWord() {
            isWord = true;
        }

        public boolean isWord() {
            return isWord;
        }

        public char getChar() {
            return character;
        }

        public NodoTrie[] achaNodoFilho() {
            if (filho == null) {
                return EMPTYNODES;
            }
            NodoTrie[] result = filho.values().toArray(new NodoTrie[filho.size()]);
            Arrays.sort(result);
            return result;
        }

        @Override
        public int compareTo(NodoTrie o) {
            // organizando em ordem alfabetica (A = 65 em ASCII, B = 66, decimal)
            return (int)character - o.character;
        }

    }



    public final NodoTrie raiz;
    private int size = 0;
    private int depth = 0; // tamanho da maior palavra

    public Trie(){
        // raiz tem um valor nulo típico de trie
        raiz = new NodoTrie((char)0);
    }

    public void addWord(String word, int index){
        NodoTrie node = raiz;
        int wdepth = 0;
        for (char ch : word.toLowerCase().toCharArray()) {
            node = node.AchaOuCria(ch);
            wdepth++;
        }

        node.bucket.add(index);
        node.setWord();
        size++;
        if (wdepth > depth) {
            depth = wdepth;
        }
    }

    public boolean containsWord(String word){
        NodoTrie node = raiz;
        for (char ch : word.toLowerCase().toCharArray()) {
            node = node.get(ch);
            if (node == null) {
                break;
            }
        }
        return node != null && node.isWord();
    }

    public int size() {
        return size;
    }

    public List<String> getWords() {
        // chamada recursiva.
        List<String> result = new ArrayList<>(size);
        char[] charstack = new char[depth];
        getWords(raiz, charstack, 0, result);
        return result;
    }

    private void getWords(final NodoTrie node, final char[] charstack, final int stackdepth, final List<String> result) {
        if (node == null) {
            return;
        }
        if (node.isWord()) {
            result.add(new String(charstack, 0, stackdepth));
            result.add(node.bucket.toString());
        }
        for (NodoTrie nodofilho : node.achaNodoFilho()) {
            charstack[stackdepth] = nodofilho.getChar();
            getWords(nodofilho, charstack, stackdepth + 1, result);
        }
    }

    public static List<Integer> returnBucket (NodoTrie root , String word ){

        for (char ch : word.toLowerCase().toCharArray()) {
            root = root.get(ch);
            if (root == null) {
                break;
            }
        }
        if (root.isWord)
            return root.bucket;
        else
            return null;
    }



}