package com.zxy.Utils;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> children = new HashMap<>();
    private Character ch;
    private boolean isEnd;

    public TrieNode() { }

    public TrieNode(Character ch) {
        this.ch = ch;
    }

    public boolean containsChild(Character c){
        return children.containsKey(c);
    }

    public void addChild(Character c){
        children.put(c,new TrieNode(c));
    }

    public TrieNode getChild(Character c){
        if(containsChild(c))
            return children.get(c);
        return null;
    }

    public boolean getEnd(){
        return isEnd;
    }
    public void setEnd(){
        isEnd = true;
    }
    public void addWords(String s){
        char[] chars =  s.toCharArray();
        TrieNode temp = this;
        for(int i=0;i<chars.length;i++){
            if(temp.containsChild(chars[i])){
                temp = temp.getChild(chars[i]);
            }else{
                temp.addChild(chars[i]);
                temp = temp.getChild(chars[i]);
            }
        }
        temp.setEnd();
    }

    public boolean containsWord(String s){
        char[] chars =  s.toCharArray();
        TrieNode temp = this;
        for(int i=0;i<chars.length;i++){
            if(!temp.containsChild(chars[i]))
                return false;
            temp = temp.getChild(chars[i]);
        }
        return temp.getEnd();
    }
}
