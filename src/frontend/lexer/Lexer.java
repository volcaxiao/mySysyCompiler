package frontend.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;

public class Lexer {
    private static final Lexer lexer = new Lexer();
    private Lexer() {}
    private static BufferedReader textReader;
    private int curChar;
    private final TokenList tokenList = new TokenList();

    public static Lexer getLexer(BufferedReader reader) {
        textReader = reader;
        return lexer;
    }
    
    private int getNextChar() {
        int curChar = 0;
        try {
            curChar = textReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return curChar;
    }

    private boolean isLetter(int c) {
        return String.valueOf((char) c).matches("[A-Za-z_]");
    }
    private boolean isDigital(int c) {
        return String.valueOf((char) c).matches("[0-9]");
    }

    //需要生成tokenList，注意屏蔽注释和space及\n
    public TokenList lex() {
        curChar = getNextChar();
        while (curChar != -1) {
            if (skipCommit()) {
                continue;
            } else if (skipSpaceLine()) {
                continue;
            }else {
                detect();
            }
        }
        return tokenList;
    }

    // 跳过注释，require退出时，curChar指在注释外的行，当然附加的是解析完了/除号
    private boolean skipCommit() {
        if (curChar == '/') {
            curChar = getNextChar();
            if (curChar == '/') {
                curChar = getNextChar();
                while (curChar != -1 && curChar != '\n') {
                    curChar = getNextChar();
                }
            } else if (curChar == '*') {
                curChar = getNextChar();
                while (curChar != -1) {
                    if (curChar == '*') {
                        curChar = getNextChar();
                        if (curChar == '/') {
                            curChar = getNextChar();
                            break;
                        }
                    }
                    curChar = getNextChar();
                }
            } else {
                tokenList.append(new Token(TokenType.DIV, "/"));
            }
            return true;
        }
        return false;
    }

    private boolean skipSpaceLine() {
        boolean flag = false;
        while (curChar != -1 && (curChar == ' ' || curChar == '\n' || curChar == '\r' || curChar == 9)) {
            curChar = getNextChar();
            flag = true;
        }
        return flag;
    }

    private void detect() {
        if (curChar == -1) {
            return;
        }
        if (detectKeyword()) {
            return;
        } else if (detectDigital()) {
            return;
        } else if (detectString()) {
            return;
        } else detectSymbol();
    }

    private boolean detectKeyword() {
        if (isLetter(curChar)) {
            StringBuilder keyword = new StringBuilder();
            keyword.append((char)curChar);
            curChar = getNextChar();
            while (curChar != -1 && (isDigital(curChar) || isLetter(curChar))) {
                keyword.append((char)curChar);
                curChar = getNextChar();
            }
            matchToken(keyword.toString());
            return true;
        }
        return false;
    }

    private boolean detectDigital() {
        if (isDigital(curChar)) {
            StringBuilder digit = new StringBuilder();
            digit.append((char)curChar);
            curChar = getNextChar();
            while(curChar != -1 && isDigital(curChar)) {
                digit.append((char)curChar);
                curChar = getNextChar();
            }
            matchToken(digit.toString());
            return true;
        }
        return false;
    }

    private boolean detectString() {
        if (curChar == '\"') {
            StringBuilder string = new StringBuilder();
            string.append("\"");
            curChar = getNextChar();
            //因为好像转义字符只有\n，所以放心大胆的收到下一个“
            while (curChar != '\"') {
                string.append((char)curChar);
                curChar = getNextChar();
            }
            string.append("\"");
            curChar = getNextChar();
            tokenList.append(new Token(TokenType.STRCON, string.toString()));
            return true;
        }
        return false;
    }

    private void detectSymbol() {
        //分为一个字符的和两个字符的
        if (curChar == '=' || curChar == '!' || curChar == '>' || curChar == '<') {
            //后面可以接=的
            String symbol = String.valueOf((char)curChar);
            curChar = getNextChar();
            if (curChar == '=') {
                symbol += "=";
                matchToken(symbol);
                curChar = getNextChar();
            } else {
                matchToken(symbol);
            }
        } else if (curChar == '&' | curChar == '|') {
            // 两个重复了的
            String symbol = String.valueOf((char)curChar);
            curChar = getNextChar();
            if (symbol.equals(String.valueOf((char)curChar))) {
                symbol += (char) curChar;
                matchToken(symbol);
                curChar = getNextChar();
            } else {
                matchToken(symbol);
            }
        } else {
            //单个的
            matchToken(String.valueOf((char)curChar));
            curChar = getNextChar();
        }
    }

    private TokenType matchToken (String token) {
        for (TokenType tokenType:
                TokenType.values()) {
            Matcher matcher = tokenType.getPattern().matcher(token);
            if (matcher.matches()) {
                tokenList.append(new Token(tokenType, token));
                return tokenType;
            }
        }
        // TODO 未匹配异常
        System.err.println("String " + token + " not recognized");
        return TokenType.ERROR;
    }
}
