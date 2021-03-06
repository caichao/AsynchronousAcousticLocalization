package hust.cc.asynchronousacousticlocalization.utils;

import java.math.BigDecimal;

public interface FlagVar {

    //public int anchorID = 1;

    //sampling rate
    int Fs = 48000;

    // buffer length to store the samples for the audiotrack (for play thread)
    int bufferSize = 20480;
    // buffer length to process the samples for the audiorecorder (for the decoder thread)

    /*********** constant parameters ***************************/
    // the constant parameters for the signal representation
    int UP_PREAMBLE = 1;
    int DOWN_PREAMBLE = 2;
    int UP_SYMBOL = 3;
    int DOWN_SYMBOL = 4;

    // message type constant
    int MESSAGE_TDOA = 50;
    int MESSAGE_RATIO = 51;
    int MESSAGE_DIFF = 52;
    int MESSAGE_JSON = 53;
    int MESSAGE_SPEED = 54;
    //public static final int

    /*****************************************************/

    /*****************************************************/
    // parameters for the preamble
    float TPreamble = 0.04f;
    int BPreamble = 4000;
    int Fmin = 18000;
    int Fmax = 22000;
    int bandPassLow = 16000;
    int bandPassHigh = 23000;

    // parameter for the symbols
    float TSymbol = 0.03f;
    int BSymbol = 1000;
    int [] FUpSymbol = new int[]{18000, 19000, 20000, 21000};
    int [] FDownSymbol = new int[]{22000, 21000, 20000, 19000};
    int numberOfSymbols = 4;

    // guard interval
    float guardInterval = 0.005f;
    int guardIntervalLength = (int)(new BigDecimal(guardInterval * Fs).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue());

    /*****************************************************/

    /***********************threshold parameters*********************/
    float corrThreshold= 0.5f;
    int numberOfPreviousSamples = 100;
//    float ratioThreshold = 15f;
    float maxAvgRatioThreshold = 8f;
    float ratioThreshold = 9;
    float ratioAvailableThreshold = 0.4f;
    /*****************************************************/

    //becon message
    float beconMessageTime = TPreamble+2*guardInterval+2*TSymbol;
    int LPreamble = (int)(new BigDecimal(TPreamble*Fs).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue());
    int LSymbol = (int)(new BigDecimal(TSymbol*Fs).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue());
    int beconMessageLength = (int)(new BigDecimal(beconMessageTime*Fs).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue());
    int endBeforeMaxCorr = 0; // tune from 0 - 200;
    int startBeforeMaxCorr = 200;

//    int [] sinSigF = {17000,17200,17400,17600,17800};
    int [] sinSigF = {17000,17100,17200,17300,17400,17500,17600,17700,17800,17900};
    int speedDetectionSigLength = 65536;
    int speedDetectionRangeF = 40;
    int soundSpeed = 34000;



    /*************************String value for message exchanging*****************************/
    String upStr = "up";
    String downStr = "down";
    String tdoaStr = "tdoa";
    String anchorIdStr = "anchorId";
    String targetIdStr = "targetId";
    String xStr = "x";
    String yStr = "y";
    String identityStr = "identity";



    int MIC_UP = 1;
    int MIC_DOWN = 2;
    int DETECT_TYPE1 = 1;
    int DETECT_TYPE2 = 2;
    int DETECT_TYPE3 = 3;
    int DETECT_TYPE4 = 4;
    int speedOffset = 0;
    int micUsed = MIC_UP;
    int preambleDetectionType = DETECT_TYPE1;
}
