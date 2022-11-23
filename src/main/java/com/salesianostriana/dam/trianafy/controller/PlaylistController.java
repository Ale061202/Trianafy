package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository repository;

    @GetMapping("/list")
    public ResponseEntity<List<Playlist>> findAll(){
        return  ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findById(@PathVariable Long id){
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping("/list")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(playlist));
    }

    @PutMapping("/list/{id}")
    public ResponseEntity<Playlist> customPlaylist(@RequestBody Playlist playlist, @PathVariable Long id){
        return ResponseEntity.of(repository.findById(id)
                .map(old -> {
                    old.setName(playlist.getName());
                    old.setDescription(playlist.getDescription());
                    return Optional.of(repository.save(old));
                }).orElse(Optional.empty())
        );
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deletePlaylist(@PathVariable Long id){
        if (repository.existsById(id))
            repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
