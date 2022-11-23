package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongRepository repository;

    @GetMapping("/song/")
    public ResponseEntity<List<Song>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findbyId(@PathVariable Long id){
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping("/song/")
    public ResponseEntity<Song> createSong(@RequestBody Song song){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(song));
    }

    @PutMapping("/song/{id}")
    public ResponseEntity<Song> customSong(@RequestBody Song song, @PathVariable Long id){
        return ResponseEntity.of(repository.findById(id)
                .map(old -> {
                    old.setYear(song.getYear());
                    old.setTitle(song.getTitle());
                    old.setAlbum(song.getAlbum());
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
