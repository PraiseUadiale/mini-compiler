grammar Expr;

program : source EOF ;

source
 : ( statement | function  )*  ;

statement
 : assignment ';'
 | ifStatement
 | whileStatement
 | arithmeticStatement';'
 | builtInFunctions ';'
  ;

builtInFunctions:
            Say '(' expression ')' #sayCall
            ;
assignment
 : Identifier '=' expression
 ;


ifStatement
 : ifStat elseIfStat* elseStat?
 ;

ifStat
 : If '('expression')' OCURLY source CCURLY
 ;

elseIfStat
 : Else If '('expression')'OCURLY source CCURLY
 ;

elseStat
 : Else OCURLY source CCURLY
 ;

whileStatement
 : While '('expression')' OCURLY source CCURLY
 ;

arithmeticStatement
 :Do expression
 ;

function :
    Func Identifier '(' functionParams? ')' OCURLY source CCURLY
    ;


functionParams
    :Identifier (',' Identifier)*
    ;

expression
 :  expression op=( TIMES | DIV | MOD) expression         #multdiv
 | expression op=(  PLUS | MINUS) expression         #addsub
 | expression op=( LT | GT | LE | GE) expression     #compare
 | expression op=( EQ | NE ) expression              #eqnotequal
 | Number                                            #number
 | Identifier                               #identifier
 | '(' expression ')'                         #parenthesis
 | String                                     #string
 ;


OCURLY: '{' ;
CCURLY: '}' ;
PLUS: '+' ;
MINUS: '-' ;
TIMES: '*' ;
DIV: '/' ;
ASSIGN: '=' ;
End: 'end' ;
Else: 'else' ;
If : 'if' ;
While : 'while' ;
GT : '>' ;
LT : '<' ;
GE : '>=' ;
LE : '<=' ;
EQ : '==' ;
NE : '!=' ;
Say : 'say' ;
Do : 'do' ;
MOD : '%' ;
Func: 'func' ;

Number
 : '-'? (Int | Float)
 ;

Identifier
 : [a-zA-Z]+
 ;

String
 : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["]
 | ['] ( ~['\r\n\\] | '\\' ~[\r\n] )* [']
 ;

WHITESPACE : [ \t\r\n]+ -> skip
 ;


fragment Int
 : '0'
 | [1-9] Digit*
 ;

fragment Digit
 : [0-9]
 ;

fragment Float
 : Digit+ '.' Digit*
 | '.' Digit+
 ;
