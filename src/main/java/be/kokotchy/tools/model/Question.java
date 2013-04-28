package be.kokotchy.tools.model;

/**
 * Question model
 * User: kokotchY
 * Date: 27/04/13
 * Time: 13:29
 */
public class Question {

    private int id;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String justification;
    private String image;
    private String correct;
    private boolean serious;
    private String prefixImage;

    public Question() {
    }

    /**
     * Return the number of answer (sometimes, the last answer isn't specified
     * @return Number of answer
     */
    public int getNbAnswer() {
        int nb = 0;
        if (answerA != null) {
            nb++;
        }
        if (answerB != null) {
            nb++;
        }
        if (answerC != null) {
            nb++;
        }
        return nb;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public boolean isSerious() {
        return serious;
    }

    public void setSerious(boolean serious) {
        this.serious = serious;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the correct response for the question
     * @return Response to the question
     */
    public String getCorrectAnswer() {
        String answer = null;
        if ("A".equals(correct)) {
            answer = answerA;
        }
        if ("B".equals(correct)) {
            answer = answerB;
        }
        if ("C".equals(correct)) {
            answer = answerC;
        }
        if (answer != null) {
            return correct.toLowerCase() +": "+answer;
        }
        return "Unknown";
    }

    /**
     * Generate an html content of the question separated by ;
     * @return Generated html for the question
     */
    public String generateQuestion() {
        StringBuilder stringBuilder = new StringBuilder();
        if (image != null) {
            stringBuilder.append("<img src=\"").append(prefixImage).append(image).append("\" />");
        } else {
            stringBuilder.append("No img");
        }
        stringBuilder.append("<br />");
        if (serious) {
            stringBuilder.append("<strong>");
        }
        stringBuilder.append(question);
        if (serious) {
            stringBuilder.append("</strong>");
        }
        stringBuilder.append("<br />");
        stringBuilder.append("<ol type=\"a\">");
        addAnswer(stringBuilder, answerA);
        addAnswer(stringBuilder, answerB);
        addAnswer(stringBuilder, answerC);
        stringBuilder.append("</ol>;");
        stringBuilder.append(getCorrectAnswer());
        stringBuilder.append("<br />");
        stringBuilder.append(justification);
        return stringBuilder.toString();
    }

    public String getPrefixImage() {
        return prefixImage;
    }

    public void setPrefixImage(String prefixImage) {
        this.prefixImage = prefixImage;
    }

    private void addAnswer(StringBuilder stringBuilder, String answer) {
        if (answer != null && !answer.isEmpty()) {
            stringBuilder.append("<li>").append(answer).append("</li>");
        }
    }
}
