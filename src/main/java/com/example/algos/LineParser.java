package com.example.algos;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.commons.lang.math.NumberUtils;

import java.util.regex.Pattern;

@Log
class LineParser {
    static enum ENTITY_TYPE {SECTION, KEY_VALUE, COMMENT}
    static String SECTION_REGEX = "^[\\[][_a-zA-Z0-9[\" \"]]+([\\]]$|[\\]]{1}[;#[\" \"]]+)";
    static String VALID_KEY_OVERRIDE_REGEX = "^[_A-Za-z0-9[\" \"]]+[<][_A-Za-z0-9[\" \"]]+[>]$";

    @Getter
    @Setter
    static class Entity {
        String section;
        Pair<String, String> keyOverride;
        Object value;
        ENTITY_TYPE type;
        boolean Invalid;

        @Override
        public String toString() {
            return "Entity{" +
                    "section='" + section + '\'' +
                    ", keyOverride=" + keyOverride +
                    ", value=" + value +
                    ", type=" + type +
                    ", Invalid=" + Invalid +
                    '}';
        }
    }

    Pair<String, String> processKey(String str) {
        String override = null;
        // In worst case take entire str as key
        String key = str;

        boolean match = Pattern.compile(VALID_KEY_OVERRIDE_REGEX).matcher(str).matches();
        if (match) {
            int i = str.indexOf('<');
            int j = str.indexOf('>');
            override = str.substring(i + 1, j).trim();
            key = str.substring(0, i).trim();
        }
        return new Pair<>(key, override);
    }

    Entity parse(String line) {
        line = line.trim();
        String comments = ";#";
        String valueInvertedCommas = "'\"";
        String KeyValeSeparator = ":=";
//        System.out.println();
//        System.out.println(line);

        Entity entity = new Entity();

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            int comment = comments.indexOf(ch);
            int separator = KeyValeSeparator.indexOf(ch);
            if (i == 0 && comment >= 0) {
                entity.setType(ENTITY_TYPE.COMMENT);
//                System.out.println("comment encountered at tht beginning: " + "IGNORED");
                break;
//            } else if (i == 0 && ch == '[') {
//                boolean sectionMatched = Pattern.compile(SECTION_REGEX).matcher(line).matches();
//                if(sectionMatched){
//                    int st = line.indexOf('[');
//                    int ed = line.indexOf(']');
//                    String section = line.substring(st + 1, ed);
//                    entity.setSection(section);
//                    entity.setType(ENTITY_TYPE.SECTION);
//                }
            } else if (i == 0 && ch == '[') {
                int endOfSection = line.indexOf(']');
                String section = line.substring(i + 1, endOfSection).trim();
                String restOfLine = line.substring(endOfSection + 1).trim();
                if (restOfLine.length() > 0 && comments.indexOf(restOfLine.charAt(0)) < 0) {
                    entity.setInvalid(true);
//                    System.out.println("Invalid section formatting: " + restOfLine);
                } else {
//                    System.out.println("Valid Section start and end detected: " + section);
                    entity.setSection(section);
                    entity.setType(ENTITY_TYPE.SECTION);
                }
                break;
            } else if (separator >= 0) {
                String key = line.substring(0, i).trim();
                String value = line.substring(i + 1).trim();
                StringBuilder parsedValue = new StringBuilder();
                entity.setType(ENTITY_TYPE.KEY_VALUE);

                if (value.length() > 0 && valueInvertedCommas.indexOf(value.charAt(0)) >= 0) {
                    char invertedComma = value.charAt(0);
                    for (int j = 0; j < value.length(); j++) {
                        if (comments.indexOf(value.charAt(j)) >= 0 && value.indexOf(invertedComma, j) < j) {
                            break;
                        }
                        parsedValue.append(value.charAt(j));
                    }
                    value = new String(parsedValue).trim();
                    int len = value.length();
                    if (value.charAt(len - 1) == invertedComma) {
                        value = value.substring(1, len-1);
                    }
//                    System.out.println(processKey(key) + " => " + value + ", isNumber: " + NumberUtils.isNumber(value));
                    entity.setKeyOverride(processKey(key));
                    entity.setValue(value);
                } else {
                    for (int j = 0; j < value.length(); j++) {
                        if (comments.indexOf(value.charAt(j)) >= 0) {
                            break;
                        }
                        parsedValue.append(value.charAt(j));
                    }
                    value = new String(parsedValue).trim();
                    String[] arr = value.split(",");
                    if (arr.length > 1) {
//                        System.out.println(key + " => ");
//                        Arrays.stream(arr).forEach(x -> System.out.print(x + ","));
                        entity.setValue(arr);
                    } else {
//                        System.out.println(processKey(key) + " => " + value + ", isNumber: " + NumberUtils.isNumber(value));
                        entity.setKeyOverride(processKey(key));
                        Object val = NumberUtils.isNumber(value) ? NumberUtils.createNumber(value) : value;
                        entity.setValue(val);
                    }
                }
                break;
            }
        }
        return entity;
    }

    public static void main(String[] args) {
        LineParser lineParser = new LineParser();
//        System.out.println(lineParser.parse(" ; name = \"hello, \" ftp\" ;comment"));
        System.out.println(lineParser.parse(" [ secti[on ] # [ ; comment"));
//        System.out.println(lineParser.parse(" name = \"hello, = \" ftp\" ; \' comment"));
//        System.out.println(lineParser.parse(" name = hello, =  ftp\" ; \' comment"));
//        System.out.println(lineParser.parse(" path = \"/srv/uploads/\" ; This is another"));
//        System.out.println(lineParser.parse(" path = /srv/uploads/ ; This is another"));
//        System.out.println(lineParser.parse(" basic_size_limit = \"26214.400\" "));
//        System.out.println(lineParser.parse(" basic_size_limit = \"26214\" "));
//        System.out.println(lineParser.parse(" basic_size_limit = 26214 "));
//        System.out.println(lineParser.parse(" basic_size_limit = 26214.400 "));
//        System.out.println(lineParser.parse(" params = array,of,values ; comment "));
//        System.out.println(lineParser.parse(" path<production>> = /srv/var/tmp/ "));
//        System.out.println(lineParser.parse(" path < production >  = /srv/var/tmp/ "));
//        System.out.println(lineParser.parse(" path<production> =  "));
//        System.out.println(lineParser.parse(" name = \"http uploading\"  "));
    }
}