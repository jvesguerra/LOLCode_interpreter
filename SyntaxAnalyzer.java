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
        map.put("^BIGGR OF$", "Comparison Operator");
        map.put("^SMALLR OF$", "Comparison Operator");
        map.put("^BOTH OF$", "AND Boolean Operator");
        map.put("^EITHER OF$", "OR Boolean Operator");
        map.put("^WON OF$", "XOR Boolean Operator");
        map.put("^NOT$", "NOT Boolean Operator");
        map.put("^ANY OF$", "Infinite Arity OR Operator");
        map.put("^ALL OF$", "Infinite Arity AND Operator");
        map.put("^SAEM$", "Comparison Operator");
        map.put("^BOTH SAEM$", "== Comparison Operator");
        map.put("^DIFFRINT$", "> or < Comparison Operator");
        map.put("^SMOOSH$", "Concatenation Operator");
        map.put("^MAEK$", "Variable typecasting");
        map.put("^IS NOW A$", "Variable typecasting");
        map.put("^A", "Refers to a particular variable");
        
        map.put("^VISIBLE$", "Used for printing a statement");
        map.put("^GIMMEH$", "Stores a string input to a variable");
        map.put("^O RLY$", "Start of If-Then statement");
        map.put("^YA RLY$", "If Statement");
        map.put("^MEBBE$", "Else-If Statement");
        map.put("^NO WAI$", "Else Statement");
        map.put("^OIC$", "End of If-Else and Switch-Case Statement");
        map.put("^WTF?$", "Start of Switch-Case");
        map.put("^OMG$", "Switch-Case Statement");
        map.put("^OMGWTF$", "Switch-Case Default Statement");
        map.put("^IM IN YR$", "Start of loop");
        map.put("^UPPIN$", "Increments a variable by 1");
        map.put("^NERFIN$", "Decrement by a variable1");
        map.put("^YR$", "Refers to the current argument or variable");
        map.put("^TIL$", "Repeats a loop");
        map.put("^WILE$", "Repeats a loop");
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
        map.put("^IM OUTTA YR$", "Loop Identifier");
        map.put("^IM IN YR$", "Loop Identifier");

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
            }else if(tokens.get(t).equals("SUM") || tokens.get(t).equals("DIFF") || tokens.get(t).equals("PRODUKT") || tokens.get(t).equals("QUOSHUNT") || tokens.get(t).equals("MOD") || tokens.get(t).equals("BIGGR") || tokens.get(t).equals("SMALLR") || tokens.get(t).equals("BOTH") || tokens.get(t).equals("EITHER") || tokens.get(t).equals("WON") || tokens.get(t).equals("ANY") || tokens.get(t).equals("ALL")){
                build_token = tokens.get(t) + " " + tokens.get(t+1);
                if(build_token.equals("SUM OF") || build_token.equals("DIFF OF") || build_token.equals("PRODUKT OF") || build_token.equals("QUOSHUNT OF") || build_token.equals("MOD OF") || build_token.equals("BIGGR OF") || build_token.equals("SMALLR OF") || build_token.equals("BOTH OF") || build_token.equals("EITHER OF") || build_token.equals("WON OF") || build_token.equals("ANY OF") || build_token.equals("ALL OF") || build_token.equals("BOTH SAEM")){
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
                            pairs.put(final_tokens.get(i),"Variable"); //put into 'pairs' hashmap
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
    
    static HashMap<String, String> grammar_map(HashMap<String, String> grammar){
        //grammar,get(key) ang result is value or yung regex
        //ginamit yung value ng ibang items sa grammar para iconcatenate sa printing,
        // {Printing=^VISIBLE -?\d+|VISIBLE [+-]?([0-9]+[.]){1}[0-9]|VISIBLE ".*"|VISIBLE WIN|FAIL$ <- value of Printing
        //str.replaceAll("^.|.$", ""), pwede gawin to remove ^ and $ sa strings
        grammar.put("NUMBR Literal", "^-?\\d+$");
        grammar.put("HAI", "^HAI$");
        grammar.put("KTHXBYE", "^KTHXBYE$");
        grammar.put("Variable", "^[A-Za-z]+[A-Za-z0-9_]*$");
        //grammar.put("NUMBAR Literal", "^[+-]?([0-9]+[.]){1}[0-9]+");
        grammar.put("NUMBAR Literal", "^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$");
        grammar.put("YARN Literal", "^\".*\"$");
        grammar.put("TROOF Literal", "^WIN$|^FAIL$"); //.substring() doesnt produce correct results kaya inalter muna yung values, should be ^WIN$|^FAIL$
        grammar.put("Variable Initialization", "^I HAS A [A-Za-z]+[A-Za-z0-9_]* ITZ [-?\\d+|\"-?\\d+\"]*$");
        grammar.put("Variable Declaration", "^I HAS A [A-Za-z]+[A-Za-z0-9_]*$");
        grammar.put("Printing", "^VISIBLE ("+ grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1)+"|"
            + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|" //.substring removes ^ and $ from the strings
            + grammar.get("YARN Literal").substring( 1,  grammar.get("YARN Literal").length()-1)+"|"
            + grammar.get("TROOF Literal").substring( 1,  grammar.get("TROOF Literal").length()-1) +"|"
            + grammar.get("Variable").replaceAll("^.|.$", "")
            +")$");
        grammar.put("Addition","^SUM OF (" 
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") + ") AN ("
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +")$");
    
        grammar.put("Subtraction","^DIFF OF (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
        grammar.put("Multiplication","^PRODUKT OF (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
        grammar.put("Division","^QUOSHUNT OF (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
        grammar.put("Mod","^MOD OF (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
        
        //COMPARISON OPERATIONS
        grammar.put("Both saem","^BOTH SAEM (" 
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") + ") AN ("
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +")$");
    
        grammar.put("Diffrint","^DIFFRINT (" 
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") + ") AN ("
        + grammar.get("NUMBAR Literal").replaceAll("^.|.$", "")+"|"
        + grammar.get("NUMBR Literal").replaceAll("^.|.$", "") +")$");
    
        grammar.put("Both saem smallr","^BOTH SAEM (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN SMALLR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +") AN ("+ grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
    
        grammar.put("Both saem biggr","^BOTH SAEM (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN BIGGR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +") AN ("+ grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
    
        grammar.put("Diffrint smallr","^DIFFRINT (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN SMALLR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +") AN ("+ grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
    
        grammar.put("Diffrint biggr","^DIFFRINT (" 
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) + ") AN BIGGR OF ("
        + grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +") AN ("+ grammar.get("NUMBAR Literal").substring( 1,  grammar.get("NUMBAR Literal").length()-1)+"|"
        + grammar.get("NUMBR Literal").substring( 1,  grammar.get("NUMBR Literal").length()-1) +")$");
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
                    else if(tokens_syntax.get(t).equals("SUM") || tokens_syntax.get(t).equals("DIFF") || tokens_syntax.get(t).equals("PRODUKT") || tokens_syntax.get(t).equals("QUOSHUNT") || tokens_syntax.get(t).equals("MOD") || tokens_syntax.get(t).equals("BIGGR") || tokens_syntax.get(t).equals("SMALLR") || tokens_syntax.get(t).equals("BOTH") || tokens_syntax.get(t).equals("EITHER") || tokens_syntax.get(t).equals("WON") || tokens_syntax.get(t).equals("ANY") || tokens_syntax.get(t).equals("ALL")){
                        bs_token = tokens_syntax.get(t) + " " + tokens_syntax.get(t+1);
                        if(bs_token.equals("SUM OF") || bs_token.equals("DIFF OF") || bs_token.equals("PRODUKT OF") || bs_token.equals("QUOSHUNT OF") || bs_token.equals("MOD OF") || bs_token.equals("BIGGR OF") || bs_token.equals("SMALLR OF") || bs_token.equals("BOTH OF") || bs_token.equals("BOTH SAEM")|| bs_token.equals("EITHER OF") || bs_token.equals("WON OF") || bs_token.equals("ANY OF") || bs_token.equals("ALL OF")){
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
                // check code line
                // System.out.println(code_line);
                statements_array.add(code_line);    //add to new array
                code_line = "";
            }else{
                code_line = code_line + " " + final_syntax_tokens.get(i);
            }
        }
        return statements_array;
    }
    
    static HashMap<String, String> check_correct_syntax(ArrayList<String> statements_array,HashMap<String, String> grammar,HashMap<String, String> correct_syntax){
        for(int i = 0; i < statements_array.size(); i++){
            boolean found = false;
            for (HashMap.Entry<String, String> set:grammar.entrySet()){
                if(Pattern.matches(set.getValue(),statements_array.get(i))){  //comparing yung each regex value sa array which containts code_line values
                    correct_syntax.put(statements_array.get(i), set.getKey());
                    found = true;
                }
            }if (found == false){
                System.out.println(statements_array.get(i) + " Syntax Error");
                i = statements_array.size(); //break
            }
        }
        return correct_syntax;
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
        grammar = grammar_map(grammar);
        // map to determine syntax errors
        HashMap<String, String> correct_syntax = new HashMap<String, String>(); 
        HashMap<Integer, ArrayList<String>> func_map = new HashMap<Integer, ArrayList<String>>();
        //HashMap<Integer, List<String>> func_map = new HashMap<Integer, List<String>>();


        // read file and store strings into array 
        try {                                      
        File file = new File("test2.txt");
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

        // PART 2. SYNTAX ALAYZER
        // if 1 run, else halt program

        // DECLARATIONS
        int run = run_syntax_analyzer(pairs);
        int tokens_syntax_size = tokens_syntax.size()-2;  // -2 since last data is KTHNXBYE, ~newline~  

        //System.out.println();

        if(run == 1){
            if(tokens_syntax.get(0).equals("HAI") || tokens_syntax.get(tokens_syntax_size).equals("KTHXBYE")){
                    // BUILD FINAL SYNTAX TOKENS
                    final_syntax_tokens = get_final_syntax_tokens(tokens_syntax, final_syntax_tokens, tokens_syntax_size);

                    int op_counter = 0;
                    for(int i = 0; i < final_syntax_tokens.size(); i += 1){
                        if(final_syntax_tokens.get(i).equals("~newline~")){
                            op_counter += 1;
                        }else{
                            if(func_map.get(op_counter) == null){
                                func_map.put(op_counter, new ArrayList<>(Arrays.asList(final_syntax_tokens.get(i))));
                            }else{
                                func_map.get(op_counter).add(final_syntax_tokens.get(i));
                            }
                            
                        }  
                    }
                    System.out.println(func_map);


                    // build statements array by new line
                    statements_array = build_statements_array(statements_array, final_syntax_tokens);
                    //remove spaces sa harap ng items sa statement array
                    statements_array = clean_statements_array(statements_array);
                    //System.out.println(statements_array);

                    // Checks if syntax is correct
                    correct_syntax = check_correct_syntax(statements_array, grammar, correct_syntax);
            }
        }else{
            System.out.println("Syntax Error");
        }
  }
}
