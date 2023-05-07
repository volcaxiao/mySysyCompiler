package frontend.lexer;

import java.util.regex.Pattern;

public enum TokenType {
    // keyword (must have lookahead assertion)
    MAINTK("main", true),
    CONSTTK("const", true),
    INTTK("int", true),
    //FLOAT("float", true),
    BREAKTK("break", true),
    CONTINUETK("continue", true),
    IFTK("if", true),
    ELSETK("else", true),
    VOIDTK("void", true),
    WHILETK("while", true),
    GETINTTK("getint", true),
    PRINTFTK("printf", true),
    RETURNTK("return", true),
    // ident
    IDENFR("[A-Za-z_][A-Za-z0-9_]*"),
    // float const
//    HEX_FLOAT("(0(x|X)[0-9A-Fa-f]*\\.[0-9A-Fa-f]*((p|P|e|E)(\\+|\\-)?[0-9A-Fa-f]*)?)|" +
//            "(0(x|X)[0-9A-Fa-f]*[\\.]?[0-9A-Fa-f]*(p|P|e|E)((\\+|\\-)?[0-9A-Fa-f]*)?)"),
//    DEC_FLOAT("([0-9]*\\.[0-9]*((p|P|e|E)(\\+|\\-)?[0-9]+)?)|" +
//            "([0-9]*[\\.]?[0-9]*(p|P|e|E)((\\+|\\-)?[0-9]+)?)"), // decimal
    // DEC_FLOAT("(0|([1-9][0-9]*))\\.[0-9]*((p|P|e|E)(\\+|\\-)?[0-9]+)?"), // decimal
    // int const
    HEX_INT("0(x|X)[0-9A-Fa-f]+"),
    OCT_INT("0[0-7]+"),
    INTCON("0|([1-9][0-9]*)"), // decimal
    // operator (double char)
    AND("&&"),
    OR("\\|\\|"),
    LEQ("<="),
    GEQ(">="),
    EQL("=="),
    NEQ("!="),
    // operator (single char)
    LSS("<"),
    GRE(">"),
    PLUS("\\+"), // regex
    MINU("-"),
    MULT("\\*"),
    DIV("/"),
    MOD("%"),
    NOT("!"),
    ASSIGN("="),
    SEMICN(";"),
    COMMA(","),
    LPARENT("\\("),
    RPARENT("\\)"),
    LBRACK("\\["),
    RBRACK("]"),
    LBRACE("\\{"),
    RBRACE("}"),
    //STRCON
    STRCON("\".*\""),
    //nothing!!!
    ERROR("!!"),
    ;
    private final String pattern;
    private final boolean isKeyword;
    TokenType(String pattern, boolean isKeyword) {
        this.pattern = pattern;
        this.isKeyword = isKeyword;
    }
    TokenType(final String pattern) {
        this(pattern, false);
    }

    public Pattern getPattern() {
        return Pattern.compile("^(" + pattern + ")" + (isKeyword ? "(?![A-Za-z0-9_])" : ""));
    }
}
