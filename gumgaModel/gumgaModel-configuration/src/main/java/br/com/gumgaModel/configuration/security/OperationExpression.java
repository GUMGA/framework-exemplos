package br.com.gumgaModel.configuration.security;

public class OperationExpression {

    public String url, method, operation;

    public OperationExpression(String operation, String url, String method) {
        this.url = url;
        this.method = method;
        this.operation = operation;
    }
}
