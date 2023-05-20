public class TranslateVisitor extends ExprBaseVisitor<String> {


    @Override
    public String visitSource(ExprParser.SourceContext ctx) {
        StringBuilder sb = new StringBuilder();


        if (ctx.statement() != null) {
            for (ExprParser.StatementContext statement : ctx.statement()) {
                sb.append(visitStatement(statement));

            }
        }


        return sb.toString();
    }


    @Override
    public String visitStatement(ExprParser.StatementContext ctx) {

        if (ctx.assignment() != null) {
            return visitAssignment(ctx.assignment());
        } else if (ctx.ifStatement() != null) {
            return visitIfStatement(ctx.ifStatement());
        } else if (ctx.whileStatement() != null) {
            return visitWhileStatement(ctx.whileStatement());
        } else if (ctx.arithmeticStatement() != null) {
            return visitArithmeticStatement(ctx.arithmeticStatement());
        } else if (ctx.builtInFunctions() != null) {
            return visitSayCall((ExprParser.SayCallContext) ctx.builtInFunctions());

        } else {
            throw new RuntimeException("Unknown statement type: " + ctx.getText());
        }

    }


    @Override
    public String visitSayCall(ExprParser.SayCallContext ctx) {
        String expression = visit(ctx.expression());
        return "print(" + expression + ")" + "\n";
    }


    @Override
    public String visitArithmeticStatement(ExprParser.ArithmeticStatementContext ctx) {
        StringBuilder sb = new StringBuilder();
        String expression = visit(ctx.expression());
        sb.append("print(").append(expression).append(")");
        sb.append("\n");
        return sb.toString();

    }

    @Override
    public String visitProgram(ExprParser.ProgramContext ctx) {
        return visit(ctx.source());
    }

    @Override
    public String visitAssignment(ExprParser.AssignmentContext ctx) {
        StringBuilder sb = new StringBuilder();
        String identifier = ctx.Identifier().getText();
        String expression = visit(ctx.expression());
        sb.append(identifier).append(" = ").append(expression);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String visitIfStatement(ExprParser.IfStatementContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append(visitIfStat(ctx.ifStat()));

        for (int i = 0; i < ctx.elseIfStat().size(); i++) {
            sb.append(visitElseIfStat(ctx.elseIfStat(i)));
        }

        if (ctx.elseStat() != null) {
            sb.append(visitElseStat(ctx.elseStat()));
        }
        return sb.toString();
    }

    @Override
    public String visitWhileStatement(ExprParser.WhileStatementContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("while ").append(visit(ctx.expression())).append(":");
        sb.append("\n");


        return getString(sb, ctx.source());
    }

    private String getString(StringBuilder sb, ExprParser.SourceContext source) {


        if (source.statement() != null) {
            for (ExprParser.StatementContext statement : source.statement()) {

                sb.append("\t");
                sb.append(visitStatement(statement));
            }
        }

        return sb.toString();
    }


    @Override
    public String visitIfStat(ExprParser.IfStatContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("if ").append(visit(ctx.expression())).append(":");
        sb.append("\n");
        sb.append("\t");

        return getString(sb, ctx.source()) + "\t";

    }


    @Override
    public String visitElseStat(ExprParser.ElseStatContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("else:");
        sb.append("\n");
        sb.append("\t");
        return getString(sb, ctx.source());

    }


    @Override
    public String visitElseIfStat(ExprParser.ElseIfStatContext ctx) {
        StringBuilder sb = new StringBuilder();

        sb.append("elif ").append(visit(ctx.expression())).append(":");
        sb.append("\n");
        sb.append("\t");
        return getString(sb, ctx.source());
    }

    @Override
    public String visitNumber(ExprParser.NumberContext ctx) {

        return ctx.getText();
    }


    @Override
    public String visitIdentifier(ExprParser.IdentifierContext ctx) {
        return ctx.getText();
    }


    @Override
    public String visitCompare(ExprParser.CompareContext ctx) {

        if (ctx.op.getType() == ExprParser.GT) {
            return visit(ctx.expression(0)) + " > " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.LT) {
            return visit(ctx.expression(0)) + " < " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.GE) {
            return visit(ctx.expression(0)) + " >= " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.LE) {
            return visit(ctx.expression(0)) + " <= " + visit(ctx.expression(1));
        } else {
            throw new RuntimeException("Unknown comparison operator: " + ctx.op.getText());
        }
    }


    @Override
    public String visitMultdiv(ExprParser.MultdivContext ctx) {
        if (ctx.op.getType() == ExprParser.TIMES) {
            return visit(ctx.expression(0)) + " * " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.DIV) {
            return visit(ctx.expression(0)) + " / " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.MOD) {

            return visit(ctx.expression(0)) + " % " + visit(ctx.expression(1));
        } else {
            throw new RuntimeException("Unknown multiplication operator: " + ctx.op.getText());
        }
    }


    @Override
    public String visitAddsub(ExprParser.AddsubContext ctx) {

        if (ctx.op.getType() == ExprParser.PLUS) {
            return visit(ctx.expression(0)) + " + " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.MINUS) {
            return visit(ctx.expression(0)) + " - " + visit(ctx.expression(1));
        } else {
            throw new RuntimeException("Unknown addition operator: " + ctx.op.getText());
        }
    }

    @Override
    public String visitParenthesis(ExprParser.ParenthesisContext ctx) {

        return "(" + visit(ctx.expression()) + ")";
    }

    @Override
    public String visitEqnotequal(ExprParser.EqnotequalContext ctx) {

        if (ctx.op.getType() == ExprParser.EQ) {
            return visit(ctx.expression(0)) + " == " + visit(ctx.expression(1));
        } else if (ctx.op.getType() == ExprParser.NE) {
            return visit(ctx.expression(0)) + " != " + visit(ctx.expression(1));
        } else {
            throw new RuntimeException("Unknown equality operator: " + ctx.op.getText());
        }

    }

    @Override
    public String visitString(ExprParser.StringContext ctx) {

        return ctx.getText();
    }

}