package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository repository;

    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@PathVariable Long id){
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping("/artist/")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(artist));
    }

    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> customArtist(@RequestBody Artist artist, @PathVariable Long id){
        return ResponseEntity.of(repository.findById(id)
                .map(old -> {
                    old.setName(artist.getName());
                    return Optional.of(repository.save(old));
                })
                .orElse(Optional.empty())
        );
    }

    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id){
        if (repository.existsById(id))
            repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
