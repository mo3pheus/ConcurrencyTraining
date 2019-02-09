package domain;

public class DecodedResult implements Comparable<DecodedResult> {
    private double wordPercentage;
    private int    asciiOffset;
    private String originalMessage;
    private String decodedMessage;

    public double getWordPercentage() {
        return wordPercentage;
    }

    public void setWordPercentage(double wordPercentage) {
        this.wordPercentage = wordPercentage;
    }

    public int getAsciiOffset() {
        return asciiOffset;
    }

    public void setAsciiOffset(int asciiOffset) {
        this.asciiOffset = asciiOffset;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getDecodedMessage() {
        return decodedMessage;
    }

    public void setDecodedMessage(String decodedMessage) {
        this.decodedMessage = decodedMessage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("valid word percentage = " + wordPercentage);
        stringBuilder.append("ascii offset = " + asciiOffset);
        stringBuilder.append("originalMessage = " + originalMessage);
        stringBuilder.append("decodedMessage = " + decodedMessage);
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(DecodedResult o) {
        return (wordPercentage > o.getWordPercentage()) ? 1 : 0;
    }
}
