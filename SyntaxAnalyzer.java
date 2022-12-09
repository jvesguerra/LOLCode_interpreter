//Esguerra, Joshua
//Granada, Alain
//B-1L

import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*;
//import java.util.HashSet;


public class SyntaxAnalyzer {
    static HashMap<String, String> lexeme_identifier_map(HashMap<String, String> map) {
        //lexeme-identifier pairs
        map.put("^KTHXBYE$", "End of Code");
        map.put("^HAI$", "Start of Code");
        map.put("^BTW$", "Single-line comment");
        map.put("^OBTW$", "Start of multiple-line comments");
        map.put("^TLDR$", "End of multiple-line comments");
        map.put("^I HAS A$", "Variable declaration");
        map.put("^ITZ", "Variable initialization");
        map.put("^R$", "Assignment operation");
    
        map.put("^SUM OF$", "Addition Operator");
        map.put("^DIFF OF$", "Subtraction Operator");
        map.put("^PRODUKT OF$", "Multiplication Operator");
        map.put("^QUOSHUNT OF$", "Division Operator");
        map.put("^MOD OF$", "Modulo Operator");
        map.put("^BIGGR OF$", "max");
        map.put("^SMALLR OF$", "min");
        map.put("^BOTH OF$", "AND Boolean Operator");
        map.put("^EITHER OF$", "OR Boolean Operator");
        map.put("^WON OF$", "XOR Boolean Operator");
        map.put("^NOT$", "NOT Boolean Operator");
        map.put("^ANY OF$", "Infinite Arity OR Operator");
        map.put("^ALL OF$", "Infinite Arity AND Operator");
        //map.put("^SAEM$", "Comparison Operator");
        map.put("^BOTH SAEM$", "== Comparison Operator");
        map.put("^DIFFRINT$", "!= Comparison Operator");
        map.put("^SMOOSH$", "Concatenation Operator");
        map.put("^MAEK$", "Variable typecasting");
        map.put("^IS NOW A$", "Variable typecasting");
        map.put("^A", "Refers to a particular variable");
        
        map.put("^VISIBLE$", "Used for printing a statement");
        map.put("^GIMMEH$", "Stores a string input to a variable");
        map.put("^O RLY[?]$", "Start of If-Then statement");
        map.put("^YA RLY$", "If Statement");
        map.put("^MEBBE$", "Else-If Statement");
        map.put("^NO WAI$", "Else Statement");
        map.put("^OIC$", "End of If-Else and Switch-Case Statement");
        map.put("^WTF[?]$", "Start of Switch-Case");
        map.put("^OMG$", "Switch-Case Statement");
        map.put("^OMGWTF$", "Switch-Case Default Statement");
        map.put("^IM IN YR$", "Start of loop");
        map.put("^UPPIN$", "Increments a variable by 1");
        map.put("^NERFIN$", "Decrement by a variable1");
        map.put("^YR$", "Refers to the current argument or variable");
        map.put("^TIL$", "Repeat loop while FAIL");
        map.put("^WILE$", "Repeat loop while WIN");
        map.put("^IM OUTTA YR$", "End of loop");
        map.put("^AN$", "Seperates arguments in an expression");
        map.put("^IT$", "If-Else/Switch-Case default variable");
        map.put("^GTFO$", "Switch-Case/Loop break statement");
        map.put("^FOUND YR$", "Returns the value of an expression");
        map.put("^NOOB$", "Untyped variable");
        map.put("^MKAY$", "Ends an expression or an operation with multiple arguments");
    
        //typecast
        map.put("^NOOB$", "NOOB typecast");
        map.put("^TROOF$", "TROOF typecast");
        map.put("^NUMBAR$", "NUMBAR typecast");
        map.put("^NUMBR$", "NUMBR typecast");
        map.put("^YARN$", "Yarn typecast");
    
        map.put("^-?\\d+$", "NUMBR Literal");
        map.put("^([+-]?\\d*\\.\\d*)$", "NUMBAR Literal");
        map.put("^\".*\"$", "YARN Literal");
        map.put("^WIN$|^FAIL$", "TROOF Literal");
    
        // map.put("^[A-Za-z]+[A-Za-z0-9_]*$", "Variable");
        map.put("^HOW IZ I$", "Function Identifier");
        map.put("^IF U SAY SO$", "Function Identifier");

        return map;
    }
    
    static ArrayList<String> remove_tabs(ArrayList<String> array){
        for(int i = 0; i < array.size(); i++) {     
            String s = array.get(i);    
            s = s.trim();
            array.set(i,s);
        }
        return array;
    }
    
    static ArrayList<String> get_tokens(ArrayList<String> final_tokens, ArrayList<String> tokens){
        String build_token = "";
        for(int t = 0; t < tokens.size(); t++){
            build_token = "";
            if(tokens.get(t).equals("I") || tokens.get(t).equals("IS") || tokens.get(t).equals("IM") || tokens.get(t).equals("HOW")){
                build_token = tokens.get(t) + " " + tokens.get(t+1) + " " + tokens.get(t+2);
                if(build_token.equals("I HAS A") || build_token.equals("IM IN YR") || build_token.equals("IM OUTTA YR") || build_token.equals("HOW IZ I") || build_token.equals("IS NOW A")){
                    final_tokens.add(build_token);
                    t = t + 2;  // update loop
                }
            // operations
            }else if(tokens.get(t).equals("SUM") || tokens.get(t).equals("DIFF") || tokens.get(t).equals("PRODUKT") || tokens.get(t).equals("QUOSHUNT") || tokens.get(t).equals("MOD") || tokens.get(t).equals("BIGGR") || tokens.get(t).equals("SMALLR") || tokens.get(t).equals("BOTH") || tokens.get(t).equals("EITHER") || tokens.get(t).equals("WON") || tokens.get(t).equals("ANY") || tokens.get(t).equals("ALL") || tokens.get(t).equals("O") || tokens.get(t).equals("YA") || tokens.get(t).equals("NO")){
                build_token = tokens.get(t) + " " + tokens.get(t+1);
                if(build_token.equals("SUM OF") || build_token.equals("DIFF OF") || build_token.equals("PRODUKT OF") || build_token.equals("QUOSHUNT OF") || build_token.equals("MOD OF") || build_token.equals("BIGGR OF") || build_token.equals("SMALLR OF") || build_token.equals("BOTH OF") || build_token.equals("EITHER OF") || build_token.equals("WON OF") || build_token.equals("ANY OF") || build_token.equals("ALL OF") || build_token.equals("BOTH SAEM") || build_token.equals("O RLY?") || build_token.equals("YA RLY") || build_token.equals("NO WAI")){
                    final_tokens.add(build_token);
                    t = t + 1;  // update loop
                }
            }else{
                final_tokens.add(tokens.get(t));
            }
        }
        return final_tokens;
    }
    
    static HashMap<String, String> classify_tokens(HashMap<String, String> pairs, HashMap<String, String> map, ArrayList<String> final_tokens){
        if(Pattern.matches("^HAI$",final_tokens.get(0))){
            pairs.put(final_tokens.get(0),"Start of Code");  //put into 'pairs' hashmap
        }
            //checking for the type of each string in the array
        for(int i = 1; i < final_tokens.size(); i++){ 
            for (HashMap.Entry<String, String> set:map.entrySet()){        
                if(Pattern.matches(set.getKey(),final_tokens.get(i))){  //if the String at index i matches a 
                    pairs.put(final_tokens.get(i),set.getValue());  //put into 'pairs' hashmap
                }
                else{
                    char ch = final_tokens.get(i).charAt(0);   
                    if(Character.isAlphabetic(ch)){ 
                        if(final_tokens.get(i-1).equals("I HAS A")){
                            if(Pattern.matches("^[a-z]+[A-Za-z0-9_]*$",final_tokens.get(i))){
                                pairs.put(final_tokens.get(i),"Variable"); //put into 'pairs' hashmap
                            }
                            
                        }
                    }
                }
            }   
        }
        return pairs;
    }
    
