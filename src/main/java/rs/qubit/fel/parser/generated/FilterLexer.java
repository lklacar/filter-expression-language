// Generated from /Users/luka.klacar/Fivetran/filter-expression-language/src/main/antlr4/Filter.g4 by ANTLR 4.13.2
package rs.qubit.fel.parser.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FilterLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LEFT_PAREN=1, RIGHT_PAREN=2, LEFT_BRACKET=3, RIGHT_BRACKET=4, COMMA=5, 
		EQUALS=6, NOTEQUALS=7, GT=8, LT=9, GTE=10, LTE=11, OR=12, AND=13, NOT=14, 
		STRING=15, NULL=16, BOOLEAN=17, IDENTIFIER=18, DATETIME=19, LONG=20, DOUBLE=21, 
		DOT=22, WS=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "COMMA", 
			"EQUALS", "NOTEQUALS", "GT", "LT", "GTE", "LTE", "OR", "AND", "NOT", 
			"STRING", "NULL", "BOOLEAN", "IDENTIFIER", "DATETIME", "LONG", "DOUBLE", 
			"DOT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'['", "']'", "','", "'='", "'!='", "'>'", "'<'", 
			"'>='", "'<='", "'||'", "'&&'", "'!'", null, "'null'", null, null, null, 
			null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "COMMA", 
			"EQUALS", "NOTEQUALS", "GT", "LT", "GTE", "LTE", "OR", "AND", "NOT", 
			"STRING", "NULL", "BOOLEAN", "IDENTIFIER", "DATETIME", "LONG", "DOUBLE", 
			"DOT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
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

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FilterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0017\u00a6\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0005\u000eS"+
		"\b\u000e\n\u000e\f\u000eV\t\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0003\u0010h\b\u0010\u0001\u0011\u0001\u0011\u0005\u0011"+
		"l\b\u0011\n\u0011\f\u0011o\t\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0004\u0012\u0086\b\u0012\u000b\u0012\f\u0012\u0087\u0003\u0012\u008a"+
		"\b\u0012\u0003\u0012\u008c\b\u0012\u0001\u0013\u0004\u0013\u008f\b\u0013"+
		"\u000b\u0013\f\u0013\u0090\u0001\u0014\u0004\u0014\u0094\b\u0014\u000b"+
		"\u0014\f\u0014\u0095\u0001\u0014\u0001\u0014\u0004\u0014\u009a\b\u0014"+
		"\u000b\u0014\f\u0014\u009b\u0001\u0015\u0001\u0015\u0001\u0016\u0004\u0016"+
		"\u00a1\b\u0016\u000b\u0016\f\u0016\u00a2\u0001\u0016\u0001\u0016\u0000"+
		"\u0000\u0017\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016"+
		"-\u0017\u0001\u0000\u0005\u0001\u0000\'\'\u0003\u0000AZ__az\u0004\u0000"+
		"09AZ__az\u0001\u000009\u0003\u0000\t\n\r\r  \u00af\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001"+
		"\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000"+
		"\u0000\u0000-\u0001\u0000\u0000\u0000\u0001/\u0001\u0000\u0000\u0000\u0003"+
		"1\u0001\u0000\u0000\u0000\u00053\u0001\u0000\u0000\u0000\u00075\u0001"+
		"\u0000\u0000\u0000\t7\u0001\u0000\u0000\u0000\u000b9\u0001\u0000\u0000"+
		"\u0000\r;\u0001\u0000\u0000\u0000\u000f>\u0001\u0000\u0000\u0000\u0011"+
		"@\u0001\u0000\u0000\u0000\u0013B\u0001\u0000\u0000\u0000\u0015E\u0001"+
		"\u0000\u0000\u0000\u0017H\u0001\u0000\u0000\u0000\u0019K\u0001\u0000\u0000"+
		"\u0000\u001bN\u0001\u0000\u0000\u0000\u001dP\u0001\u0000\u0000\u0000\u001f"+
		"Y\u0001\u0000\u0000\u0000!g\u0001\u0000\u0000\u0000#i\u0001\u0000\u0000"+
		"\u0000%p\u0001\u0000\u0000\u0000\'\u008e\u0001\u0000\u0000\u0000)\u0093"+
		"\u0001\u0000\u0000\u0000+\u009d\u0001\u0000\u0000\u0000-\u00a0\u0001\u0000"+
		"\u0000\u0000/0\u0005(\u0000\u00000\u0002\u0001\u0000\u0000\u000012\u0005"+
		")\u0000\u00002\u0004\u0001\u0000\u0000\u000034\u0005[\u0000\u00004\u0006"+
		"\u0001\u0000\u0000\u000056\u0005]\u0000\u00006\b\u0001\u0000\u0000\u0000"+
		"78\u0005,\u0000\u00008\n\u0001\u0000\u0000\u00009:\u0005=\u0000\u0000"+
		":\f\u0001\u0000\u0000\u0000;<\u0005!\u0000\u0000<=\u0005=\u0000\u0000"+
		"=\u000e\u0001\u0000\u0000\u0000>?\u0005>\u0000\u0000?\u0010\u0001\u0000"+
		"\u0000\u0000@A\u0005<\u0000\u0000A\u0012\u0001\u0000\u0000\u0000BC\u0005"+
		">\u0000\u0000CD\u0005=\u0000\u0000D\u0014\u0001\u0000\u0000\u0000EF\u0005"+
		"<\u0000\u0000FG\u0005=\u0000\u0000G\u0016\u0001\u0000\u0000\u0000HI\u0005"+
		"|\u0000\u0000IJ\u0005|\u0000\u0000J\u0018\u0001\u0000\u0000\u0000KL\u0005"+
		"&\u0000\u0000LM\u0005&\u0000\u0000M\u001a\u0001\u0000\u0000\u0000NO\u0005"+
		"!\u0000\u0000O\u001c\u0001\u0000\u0000\u0000PT\u0005\'\u0000\u0000QS\b"+
		"\u0000\u0000\u0000RQ\u0001\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000"+
		"TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UW\u0001\u0000\u0000"+
		"\u0000VT\u0001\u0000\u0000\u0000WX\u0005\'\u0000\u0000X\u001e\u0001\u0000"+
		"\u0000\u0000YZ\u0005n\u0000\u0000Z[\u0005u\u0000\u0000[\\\u0005l\u0000"+
		"\u0000\\]\u0005l\u0000\u0000] \u0001\u0000\u0000\u0000^_\u0005t\u0000"+
		"\u0000_`\u0005r\u0000\u0000`a\u0005u\u0000\u0000ah\u0005e\u0000\u0000"+
		"bc\u0005f\u0000\u0000cd\u0005a\u0000\u0000de\u0005l\u0000\u0000ef\u0005"+
		"s\u0000\u0000fh\u0005e\u0000\u0000g^\u0001\u0000\u0000\u0000gb\u0001\u0000"+
		"\u0000\u0000h\"\u0001\u0000\u0000\u0000im\u0007\u0001\u0000\u0000jl\u0007"+
		"\u0002\u0000\u0000kj\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000"+
		"mk\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000n$\u0001\u0000\u0000"+
		"\u0000om\u0001\u0000\u0000\u0000pq\u0007\u0003\u0000\u0000qr\u0007\u0003"+
		"\u0000\u0000rs\u0007\u0003\u0000\u0000st\u0007\u0003\u0000\u0000tu\u0005"+
		"-\u0000\u0000uv\u0007\u0003\u0000\u0000vw\u0007\u0003\u0000\u0000wx\u0005"+
		"-\u0000\u0000xy\u0007\u0003\u0000\u0000y\u008b\u0007\u0003\u0000\u0000"+
		"z{\u0005T\u0000\u0000{|\u0007\u0003\u0000\u0000|}\u0007\u0003\u0000\u0000"+
		"}~\u0005:\u0000\u0000~\u007f\u0007\u0003\u0000\u0000\u007f\u0080\u0007"+
		"\u0003\u0000\u0000\u0080\u0081\u0005:\u0000\u0000\u0081\u0082\u0007\u0003"+
		"\u0000\u0000\u0082\u0089\u0007\u0003\u0000\u0000\u0083\u0085\u0005.\u0000"+
		"\u0000\u0084\u0086\u0007\u0003\u0000\u0000\u0085\u0084\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000"+
		"\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u008a\u0001\u0000\u0000"+
		"\u0000\u0089\u0083\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000"+
		"\u0000\u008a\u008c\u0001\u0000\u0000\u0000\u008bz\u0001\u0000\u0000\u0000"+
		"\u008b\u008c\u0001\u0000\u0000\u0000\u008c&\u0001\u0000\u0000\u0000\u008d"+
		"\u008f\u0007\u0003\u0000\u0000\u008e\u008d\u0001\u0000\u0000\u0000\u008f"+
		"\u0090\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0090"+
		"\u0091\u0001\u0000\u0000\u0000\u0091(\u0001\u0000\u0000\u0000\u0092\u0094"+
		"\u0007\u0003\u0000\u0000\u0093\u0092\u0001\u0000\u0000\u0000\u0094\u0095"+
		"\u0001\u0000\u0000\u0000\u0095\u0093\u0001\u0000\u0000\u0000\u0095\u0096"+
		"\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0099"+
		"\u0005.\u0000\u0000\u0098\u009a\u0007\u0003\u0000\u0000\u0099\u0098\u0001"+
		"\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u0099\u0001"+
		"\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c*\u0001\u0000"+
		"\u0000\u0000\u009d\u009e\u0005.\u0000\u0000\u009e,\u0001\u0000\u0000\u0000"+
		"\u009f\u00a1\u0007\u0004\u0000\u0000\u00a0\u009f\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a5\u0006\u0016\u0000\u0000\u00a5.\u0001\u0000\u0000\u0000\u000b"+
		"\u0000Tgm\u0087\u0089\u008b\u0090\u0095\u009b\u00a2\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}