package frontend.lexer;

import java.util.ArrayList;

public class TokenList {
    private final ArrayList<Token> tokenList = new ArrayList<>();
    public void append(Token token) {
        tokenList.add(token);
    }

    @Override
    public String toString() {
        StringBuilder tokenListStr = new StringBuilder();
        for (Token token:
             tokenList) {
            tokenListStr.append(token.toString()).append("\n");
        }
        return tokenListStr.toString();
    }
}
