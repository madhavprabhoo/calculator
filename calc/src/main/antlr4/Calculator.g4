grammar Calculator;

@header {
package calculator;
}

eval : mathExp
     ;

mathExp : 'let'  '(' ID ',' mathExp ',' mathExp ')' # let
        | 'add'  '(' mathExp ',' mathExp ')'        # add
        | 'sub'  '(' mathExp ',' mathExp ')'        # sub
        | 'mult' '(' mathExp ',' mathExp ')'        # mult
        | 'div'  '(' mathExp ',' mathExp ')'        # div
        | NUM                                       # integer
        | ID                                        # id
        ;

ID  :  ('a'..'z' | 'A'..'Z')+ ;              // match variables
NUM :  ('0'..'9')+ ;                         // match numbers
WS  :  (' ' | '\t' | '\r'| '\n') -> skip ;   // ignore whitespace
