package pl.krysinski.wsb.restwsb;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SongRepository {

    Long count =2L;

    List<Song> songs = new LinkedList<>(Arrays.asList(
            new Song (1L, "BFF", "Pisarz miłości"),
            new Song (2L, "Gigi diaugistno","Another world"))
    );

    public List<Song> findAll() {
        return songs;
    }

    public Song find(Long id) {
        return songs.stream().filter(song -> song.getId().equals(id)).findFirst().orElse(null);
    }

    public Song create(Song song) {
        song.setId(++count);
        songs.add(song);
        return song;
    }

    public Song update(Long id, Song song) {
        Song songToUpdate = find(id);

        if (songToUpdate == null){
            return null;
        }

        songToUpdate.setTitle(song.getTitle());
        songToUpdate.setAuthor(song.getAuthor());

        return songToUpdate;
    }

    public void delete(Long id) {
        Song song = find(id);
        songs.remove(song);
    }
}
