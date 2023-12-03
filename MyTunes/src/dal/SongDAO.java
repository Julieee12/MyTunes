package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDAO {
    private final databaseConnector dbConnection = new databaseConnector();


    @Override
    public void createSong(Song s) {
        try(Connection con = dbConnection.getConnection()) {
            String sql = "INSERT INTO Songs (Title, Artist, Category, Time, Path)" + " VALUES (?,?,?,?,?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, s.getSongTitle());
            pt.setString(2, s.getArtist());
            pt.setString(3, s.getCategory());
            pt.setDouble(4, s.getDuration());
            pt.setString(5, s.getFilePath());
            pt.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Song getSong(int id) {

        try (Connection con = dbConnection.getConnection()) {
            String sql = "SELECT * FROM Songs WHERE ID=?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setInt(1, id);
            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                Double time = rs.getDouble("Time");
                String path = rs.getString("Path");

                return new Song(artist, title, time, category, path, id);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Song> getAllSongs() throws SQLException {
        List <Song> songs = new ArrayList<>();
        try(Connection con = dbConnection.getConnection()){
            String sql = "SELECT * FROM Songs";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){

                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                Double time = rs.getDouble("Time");
                int id = rs.getInt("ID");
                String path = rs.getString("Path");

                Song s = new Song(artist,title,time,category,path,id);
                songs.add(s);

            }
            return songs;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void deleteSongs(ObservableList<Song> songsToDelete) throws SQLException {
        try (Connection con = dbConnection.getConnection()) {
            String sql = "DELETE FROM Songs WHERE ID=?";
            try (PreparedStatement pt = con.prepareStatement(sql)) {
                for (Song song : songsToDelete) {
                    int id = song.getId();
                    pt.setInt(1, id);
                    pt.addBatch();
                }
                pt.clearParameters();
                pt.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
