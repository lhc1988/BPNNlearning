package lab.cgcl.aliBigdata.BPNNlearning.CF;

/*This function calculate the PEAESON correlation between two vectors via the the equation 
 * Similarity= { [(V1(i)-u1) * (V2(j)-u2)] / sqrt [ (V1(i)-u1)^2 * (V2(j)-u2)^2] } 
 * sqrt stands for square root*/

public class VectorSimilarity {
    public static double Pearson(Integer[] vector1, Integer[] vector2) throws Exception {
        if (vector1.length != vector2.length)
            throw new Exception("vector1.length != vector2.length");    
        double avg1 = CollaborationFilter.getAvg(vector1);
        double avg2 = CollaborationFilter.getAvg(vector2);
        double prod = 0;
        double vec1sq = 0;
        double vec2sq = 0;
        for(int i = 0; i < vector1.length; i++) {
            prod += (vector1[i] - avg1) * (vector2[i] - avg2);
            vec1sq += Math.pow((vector1[i] - avg1), 2);//Bug * Math.pow((vector1[i] - avg1), 2);
            vec2sq += Math.pow((vector2[i] - avg2), 2);//Bug * Math.pow((vector2[i] - avg2), 2);
        }
        return prod / Math.sqrt(vec1sq * vec2sq);
    }
}
