package hust.cc.asynchronousacousticlocalization.utils;

public interface FlagVar {

    //sampling rate
    public int Fs = 48000;

    // buffer length to store the samples for the audiotrack (for play thread)
    public int bufferSize = 20480;
    // buffer length to process the samples for the audiorecorder (for the decoder thread)

    /*********** constant parameters ***************************/
    // the constant parameters for the signal representation
    public static final int UP_PREAMBLE = 1;
    public static final int DOWN_PREAMBLE = 2;
    public static final int UP_SYMBOL = 3;
    public static final int DOWN_SYMBOL = 4;

    // message type constant
    public static final int MESSAGE_TDOA = 50;
    //public static final int

    /*****************************************************/

    /*****************************************************/
    // parameters for the preamble
    public float TPreamble = 0.04f;
    public int BPreamble = 4000;
    public int Fmin = 17500;
    public int Fmax = 21500;
    public int preambleLength = (int)(Fs * TPreamble);

    // parameter for the symbols
    public float TSymbol = 0.03f;
    public int BSymbol = 1000;
    public int [] FUpSymbol = new int[]{17500, 18500, 19500, 20500};
    public int [] FDownSymbol = new int[]{21500, 20500, 19500, 18500};
    public final int symbolLength = (int)(Fs * TSymbol);
    public int numberOfSymbols = 4;

    // guard interval
    public float guardInterval = 0.005f;
    public int guardIntervalLength = (int)(guardInterval * Fs);

    /*****************************************************/

    /***********************threshold parameters*********************/
    public float preambleDetectionThreshold= 0.03f;
    public int numberOfPreviousSamples = 100;
    public float ratioThreshold = 5;
    /*****************************************************/

    //becon message
    float beconMessageTime = TPreamble+guardInterval+TSymbol;
    int beconMessageLength = (int)(beconMessageTime*Fs);


}
