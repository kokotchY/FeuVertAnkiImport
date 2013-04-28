package be.kokotchy.tools;

import be.kokotchy.tools.model.Question;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main class of the application
 * User: kokotchY
 * Date: 27/04/13
 * Time: 11:42
 */
public class App
{

    public static final String DB_PATH = "C:\\feuvert\\www\\db\\feuvert_contenu.sqlite";
    public static final String SOURCE_IMAGE_PATH = "E:\\media\\jpg_400";
    public static final String TARGET_IMAGE_PATH = "C:\\Users\\kokotchY2\\Documents\\Anki\\User 1\\collection.media";
    public static final String PREFIX_IMAGE = "test_";
    public static final String OUTPUT_FILENAME = "test.txt";

    public static void main( String[] args )
    {
        new App();
    }

    public App() {
        File sourceImagesLibrary = new File(SOURCE_IMAGE_PATH);
        File targetImageLibrary = new File(TARGET_IMAGE_PATH);
        Connection connection = null;
        PrintWriter writer = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+ DB_PATH);
            Statement statement = connection.createStatement();

            String sql = "SELECT q.IDQuestion,q.Question_FR,q.RepA_FR,q.RepB_FR,q.RepC_FR,q.Justification_FR,q.correct,q.FauteGrave,m.FileMedia_Numerique_FR" +
                    " FROM web_question q, web_photographie p, web_mediafile m " +
                    " WHERE q.IDPhoto = p.IDPhoto and p.Ref_Image = m.IDMedia";
            ResultSet resultSet = statement.executeQuery(sql);
            File file = new File(OUTPUT_FILENAME);
            writer = new PrintWriter(new FileWriter(file));
            while (resultSet.next()) {
                Question question = new Question();
                question.setPrefixImage(PREFIX_IMAGE);
                question.setId(resultSet.getInt(Constants.ID_QUESTION));
                question.setQuestion(resultSet.getString(Constants.QUESTION_FR));
                question.setAnswerA(resultSet.getString(Constants.REP_A_FR).trim());
                question.setAnswerB(resultSet.getString(Constants.REP_B_FR).trim());
                question.setAnswerC(resultSet.getString(Constants.REP_C_FR).trim());
                question.setJustification(resultSet.getString(Constants.JUSTIFICATION_FR));
                question.setImage(resultSet.getString(Constants.FILE_MEDIA_NUMERIQUE_FR).trim());
                question.setCorrect(resultSet.getString(Constants.CORRECT));
                question.setSerious(Constants.FAUTE_GRAVE_OUI.equals(resultSet.getString(Constants.FAUTE_GRAVE)));
                writer.println(question.generateQuestion());
                copyImage(new File(sourceImagesLibrary, question.getImage()), new File(targetImageLibrary, PREFIX_IMAGE + question.getImage()));
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void copyImage(File sourceFile, File destFile) {
        if (sourceFile.exists()) {
            try {
                FileUtils.copyFile(sourceFile, destFile);
            } catch (IOException e) {
                System.err.println("Error when trying to copy the file");
            }
        }
    }
}

