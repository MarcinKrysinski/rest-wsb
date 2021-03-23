package pl.krysinski.wsb.restwsb;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("songs")
public class SongHATEOASCotroller {

    final SongRepository songRepository;

    public SongHATEOASCotroller(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping
    CollectionModel<EntityModel<Song>> findAll() {
        List<EntityModel<Song>> songs = songRepository.findAll().stream()
                .map(m->EntityModel.of(m, linkTo(methodOn(SongHATEOASCotroller.class).find(m.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(songs, linkTo(methodOn(SongHATEOASCotroller.class).findAll()).withSelfRel());
    }

    @GetMapping("{id}")
    ResponseEntity<?> find(@PathVariable Long id) {
        Song song = songRepository.find(id);

        if (song != null) {
            EntityModel<Song> songModel = EntityModel.of(song,
                    linkTo(methodOn(SongHATEOASCotroller.class).find(id)).withSelfRel(),
                    linkTo(methodOn(SongHATEOASCotroller.class).findAll()).withRel("songs"));
            return ResponseEntity.ok().body(songModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Song song) {
        Song savedSong = songRepository.create(song);
        if(savedSong != null){
            EntityModel<Song> songModel = EntityModel.of(song,
                    linkTo(methodOn(SongHATEOASCotroller.class).find(savedSong.getId())).withSelfRel());

            return ResponseEntity.created(songModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(songModel);

        }else{
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Song song){
        Song updatedSong = songRepository.update(id, song);

        if(updatedSong == null){
            return ResponseEntity.notFound().build();
        }else {
            EntityModel<Song> updatedSongModel = EntityModel.of(updatedSong,
                    linkTo(methodOn(SongHATEOASCotroller.class).find(id)).withSelfRel());
            return ResponseEntity.ok().body(updatedSongModel);
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        songRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
