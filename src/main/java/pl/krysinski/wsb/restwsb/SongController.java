package pl.krysinski.wsb.restwsb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("songs")
public class SongController {

    final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping
    List<Song> findAll() {
        return songRepository.findAll();
    }

    @GetMapping("{id}")
    ResponseEntity<?> find(@PathVariable Long id) {
        Song song = songRepository.find(id);

        if (song != null) {
            return ResponseEntity.ok().body(song);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Song song) throws URISyntaxException {
        Song savedSong = songRepository.create(song);
        return ResponseEntity.created(new URI("http://localhost:8080/songs" + song.getId())).body(savedSong);
    }

    @PutMapping("{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Song song){
        Song updatedSong = songRepository.update(id, song);

        if(updatedSong == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok().body(updatedSong);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        songRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
