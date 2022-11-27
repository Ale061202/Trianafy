package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongRepository repository;
    private final SongDtoConverter dtoConverter;

    private final ArtistRepository artistRepository;

    @GetMapping("/song/")
    public ResponseEntity<List<Song>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findbyId(@PathVariable Long id){
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping("/song/")
    public ResponseEntity<Song> createSong(@RequestBody CreateSongDto cs){
        if (cs.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Song song = dtoConverter.createSongDtoToSong(cs);
        Artist artist = artistRepository.findById(cs.getArtistId()).orElse(null);

        song.setArtist(artist);

        song = repository.save(song);

        return ResponseEntity.status(HttpStatus.CREATED).body(song);
    }

    @PutMapping("/song/{id}")
    public ResponseEntity<Song> customSong(@RequestBody CreateSongDto cs, @PathVariable Long id){
        if (cs.getArtistId() == null){
            return ResponseEntity.badRequest().build();
        }

        Song song = dtoConverter.createSongDtoToSong(cs);
        Artist artist = artistRepository.findById(cs.getArtistId()).orElse(null);

        return ResponseEntity.of(repository.findById(id)
                .map(old -> {
                    old.setYear(song.getYear());
                    old.setTitle(song.getTitle());
                    old.setAlbum(song.getAlbum());
                    old.setArtist(artist);
                    return Optional.of(repository.save(old));
                })
                .orElse(Optional.empty())
        );
    }

    @DeleteMapping("/song/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id){
        if(repository.existsById(id))
            repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
