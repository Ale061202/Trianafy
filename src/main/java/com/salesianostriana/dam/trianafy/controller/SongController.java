package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
