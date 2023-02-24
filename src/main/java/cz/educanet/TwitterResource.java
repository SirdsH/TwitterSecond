package cz.educanet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class TwitterResource {

    private Twitter twitterArr = null;

    public void createStock(String content, String author) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO twitter.tweets(content, author, likes, created_at, updated_at) VALUES (?, ?, 0, NOW(), NOW())"
                );

        ) {
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, author);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Twitter> getTwitter() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/superhero?user=root&password=");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM twitter.tweets");
        ) {
            ArrayList<Twitter> twitterArrayList = new ArrayList<>();

            while (resultSet.next()) {
                twitterArrayList.add(new Twitter(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getTimestamp(5).toLocalDateTime(), resultSet.getTimestamp(6).toLocalDateTime()));
            }

            return twitterArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTweet(int tweetID) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/stock_market?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM twitter.tweets WHERE tweet_id = ?"
                );

        ) {
            preparedStatement.setInt(1, tweetID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLike(int tweetID) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/twitter?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE twitter.tweets SET likes = likes + 1 WHERE tweet_id = ?"
                );

        ) {
            preparedStatement.setInt(1, tweetID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTweet(int tweetID, String content, String author) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/stock_market?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE twitter.tweets SET content = ?, author = ?, updated_at = NOW() WHERE tweet_id = ?"
                );

        ) {
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, tweetID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Twitter specificTweet(int tweetID) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/superhero?user=root&password=");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM twitter.tweets WHERE tweet_id = ?"
                )) {
            preparedStatement.setInt(1, tweetID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return new Twitter(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getTimestamp(5).toLocalDateTime(),
                        resultSet.getTimestamp(6).toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTweetOne(String content, String author) throws IOException {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        updateTweet(Integer.parseInt(id), content, author);
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public Twitter getSpecificTweet() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (twitterArr == null || twitterArr.getTweetID() != Integer.parseInt(id)) {
            twitterArr = specificTweet(Integer.parseInt(id));
        }
        return twitterArr;
    }


}