    static HashMap<String, String> get_invalid_tokens(HashMap<String, String> pairs, ArrayList<String> final_tokens){
        for(int i = 0; i < final_tokens.size(); i++){
            if(pairs.containsKey(final_tokens.get(i))){
                // do nothing
            }else{
                pairs.put(final_tokens.get(i), "Invalid Token");
            }
        }
        return pairs;
    }
    
    static int run_syntax_analyzer(HashMap<String, String> pairs){
        int syntax_analyzer_run = 1;
        if(pairs.containsValue("Invalid Token")){
            syntax_analyzer_run = 0;
        }
            return syntax_analyzer_run;
    }
    
    static HashMap<String, String> create_exp_map(HashMap<String, String> exp_map){
        // local
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Variable", "^[A-Za-z]+[A-Za-z0-9_]*$");
        map.put("Number","^([+-]?\\d*\\.\\d*)$");

        exp_map.put("Addition Exp","^(" 
        + map.get("Number").replaceAll("^.|.$", "")+
        "|" + map.get("Variable").replaceAll("^.|.$", "") + 
        ") AN ("
        + map.get("Number").replaceAll("^.|.$", "")+
        "|" + map.get("Variable").replaceAll("^.|.$", "") + 
        ")$");

        return exp_map;
    }
    static HashMap<String, String> grammar_map(HashMap<String, String> grammar, HashMap<String, String> map,HashMap<String, String> exp_map){
        //grammar,get(key) ang result is value or yung regex
        //ginamit yung value ng ibang items sa grammar para iconcatenate sa printing,
        // {Printing=^VISIBLE -?\d+|VISIBLE [+-]?([0-9]+[.]){1}[0-9]|VISIBLE ".*"|VISIBLE WIN|FAIL$ <- value of Printing
        //str.replaceAll("^.|.$", ""), pwede gawin to remove ^ and $ sa strings
        HashMap<String, String> var = new HashMap<String, String>();
        var.put("Variable", "^[a-z]+[A-Za-z0-9_]*$");
        var.put("Variable IT", "^[A-Za-z]+[A-Za-z0-9_]*$");
        //var.put("Number", "^[-+]?[0-9]*.?[0-9]+$");
        var.put("Number", "^[\"]?[-+]?[0-9]*.?[0-9]+[\"]?$");
        //String VARIABLE = "^[A-Za-z]+[A-Za-z0-9_]*$";

        // if else
        grammar.put("Start of If-Then statement", "^O RLY[?]$");
        grammar.put("If Statement", "^YA RLY$");
        grammar.put("Else Statement", "^NO WAI$");
        grammar.put("End of If-Else and Switch-Case Statement", "^OIC$");
        grammar.put("Else-If Statement", "^MEBBE$");

        // switch case
        grammar.put("Start of Switch-Case", "^WTF[?]$");
        grammar.put("Switch-Case Default Statement", "^OMGWTF$");
        grammar.put("Switch-Case/Loop break statement", "^GTFO$");
       

        grammar.put("NUMBR Literal", "^-?\\d+$");
        grammar.put("HAI", "^HAI$");
        grammar.put("KTHXBYE", "^KTHXBYE$");
        //grammar.put("Variable", "^[A-Za-z]+[A-Za-z0-9_]*$");
        //grammar.put("NUMBAR Literal", "^[+-]?([0-9]+[.]){1}[0-9]+");
        grammar.put("NUMBAR Literal", "^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$");
        grammar.put("YARN Literal", "^\".*\"$");
        grammar.put("TROOF Literal", "^WIN$|^FAIL$"); //.substring() doesnt produce correct results kaya inalter muna yung values, should be ^WIN$|^FAIL$
        grammar.put("Variable Initialization number", "^I HAS A [A-Za-z]+[A-Za-z0-9_]* ITZ [-?\\d+|\"-?\\d+\"]*$");
        // grammar.put("Variable Initialization", "^I HAS A [A-Za-z]+[A-Za-z0-9_]* ITZ (" + map.get("Variable").replaceAll("^.|.$", "") 
        // +")$");
        grammar.put("Variable Initialization String", "^I HAS A [A-Za-z]+[A-Za-z0-9_]* ITZ \".*\"$");
        grammar.put("Variable Declaration", "^I HAS A [A-Za-z]+[A-Za-z0-9_]*$");
        grammar.put("Printing", "^VISIBLE ("+ grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1)+"|"
            + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|" //.substring removes ^ and $ from the strings
            + grammar.get("YARN Literal").substring( 1,  grammar.get("YARN Literal").length()-1)+"|"
            + grammar.get("TROOF Literal").substring( 1,  grammar.get("TROOF Literal").length()-1) +"|"
            + var.get("Variable IT").replaceAll("^.|.$", "") +"|"
            + ".*" +"|"
            + var.get("Variable").replaceAll("^.|.$", "")
            +")$");

        grammar.put("Addition","^SUM OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");

        grammar.put("Subtraction","^DIFF OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");

        grammar.put("Multiplication","^PRODUKT OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");

        grammar.put("Division","^QUOSHUNT OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");

        grammar.put("Mod","^MOD OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");

        grammar.put("Max number","^BIGGR OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");

        grammar.put("Min number","^SMALLR OF (" 
        + var.get("Number").replaceAll("^.|.$", "")+"|"
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ") [AN ("
        + var.get("Number").replaceAll("^.|.$", "") +"|" 
        + var.get("Variable").replaceAll("^.|.$", "") + 
        ")]+$");
        
