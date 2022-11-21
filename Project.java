import javax.swing.JFrame;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import java.util.HashSet;

import java.awt.*;

public class Project extends javax.swing.JFrame {

    public Project() {
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

    }
                    
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        codeArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        variableTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        lexemeTable = new javax.swing.JTable();
        executeButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        consoleArea = new javax.swing.JTextArea();
        uploadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        codeArea.setColumns(20);
        codeArea.setRows(5);
        jScrollPane1.setViewportView(codeArea);

        variableTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Value", "Type"
            }
        ));
        jScrollPane2.setViewportView(variableTable);
        if (variableTable.getColumnModel().getColumnCount() > 0) {
            variableTable.getColumnModel().getColumn(0).setResizable(false);
            variableTable.getColumnModel().getColumn(1).setResizable(false);
            variableTable.getColumnModel().getColumn(2).setResizable(false);
        }

        lexemeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Lexeme", "Type", "Description", "Line of Code?"
            }
        ));
        jScrollPane3.setViewportView(lexemeTable);
        if (lexemeTable.getColumnModel().getColumnCount() > 0) {
            lexemeTable.getColumnModel().getColumn(0).setResizable(false);
            lexemeTable.getColumnModel().getColumn(1).setResizable(false);
            lexemeTable.getColumnModel().getColumn(2).setResizable(false);
        }

        executeButton.setText("Execute");

        clearButton.setText("Clear Output");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        consoleArea.setEditable(false);
        consoleArea.setColumns(20);
        consoleArea.setRows(5);
        consoleArea.setFocusable(false);
        jScrollPane4.setViewportView(consoleArea);

        uploadButton.setText("Click here to upload your LOLcode file");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(executeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(uploadButton, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(clearButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uploadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(executeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        pack();
    }// </editor-fold>                        

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        
    }                                           

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });

        ArrayList<String> array = new ArrayList<>();  //strings from the input lolcode are put here, initial array
    
    try {                                       //read file and store strings into array
      File file = new File("test.txt");
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

    HashMap<String, String> map = new HashMap<String, String>();    //tokens that are available from the lolcode specification
    HashMap<String, String> pairs = new HashMap<String, String>();  //tokens from the input, this will be the final hashmap which will contain all the lexeme-identifier
                                                                    //pairs from the sample input code
    for(int i = 0; i < array.size(); i++) {     //remove tabs in front of strings
        String s = array.get(i);    
        s = s.trim();
        array.set(i,s);
    }
    
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

    map.put("^-?\\d+$", "NUMBR Literal");
    map.put("^([+-]?\\d*\\.?\\d*)$", "NUMBAR Literal");
    map.put("^\".*\"$", "YARN Literal");
    map.put("^WIN$|^FAIL$", "TROOF Literal");

    // map.put("^[A-Za-z]+[A-Za-z0-9_]*$", "Variable");
    map.put("^HOW IZ I$", "Function Identifier");
    map.put("^IF U SAY SO$", "Function Identifier");
    map.put("^IM OUTTA YR$", "Loop Identifier");
    map.put("^IM IN YR$", "Loop Identifier");
    
    ArrayList<String> tokens = new ArrayList<String>();     // array list to save our tokens
    ArrayList<String> final_tokens = new ArrayList<String>();

    // int start_arr = array.size();
    for(int n = 0; n < array.size(); n++){
        // String token_line = "SUM OF DIFF OF PRODUKT OF QUOSHUNT OF MOD OF BIGGR OF SMALLR OF BOTH OF EITHER OF WON OF ANY OF ALL OF";              // sample token line
        String token_line = array.get(n);
        int token_line_length = token_line.length();                // get length of each token line
        char[] token_line_arr = token_line.toCharArray();           // converts the String to array of characters
     
        int string_counter = 0;                                 // for special cases such as strings
    
        // variables to be used for the loop
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
                tokens.add(current_token);
                current_token = "";
            }
        }
    
        // check for I HAS A Identifiers
        String build_token = "";
        for(int t = 0; t < tokens.size(); t++){
            build_token = "";
            if(tokens.get(t).equals("I")){
                build_token = tokens.get(t) + " " + tokens.get(t+1) + " " + tokens.get(t+2);
                if(build_token.equals("I HAS A")){
                    final_tokens.add(build_token);
                    t = t + 2;  // update loop
                }
            // operations
            }else if(tokens.get(t).equals("SUM") || tokens.get(t).equals("DIFF") || tokens.get(t).equals("PRODUKT") || tokens.get(t).equals("QUOSHUNT") || tokens.get(t).equals("MOD") || tokens.get(t).equals("BIGGR") || tokens.get(t).equals("SMALLR") || tokens.get(t).equals("BOTH") || tokens.get(t).equals("EITHER") || tokens.get(t).equals("WON") || tokens.get(t).equals("ANY") || tokens.get(t).equals("ALL")){
                build_token = tokens.get(t) + " " + tokens.get(t+1);
                if(build_token.equals("SUM OF") || build_token.equals("DIFF OF") || build_token.equals("PRODUKT OF") || build_token.equals("QUOSHUNT OF") || build_token.equals("MOD OF") || build_token.equals("BIGGR OF") || build_token.equals("SMALLR OF") || build_token.equals("BOTH OF") || build_token.equals("EITHER OF") || build_token.equals("WON OF") || build_token.equals("ANY OF") || build_token.equals("ALL OF")){
                    final_tokens.add(build_token);
                    t = t + 1;  // update loop
                }
            }else{
                final_tokens.add(tokens.get(t));
            }
        }
    }


    //checking for the type of each string in the array
    for(int i = array.size(); i < final_tokens.size(); i++){ 
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

    //check for values that were not removed in in the checking above if they are already in the 'pairs' hashmap, duplicate checking
    for (HashMap.Entry<String, String> set:pairs.entrySet()){
        for(int i = 0; i < final_tokens.size(); i++){
            if(set.getKey().equals(final_tokens.get(i))){
                final_tokens.remove(i); //remove element from the finals_token array for easier duplicate checking
                i--; //decrement counter since an element was deleted
            } 
        }
    }

    //for invalid tokens, since they do not match any lexeme-identifier pair in the 'map' hashmap
    for(int i = 0; i < final_tokens.size(); i++){
        pairs.put(final_tokens.get(i), "Invalid Token");
    }


    //print the lexeme-identifier pairs that are in the lolcode input
    for (HashMap.Entry<String, String> set:pairs.entrySet()){
        System.out.println(set.getKey() + " = " + set.getValue());  //printing of the values from the 'pairs' hashmap
    }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton clearButton;
    private javax.swing.JTextArea codeArea;
    private javax.swing.JTextArea consoleArea;
    private javax.swing.JButton executeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable lexemeTable;
    private javax.swing.JButton uploadButton;
    private javax.swing.JTable variableTable;
    // End of variables declaration                   
}
