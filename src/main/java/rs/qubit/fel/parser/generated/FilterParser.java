// Generated from /home/luka/Projects/Personal/filter-evaluation-engine/src/main/antlr4/Filter.g4 by ANTLR 4.13.1
package rs.qubit.fel.parser.generated;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FilterParser extends Parser {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, LEFT_PAREN = 4, RIGHT_PAREN = 5, LEFT_BRACKET = 6, RIGHT_BRACKET = 7,
            COMMA = 8, EQUALS = 9, NOTEQUALS = 10, GT = 11, LT = 12, GTE = 13, LTE = 14, OR = 15,
            AND = 16, NOT = 17, STRING = 18, NULL = 19, BOOLEAN = 20, IDENTIFIER = 21, LONG = 22,
            DOUBLE = 23, DOT = 24, WS = 25;
    public static final int
            RULE_expression = 0;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\u0004\u0001\u0019L\u0002\u0000\u0007\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u001b\b\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\"\b" +
            "\u0000\n\u0000\f\u0000%\t\u0000\u0003\u0000\'\b\u0000\u0001\u0000\u0003" +
            "\u0000*\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
            "\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000G\b" +
            "\u0000\n\u0000\f\u0000J\t\u0000\u0001\u0000\u0000\u0001\u0000\u0001\u0000" +
            "\u0000\u0000_\u0000)\u0001\u0000\u0000\u0000\u0002\u0003\u0006\u0000\uffff" +
            "\uffff\u0000\u0003\u0004\u0005\u0004\u0000\u0000\u0004\u0005\u0003\u0000" +
            "\u0000\u0000\u0005\u0006\u0005\u0005\u0000\u0000\u0006*\u0001\u0000\u0000" +
            "\u0000\u0007\b\u0005\u0011\u0000\u0000\b*\u0003\u0000\u0000\u0011\t*\u0005" +
            "\u0015\u0000\u0000\n*\u0005\u0012\u0000\u0000\u000b*\u0005\u0016\u0000" +
            "\u0000\f*\u0005\u0017\u0000\u0000\r*\u0005\u0014\u0000\u0000\u000e*\u0005" +
            "\u0013\u0000\u0000\u000f\u0010\u0005\u0016\u0000\u0000\u0010\u0011\u0005" +
            "\u0001\u0000\u0000\u0011\u0012\u0005\u0016\u0000\u0000\u0012\u0013\u0005" +
            "\u0001\u0000\u0000\u0013\u001a\u0005\u0016\u0000\u0000\u0014\u0015\u0005" +
            "\u0002\u0000\u0000\u0015\u0016\u0005\u0016\u0000\u0000\u0016\u0017\u0005" +
            "\u0003\u0000\u0000\u0017\u0018\u0005\u0016\u0000\u0000\u0018\u0019\u0005" +
            "\u0003\u0000\u0000\u0019\u001b\u0005\u0016\u0000\u0000\u001a\u0014\u0001" +
            "\u0000\u0000\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b*\u0001\u0000" +
            "\u0000\u0000\u001c\u001d\u0005\u0015\u0000\u0000\u001d&\u0005\u0004\u0000" +
            "\u0000\u001e#\u0003\u0000\u0000\u0000\u001f \u0005\b\u0000\u0000 \"\u0003" +
            "\u0000\u0000\u0000!\u001f\u0001\u0000\u0000\u0000\"%\u0001\u0000\u0000" +
            "\u0000#!\u0001\u0000\u0000\u0000#$\u0001\u0000\u0000\u0000$\'\u0001\u0000" +
            "\u0000\u0000%#\u0001\u0000\u0000\u0000&\u001e\u0001\u0000\u0000\u0000" +
            "&\'\u0001\u0000\u0000\u0000\'(\u0001\u0000\u0000\u0000(*\u0005\u0005\u0000" +
            "\u0000)\u0002\u0001\u0000\u0000\u0000)\u0007\u0001\u0000\u0000\u0000)" +
            "\t\u0001\u0000\u0000\u0000)\n\u0001\u0000\u0000\u0000)\u000b\u0001\u0000" +
            "\u0000\u0000)\f\u0001\u0000\u0000\u0000)\r\u0001\u0000\u0000\u0000)\u000e" +
            "\u0001\u0000\u0000\u0000)\u000f\u0001\u0000\u0000\u0000)\u001c\u0001\u0000" +
            "\u0000\u0000*H\u0001\u0000\u0000\u0000+,\n\u0010\u0000\u0000,-\u0005\t" +
            "\u0000\u0000-G\u0003\u0000\u0000\u0011./\n\u000f\u0000\u0000/0\u0005\n" +
            "\u0000\u00000G\u0003\u0000\u0000\u001012\n\u000e\u0000\u000023\u0005\u000b" +
            "\u0000\u00003G\u0003\u0000\u0000\u000f45\n\r\u0000\u000056\u0005\f\u0000" +
            "\u00006G\u0003\u0000\u0000\u000e78\n\f\u0000\u000089\u0005\r\u0000\u0000" +
            "9G\u0003\u0000\u0000\r:;\n\u000b\u0000\u0000;<\u0005\u000e\u0000\u0000" +
            "<G\u0003\u0000\u0000\f=>\n\n\u0000\u0000>?\u0005\u0010\u0000\u0000?G\u0003" +
            "\u0000\u0000\u000b@A\n\t\u0000\u0000AB\u0005\u000f\u0000\u0000BG\u0003" +
            "\u0000\u0000\nCD\n\u0012\u0000\u0000DE\u0005\u0018\u0000\u0000EG\u0005" +
            "\u0015\u0000\u0000F+\u0001\u0000\u0000\u0000F.\u0001\u0000\u0000\u0000" +
            "F1\u0001\u0000\u0000\u0000F4\u0001\u0000\u0000\u0000F7\u0001\u0000\u0000" +
            "\u0000F:\u0001\u0000\u0000\u0000F=\u0001\u0000\u0000\u0000F@\u0001\u0000" +
            "\u0000\u0000FC\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HF\u0001" +
            "\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000I\u0001\u0001\u0000\u0000" +
            "\u0000JH\u0001\u0000\u0000\u0000\u0006\u001a#&)FH";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public FilterParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
                "expression"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'-'", "'T'", "':'", "'('", "')'", "'['", "']'", "','", "'='",
                "'!='", "'>'", "'<'", "'>='", "'<='", "'||'", "'&&'", "'!'", null, "'null'",
                null, null, null, null, "'.'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, null, null, null, "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET",
                "RIGHT_BRACKET", "COMMA", "EQUALS", "NOTEQUALS", "GT", "LT", "GTE", "LTE",
                "OR", "AND", "NOT", "STRING", "NULL", "BOOLEAN", "IDENTIFIER", "LONG",
                "DOUBLE", "DOT", "WS"
        };
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "Filter.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final ExpressionContext expression() throws RecognitionException {
        return expression(0);
    }

    private ExpressionContext expression(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
        ExpressionContext _prevctx = _localctx;
        int _startState = 0;
        enterRecursionRule(_localctx, 0, RULE_expression, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(41);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                    case 1: {
                        _localctx = new ParenExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(3);
                        match(LEFT_PAREN);
                        setState(4);
                        expression(0);
                        setState(5);
                        match(RIGHT_PAREN);
                    }
                    break;
                    case 2: {
                        _localctx = new NotExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(7);
                        match(NOT);
                        setState(8);
                        expression(17);
                    }
                    break;
                    case 3: {
                        _localctx = new IdentifierExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(9);
                        match(IDENTIFIER);
                    }
                    break;
                    case 4: {
                        _localctx = new StringExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(10);
                        match(STRING);
                    }
                    break;
                    case 5: {
                        _localctx = new LongExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(11);
                        match(LONG);
                    }
                    break;
                    case 6: {
                        _localctx = new DoubleExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(12);
                        match(DOUBLE);
                    }
                    break;
                    case 7: {
                        _localctx = new BooleanExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(13);
                        match(BOOLEAN);
                    }
                    break;
                    case 8: {
                        _localctx = new NullExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(14);
                        match(NULL);
                    }
                    break;
                    case 9: {
                        _localctx = new DateTimeExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(15);
                        ((DateTimeExpressionContext) _localctx).year = match(LONG);
                        setState(16);
                        match(T__0);
                        setState(17);
                        ((DateTimeExpressionContext) _localctx).month = match(LONG);
                        setState(18);
                        match(T__0);
                        setState(19);
                        ((DateTimeExpressionContext) _localctx).day = match(LONG);
                        setState(26);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                            case 1: {
                                setState(20);
                                match(T__1);
                                setState(21);
                                ((DateTimeExpressionContext) _localctx).hour = match(LONG);
                                setState(22);
                                match(T__2);
                                setState(23);
                                ((DateTimeExpressionContext) _localctx).minute = match(LONG);
                                setState(24);
                                match(T__2);
                                setState(25);
                                ((DateTimeExpressionContext) _localctx).second = match(LONG);
                            }
                            break;
                        }
                    }
                    break;
                    case 10: {
                        _localctx = new FunctionCallExpressionContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(28);
                        ((FunctionCallExpressionContext) _localctx).function = match(IDENTIFIER);
                        setState(29);
                        match(LEFT_PAREN);
                        setState(38);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 16646160L) != 0)) {
                            {
                                setState(30);
                                expression(0);
                                setState(35);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == COMMA) {
                                    {
                                        {
                                            setState(31);
                                            match(COMMA);
                                            setState(32);
                                            expression(0);
                                        }
                                    }
                                    setState(37);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                            }
                        }

                        setState(40);
                        match(RIGHT_PAREN);
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(72);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(70);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
                                case 1: {
                                    _localctx = new EqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((EqualsExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(43);
                                    if (!(precpred(_ctx, 16)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 16)");
                                    setState(44);
                                    match(EQUALS);
                                    setState(45);
                                    ((EqualsExpressionContext) _localctx).right = expression(17);
                                }
                                break;
                                case 2: {
                                    _localctx = new NotEqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((NotEqualsExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(46);
                                    if (!(precpred(_ctx, 15)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 15)");
                                    setState(47);
                                    match(NOTEQUALS);
                                    setState(48);
                                    ((NotEqualsExpressionContext) _localctx).right = expression(16);
                                }
                                break;
                                case 3: {
                                    _localctx = new GreaterThanExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((GreaterThanExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(49);
                                    if (!(precpred(_ctx, 14)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 14)");
                                    setState(50);
                                    match(GT);
                                    setState(51);
                                    ((GreaterThanExpressionContext) _localctx).right = expression(15);
                                }
                                break;
                                case 4: {
                                    _localctx = new LessThanExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((LessThanExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(52);
                                    if (!(precpred(_ctx, 13)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 13)");
                                    setState(53);
                                    match(LT);
                                    setState(54);
                                    ((LessThanExpressionContext) _localctx).right = expression(14);
                                }
                                break;
                                case 5: {
                                    _localctx = new GreaterThanOrEqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((GreaterThanOrEqualsExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(55);
                                    if (!(precpred(_ctx, 12)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 12)");
                                    setState(56);
                                    match(GTE);
                                    setState(57);
                                    ((GreaterThanOrEqualsExpressionContext) _localctx).right = expression(13);
                                }
                                break;
                                case 6: {
                                    _localctx = new LessThanOrEqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((LessThanOrEqualsExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(58);
                                    if (!(precpred(_ctx, 11)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 11)");
                                    setState(59);
                                    match(LTE);
                                    setState(60);
                                    ((LessThanOrEqualsExpressionContext) _localctx).right = expression(12);
                                }
                                break;
                                case 7: {
                                    _localctx = new AndExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((AndExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(61);
                                    if (!(precpred(_ctx, 10)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 10)");
                                    setState(62);
                                    match(AND);
                                    setState(63);
                                    ((AndExpressionContext) _localctx).right = expression(11);
                                }
                                break;
                                case 8: {
                                    _localctx = new OrExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((OrExpressionContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(64);
                                    if (!(precpred(_ctx, 9)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                                    setState(65);
                                    match(OR);
                                    setState(66);
                                    ((OrExpressionContext) _localctx).right = expression(10);
                                }
                                break;
                                case 9: {
                                    _localctx = new DotExpressionContext(new ExpressionContext(_parentctx, _parentState));
                                    ((DotExpressionContext) _localctx).object = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                                    setState(67);
                                    if (!(precpred(_ctx, 18)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 18)");
                                    setState(68);
                                    match(DOT);
                                    setState(69);
                                    ((DotExpressionContext) _localctx).field = match(IDENTIFIER);
                                }
                                break;
                            }
                        }
                    }
                    setState(74);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 0:
                return expression_sempred((ExpressionContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 16);
            case 1:
                return precpred(_ctx, 15);
            case 2:
                return precpred(_ctx, 14);
            case 3:
                return precpred(_ctx, 13);
            case 4:
                return precpred(_ctx, 12);
            case 5:
                return precpred(_ctx, 11);
            case 6:
                return precpred(_ctx, 10);
            case 7:
                return precpred(_ctx, 9);
            case 8:
                return precpred(_ctx, 18);
        }
        return true;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ExpressionContext extends ParserRuleContext {
        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExpressionContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }

        public void copyFrom(ExpressionContext ctx) {
            super.copyFrom(ctx);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DateTimeExpressionContext extends ExpressionContext {
        public Token year;
        public Token month;
        public Token day;
        public Token hour;
        public Token minute;
        public Token second;

        public DateTimeExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public List<TerminalNode> LONG() {
            return getTokens(FilterParser.LONG);
        }

        public TerminalNode LONG(int i) {
            return getToken(FilterParser.LONG, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterDateTimeExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitDateTimeExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitDateTimeExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DotExpressionContext extends ExpressionContext {
        public ExpressionContext object;
        public Token field;

        public DotExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode DOT() {
            return getToken(FilterParser.DOT, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode IDENTIFIER() {
            return getToken(FilterParser.IDENTIFIER, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterDotExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitDotExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitDotExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class IdentifierExpressionContext extends ExpressionContext {
        public IdentifierExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode IDENTIFIER() {
            return getToken(FilterParser.IDENTIFIER, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterIdentifierExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitIdentifierExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitIdentifierExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NotExpressionContext extends ExpressionContext {
        public NotExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode NOT() {
            return getToken(FilterParser.NOT, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterNotExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitNotExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitNotExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ParenExpressionContext extends ExpressionContext {
        public ParenExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode LEFT_PAREN() {
            return getToken(FilterParser.LEFT_PAREN, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode RIGHT_PAREN() {
            return getToken(FilterParser.RIGHT_PAREN, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterParenExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitParenExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitParenExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class GreaterThanExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public GreaterThanExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode GT() {
            return getToken(FilterParser.GT, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterGreaterThanExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitGreaterThanExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitGreaterThanExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LessThanOrEqualsExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public LessThanOrEqualsExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode LTE() {
            return getToken(FilterParser.LTE, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterLessThanOrEqualsExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitLessThanOrEqualsExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitLessThanOrEqualsExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class BooleanExpressionContext extends ExpressionContext {
        public BooleanExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode BOOLEAN() {
            return getToken(FilterParser.BOOLEAN, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterBooleanExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitBooleanExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitBooleanExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class OrExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public OrExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode OR() {
            return getToken(FilterParser.OR, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterOrExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitOrExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor) return ((FilterVisitor<? extends T>) visitor).visitOrExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class GreaterThanOrEqualsExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public GreaterThanOrEqualsExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode GTE() {
            return getToken(FilterParser.GTE, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener)
                ((FilterListener) listener).enterGreaterThanOrEqualsExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitGreaterThanOrEqualsExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitGreaterThanOrEqualsExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NotEqualsExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public NotEqualsExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode NOTEQUALS() {
            return getToken(FilterParser.NOTEQUALS, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterNotEqualsExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitNotEqualsExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitNotEqualsExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class AndExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public AndExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode AND() {
            return getToken(FilterParser.AND, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterAndExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitAndExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitAndExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class StringExpressionContext extends ExpressionContext {
        public StringExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode STRING() {
            return getToken(FilterParser.STRING, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterStringExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitStringExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitStringExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LongExpressionContext extends ExpressionContext {
        public LongExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode LONG() {
            return getToken(FilterParser.LONG, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterLongExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitLongExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitLongExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class EqualsExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public EqualsExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode EQUALS() {
            return getToken(FilterParser.EQUALS, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterEqualsExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitEqualsExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitEqualsExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class DoubleExpressionContext extends ExpressionContext {
        public DoubleExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode DOUBLE() {
            return getToken(FilterParser.DOUBLE, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterDoubleExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitDoubleExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitDoubleExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class NullExpressionContext extends ExpressionContext {
        public NullExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode NULL() {
            return getToken(FilterParser.NULL, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterNullExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitNullExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitNullExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class LessThanExpressionContext extends ExpressionContext {
        public ExpressionContext left;
        public ExpressionContext right;

        public LessThanExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode LT() {
            return getToken(FilterParser.LT, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterLessThanExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitLessThanExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitLessThanExpression(this);
            else return visitor.visitChildren(this);
        }
    }

    @SuppressWarnings("CheckReturnValue")
    public static class FunctionCallExpressionContext extends ExpressionContext {
        public Token function;

        public FunctionCallExpressionContext(ExpressionContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode LEFT_PAREN() {
            return getToken(FilterParser.LEFT_PAREN, 0);
        }

        public TerminalNode RIGHT_PAREN() {
            return getToken(FilterParser.RIGHT_PAREN, 0);
        }

        public TerminalNode IDENTIFIER() {
            return getToken(FilterParser.IDENTIFIER, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(FilterParser.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(FilterParser.COMMA, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).enterFunctionCallExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FilterListener) ((FilterListener) listener).exitFunctionCallExpression(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FilterVisitor)
                return ((FilterVisitor<? extends T>) visitor).visitFunctionCallExpression(this);
            else return visitor.visitChildren(this);
        }
    }
}