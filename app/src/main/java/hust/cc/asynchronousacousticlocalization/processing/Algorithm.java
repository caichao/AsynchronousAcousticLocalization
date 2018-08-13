package hust.cc.asynchronousacousticlocalization.processing;

import android.util.Log;

/**
 * Created by cc on 2017/11/29.
 */

public class Algorithm {

    private static final String TAG = "Algorihtm";

    /**
     *
     * @param numbers
     * @param low
     * @param high
     * @return
     */
    public  static int getMiddle(int[] numbers, int low,int high)
    {
        int temp = numbers[low];
        while(low < high)
        {
            while(low < high && numbers[high] >= temp)
            {
                high--;
            }
            numbers[low] = numbers[high];
            while(low < high && numbers[low] < temp)
            {
                low++;
            }
            numbers[high] = numbers[low] ;
        }
        numbers[low] = temp ;
        return low ;
    }

    /**
     * quick sort with designated upper and lower bound of an array
     * @param numbers: vectors
     * @param low: low index of vecotors
     * @param high: high index of vectors
     */
    public  static void quickSort(int[] numbers,int low,int high)
    {
        if(low < high)
        {
            int middle = getMiddle(numbers,low,high);
            quickSort(numbers, low, middle-1);
            quickSort(numbers, middle+1, high);
        }
    }

    public static CriticalPoint getCriticalPoint(double[] s, int low, int high){
        CriticalPoint criticalPoint = new CriticalPoint();
        for(int i = low; i < high; i++){
            if(s[i] > criticalPoint.peak){
                criticalPoint.peak = s[i];
                criticalPoint.index = i;
            }
        }
        return criticalPoint;
    }

    public static CriticalPoint getCriticalPoint(float[] s, int low, int high){
        CriticalPoint criticalPoint = new CriticalPoint();
        for(int i = low; i < high; i++){
            if(s[i] > criticalPoint.peak){
                criticalPoint.peak = s[i];
                criticalPoint.index = i;
            }
        }
        return criticalPoint;
    }

    public static IndexMaxVarInfo peakRefinement(float[] peaks, int index){
        System.out.print("peaks length = " + peaks.length);
        System.out.print("index = " + index);
        int shift = 0;
        int length = 500;
        int low = index - length;
        if(index < 0){
            index = 0;
            shift = 0;
        return null;
    }
        System.out.print("low = " + low);
    float[] normalized = new float[length - 200];
        for(int j = 200; j < length; j++){
        normalized[j - 200] = peaks[j] / meanValue(peaks, low + j - 200, low + j);
    }
    IndexMaxVarInfo  indexMaxVarInfo = getMaxInfo(normalized, 0, length - 200);
    shift = length - indexMaxVarInfo.index;

        System.out.print("shift = " + shift);
        return indexMaxVarInfo;
    }

    public static void getMagnitude(double[] magnitude, double[] complex){
        int length = magnitude.length;
        for(int i = 0; i < length; i++){
            magnitude[i] = Math.sqrt(complex[2*i]*complex[2*i] + complex[2*i+1]*complex[2*i+1]);
        }
    }

    /**
     * get both the max vlaue and its corresponding index
     * @param s - input array in float format
     * @param low - low index of the array that to be searched
     * @param high - high index of the array that to be searched
     * @return class IndexMaxVarInfo that contains both the max value and its index in the array
     */
    public static IndexMaxVarInfo getMaxInfo(float s[], int low, int high){
//        Log.e(TAG, "data length = " + s.length);
//        Log.e(TAG, "low index = " + low);
//        Log.e(TAG, "high index +" + high);
        outOfRangeDetection(s.length,low,high);
        IndexMaxVarInfo indexMaxVarInfo = new IndexMaxVarInfo();
        indexMaxVarInfo.index = low;
        indexMaxVarInfo.fitVal = s[low];
        for(int i = low; i < high; i++){
            if(s[i] > indexMaxVarInfo.fitVal){
                indexMaxVarInfo.fitVal = s[i];
                indexMaxVarInfo.index = i;
            }
        }
        return indexMaxVarInfo;
    }

    /**
     * to get the max value of the short array
     * @param s - samples in short format
     * @param low - low index
     * @param high - high index
     * @return both the max value and its corresponding index
     */
    public static IndexMaxVarInfo getMaxInfo(short s[], int low, int high){
        outOfRangeDetection(s.length,low,high);
        IndexMaxVarInfo indexMaxVarInfo = new IndexMaxVarInfo();
        indexMaxVarInfo.index = low;
        indexMaxVarInfo.fitVal = s[low];
        for(int i = low; i <= high; i++){
            if(s[i] > indexMaxVarInfo.fitVal){
                indexMaxVarInfo.fitVal = s[i];
                indexMaxVarInfo.index = i;
            }
        }
        return indexMaxVarInfo;
    }

    public static float meanValue(float[] s, int low, int high){
        float sum = 0;
        for(int i = low ; i <= high ; i++){
            sum += s[(i+s.length)%s.length];
        }
        sum /= (high - low + 1);
        return sum;
    }

    public static double meanValue(double[] s, int low, int high){
        double sum = 0;
        for(int i = low ; i <= high ; i++){
            sum += s[i];
        }
        sum /= (high - low + 1);
        return sum;
    }

    public static short meanValue(short[] s, int low, int high){
        outOfRangeDetection(s.length,low,high);
        long sum = 0;
        for(int i = low ; i < high ; i++){
            sum += s[i];
        }
        sum /= (high - low + 1);
        return (short) sum;
    }

    public static double getMax(double[] s, int low, int high){
        double max = Double.MIN_VALUE;
        for(int i = low; i < high; i++){
            if(max < s[i]){
                max = s[i];
            }
        }
        return max;
    }

    public static float getMax(float[] s, int low, int high){
        float max = Float.MIN_VALUE;
        for(int i = low; i < high; i++){
            if(max < s[i]){
                max = s[i];
            }
        }
        return max;
    }

    public static double getMax(short[] s, int low, int high){
        double max = Double.MIN_VALUE;
        for(int i = low; i < high; i++){
            if(max < s[i]){
                max = s[i];
            }
        }
        return max;
    }


    public static void outOfRangeDetection(int len, int low, int high){
        if(low <= high && low >= 0 && high <= len){
            return;
        }else{
            System.out.println("len:"+len+"  low:"+low+"  high:"+high);
            throw new RuntimeException("out of range.");
        }

    }
}