        //COMPARISON OPERATIONS
        grammar.put("Both saem","^BOTH SAEM (" 
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +
        "|" + var.get("Variable").replaceAll("^.|.$", "") +
        ") AN ("
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");
    
        grammar.put("Diffrint","^DIFFRINT (" 
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +
        "|" + var.get("Variable").replaceAll("^.|.$", "") +
        ") AN ("
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");
    
        grammar.put("Both saem smallr","^BOTH SAEM (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") +
        ") AN SMALLR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") +
        ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");
    
        grammar.put("Both saem biggr","^BOTH SAEM (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ") AN BIGGR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");
    
        grammar.put("Diffrint smallr","^DIFFRINT (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ") AN SMALLR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");
    
        grammar.put("Diffrint biggr","^DIFFRINT (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ") AN BIGGR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");

        grammar.put("String Concatenation","^SMOOSH (" 
        + ".*" +
        "|" + var.get("Variable").replaceAll("^.|.$", "") +
        ") AN ("
        + ".*" +
        "|" + var.get("Variable").replaceAll("^.|.$", "") + 
        ")$");

        grammar.put("Input Statement","^GIMMEH " + var.get("Variable").replaceAll("^.|.$", "")+"$");

        grammar.put("Expression",exp_map.get("Addition Exp").replaceAll("^.|.$", ""));
        
        grammar.put("Assignment", var.get("Variable").replaceAll("^.|.$", "") +
        " R ("
        + grammar.get("Subtraction").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Multiplication").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Division").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Mod").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Addition").replaceAll("^.|.$", "")
        +  ")$");

        grammar.put("Variable Initialization Expression", "^I HAS A [A-Za-z]+[A-Za-z0-9_]* ITZ ("
        + grammar.get("Subtraction").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Multiplication").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Division").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Mod").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Min number").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Max number").replaceAll("^.|.$", "") + "|" 
        + grammar.get("Addition").replaceAll("^.|.$", "")
        + ")$");

        grammar.put("OMG <value literal>", "^OMG ("
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "") + "|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("YARN Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("TROOF Literal").replaceAll("^.|.$", "")+"|"
         + ")$");

         grammar.put("Loop IM IN YR", "^IM IN YR " + var.get("Variable").replaceAll("^.|.$", "") + " UPPIN YR "
         + var.get("Variable").replaceAll("^.|.$", "") + " (TIL|WILE) ("
         + grammar.get("Both saem").replaceAll("^.|.$", "") + "|"
         + grammar.get("Diffrint").replaceAll("^.|.$", "") +
          ")$");

        grammar.put("Loop IM OUTTA YR", "^IM OUTTA YR " + var.get("Variable").replaceAll("^.|.$", "") +
        "$");
        return grammar;
    }
    
    static ArrayList<String> clean_statements_array(ArrayList<String> statements_array){
        for(int i = 0; i < statements_array.size(); i++){
            statements_array.set(i, statements_array.get(i).trim());    //remove spaces sa harap ng items
        }
        return statements_array;
    }
    
    static ArrayList<String> get_final_syntax_tokens(ArrayList<String> tokens_syntax,ArrayList<String> final_syntax_tokens, int tokens_syntax_size){
                String bs_token = "";
                for(int t = 0; t < tokens_syntax.size(); t++){
                    bs_token = "";
                    if(tokens_syntax.get(t).equals("I") || tokens_syntax.get(t).equals("IS") || tokens_syntax.get(t).equals("IM") || tokens_syntax.get(t).equals("HOW")){
                        bs_token = tokens_syntax.get(t) + " " + tokens_syntax.get(t+1) + " " + tokens_syntax.get(t+2);
                        if(bs_token.equals("I HAS A") || bs_token.equals("IM IN YR") || bs_token.equals("IM OUTTA YR") || bs_token.equals("HOW IZ I") || bs_token.equals("IS NOW A")){
                            final_syntax_tokens.add(bs_token);
                            t = t + 2;  // update loop
                        }
                    // operations
                    }
                    else if(tokens_syntax.get(t).equals("SUM") || tokens_syntax.get(t).equals("DIFF") || tokens_syntax.get(t).equals("PRODUKT") || tokens_syntax.get(t).equals("QUOSHUNT") || tokens_syntax.get(t).equals("MOD") || tokens_syntax.get(t).equals("BIGGR") || tokens_syntax.get(t).equals("SMALLR") || tokens_syntax.get(t).equals("BOTH") || tokens_syntax.get(t).equals("EITHER") || tokens_syntax.get(t).equals("WON") || tokens_syntax.get(t).equals("ANY") || tokens_syntax.get(t).equals("ALL") || tokens_syntax.get(t).equals("O") || tokens_syntax.get(t).equals("YA") || tokens_syntax.get(t).equals("NO")){
                        bs_token = tokens_syntax.get(t) + " " + tokens_syntax.get(t+1);
                        if(bs_token.equals("SUM OF") || bs_token.equals("DIFF OF") || bs_token.equals("PRODUKT OF") || bs_token.equals("QUOSHUNT OF") || bs_token.equals("MOD OF") || bs_token.equals("BIGGR OF") || bs_token.equals("SMALLR OF") || bs_token.equals("BOTH OF") || bs_token.equals("BOTH SAEM")|| bs_token.equals("EITHER OF") || bs_token.equals("WON OF") || bs_token.equals("ANY OF") || bs_token.equals("ALL OF") || bs_token.equals("O RLY?") || bs_token.equals("YA RLY") || bs_token.equals("NO WAI")){
                            final_syntax_tokens.add(bs_token);
                            t = t + 1;  // update loop
                        }
                    }
                    else{
                        final_syntax_tokens.add(tokens_syntax.get(t));
                    }
                }
                return final_syntax_tokens;
    }
    
    static ArrayList<String> build_statements_array(ArrayList<String> statements_array, ArrayList<String> final_syntax_tokens){
        String code_line = "";
        for(int i = 0; i < final_syntax_tokens.size(); i++){
            if(final_syntax_tokens.get(i).equals("~newline~")){
                statements_array.add(code_line);    //add to new array
                code_line = "";
            }else{
                code_line = code_line + " " + final_syntax_tokens.get(i);
            }
        }
        return statements_array;
    }
    
    public static void main(String[] args) {

        // DECLARATIONS
        //strings from the input lolcode are put here, initial array
        ArrayList<String> array = new ArrayList<>();  
        //tokens that are available from the lolcode specification
        HashMap<String, String> map = new HashMap<String, String>();   
        //tokens from the input, this will be the final hashmap which will contain all the lexeme-identifier
        HashMap<String, String> pairs = new HashMap<String, String>();
        ArrayList<String> tokens = new ArrayList<String>();     // list to save our tokens
        ArrayList<String> final_tokens = new ArrayList<String>();
        ArrayList<String> tokens_syntax = new ArrayList<String>();     //list to save our tokens
        ArrayList<String> final_syntax_tokens = new ArrayList<String>();
        ArrayList<String> statements_array = new ArrayList<>(); //new array
        // map to check for correct grammar
        HashMap<String, String> grammar = new HashMap<String, String>();
        
        // map to determine syntax errors
        HashMap<String, String> correct_syntax = new HashMap<String, String>(); 
        HashMap<Integer, ArrayList<String>> for_sem_analysis = new HashMap<Integer, ArrayList<String>>();
        HashMap<String, String> var_map = new HashMap<String, String>();
        HashMap<String, String> exp_map = new HashMap<String, String>();
        var_map.put("IT","0");
        exp_map = create_exp_map(exp_map);
        grammar = grammar_map(grammar,map,exp_map);
        

        // read file and store strings into array 
        try {                                      
        File file = new File("variables.txt"); //file name
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            array.add(data);  //store into array
        }
        scanner.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }

        //remove tabs in front of strings
        array = remove_tabs(array);

        // put lexeme identifier in hashmap map
        map = lexeme_identifier_map(map);
        
        // PART 1. LEXICAL ANALYZER
        // tokenize the input --> creates array, tokens_syntax, tokens
        for(int n = 0; n < array.size(); n++){
            String token_line = array.get(n);
            int token_line_length = token_line.length();                // get length of each token line
            char[] token_line_arr = token_line.toCharArray();           // converts the String to array of characters
            int string_counter = 0;                                 // for special cases such as strings
            char current_char = 'a';
            String current_token = "";
        
            for(int i = 0; i < token_line_length; i++){         // traverse the token line by char
                current_char = token_line_arr[i];
                
                if(string_counter == 1){                                                  
                    if(current_char == '\"'){
                        current_token = current_token + "\"";
                        string_counter = 2;
                    }else{
                        current_token = current_token + current_char;  
                    }
                }
        
                if(string_counter == 0){                    // if current_char encounters a white space (newline) while string counter == 0
                    if(current_char == ' '){                // it means current token has ended and we will append to to tokens array
                        //tokens_syntax.add("~whitespace~");
                        tokens_syntax.add(current_token);
                        tokens.add(current_token);
                        current_token = "";
                    }else if(current_char == '\"'){
                        current_token = current_token + "\"";
                        string_counter = 1;
                    }else{
                        current_token = current_token + current_char;   // creates the first token
                    }
                }
        
                if(string_counter == 2){
                    string_counter = 0;
                }
        
                if(i == token_line_length-1){
                    //tokens_syntax.add("~whitespace~");
                    tokens_syntax.add(current_token);
                    tokens.add(current_token);
                    current_token = "";
                }
            }
            tokens_syntax.add("~newline~");
        }

        // builds the keywords from the tokens array
        // ex. "I", "HAS", "A" = "I HAS A"
        final_tokens = get_tokens(final_tokens, tokens);

        // classify final_tokens and put into pairs hashmap
        pairs = classify_tokens(pairs, map, final_tokens);

        // if final tokens not in pairs hash map as value
        // set final token as invalid token
        pairs = get_invalid_tokens(pairs, final_tokens);

        //System.out.println(pairs);

        // PART 2. SYNTAX ALAYZER
        // if 1 run, else halt program

        System.out.println(pairs);
        // DECLARATIONS
        //int run = run_syntax_analyzer(pairs);
        int tokens_syntax_size = tokens_syntax.size()-2;  // -2 since last data is KTHNXBYE, ~newline~  

        if(tokens_syntax.get(0).equals("HAI") || tokens_syntax.get(tokens_syntax_size).equals("KTHXBYE")){
                // BUILD FINAL SYNTAX TOKENS
                final_syntax_tokens = get_final_syntax_tokens(tokens_syntax, final_syntax_tokens, tokens_syntax_size);
                //System.out.println(final_syntax_tokens);

                // build hashmap for semantic analysis
                int op_counter = 0;
                for(int i = 0; i < final_syntax_tokens.size(); i += 1){
                    if(final_syntax_tokens.get(i).equals("~newline~")){
                        op_counter += 1;
                    }else{
                        if(for_sem_analysis.get(op_counter) == null){
                            for_sem_analysis.put(op_counter, new ArrayList<>(Arrays.asList(final_syntax_tokens.get(i))));
                        }else{
                            for_sem_analysis.get(op_counter).add(final_syntax_tokens.get(i));
                        }
                        
                    }  
                }
                
                System.out.println(for_sem_analysis);

                // build statements array by new line
                statements_array = build_statements_array(statements_array, final_syntax_tokens);
                //remove spaces sa harap ng items sa statement array
                statements_array = clean_statements_array(statements_array);

                //System.out.println(statements_array);

                // Checks if syntax is correct
                //correct_syntax = check_correct_syntax(statements_array, grammar, correct_syntax);

                int run_ie = 0;
                int oic_index = 0;
                int next_index = 0;
                int omg_index = 0;
                int omg = 0;
                int run_loop = 0;
                int start_of_loop = 0;
                int temp_num_for_loop = 0;
                int wile_til = 0;
                for(int i = 0; i < statements_array.size(); i++){
                    boolean found = false;
                    for (HashMap.Entry<String, String> set:grammar.entrySet()){
                        if(Pattern.matches(set.getValue(),statements_array.get(i))){  //comparing yung each regex value sa array which containts code_line values
                            correct_syntax.put(statements_array.get(i), set.getKey());
                            found = true;
                            ArrayList<String> temp = for_sem_analysis.get(i); //new array
                            int next_i = i + 1;                            
                            float arg1 = 0;
                            float arg2 = 0;

                            // LOOPS
                            if(pairs.get(temp.get(0)).equals("Start of loop")){
                                if (run_loop == 0){
                                    start_of_loop = i;
                                }
                                var_map.put(temp.get(1),"Loop Label");

                                if(run_loop == 1){
                                    if(pairs.get(temp.get(2)).equals("Increments a variable by 1")){
                                        temp_num_for_loop = Integer.parseInt(var_map.get(temp.get(4))) + 1;
                                        var_map.put(temp.get(4),Integer.toString(temp_num_for_loop));
                                    }
    
                                    if(pairs.get(temp.get(2)).equals("Decrement by a variable1")){
                                        temp_num_for_loop = Integer.parseInt(var_map.get(temp.get(4))) - 1;
                                        var_map.put(temp.get(4),Integer.toString(temp_num_for_loop));
                                    }
                                }

                                if(pairs.get(temp.get(5)).equals("Repeat loop while FAIL")){ 
                                    if(pairs.get(temp.get(6)).equals("== Comparison Operator")){
                                        // INITIALIZE ARG1 and ARG2
                                        if(var_map.containsKey(temp.get(7))){
                                            arg1 = Float.parseFloat(var_map.get(temp.get(7)));
                                        }else{
                                            arg1 = Float.parseFloat(temp.get(7));
                                        }
                                        if(var_map.containsKey(temp.get(9))){
                                            arg2 = Float.parseFloat(var_map.get(temp.get(9)));
                                        }else{
                                            arg2 = Float.parseFloat(temp.get(9));
                                        }
    
                                        if(arg1 == arg2){
                                            var_map.put("loop_bool","WIN");
                                            run_loop = 0;
                                        }else{
                                            var_map.put("loop_bool","FAIL");
                                        }
                                    }
                                }
                                

                                if(pairs.get(temp.get(5)).equals("Repeat loop while WIN")){                                
                                    if(pairs.get(temp.get(6)).equals("!= Comparison Operator")){
                                    // INITIALIZE ARG1 and ARG2
                                    if(var_map.containsKey(temp.get(7))){
                                        arg1 = Float.parseFloat(var_map.get(temp.get(7)));
                                    }else{
                                        arg1 = Float.parseFloat(temp.get(7));
                                    }
                                    if(var_map.containsKey(temp.get(9))){
                                        arg2 = Float.parseFloat(var_map.get(temp.get(9)));
                                    }else{
                                        arg2 = Float.parseFloat(temp.get(9));
                                    }

                                    if(arg1 != arg2){
                                        var_map.put("loop_bool","WIN");
                                        wile_til = 1;
                                    }else{
                                        var_map.put("loop_bool","FAIL");

                                        
                                        next_i = i + 1;
                                        if(run_loop == 1){
                                            while(!(pairs.get(for_sem_analysis.get(next_i).get(0)).equals("End of loop"))){
                                                oic_index+= 1;
                                                next_i += 1;
                                            }
                                            i = i + oic_index;
                                            oic_index = 0;
                                        }
                                        run_loop = 0;
                                    }
                                }}

                                run_loop = 1;
                            }

                            if (run_loop == 1){
                                if(pairs.get(temp.get(0)).equals("End of loop")){
                                    if(wile_til == 0){
                                        if(var_map.get("loop_bool").equals("FAIL")){
                                            i = start_of_loop;
                                        }
                                    }else{
                                        if(var_map.get("loop_bool").equals("WIN")){
                                            i = start_of_loop;
                                        }
                                    }

                                }
                            }

                            // SWITCH CASE
                            if(pairs.get(temp.get(0)).equals("Start of Switch-Case")){
                            }

                            // OMG lines
                            if(pairs.get(temp.get(0)).equals("Switch-Case Statement")){
                                // checks if IT == OMG <value>
                                if(temp.get(1).equals(var_map.get("IT")) && omg == 0){
                                    omg = 1;
                                } 
                                // ELSE FIND NEXT OMG or default
                                else{
                                    while((!(pairs.get(for_sem_analysis.get(next_i).get(0)).equals("Switch-Case Statement")))){
                                        if(pairs.get(for_sem_analysis.get(next_i).get(0)).equals("Switch-Case Default Statement")){
                                            break;
                                        }
                                        omg_index+= 1;
                                        next_i += 1;
                                    }
                                    i = i + omg_index;                                    
                                    omg_index = 0;
                                }
                            }

                            // every GTFO we check if a case has already been run
                            // if so we skip to OIC
                            if(pairs.get(temp.get(0)).equals("Switch-Case/Loop break statement")){
                                // means a case have been run
                                if(omg == 1){
                                    while(!(pairs.get(for_sem_analysis.get(next_i).get(0)).equals("End of If-Else and Switch-Case Statement"))){
                                        oic_index+= 1;
                                        next_i += 1;
                                    }
                                    i = i + oic_index;
                                    oic_index = 0;
                                }
                            }

                            // If else
                            if(pairs.get(temp.get(0)).equals("If Statement")){
                                if(var_map.get("bool").equals("WIN")){
                                    run_ie = 1;
                                }else{
                                    // skip to else statement
                                    while(!(pairs.get(for_sem_analysis.get(next_i).get(0)).equals("Else Statement"))){
                                        next_index+= 1;
                                        next_i += 1;
                                    }
                                    i = i + next_index;
                                    next_index = 0;
                                    
                                }
                            } 

                            if(pairs.get(temp.get(0)).equals("Else Statement")){
                                if(run_ie == 1){ // this means if statement run
                                    while(!(pairs.get(for_sem_analysis.get(next_i).get(0)).equals("End of If-Else and Switch-Case Statement"))){
                                        oic_index+= 1;
                                        next_i += 1;
                                    }
                                    i = i + oic_index;
                                    oic_index = 0;
                                }
                            }


                            if(pairs.get(temp.get(0)).equals("End of If-Else and Switch-Case Statement")){
                                //System.out.println("End of If-Else statement");
                            }
                            

                            // Variable declaration
                            if(pairs.get(temp.get(0)).equals("Variable declaration")){
                                // I HAS A thing ITZ 5
                                if (for_sem_analysis.get(i).size() ==4){
                                    var_map.put(temp.get(1),temp.get(3));
                                }
                                // I HAS A thing
                                if (for_sem_analysis.get(i).size() == 2){
                                    var_map.put(temp.get(1),"0");
                                }
                                
                            }

                            // bypass Assignment statements
                            int op_bypass = 0;
                            int save_to_it = 0;
                            if(pairs.get(temp.get(0)).equals("Addition Operator") || pairs.get(temp.get(0)).equals("Subtraction Operator") || pairs.get(temp.get(0)).equals("Multiplication Operator") || pairs.get(temp.get(0)).equals("Division Operator") || pairs.get(temp.get(0)).equals("Modulo Operator")){
                                op_bypass = 1;
                                save_to_it = 1;
                            }

                            if(set.getKey().equals("Variable Initialization Expression")){
                                op_bypass = 1;
                                save_to_it = 2;
                            }

                            // Assignment Statements
                            if(pairs.get(temp.get(0)).equals("Variable") || op_bypass == 1){
                                if(temp.get(1).equals("R") || op_bypass == 1){
                                    int counter = 0, op = 0;
                                    // If VARIABLE R SUM OF 5 AN 6
                                    if(save_to_it == 0){
                                        counter = 3;        // start of the number
                                        op = 2;             // index of operation
                                    // IF SUM OF 5 AN 6
                                    }else if (save_to_it == 1){
                                        counter = 1;
                                        op = 0;
                                    }
                                    else if (save_to_it == 2){
                                        counter = 4;
                                        op = 3;
                                    }
                                    
                                    int temp_length = temp.size();       
                                    float accum = 0;
                                    float temp_num = 0;

                                    while (counter != temp_length){
                                        if(var_map.containsKey(temp.get(counter))){
                                            temp_num = Float.parseFloat(var_map.get(temp.get(counter)));
                                        }else{
                                            temp_num = Float.parseFloat(temp.get(counter));
                                        }
                                        // ADDITION
                                        if(pairs.get(temp.get(op)).equals("Addition Operator")){
                                            accum = temp_num + accum;
                                        }
   
                                        // SUBTRACTION
                                        if(pairs.get(temp.get(op)).equals("Subtraction Operator")){
                                            // sets the first number to be subtracted to
                                            if(accum == 0){
                                                accum = Float.parseFloat(temp.get(counter));
                                                
                                            }else{
                                                temp_num = Float.parseFloat(temp.get(counter));
                                                accum = accum - temp_num;
                                            }
                                        }

                                        // MULTIPLICATION
                                        float product;
                                        if(pairs.get(temp.get(op)).equals("Multiplication Operator")){
                                            temp_num = Float.parseFloat(temp.get(counter));
                                            if(accum == 0){
                                                product = 0;
                                                accum = 1;
                                                accum = accum * temp_num;
                                            }else{
                                                product = accum * temp_num;
                                                accum = product;
                                            }

                                        }

                                        float quotient;
                                        if(pairs.get(temp.get(op)).equals("Division Operator")){
                                            temp_num = Float.parseFloat(temp.get(counter));
                                            if(accum == 0){
                                                accum = temp_num;
                                            }else{
                                                quotient = accum / temp_num;
                                                accum = quotient;
                                            }
                                        }

                                        float mod;
                                        if(pairs.get(temp.get(op)).equals("Modulo Operator")){
                                            temp_num = Float.parseFloat(temp.get(counter));
                                            if(accum == 0){
                                                accum = temp_num;
                                            }else{
                                                mod = accum % temp_num;
                                                accum = mod;
                                            }
                                        }

                                        if(pairs.get(temp.get(op)).equals("max")){
                                            temp_num = Float.parseFloat(temp.get(counter));
                                            if(accum == 0){
                                                accum = temp_num;
                                            }else{
                                                if(accum < temp_num){ // if accum is less then it is not max
                                                    accum = temp_num;
                                                }
                                            }
                                        }

                                        if(pairs.get(temp.get(op)).equals("min")){
                                            temp_num = Float.parseFloat(temp.get(counter));
                                            if(accum == 0){
                                                accum = temp_num;
                                            }else{
                                                if(accum > temp_num){ // if accum is greater then it is not min
                                                    accum = temp_num;
                                                }
                                            }
                                        }

                                        if(counter == temp_length-1){
                                            counter++;
                                        }else{
                                            counter = counter + 2;
                                        }  
                                    }
                                    // SAVE TO VARIABLE
                                    if(save_to_it == 0){
                                        var_map.put(temp.get(0),Float.toString(accum));
                                    // SAVE TO IT
                                    }
                                    else if (save_to_it == 1){
                                        var_map.put("IT",Float.toString(accum));
                                    }
                                    else if (save_to_it == 2){
                                        var_map.put(temp.get(1),Float.toString(accum));
                                    }
                                    
                                }
                            }

                            // R
                            if (for_sem_analysis.get(i).size() == 3){
                                if(pairs.get(temp.get(1)).equals("Assignment operation")){
                                    var_map.put(temp.get(0),temp.get(2));
                                }
                            }

                            // GIMMEH
                            if(pairs.get(temp.get(0)).equals("Stores a string input to a variable")){
                                try (Scanner scanner_input = new Scanner(System.in)) {
                                    System.out.println("TYPE SOMETHING AND ENTER");
                                    String user_input = scanner_input.nextLine();
                                    if(var_map.containsKey(temp.get(1))){
                                        var_map.put(temp.get(1),user_input);
                                    }else{
                                        System.out.println("Can only be saved to a variable");
                                    }
                                } 
                            }

                            // VISIBLE
                            if(pairs.get(temp.get(0)).equals("Used for printing a statement")){
                                String print_statement = "";
                                if(temp.size() > 2){
                                    for(int loop_print = 0; loop_print < temp.size(); loop_print+=1){
                                        print_statement = print_statement + temp.get(loop_print) + " ";
                                    }
                                    System.out.println(print_statement);
                                }else{
                                    if(var_map.containsKey(temp.get(1))){
                                        System.out.println(var_map.get(temp.get(1)));
                                    }else{
                                        System.out.println(temp.get(1));
                                    } 
                                }

                            }

                            // SMOOSH
                            if(pairs.get(temp.get(0)).equals("Concatenation Operator")){
                                int temp_length = temp.size() / 2;
                                int j = 0;
                                int str_counter = 1;
                                String build_string = "";
                                while(j < temp_length){
                                    if(pairs.get(temp.get(str_counter)).equals("Variable")){
                                        String temp1 = var_map.get(temp.get(1)).replaceAll("\"", ""); 
                                        build_string = build_string + temp1;
                                    }else{
                                        build_string = build_string + temp.get(str_counter).replaceAll("\"", ""); 
                                    }
                                    str_counter += 2;
                                    j += 1;
                                }
                                build_string = "\"" + build_string + "\"";
                                if(var_map.containsKey(temp.get(1))){
                                    var_map.put(temp.get(1),build_string);
                                }else{
                                    var_map.put("IT",build_string);
                                }
                                
                            }


                            // COMPARISON OPERATIONS


                            // BOTH SAEM
                            if(pairs.get(temp.get(0)).equals("== Comparison Operator")){
                                arg1 = Float.parseFloat(temp.get(1));
                                // BIGGR
                                if(pairs.get(temp.get(3)).equals("max")){
                                    arg2 = Float.parseFloat(temp.get(6));
                                    if(arg1 >= arg2){
                                        var_map.put("bool","WIN");
                                        //System.out.println("TRUE");
                                    }else{
                                        var_map.put("bool","FAIL");
                                        //System.out.println("FALSE");
                                    }
                                }
                                // SMALLR
                                else if(pairs.get(temp.get(3)).equals("min")){
                                    arg2 = Float.parseFloat(temp.get(6));
                                    if(arg1 <= arg2){
                                        var_map.put("bool","WIN");
                                        //System.out.println("TRUE");
                                    }else{
                                        var_map.put("bool","FAIL");
                                        //System.out.println("FALSE");
                                    }
                                }
                                else{
                                    arg2 = Float.parseFloat(temp.get(3));
                                    if(arg1 == arg2){
                                        var_map.put("bool","WIN");
                                        //System.out.println("TRUE");
                                    }else{
                                        var_map.put("bool","FAIL");
                                        //System.out.println("FALSE");
                                    }
                                }
                            }

                            // DIFFRINT
                            if(pairs.get(temp.get(0)).equals("!= Comparison Operator")){
                                arg1 = Float.parseFloat(temp.get(1));
                                // BIGGR
                                if(pairs.get(temp.get(3)).equals("max")){
                                    arg2 = Float.parseFloat(temp.get(6));
                                    if(arg1 < arg2){
                                        //System.out.println("WIN");
                                        var_map.put("bool","WIN");
                                    }else{
                                        //System.out.println("FAIL");
                                        var_map.put("bool","FAIL");
                                    }
                                }
                                // SMALLR
                                else if(pairs.get(temp.get(3)).equals("min")){
                                    arg2 = Float.parseFloat(temp.get(6));
                                    if(arg1 > arg2){
                                        //System.out.println("WIN");
                                        var_map.put("bool","WIN");
                                    }else{
                                        //System.out.println("FALSE");
                                        var_map.put("bool","FAIL");
                                    }
                                }
                                else{
                                    arg2 = Float.parseFloat(temp.get(3));
                                    if(arg1 != arg2){
                                        //System.out.println("WIN");
                                        var_map.put("bool","WIN");
                                    }else{
                                        //System.out.println("FAIL");
                                        var_map.put("bool","FAIL");
                                    }
                                }
                            }

                            // BOOLEAN OPERATIONS

                        }
                    }
                    //syntax error
                    if (found == false){
                        int temp_i = 0;
                        Boolean var_found = false;
                        Boolean error_found = false;
                        int syntax_error_i; 
                        int argument_count = 0;
                        int operation_count = 0;
                        int an_count = 0;
                        String[] syntax_error_string = statements_array.get(i).split(" "); 
                        int i_plus = i + 1;
                        System.out.println(statements_array.get(i) + " Syntax Error at line " + i_plus);
                        //Syntax Error checking 
                        //variable declaration, missing pa yung sa operations
                        if(syntax_error_string[0].equals("I")
                        &&syntax_error_string[1].equals("HAS")
                        &&syntax_error_string[2].equals("A")){
                            //uninitialized variable
                            if(syntax_error_string.length == 4){
                                if(!syntax_error_string[3].matches("^[a-z]+[A-Za-z0-9_]*$")){
                                    System.out.println("Syntax error: invalid variable name");
                                    break;
                                }
                            }

                            if(syntax_error_string.length > 4){
                                if(!syntax_error_string[4].equals("ITZ")){
                                    System.out.println("Syntax error: missing keyword");
                                    break;
                                }

                                if(syntax_error_string.length == 5){
                                    System.out.println("Syntax error: missing argument");
                                }

                                if(syntax_error_string.length > 6&&(!syntax_error_string[5].matches("^[a-z]+[A-Za-z0-9_]*$")
                                ||!syntax_error_string[5].matches("^\".*\"$"))){
                                    System.out.println("Syntax error: invalid type");
                                    break;
                                }
                            }
                        }
                        //string concat "^\".*\"$"
                        if(syntax_error_string[0].equals("SMOOSH")
                        ||(syntax_error_string[0].equals("VISIBLE")&&syntax_error_string[1].equals("SMOOSH"))){
                            if(syntax_error_string[syntax_error_string.length-1].equals("AN")){
                                System.out.println("Syntax error: invalid end to operation");
                                error_found = true;
                                break;
                            }

                            temp_i = 1;
                            if(syntax_error_string[1].equals("SMOOSH")){
                                temp_i = 2;
                            }
                            
                            if(syntax_error_string.length <= 3){
                                if(!syntax_error_string[temp_i].matches("^\".*\"$")
                                ||!syntax_error_string[temp_i].matches("^[a-z]+[A-Za-z0-9_]*$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }
                            }
                            
                            if(syntax_error_string.length > 3){ 
                                
                                if(!syntax_error_string[temp_i].matches("^\".*\"$")
                                ||!syntax_error_string[temp_i].matches("^[a-z]+[A-Za-z0-9_]*$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }

                                if(!syntax_error_string[temp_i+1].equals("AN")){
                                    System.out.println("Syntax error: missing AN");
                                    error_found = true;
                                    break;
                                }

                                if(!syntax_error_string[temp_i+2].matches("^\".*\"$")
                                ||!syntax_error_string[temp_i+2].matches("^[a-z]+[A-Za-z0-9_]*$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }

                                if(syntax_error_string.length>4){
                                    for(syntax_error_i = temp_i+3; syntax_error_i< syntax_error_string.length; syntax_error_i++){
                                        if(!syntax_error_string[syntax_error_i].equals("AN")){
                                            System.out.println("Syntax error: missing AN");
                                            error_found = true;
                                            break;
                                        }

                                        if(!syntax_error_string[syntax_error_i+1].matches("^\".*\"$")
                                        ||!syntax_error_string[syntax_error_i+1].matches("^[a-z]+[A-Za-z0-9_]*$")){
                                            System.out.println("Syntax error: invalid data type");
                                            error_found = true;
                                            break;
                                        }

                                        if(syntax_error_i<syntax_error_string.length-2){
                                            syntax_error_i = syntax_error_i + 2;
                                        }
                                    }
                                }
                            }
                        }
                        // map.put("^-?\\d+$", "NUMBR Literal");
                        // map.put("^([+-]?\\d*\\.\\d*)$", "NUMBAR Literal");
                        // map.put("^\".*\"$", "YARN Literal");
                        // map.put("^WIN$|^FAIL$", "TROOF Literal");
                        //numerical operations//////////////////////////////////////////////////////////////////////
                        if(syntax_error_string[0].equals("SUM")
                            ||syntax_error_string[0].equals("DIFF")
                            ||syntax_error_string[0].equals("PRODUKT")
                            ||syntax_error_string[0].equals("QUOSHUNT")
                            ||syntax_error_string[0].equals("MOD")){ 
                                if(syntax_error_string[syntax_error_string.length-1].equals("AN")){
                                    System.out.println("Syntax error: invalid end to operation");
                                    error_found = true;
                                    break;
                                }
                            
                                for(syntax_error_i = 0; syntax_error_i < syntax_error_string.length; syntax_error_i++){
                                    if(
                                    syntax_error_string[syntax_error_i].equals("SUM")
                                    ||syntax_error_string[syntax_error_i].equals("DIFF")
                                    ||syntax_error_string[syntax_error_i].equals("PRODUKT")
                                    ||syntax_error_string[syntax_error_i].equals("QUOSHUNT")
                                    ||syntax_error_string[syntax_error_i].equals("MOD")
                                    &&syntax_error_string[syntax_error_i+1].equals("OF")){
                                        operation_count++;
                                        syntax_error_i++;
                                    }else if(    
                                    syntax_error_string[syntax_error_i].matches("^-?\\d+$")
                                    ||syntax_error_string[syntax_error_i].matches("^([+-]?\\d*\\.\\d*)$")
                                    ||syntax_error_string[syntax_error_i].matches("^[a-z]+[A-Za-z0-9_]*$")//check variable type here
                                    ||syntax_error_string[syntax_error_i].matches("^\"-?\\d+\"$")
                                    ||syntax_error_string[syntax_error_i].matches("^\"([+-]?\\d*\\.\\d*)\"$")
                                    ){
                                        //if two numbers or variables are beside each other
                                        if(
                                        syntax_error_string[syntax_error_i+1].matches("^-?\\d+$")
                                        ||syntax_error_string[syntax_error_i+1].matches("^([+-]?\\d*\\.\\d*)$")
                                        ||syntax_error_string[syntax_error_i+1].matches("^[a-z]+[A-Za-z0-9_]*$") //check variable type here
                                        ||syntax_error_string[syntax_error_i].matches("^\"-?\\d+\"$")
                                        ||syntax_error_string[syntax_error_i].matches("^\"([+-]?\\d*\\.\\d*)\"$")
                                        &&syntax_error_i<syntax_error_string.length-1){
                                            System.out.println("Syntax error: missing AN");
                                            error_found = true;
                                            break;
                                        }else{
                                            argument_count++;
                                        }
                                    //if two ANs are beside each other
                                    }else if(
                                    syntax_error_string[syntax_error_i].equals("AN")
                                    &&syntax_error_string[syntax_error_i+1].equals("AN")
                                    &&syntax_error_i<syntax_error_string.length-1){
                                        System.out.println("Syntax error: missing an argument");
                                        error_found = true;
                                        break;
                                    }else{
                                        System.out.println("Syntax error: invalid statement");
                                        error_found = true;
                                        break;
                                    }
                                }
                                //correct format but has too many or too little arguments
                                if(error_found == false){
                                    if(operation_count >= argument_count){
                                        System.out.println("Syntax error: too few arguments");
                                    }
                                    if(argument_count - operation_count > 2){
                                        System.out.println("Syntax error: too many arguments");
                                    }
                                }
     
                            }
                        //Boolean Operations finite arity
                        //ALL OF NOT x AN BOTH OF y AN z AN EITHER OF x AN y MKAY 
                        if(
                        (syntax_error_string[0].equals("BOTH")
                        ||syntax_error_string[0].equals("EITHER")
                        ||syntax_error_string[0].equals("WON")
                        ||syntax_error_string[0].equals("ALL")
                        ||syntax_error_string[0].equals("ANY")
                        &&syntax_error_string[1].equals("OF"))
                        ||syntax_error_string[0].equals("NOT")){
                            if(syntax_error_string[syntax_error_string.length-1].equals("AN")){
                                System.out.println("Syntax error: invalid end to statement");
                                error_found = true;
                                break;
                            }else if(syntax_error_string[0].equals("NOT")){
                                if(syntax_error_string.length > 2){
                                    System.out.println("Syntax error: invalid statement length");
                                    error_found = true;
                                    break;
                                }else if(syntax_error_string.length < 2){
                                    System.out.println("Syntax error: missing argument");
                                    error_found = true;
                                    break;
                                }else{
                                    if(!(syntax_error_string[1].equals("WIN")
                                    ||syntax_error_string[1].equals("FAIL")
                                    ||syntax_error_string[1].matches("^[a-z]+[A-Za-z0-9_]*$"))){ //check variable type here
                                        System.out.println("Syntax error: invalid variable type");
                                    }
                                }
                            }else if((syntax_error_string[0].equals("BOTH")
                                    ||syntax_error_string[0].equals("EITHER")
                                    ||syntax_error_string[0].equals("WON"))
                                    &&syntax_error_string[1].equals("OF")){
                                    if(
                                        !(syntax_error_string[2].equals("WIN")
                                        ||syntax_error_string[2].equals("FAIL")
                                        ||syntax_error_string[2].matches("^[a-z]+[A-Za-z0-9_]*$"))){
                                            System.out.println("Syntax error: invalid argument type");  
                                            error_found = true;
                                            break;
                                    }else if(!syntax_error_string[3].equals("AN")){
                                        System.out.println("Syntax error: missing AN operator");
                                    }else if(!(syntax_error_string[4].equals("WIN")
                                            ||syntax_error_string[4].equals("FAIL")
                                            ||syntax_error_string[4].matches("^[a-z]+[A-Za-z0-9_]*$"))){
                                            System.out.println("Syntax error: invalid argument type");  
                                            error_found = true;
                                            break;
                                    }else if(syntax_error_string.length > 5){
                                        System.out.println("Syntax error: extra argument");
                                    }
                                }
                        }
                        //boolean operations infinite arity/////////////////////////////////////////////////////////////
                        if((syntax_error_string[0].equals("ALL")||syntax_error_string[0].equals("ANY"))
                        &&syntax_error_string[1].equals("OF")){
                            if(syntax_error_string[syntax_error_string.length-1].equals("AN")
                            ||!syntax_error_string[syntax_error_string.length-1].equals("MKAY")){
                                System.out.println("Syntax error: invalid end to statement");
                                error_found = true;
                                break;
                            }
                            int mkay_count = 0;
                            for(syntax_error_i = 2; syntax_error_i < syntax_error_string.length; syntax_error_i++){
                                if(syntax_error_string[syntax_error_i].equals("MKAY")){
                                    mkay_count++;
                                }
                            }

                            if(mkay_count > 1){
                                System.out.println("Syntax error: multiple MKAYs");
                                error_found = true;
                                break;
                               
                            }
                        
                            for(syntax_error_i = 2; syntax_error_i < syntax_error_string.length; syntax_error_i++){


                                if(syntax_error_string[syntax_error_i].equals("NOT")){ 
                                    if(
                                    !(syntax_error_string[syntax_error_i+1].equals("WIN")
                                    ||syntax_error_string[syntax_error_i+1].equals("FAIL")
                                    ||syntax_error_string[syntax_error_i+1].matches("^[a-z]+[A-Za-z0-9_]*$"))
                                    &&syntax_error_i<syntax_error_string.length-1){          //check variable type here
                                        System.out.println("Syntax error: invalid variable type");
                                        error_found = true;
                                        break;
                                    }else if(!syntax_error_string[syntax_error_i+2].equals("AN")
                                    &&syntax_error_i+2<syntax_error_string.length-1){
                                            System.out.println("Syntax error: invalid end to statement asdasd");
                                            error_found = true;
                                            break;
                                    }

                                    if(syntax_error_i<syntax_error_string.length-2){
                                        syntax_error_i = syntax_error_i + 2;
                                    }
                                    
                                }
                                if(
                                    (syntax_error_string[syntax_error_i].equals("BOTH")
                                    ||syntax_error_string[syntax_error_i].equals("EITHER")
                                    ||syntax_error_string[syntax_error_i].equals("WON"))
                                    &&syntax_error_string[syntax_error_i+1].equals("OF")){
                                    if(
                                        !(syntax_error_string[syntax_error_i+2].equals("WIN")
                                        ||syntax_error_string[syntax_error_i+2].equals("FAIL")
                                        ||syntax_error_string[syntax_error_i+2].matches("^[a-z]+[A-Za-z0-9_]*$"))){
                                            System.out.println("Syntax error: invalid argument type");  
                                            error_found = true;
                                            break;
                                    }else if(!syntax_error_string[syntax_error_i+3].equals("AN")){
                                        System.out.println("Syntax error: missing AN operator");
                                        error_found = true;
                                        break;
                                    }else if(
                                        !(syntax_error_string[syntax_error_i+4].equals("WIN")
                                        ||syntax_error_string[syntax_error_i+4].equals("FAIL")
                                        ||syntax_error_string[syntax_error_i+4].matches("^[a-z]+[A-Za-z0-9_]*$"))){
                                            System.out.println("Syntax error: invalid argument type");  
                                            error_found = true;
                                            break;
                                    }else if(!syntax_error_string[syntax_error_i+5].equals("AN")
                                    &&syntax_error_i+5<syntax_error_string.length-1){
                                            System.out.println("Syntax error: invalid end to statement asdasd");
                                            error_found = true;
                                            break;
                                    }
                                    
                                    if(syntax_error_i<syntax_error_string.length-5){
                                        syntax_error_i = syntax_error_i + 5;
                                    }
                                }
                            }
                        }

                        //Comparison Operations and Relational Operations////
                        //comparison operations
                        if((syntax_error_string[0].equals("BOTH")
                            &&syntax_error_string[1].equals("SAEM"))
                            ||syntax_error_string[0].equals("DIFFRINT")){
                            if(syntax_error_string[syntax_error_string.length-1].equals("AN")){
                                System.out.println("Syntax error: invalid end to statement");
                                error_found = true;
                                break;
                            }
                            temp_i = 1;
                            if(syntax_error_string[0].equals("BOTH")){
                                temp_i = 2;
                            }    
                            
                            if(syntax_error_string.length <= 5){
                                if(syntax_error_string[temp_i].matches("^-?\\d+$")
                                ||syntax_error_string[temp_i].matches("^([+-]?\\d*\\.\\d*)$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }

                                if(!syntax_error_string[temp_i+1].equals("AN")){
                                    System.out.println("Syntax error: AN expected");
                                    error_found = true;
                                    break;
                                }

                                if(syntax_error_string[temp_i+2].matches("^-?\\d+$")
                                ||syntax_error_string[temp_i+2].matches("^([+-]?\\d*\\.\\d*)$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }
                            }

                            //relational operations
                            if(syntax_error_string.length > 5 && syntax_error_string.length <= 9){
                                if(syntax_error_string[temp_i].matches("^-?\\d+$")
                                ||syntax_error_string[temp_i].matches("^([+-]?\\d*\\.\\d*)$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }

                                if(!syntax_error_string[temp_i+1].equals("AN")){
                                    System.out.println("Syntax error: AN expected");
                                    error_found = true;
                                    break;
                                }

                                if(syntax_error_string[temp_i+2].equals("SMALLR")
                                ||syntax_error_string[temp_i+2].equals("BIGGR")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }

                                if(!syntax_error_string[temp_i+3].equals("OF")){
                                    System.out.println("Syntax error: OF expected");
                                    error_found = true;
                                    break;
                                }

                                if(syntax_error_string[temp_i+4].matches("^-?\\d+$")
                                ||syntax_error_string[temp_i+4].matches("^([+-]?\\d*\\.\\d*)$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }

                                if(!syntax_error_string[temp_i+5].equals("AN")){
                                    System.out.println("Syntax error: AN expected");
                                    error_found = true;
                                    break;
                                }

                                if(syntax_error_string[temp_i+6].matches("^-?\\d+$")
                                ||syntax_error_string[temp_i+6].matches("^([+-]?\\d*\\.\\d*)$")){
                                    System.out.println("Syntax error: invalid data type");
                                    error_found = true;
                                    break;
                                }
                            }

                            if(syntax_error_string.length >= 10){
                                System.out.println("Syntax error: invalid operation length");
                                error_found = true;
                                break;
                            }
                        }
                        //Typecasting
                        // if(syntax_error_string[0].equals("MAEK")){
                        //         for (HashMap.Entry<String, String> set:var_map.entrySet()){        
                        //             if(Pattern.matches(set.getKey(),syntax_error_string[1])){
                        //                 var_found = true;
                        //             }
                        //         }
                        //         if(var_found == false){
                        //             System.out.println("Syntax error: Variable has not been initialized");
                        //         }else{
                        //             if(syntax_error_string.length == 4){
                        //                 if(syntax_error_string[2].equals("A")){
                        //                     if(!syntax_error_string[3].equals("NUMBR")
                        //                     ||!syntax_error_string[3].equals("NUMBAR")
                        //                     ||!syntax_error_string[3].equals("YARN")){
                        //                     System.out.println("Syntax error: invalid data type");
                        //                     }
                        //                 }
                        //         }else if(syntax_error_string.length == 3){
                        //                 if(!syntax_error_string[3].equals("NUMBR")
                        //                 ||!syntax_error_string[3].equals("NUMBAR")
                        //                 ||!syntax_error_string[3].equals("YARN")){
                        //                 System.out.println("Syntax error: invalid data type");
                        //                 }
                        //             }
                        //         }   
                        //     }
                            
                            i = statements_array.size(); //break
                    }
                }
                System.out.print(var_map);
            }
        }
}
