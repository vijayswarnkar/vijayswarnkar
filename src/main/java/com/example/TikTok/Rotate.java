package com.example.TikTok;

import java.util.ArrayList;
import java.util.List;

public class Rotate {
    static boolean rotateBox(String[] box) {
        List<String> ans = new ArrayList<>();
        for(int ctr=box.length-1;ctr >=0;ctr--) {
            String str = box[ctr];
            str += 'X';
            int o = 0;
            char[] chars = new char[str.length()];
            for(int i=0;i<str.length();i++) {
                char ch = str.charAt(i);
                chars[i] = '_';
                if(ch == 'O') {
                    o++;
                }
                if(ch == 'X'){
                    int j = i;
                    chars[j--] = 'X';
                    while(o-- > 0) chars[j--] = 'O';
                    o = 0;
                }
            }
            ans.add(new String(chars));
//            System.out.println(chars);
        }
        for(int j=0;j<ans.get(0).length();j++){
            for(int i=0;i<ans.size();i++){
                System.out.print(ans.get(i).charAt(j));
            }
            System.out.println();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Rotate.rotateBox(new String[]{
                "OO X ",
                "O  O ",
                "O XO "
        }));
    }
}
