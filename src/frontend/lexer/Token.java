package frontend.lexer;

public class Token {
    private final TokenType tokenType;
    private final String content;

    public Token(TokenType tokenType, String content) {
        this.tokenType = tokenType;
        this.content = content;
    }

    @Override
    public String toString() {
        return tokenType + " " + content;
    }
}
