package code_clone;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CosineSimilarity {

    public static List<double[]> similarArray = new ArrayList<>();

    // Refactored method to get cosine similarities for all project files
    public void calculateSimilarities(List<String> projectFileNames1, List<String> projectFileNames2, List<double[]> tfidfVectors1, List<double[]> tfidfVectors2) {
        for (int i = 0; i < projectFileNames1.size(); i++) {
            double[] similarities = calculateProjectSimilarities(i, projectFileNames1, projectFileNames2, tfidfVectors1, tfidfVectors2);
            similarArray.add(similarities);  // Store results
        }
    }

    // Method to calculate similarity between one project file and all others
    private double[] calculateProjectSimilarities(int index, List<String> projectFileNames1, List<String> projectFileNames2, List<double[]> tfidfVectors1, List<double[]> tfidfVectors2) {
        double[] similarities = new double[projectFileNames2.size()];
        for (int j = 0; j < projectFileNames2.size(); j++) {
            double similarity = cosineSimilarity(tfidfVectors1.get(index), tfidfVectors2.get(j));
            similarities[j] = similarity;

            // Print or log similarity
            printSimilarity(projectFileNames1.get(index), projectFileNames2.get(j), similarity);
        }
        return similarities;
    }

    // Method to calculate cosine similarity between two vectors
    public double cosineSimilarity(double[] vector1, double[] vector2) {
        double dotProduct = 0;
        double vector1Magnitude = 0;
        double vector2Magnitude = 0;

        for (int i = 0; i < vector2.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            vector1Magnitude += Math.pow(vector1[i], 2);
            vector2Magnitude += Math.pow(vector2[i], 2);
        }

        vector1Magnitude = Math.sqrt(vector1Magnitude);
        vector2Magnitude = Math.sqrt(vector2Magnitude);

        // Calculate cosine similarity
        double cosineSimilarity = dotProduct / (vector1Magnitude * vector2Magnitude) * 100;
        return Double.isNaN(cosineSimilarity) ? 0.0 : cosineSimilarity;
    }

    // Method to print or log the similarity result
    private void printSimilarity(String projectFile1, String projectFile2, double similarity) {
        double roundedSimilarity = roundToTwoDecimalPlaces(similarity);
        System.out.println(projectFile1 + " vs " + projectFile2 + " " + roundedSimilarity + "%");
    }

    // Method to round a value to two decimal places
    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}



